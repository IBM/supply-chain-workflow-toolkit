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
public class UserManageClientTest {
  @Mock CloseableHttpClient mockHttpClient;

  @Mock CloseableHttpResponse mockHttpResponse;

  @Mock HttpEntity mockEntity;

  @Test
  public void shouldGetCSRFToken() throws IOException {
    InputStream mockStream = new ByteArrayInputStream("{\"result\":\"csrf_token\"}".getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenReturn(mockEntity);
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    Integer testLifeTime = 200;
    String result =
        new UserManageClient(mockHttpClient)
            .getCSRFToken("testBAWUrl", "testUsername", "testPassword", testLifeTime);
    assertEquals("{\"result\":\"csrf_token\"}", result);
  }

  @Test
  public void shouldReturnErrorWhenGetCSRFToken() throws IOException {
    InputStream mockStream =
        new ByteArrayInputStream("{\"error\":\"failed to get ibm csrf token\"}".getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenThrow(new NullPointerException("Error occurred"));
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    Integer testLifeTime = 200;
    String result =
        new UserManageClient(mockHttpClient)
            .getCSRFToken("testBAWUrl", "testUsername", "testPassword", testLifeTime);
    assertEquals("{\"error\":\"Error occurred\"}", result);
  }

  @Test
  public void shouldAddNewUser() throws IOException {
    InputStream mockStream = new ByteArrayInputStream("{\"result\":\"new user added\"}".getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenReturn(mockEntity);
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    String testUserInfoObj = "{\"foo\", \"bar\"}";
    String token = "mockToken";
    String result =
        new UserManageClient(mockHttpClient)
            .addNewUser("testUrl", "testUsername", "testPassword", token, testUserInfoObj);
    assertEquals("{\"result\":\"new user added\"}", result);
  }

  @Test
  public void shouldReturnErrorWhenAddNewUser() throws IOException {
    InputStream mockStream =
        new ByteArrayInputStream("{\"error\":\"failed to add new user\"}".getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenThrow(new NullPointerException("Error occurred"));
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    String testUserInfoObj = "{\"foo\", \"bar\"}";
    String token = "mockToken";
    String result =
        new UserManageClient(mockHttpClient)
            .addNewUser("testUrl", "testUsername", "testPassword", token, testUserInfoObj);
    assertEquals("{\"error\":\"Error occurred\"}", result);
  }

  @Test
  public void shouldUpdateUser() throws IOException {
    InputStream mockStream =
        new ByteArrayInputStream("{\"result\":\"update existing user\"}".getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenReturn(mockEntity);
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    String testUserInfoObj = "{\"foo\", \"bar\"}";
    String token = "mockToken";
    String userId = "mockUserId";
    String result =
        new UserManageClient(mockHttpClient)
            .updateExistingUser(
                "testUrl", "testUsername", "testPassword", token, userId, testUserInfoObj);
    assertEquals("{\"result\":\"update existing user\"}", result);
  }

  @Test
  public void shouldReturnErrorWhenUpdateUser() throws IOException {
    InputStream mockStream =
        new ByteArrayInputStream("{\"error\":\"failed to add new user\"}".getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenThrow(new NullPointerException("Error occurred"));
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    String testUserInfoObj = "{\"foo\", \"bar\"}";
    String token = "mockToken";
    String userId = "mockUserId";
    String result =
        new UserManageClient(mockHttpClient)
            .updateExistingUser(
                "testUrl", "testUsername", "testPassword", token, userId, testUserInfoObj);
    assertEquals("{\"error\":\"Error occurred\"}", result);
  }

  @Test
  public void shouldCheckUserById() throws IOException {
    InputStream mockStream =
        new ByteArrayInputStream("{\"result\":\"total_size is 0\"}".getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenReturn(mockEntity);
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    String token = "mockToken";
    String userId = "mockUserId";
    String result =
        new UserManageClient(mockHttpClient)
            .checkUserByUserId("testUrl", "testUsername", "testPassword", token, userId);
    assertEquals("{\"result\":\"total_size is 0\"}", result);
  }

  @Test
  public void shouldReturnErrorWhenCheckUserById() throws IOException {
    InputStream mockStream =
        new ByteArrayInputStream("{\"error\":\"failed to check by user id\"}".getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenThrow(new NullPointerException("Error occurred"));
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    String token = "mockToken";
    String userId = "mockUserId";
    String result =
        new UserManageClient(mockHttpClient)
            .checkUserByUserId("testUrl", "testUsername", "testPassword", token, userId);
    assertEquals("{\"error\":\"Error occurred\"}", result);
  }
}
