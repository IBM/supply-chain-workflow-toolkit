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

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
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

  protected static final String AUTHORIZATION = "Authorization";
  protected static final String IBM_CSRF_TOKEN = "IBM-CSRF-TOKEN";
  protected static final String BPM_CSRF_TOKEN = "bpmcsrftoken";

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

  protected Map<String, String> buildBasicAuthHeader(
      String userName, String password, HttpUriRequest httpRequest) {
    Map<String, String> headers = new ConcurrentHashMap<>();
    final Base64.Encoder encoder = Base64.getEncoder();
    String basicToken =
        "Basic "
            + encoder.encodeToString((userName + ':' + password).getBytes(StandardCharsets.UTF_8));
    headers.put(BaseClient.AUTHORIZATION, basicToken);
    headers.forEach(httpRequest::addHeader);
    return headers;
  }

  protected Map<String, String> buildBasicAuthHeaderWithIBMCsrfToken(
      String userName, String password, String csrfToken, HttpUriRequest httpRequest) {
    Map<String, String> headers = buildBasicAuthHeader(userName, password, httpRequest);
    httpRequest.addHeader(IBM_CSRF_TOKEN, csrfToken);
    return headers;
  }

  protected Map<String, String> buildBasicAuthHeaderWithBPMCsrfToken(
      String userName, String password, String csrfToken, HttpUriRequest httpRequest) {
    Map<String, String> headers = buildBasicAuthHeader(userName, password, httpRequest);
    httpRequest.addHeader(BPM_CSRF_TOKEN, csrfToken);
    return headers;
  }

  protected String processHttpRequest(HttpRequestBase request) {
    try (CloseableHttpResponse response = httpClient.execute(request)) {
      InputStream contentStream = response.getEntity().getContent();
      String result = Utils.inputStreamToString(contentStream);
      logger.info(result);
      return result;
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      return "{\"error\":\"" + e.getMessage() + "\"}";
    }
  }

  /**
   * @param csrfTokenUrl CSRF TOKEN URL
   * @param userName BAW functional user name
   * @param password BAW functional password
   * @param lifeTime requested life time (seconds), e.g. 7200
   * @return csrf token
   */
  protected String getCSRFToken(
      String csrfTokenUrl, String userName, String password, Integer lifeTime) {
    HttpPost httpPost = new HttpPost(csrfTokenUrl);
    buildBasicAuthHeader(userName, password, httpPost);
    httpPost.setEntity(
        new StringEntity(
            "{\"requested_lifetime\": " + lifeTime + "}", ContentType.APPLICATION_JSON));
    return processHttpRequest(httpPost);
  }
}
