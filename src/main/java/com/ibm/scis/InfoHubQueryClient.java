/*
 * (C) Copyright 2021 IBM Corporation.
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

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

/** InfoHub business data query service */
public class InfoHubQueryClient extends BaseClient {
  private static final Logger logger = Logger.getLogger(InfoHubQueryClient.class.getName());
  private static final ConcurrentMap<String, String> HEADERS = new ConcurrentHashMap<>();

  public InfoHubQueryClient(CloseableHttpClient client) {
    httpClient = client;
  }

  /** For BAW initializing this class. */
  public InfoHubQueryClient() {
    super();
  }

  /**
   * Query Sterling InfoHub business object data
   *
   * @param endpoint Fully specified URL points to InfoHub query API
   * @param dataQuery Query criteria
   * @param clientId Sterling saascore platform client Id
   * @param clientSecret Sterling saascore platform client secret
   * @return Query results in String format
   * @throws IOException
   */
  public String executeQuery(
      String endpoint, String dataQuery, String clientId, String clientSecret) throws IOException {
    logger.log(Level.INFO, dataQuery);
    HttpPost httpPost = buildHttpPost(endpoint, dataQuery, clientId, clientSecret);

    try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
      InputStream contentStream = response.getEntity().getContent();
      String responseString = Utils.inputStreamToString(contentStream);
      return responseString;
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      throw e;
    }
  }

  private HttpPost buildHttpPost(
      String endpoint, String input, String clientId, String clientSecret) {
    HttpPost httpPost = new HttpPost(endpoint);
    HEADERS.put(X_IBM_CLIENT_ID, clientId);
    HEADERS.put(X_TENANT_ID, clientId);
    HEADERS.put(X_IBM_CLIENT_SECRET, clientSecret);
    HEADERS.forEach(httpPost::addHeader);
    httpPost.setEntity(new StringEntity(input, ContentType.APPLICATION_JSON));
    return httpPost;
  }
}
