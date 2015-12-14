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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.security.cert.Certificate;

/**
 * Created by sbortolussi on 22/10/2015.
 */
public class CertificateFactory {

    public static final String X_509_CERTIFICATE = "X.509";

    public static Certificate newInstance(String certificate) {
        if (certificate == null || "".equals(certificate))
            throw new IllegalArgumentException("Invalid certificate. Certificate should have text.");

        try (ByteArrayInputStream bais = new ByteArrayInputStream(certificate.getBytes()); BufferedInputStream bis = new BufferedInputStream(bais)) {
            java.security.cert.CertificateFactory cf = java.security.cert.CertificateFactory.getInstance(X_509_CERTIFICATE);
            Certificate cert = null;
            while (bis.available() > 0) {
                cert = cf.generateCertificate(bis);
            }
            return cert;
        } catch (Exception e) {
            String message = String
                    .format("Cannot create certificate.", e);
            throw new IllegalStateException(message, e);
        }
    }

}
