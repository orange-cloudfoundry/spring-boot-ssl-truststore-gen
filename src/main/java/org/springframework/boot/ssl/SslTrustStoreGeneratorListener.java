/*
 * Copyright (C) 2015 Orange
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.springframework.boot.ssl;

import com.orange.clara.cloud.truststore.TrustStoreGenerator;
import com.orange.clara.cloud.truststore.TrustStoreProperty;
import com.orange.clara.cloud.truststore.TrustStorePropertyReader;
import com.orange.clara.cloud.truststore.TrustStoreStorePropertyJsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

/**
 * Created by sbortolussi on 21/10/2015.
 */
public class SslTrustStoreGeneratorListener implements
        ApplicationListener<ApplicationPreparedEvent>, Ordered {

    public static final String KEYSTORE_PROPERTY_NAME = "KEYSTORE";

    private static Logger LOGGER = LoggerFactory.getLogger(SslTrustStoreGeneratorListener.class);

    private int order = HIGHEST_PRECEDENCE;

    public SslTrustStoreGeneratorListener() {
    }

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent applicationPreparedEvent) {
        try {
            TrustStorePropertyReader keyStorePropertyReader = new TrustStoreStorePropertyJsonReader();
            final TrustStoreProperty keyStoreProperty = keyStorePropertyReader.read(getSystemProperty(KEYSTORE_PROPERTY_NAME));
            LOGGER.info("Generating truststore file");
            TrustStoreGenerator keyStoreGenerator = new TrustStoreGenerator();
            keyStoreGenerator.generate(keyStoreProperty);
        } catch (Exception e) {
            String message = "Cannot create truststore.";
            LOGGER.error(message);
            throw new IllegalStateException(message, e);
        }
        LOGGER.info("truststore generated");

    }

    private String getSystemProperty(String propertyName) {
        String keystore = (System.getProperty(propertyName) != null ? System.getProperty(propertyName) : System.getenv(propertyName)) ;
        if (keystore == null){
            throw new IllegalStateException("System property '"+propertyName+"' has not been defined.");
        }
        return keystore;
    }

    @Override
    public int getOrder() {
        return order;
    }

}
