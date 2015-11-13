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

import com.orange.clara.cloud.truststore.TrustStoreGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootSslApplicationTests.SpringBootSslApplication.class)
@WebIntegrationTest
public class SpringBootSslApplicationTests {

    public static final String SSL_TRUST_STORE_SYSTEM_PROPERTY = "javax.net.ssl.trustStore";
    public static final String SSL_TRUST_STORE_PASSWORD_SYSTEM_PROPERTY = "javax.net.ssl.trustStorePassword";

	@Test
	public void truststore_system_properties_should_be_set() {
        Assert.assertNotNull(System.getProperty(SSL_TRUST_STORE_SYSTEM_PROPERTY));
        Assert.assertNotNull(System.getProperty(SSL_TRUST_STORE_PASSWORD_SYSTEM_PROPERTY));
	}

    @SpringBootApplication
    public static class SpringBootSslApplication {

        public static void main(String[] args) {
            SpringApplication.run(SpringBootSslApplication.class, args);
        }
    }
}
