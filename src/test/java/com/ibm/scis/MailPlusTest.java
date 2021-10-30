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

import static org.junit.Assert.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MailPlusTest {

  private final String smtpHost = "";
  private final String username = "apiKey";
  private final String password = "apiKeyValue";
  private final String to = "user@ibm.com";
  private final String cc = "cc@ibm.com";
  private final String bcc = "bcc@ibm.com";
  private final String from = "sender@ibm.com";
  private final String replyTo = "reply@ibm.com";
  private final String subject = "Notification from BAW";
  private final String contentType = "text/plain";
  private final String content = "This is a notifcation from BAW system.";
  private final String importance = "importance";
  private final String attachmentFileNames = "copy.txt";

  private Transport transport = Mockito.mock(Transport.class);

  /** Send mail without recipient * */
  @Test
  public void test_sendEmail_withoutRecipient() {
    assertThrows(
        MessagingException.class,
        () -> {
          new MailPlus()
              .sendMessageWithCredentials(
                  smtpHost,
                  username,
                  password,
                  null,
                  from,
                  replyTo,
                  "",
                  "",
                  subject,
                  contentType,
                  content,
                  importance,
                  attachmentFileNames);
        });
  }

  /** Send mail without sender * */
  @Test
  public void test_sendEmail_withoutSender() {
    assertThrows(
        MessagingException.class,
        () -> {
          new MailPlus()
              .sendMessageWithCredentials(
                  smtpHost,
                  username,
                  password,
                  to,
                  null,
                  replyTo,
                  "",
                  "",
                  subject,
                  contentType,
                  content,
                  importance,
                  attachmentFileNames);
        });
  }

  /** Send mail without subject * */
  @Test
  public void test_sendEmail_withoutContent() {
    assertThrows(
        MessagingException.class,
        () -> {
          new MailPlus()
              .sendMessageWithCredentials(
                  smtpHost,
                  username,
                  password,
                  to,
                  from,
                  replyTo,
                  "",
                  "",
                  subject,
                  contentType,
                  null,
                  importance,
                  attachmentFileNames);
        });
  }

  /** Send mail without subject * */
  @Test
  public void test_sendEmail_withoutSubject() {
    assertThrows(
        MessagingException.class,
        () -> {
          new MailPlus()
              .sendMessageWithCredentials(
                  smtpHost,
                  username,
                  password,
                  to,
                  from,
                  replyTo,
                  "",
                  "",
                  null,
                  contentType,
                  content,
                  importance,
                  attachmentFileNames);
        });
  }

  /** Send mail with invalid content type * */
  @Test
  public void test_sendEmail_invalidContentType() {
    String invalid_contentType = "text/xml";
    assertThrows(
        MessagingException.class,
        () -> {
          new MailPlus()
              .sendMessageWithCredentials(
                  smtpHost,
                  username,
                  password,
                  to,
                  from,
                  replyTo,
                  "",
                  "",
                  subject,
                  invalid_contentType,
                  content,
                  importance,
                  attachmentFileNames);
        });
  }

  /**
   * Send mail with valid input *
   *
   * @throws MessagingException
   */
  @Test
  public void test_sendEmail_withValidInput() throws MessagingException {
    TransportDelegator mockDelegator = Mockito.mock(TransportDelegator.class);
    doNothing().when(mockDelegator).send(Mockito.any(), isA(String.class), isA(String.class));
    new MailPlus(mockDelegator)
        .sendMessageWithCredentials(
            smtpHost,
            username,
            password,
            to,
            from,
            replyTo,
            cc,
            bcc,
            subject,
            contentType,
            content,
            importance,
            attachmentFileNames);
    verify(mockDelegator, times(1)).send(any(Message.class), any(String.class), any(String.class));
  }
}
