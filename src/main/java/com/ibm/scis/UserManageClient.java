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

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

public class UserManageClient extends BaseClient {
  private static final Logger logger = Logger.getLogger(UserManageClient.class.getName());

  public UserManageClient(CloseableHttpClient client) {
    httpClient = client;
  }

  /** For BAW initializing this class. */
  public UserManageClient() {
    super();
  }

  public String getCSRFToken(
      String csrfTokenUrl, String userName, String password, Integer lifeTime) {
    return super.getCSRFToken(csrfTokenUrl, userName, password, lifeTime);
  }

  public String addNewUser(
      String url, String bawUserName, String bawPassword, String token, String userInfo) {
    String endpoint =
        String.format("%s?%s&%s", url, "skip_email=true", "activate_automatically=true");

    HttpPost httpPost = new HttpPost(endpoint);
    logger.log(Level.INFO, userInfo);
    buildBasicAuthHeaderWithIBMCsrfToken(bawUserName, bawPassword, token, httpPost);
    httpPost.setEntity(new StringEntity(userInfo, ContentType.APPLICATION_JSON));
    return processHttpRequest(httpPost);
  }

  public String updateExistingUser(
      String url,
      String bawUserName,
      String bawPassword,
      String token,
      String userId,
      String updatedUserInfo) {
    String endpoint = String.format("%s/%s?%s", url, userId, "update_mode=merge");
    HttpPut httpPut = new HttpPut(endpoint);
    logger.log(Level.INFO, updatedUserInfo);
    buildBasicAuthHeaderWithIBMCsrfToken(bawUserName, bawPassword, token, httpPut);
    httpPut.setEntity(new StringEntity(updatedUserInfo, ContentType.APPLICATION_JSON));
    return processHttpRequest(httpPut);
  }

  public String checkUserByUserId(
      String url, String bawUserName, String bawPassword, String token, String userId) {
    String endpoint =
        String.format("%s?%s&%s", url, "optional_parts=details,groups", "search_term=" + userId);
    HttpGet httpGet = new HttpGet(endpoint);
    logger.log(Level.INFO, endpoint);
    buildBasicAuthHeaderWithIBMCsrfToken(bawUserName, bawPassword, token, httpGet);
    return processHttpRequest(httpGet);
  }
}
