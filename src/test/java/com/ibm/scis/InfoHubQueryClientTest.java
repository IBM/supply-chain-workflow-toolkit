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
import static org.junit.Assert.assertThrows;
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
public class InfoHubQueryClientTest extends BaseClient {

  @Mock CloseableHttpClient mockHttpClient;

  @Mock CloseableHttpResponse mockHttpResponse;

  @Mock HttpEntity mockEntity;

  @Test
  public void shouldExecuteForJSON() throws IOException {
    InputStream mockStream = new ByteArrayInputStream("{\"foo\":\"bar\"}".getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenReturn(mockEntity);
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    String result =
        new InfoHubQueryClient(mockHttpClient)
            .executeQuery(
                "testEndpoint",
                "testDataQuery",
                "testClientId",
                "testClientSecret",
                "testUsername");
    assertEquals("{\"foo\":\"bar\"}", result);
  }

  @Test
  public void shouldExecuteOnNonProdAndReturnJSONWithError() throws IOException {
    InputStream mockStream = new ByteArrayInputStream("{\"error\":\"errorMessage\"}".getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenReturn(mockEntity);
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    String result =
        new InfoHubQueryClient(mockHttpClient)
            .executeQuery(
                "testEndpoint",
                "testDataQuery",
                "testClientId",
                "testClientSecret",
                "testUsername");
    assertEquals("{\"error\":\"errorMessage\"}", result);
  }

  @Test
  public void shouldReturnErrorIfDuringHTTPRequest() throws IOException {
    InputStream mockStream =
        new ByteArrayInputStream("{\"error\":\"Field type must be empty\"}".getBytes());
    when(mockEntity.getContent()).thenReturn(mockStream);
    when(mockHttpResponse.getEntity()).thenThrow(new NullPointerException("Error occurred"));
    when(mockHttpClient.execute(Mockito.any())).thenReturn(mockHttpResponse);
    Throwable exception =
        assertThrows(
            NullPointerException.class,
            () ->
                new InfoHubQueryClient(mockHttpClient)
                    .executeQuery(
                        "testEndpoint",
                        "testDataQuery",
                        "testClientId",
                        "testClientSecret",
                        "testUsername"));
    assertEquals("Error occurred", exception.getMessage());
  }
}
