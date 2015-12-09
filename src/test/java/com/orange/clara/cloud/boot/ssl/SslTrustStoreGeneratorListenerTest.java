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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.test.OutputCapture;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

/**
 * Created by sbortolussi on 09/12/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class SslTrustStoreGeneratorListenerTest {

    @Mock
    private ApplicationStartedEvent event;

    @Mock
    private SslTrustStoreGeneratorListener.PropertyResolver propertyResolver;

    private SslTrustStoreGeneratorListener sslTrustStoreGeneratorListener;

    @Mock
    TrustStoreGenerator keyStoreGenerator;

    @Rule
    public OutputCapture capture = new OutputCapture();


    @Before
    public void setup() throws IllegalArgumentException, IllegalAccessException {
        sslTrustStoreGeneratorListener = new SslTrustStoreGeneratorListener();
        Field envField = ReflectionUtils.findField(SslTrustStoreGeneratorListener.class, "propertyResolver");
        ReflectionUtils.makeAccessible(envField);
        ReflectionUtils.setField(envField, sslTrustStoreGeneratorListener, propertyResolver);
        Field sslCertTrusterField = ReflectionUtils.findField(SslTrustStoreGeneratorListener.class,
                "keyStoreGenerator");
        ReflectionUtils.makeAccessible(sslCertTrusterField);
        ReflectionUtils.setField(sslCertTrusterField, sslTrustStoreGeneratorListener, keyStoreGenerator);
    }

    @Test
    public void should_do_no_changes_if_no_certificate_is_set_in_TRUSTSTORE_SYSTEM_property() throws Exception {
        Mockito.when(propertyResolver.getSystemProperty("TRUSTSTORE")).thenReturn("");
        sslTrustStoreGeneratorListener.onApplicationEvent(event);
        Mockito.verifyZeroInteractions(keyStoreGenerator);
        Assert.assertNull(System.getProperty(SslTrustStoreGeneratorListener.SSL_TRUST_STORE_SYSTEM_PROPERTY));
        Assert.assertNull(System.getProperty(SslTrustStoreGeneratorListener.SSL_TRUST_STORE_PASSWORD_SYSTEM_PROPERTY));
    }

    @Test
    public void should_log_warning_if_no_certificate_is_set_in_TRUSTSTORE_SYSTEM_property() throws Exception {
        Mockito.when(propertyResolver.getSystemProperty("TRUSTSTORE")).thenReturn("");
        sslTrustStoreGeneratorListener.onApplicationEvent(event);
        assertThat(capture.toString(), containsString("No additional CA certificate has been defined using TRUSTSTORE system property"));
    }

    @Test(expected = IllegalStateException.class)
    public void should_fail_if_no_TRUSTSTORE_SYSTEM_property_exists() throws Exception {
        SslTrustStoreGeneratorListener.PropertyResolver resolver = sslTrustStoreGeneratorListener.new PropertyResolver();
        resolver.getSystemProperty("dummy");
    }
}