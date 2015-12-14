/*
 *
 *  * Copyright (C) 2015 Orange
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 *
 */

package com.orange.clara.cloud.boot.ssl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

/**
 * Provide spring boot application with a java truststore composed from :
 * <ul>
 * <li>default truststore CA certificates</li>
 * <li>additional CA certificates extracted from <i>KEYSTORE</i> system property</li>
 * </ul>
 * The java trustore will be accessible through <i>javax.net.ssl.trustStore</i> and <i>javax.net.ssl.trustStorePassword</i> system properties.
 *
 * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/jsse/JSSERefGuide.html">JSSE Reference Guide</a>
 * <p>
 * <P>Created by sbortolussi on 21/10/2015.
 */
public class SslTrustStoreGeneratorListener implements
        ApplicationListener<ApplicationStartedEvent>, Ordered {

    public static final String SSL_TRUST_STORE_SYSTEM_PROPERTY = "javax.net.ssl.trustStore";
    public static final String SSL_TRUST_STORE_PASSWORD_SYSTEM_PROPERTY = "javax.net.ssl.trustStorePassword";

    public static final String TRUSTED_CA_CERTIFICATE_PROPERTY_NAME = "TRUSTED_CA_CERTIFICATE";

    private static Logger LOGGER = LoggerFactory.getLogger(SslTrustStoreGeneratorListener.class);

    private int order = HIGHEST_PRECEDENCE;

    private PropertyResolver propertyResolver = new PropertyResolver();

    private DefaultTrustStoreAppender trustStoreAppender = new DefaultTrustStoreAppender();

    public SslTrustStoreGeneratorListener() {
    }

    public void onApplicationEvent(ApplicationStartedEvent event) {
        try {
            final String certificate = propertyResolver.getSystemProperty(TRUSTED_CA_CERTIFICATE_PROPERTY_NAME);
            if (!"".equals(certificate)) {
                LOGGER.info("Following additional CA Certificate has been defined in {} system property : {}", TRUSTED_CA_CERTIFICATE_PROPERTY_NAME, certificate);
                final TrustStoreInfo trustStoreInfo = trustStoreAppender.append(CertificateFactory.newInstance(certificate));
                System.setProperty(SSL_TRUST_STORE_SYSTEM_PROPERTY, trustStoreInfo.getTrustStorefFile().getAbsolutePath());
                LOGGER.info("Setting {} system property to {}", SSL_TRUST_STORE_SYSTEM_PROPERTY, trustStoreInfo.getTrustStorefFile().getAbsolutePath());
                System.setProperty(SSL_TRUST_STORE_PASSWORD_SYSTEM_PROPERTY, trustStoreInfo.getPassword());
                LOGGER.info("Setting {} system property to {}", SSL_TRUST_STORE_PASSWORD_SYSTEM_PROPERTY, trustStoreInfo.getPassword());
            } else {
                LOGGER.warn("No additional CA certificate has been defined using {} system property", TRUSTED_CA_CERTIFICATE_PROPERTY_NAME);
            }
        } catch (Exception e) {
            String message = "Cannot create truststore.";
            LOGGER.error(message);
            throw new IllegalStateException(message, e);
        }
        LOGGER.info("truststore generated");

    }

    public int getOrder() {
        return order;
    }

    class PropertyResolver {
        public String getSystemProperty(String propertyName) {
            String keystore = (System.getProperty(propertyName) != null ? System.getProperty(propertyName) : System.getenv(propertyName));
            if (keystore == null) {
                throw new IllegalStateException("System property '" + propertyName + "' has not been defined.");
            }
            return keystore;
        }
    }

}


