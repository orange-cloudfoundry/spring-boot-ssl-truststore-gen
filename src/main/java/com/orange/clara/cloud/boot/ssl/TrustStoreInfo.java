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

import java.io.File;

/**
 *
 * Created by sbortolussi on 12/11/2015.
 */
public class TrustStoreInfo {

    private File trustStoreFile;

    private String password;

    public TrustStoreInfo(File file, String password) {
        setPassword(password);
        setTrustStoreFile(file);
    }

    private void setTrustStoreFile(File file) {
        if (file == null)
            throw new IllegalArgumentException("Unable to create truststore info. trustore file should be set");
        this.trustStoreFile = file;
    }

    private void setPassword(String password) {
        if (password == null || "".equals(password))
            throw new IllegalArgumentException("Unable to create truststore info. password should be set");
        this.password = password;
    }


    public File getTrustStorefFile() {
        return trustStoreFile;
    }

    public String getPassword() {
        return password;
    }



}
