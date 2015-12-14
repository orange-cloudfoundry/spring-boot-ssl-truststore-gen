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

import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.UUID;


/**
 * Created by sbortolussi on 28/10/2015.
 * <p>
 * Credits to https://github.com/cloudfoundry/cf-java-client/blob/master/cloudfoundry-client-spring/src/main/java/org/cloudfoundry/client/spring/util/CertificateCollectingSslCertificateTruster.java
 */
public class DefaultTrustStoreAppender {

    public static final String TRUSTSTORE_FILENAME = "truststore";

    private static Logger LOGGER = LoggerFactory.getLogger(DefaultTrustStoreAppender.class);

    /**
     * Create new java truststore from default truststore. Add given CA certificate to it.
     *
     * @param certificate
     * @return TrustStoreInfo
     * @see <a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/jsse/JSSERefGuide.html">JSSE Reference Guide</a>
     */
    public TrustStoreInfo append(Certificate certificate) {
        try {
            X509TrustManager trustManager = getDefaultTrustManager();

            try {
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null);

                if (trustManager != null) {
                    for (X509Certificate cert : trustManager.getAcceptedIssuers()) {
                        trustStore.setCertificateEntry(UUID.randomUUID().toString(), cert);
                        LOGGER.debug("adding existing certificate to truststore {}", cert);
                    }
                }

                if (certificate != null) {
                    trustStore.setCertificateEntry(UUID.randomUUID().toString(), certificate);
                    LOGGER.debug("adding new certificate to truststore {}", certificate);
                }

                String password = UUID.randomUUID().toString();
                File trustStoreOutputFile = File.createTempFile(TRUSTSTORE_FILENAME, null);
                trustStoreOutputFile.deleteOnExit();
                trustStore.store(new FileOutputStream(trustStoreOutputFile), password.toCharArray());

                return new TrustStoreInfo(trustStoreOutputFile, password);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private X509TrustManager getDefaultTrustManager() throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init((KeyStore) null);
        return (X509TrustManager) factory.getTrustManagers()[0];
    }

}
