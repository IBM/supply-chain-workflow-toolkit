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
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

public class UserGroupSyncClient extends BaseClient {
  private static final Logger logger = Logger.getLogger(UserGroupSyncClient.class.getName());

  public UserGroupSyncClient(CloseableHttpClient client) {
    httpClient = client;
  }

  public String getBPMCSRFToken(
      String csrfTokenUrl, String userName, String password, Integer lifeTime) {
    return super.getCSRFToken(csrfTokenUrl, userName, password, lifeTime);
  }

  /** For BAW initializing this class. */
  public UserGroupSyncClient() {
    super();
  }

  public String usersSync(
      String usersSyncURL,
      String bawUserName,
      String bawPassword,
      String token,
      String usersArray) {
    String endpoint =
        String.format("%s?%s&%s", usersSyncURL, "add_to_db=true", "sync_user_state=true");

    HttpPost httpPost = new HttpPost(endpoint);
    logger.log(Level.INFO, usersArray);
    buildBasicAuthHeaderWithBPMCsrfToken(bawUserName, bawPassword, token, httpPost);
    httpPost.setEntity(new StringEntity(usersArray, ContentType.APPLICATION_JSON));
    return processHttpRequest(httpPost);
  }

  public String groupsSync(
      String usersSyncURL,
      String bawUserName,
      String bawPassword,
      String token,
      String groupsArray) {
    String endpoint =
        String.format("%s?%s&%s", usersSyncURL, "add_users_to_db=true", "sync_group_state=true");

    HttpPost httpPost = new HttpPost(endpoint);
    logger.log(Level.INFO, groupsArray);
    buildBasicAuthHeaderWithBPMCsrfToken(bawUserName, bawPassword, token, httpPost);
    httpPost.setEntity(new StringEntity(groupsArray, ContentType.APPLICATION_JSON));
    return processHttpRequest(httpPost);
  }

  public String checkSyncResult(String url, String bawUserName, String bawPassword, String token) {

    HttpGet httpGet = new HttpGet(url);
    logger.log(Level.INFO, url);
    buildBasicAuthHeaderWithBPMCsrfToken(bawUserName, bawPassword, token, httpGet);
    return processHttpRequest(httpGet);
  }
}
