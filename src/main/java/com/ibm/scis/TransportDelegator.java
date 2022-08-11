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

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

/** TransportDelegator to delegates the Transport.send call. */
public class TransportDelegator {
  /**
   * Send message
   *
   * @param message message
   * @throws MessagingException
   */
  public void send(Message message) throws MessagingException {
    Transport.send(message);
  }

  /**
   * Send message with username and password
   *
   * @param message message
   * @param username username
   * @param password password
   * @throws MessagingException
   */
  public void send(Message message, String username, String password) throws MessagingException {
    Transport.send(message, username, password);
  }
}
