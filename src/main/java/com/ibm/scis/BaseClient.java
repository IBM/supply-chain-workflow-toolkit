/*
 * (C) Copyright 2022 IBM Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibm.scis;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;

/** Base Client */
public class BaseClient {
  private static final Logger logger = Logger.getLogger(BaseClient.class.getName());
  protected static CloseableHttpClient httpClient;
  protected static RequestConfig config;

  protected static final String X_IBM_CLIENT_ID = "X-IBM-Client-Id";
  protected static final String X_IBM_CLIENT_SECRET = "X-IBM-Client-Secret";
  protected static final String IBM_USERNAME = "username";

  protected static final String CONTENT_TYPE = "content-type";
  protected static final String CONTENT_TYPE_JSON = "application/json";

  protected static final String TLS_VERSION = "TLSv1.2";
  protected static final int CONNECT_TIMEOUT = 20 * 1000;
  protected static final int CONNECT_REQUEST_TIMEOUT = 60 * 1000;
  protected static final int SOCKET_TIMEOUT = 60 * 1000;

  static {
    System.setProperty("https.protocols", TLS_VERSION);
    config =
        RequestConfig.custom()
            .setConnectTimeout(CONNECT_TIMEOUT)
            .setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT)
            .setSocketTimeout(SOCKET_TIMEOUT)
            .build();
    SSLContext ctx = null;
    try {
      ctx = SSLContexts.custom().setProtocol(TLS_VERSION).build();
    } catch (KeyManagementException | NoSuchAlgorithmException e) {
      logger.log(Level.SEVERE, e.getMessage());
    }
    httpClient =
        HttpClientBuilder.create().setDefaultRequestConfig(config).setSSLContext(ctx).build();
  }
}
