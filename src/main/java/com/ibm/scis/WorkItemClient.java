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

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

/** A client to create or update work item definition in Sterling InfoHub system */
public class WorkItemClient extends BaseClient {
  private static final Logger logger = Logger.getLogger(WorkItemClient.class.getName());

  public WorkItemClient(CloseableHttpClient client) {
    httpClient = client;
  }

  /** For BAW initializing this class. */
  public WorkItemClient() {
    super();
  }

  /**
   * Create a work item via Sterling InfoHub workitem API
   *
   * @param url Fully specified URL points to InfoHub workitem API
   * @param clientId Sterling saascore platform client Id
   * @param clientSecret Sterling saascore platform client secret
   * @param workItem Metadata of new work item
   * @return Work item Id
   */
  public String createWorkItem(String url, String clientId, String clientSecret, String workItem) {
    HttpPost httpPost = new HttpPost(url);
    try {
      logger.log(Level.INFO, workItem);
      buildHeaders(clientId, clientSecret, httpPost);
      setPostBody(httpPost, workItem);
    } catch (UnsupportedEncodingException e) {
      return buildErrorResponse(e);
    }
    return sendRequest(httpPost);
  }

  /**
   * Update work item via Sterling InfoHub workitem API
   *
   * @param url Fully specified URL points to InfoHub workitem API
   * @param workItemId Work item Id
   * @param clientId Sterling saascore platform client Id
   * @param clientSecret Sterling saascore platform client secret
   * @param workItemPartial Partial metadata of work item for update
   * @return Work item Id
   */
  public String updateWorkItem(
      String url, String workItemId, String clientId, String clientSecret, String workItemPartial) {
    String endpoint = String.format("%s/%s", url, workItemId);
    HttpPut httpPut = new HttpPut(endpoint);
    try {
      logger.log(Level.INFO, workItemPartial);
      buildHeaders(clientId, clientSecret, httpPut);
      setPostBody(httpPut, workItemPartial);
    } catch (UnsupportedEncodingException e) {
      return buildErrorResponse(e);
    }
    return sendRequest(httpPut);
  }

  private Map<String, String> buildHeaders(
      String clientId, String clientSecret, HttpUriRequest httpRequest) {
    ConcurrentMap<String, String> headers = new ConcurrentHashMap<>();
    headers.put(X_IBM_CLIENT_ID, clientId);
    headers.put(X_IBM_CLIENT_SECRET, clientSecret);
    headers.forEach(httpRequest::addHeader);
    return headers;
  }

  private void setPostBody(HttpEntityEnclosingRequest httpRequest, String workItem)
      throws UnsupportedEncodingException {
    StringEntity params = new StringEntity(workItem);
    httpRequest.addHeader(CONTENT_TYPE, CONTENT_TYPE_JSON);
    httpRequest.setEntity(params);
  }

  private String sendRequest(HttpUriRequest httpRequest) {
    try (CloseableHttpResponse response = httpClient.execute(httpRequest)) {
      InputStream contentStream = response.getEntity().getContent();
      String result = Utils.inputStreamToString(contentStream);
      logger.info(result);
      return result;
    } catch (Exception e) {
      logger.log(Level.SEVERE, e.getMessage());
      return buildErrorResponse(e);
    }
  }

  private String buildErrorResponse(Exception e) {
    String errorMsg = "{\"error\":\"" + e.getMessage() + "\"}";
    return errorMsg;
  }
}
