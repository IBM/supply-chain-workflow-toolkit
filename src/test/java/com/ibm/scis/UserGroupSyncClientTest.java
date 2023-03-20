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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserGroupSyncClientTest {
  @Mock CloseableHttpClient mockHttpClient;

  @Mock CloseableHttpResponse mockHttpResponse;

  @Mock HttpEntity mockEntity;

  @Test
  public void shouldGetBPMCSRFToken() throws IOException {
    InputStream mockStream =
        new ByteArrayInputStream("{\"result\":\"csrf_token\", \"expiration\":7200}".getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenReturn(mockEntity);
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    Integer testLifeTime = 200;
    String result =
        new UserGroupSyncClient(mockHttpClient)
            .getBPMCSRFToken("testBAWUrl", "testUsername", "testPassword", testLifeTime);
    assertEquals("{\"result\":\"csrf_token\", \"expiration\":7200}", result);
  }

  @Test
  public void shouldReturnErrorWhenGetBPMCSRFToken() throws IOException {
    InputStream mockStream =
        new ByteArrayInputStream("{\"error\":\"failed to get BPM csrf token\"}".getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenThrow(new NullPointerException("Error occurred"));
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    Integer testLifeTime = 200;
    String result =
        new UserGroupSyncClient(mockHttpClient)
            .getBPMCSRFToken("testBAWUrl", "testUsername", "testPassword", testLifeTime);
    assertEquals("{\"error\":\"Error occurred\"}", result);
  }

  @Test
  public void shouldSyncUsers() throws IOException {
    InputStream mockStream =
        new ByteArrayInputStream(
            "{\"result\":\"Your request to synchronize the specified users was submitted.\"}"
                .getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenReturn(mockEntity);
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    String testUserArray = "[\"foo\", \"bar\"]";
    String token = "mockToken";
    String result =
        new UserGroupSyncClient(mockHttpClient)
            .usersSync("testUrl", "testUsername", "testPassword", token, testUserArray);
    assertEquals(
        "{\"result\":\"Your request to synchronize the specified users was submitted.\"}", result);
  }

  @Test
  public void shouldReturnErrorWhenSyncUsers() throws IOException {
    InputStream mockStream =
        new ByteArrayInputStream("{\"error\":\"failed to sync user\"}".getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenThrow(new NullPointerException("Error occurred"));
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    String testUserArray = "[\"foo\", \"bar\"]";
    String token = "mockToken";
    String result =
        new UserGroupSyncClient(mockHttpClient)
            .usersSync("testUrl", "testUsername", "testPassword", token, testUserArray);
    assertEquals("{\"error\":\"Error occurred\"}", result);
  }

  @Test
  public void shouldSyncGroups() throws IOException {
    InputStream mockStream =
        new ByteArrayInputStream(
            "{\"result\":\"Your request to synchronize the specified groups was submitted.\"}"
                .getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenReturn(mockEntity);
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    String testGroupArray = "[\"foo\", \"bar\"]";
    String token = "mockToken";
    String result =
        new UserGroupSyncClient(mockHttpClient)
            .groupsSync("testUrl", "testUsername", "testPassword", token, testGroupArray);
    assertEquals(
        "{\"result\":\"Your request to synchronize the specified groups was submitted.\"}", result);
  }

  @Test
  public void shouldReturnErrorWhenSyncGroups() throws IOException {
    InputStream mockStream =
        new ByteArrayInputStream("{\"error\":\"failed to sync group\"}".getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenThrow(new NullPointerException("Error occurred"));
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    String testGroupArray = "[\"foo\", \"bar\"]";
    String token = "mockToken";
    String result =
        new UserGroupSyncClient(mockHttpClient)
            .groupsSync("testUrl", "testUsername", "testPassword", token, testGroupArray);
    assertEquals("{\"error\":\"Error occurred\"}", result);
  }

  @Test
  public void shouldCheckSyncResult() throws IOException {
    InputStream mockStream = new ByteArrayInputStream("{\"state\":\"success.\"}".getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenReturn(mockEntity);
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    String token = "mockToken";
    String result =
        new UserGroupSyncClient(mockHttpClient)
            .checkSyncResult("testUrl", "testUsername", "testPassword", token);
    assertEquals("{\"state\":\"success.\"}", result);
  }

  @Test
  public void shouldReturnErrorCheckSyncResult() throws IOException {
    InputStream mockStream = new ByteArrayInputStream("{\"error\":\"sync failed.\"}".getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenThrow(new NullPointerException("Error occurred"));
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    String token = "mockToken";
    String result =
        new UserGroupSyncClient(mockHttpClient)
            .checkSyncResult("testUrl", "testUsername", "testPassword", token);
    assertEquals("{\"error\":\"Error occurred\"}", result);
  }
}
