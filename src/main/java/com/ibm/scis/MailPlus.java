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

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.ContentType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.ParseException;

/** This class provides a sample implementation for email services */
public class MailPlus {

  private static final String CLASS_NAME = MailPlus.class.getName();
  private static final Logger logger = Logger.getLogger(CLASS_NAME);

  /** Constant for plain text messages contentType. */
  private static final String CONTENT_TYPE_PLAIN_TEXT = "text/plain";
  /** Constant for html text messages contentType. */
  private static final String CONTENT_TYPE_HTML = "text/html";
  /** Constant for Low Importance */
  private static final String IMPORTANCE_LOW = "low";
  /** Constant for Normal Importance */
  private static final String IMPORTANCE_NORMAL = "normal";
  /** Constant for High Importance */
  private static final String IMPORTANCE_HIGH = "high";
  /** Constant for Importance header */
  private static final String IMPORTANCE_HEADER = "Importance";
  /** Constants for the mail protocol */
  private String _smtpHost = null;

  private List<InternetAddress> _to = null;
  private InternetAddress _from = null;
  private List<InternetAddress> _replyTo = null;
  private List<InternetAddress> _cc = null;
  private List<InternetAddress> _bcc = null;
  private String _subject = null;
  private ContentType _contentType = null;
  private String _content = null;
  private String _importance = IMPORTANCE_NORMAL;
  private List<String> _attachmentFileNames = null;
  private TransportDelegator _transportDelegator;

  public MailPlus() {
    _transportDelegator = new TransportDelegator();
  }

  public MailPlus(TransportDelegator delegator) {
    _transportDelegator = delegator;
  }

  /**
   * Send an email message to an SMTP server with credentials
   *
   * @param smtpHost The host to connect to.
   * @param username The user name registered for email service.
   * @param password The password for email service.
   * @param to (Required)Comma separated list of email addresses of the 'To' recipients.
   * @param from (Required)The email address of the sender.
   * @param replyTo (Optional)Comma separated list of email addresses to which replies should be
   *     directed.
   * @param cc (Optional)Comma separated list of email addresses of the 'Cc' recipients.
   * @param bcc (Optional)Comma separated list of email addresses of the 'Bcc' recipients.
   * @param subject The subject of the email.
   * @param contentType The MIME content type; i.e. 'text/html' or 'text/plain'.
   * @param content The body of the email.
   * @param importance The importance of the email; i.e. 'high', 'normal', or 'low'. Invalid values
   *     are ignored.
   * @param attachmentFileNames Comma separated list of file names to be attached to the email.
   * @exception MessagingException If something went wrong.
   */
  public void sendMessageWithCredentials(
      String smtpHost,
      String username,
      String password,
      String to,
      String from,
      String replyTo,
      String cc,
      String bcc,
      String subject,
      String contentType,
      String content,
      String importance,
      String attachmentFileNames)
      throws MessagingException {

    final String METHOD_NAME = "sendMessageWithCredentials";
    logger.entering(
        CLASS_NAME,
        METHOD_NAME,
        new Object[] {
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
          attachmentFileNames
        });

    setSMTPHost(smtpHost);

    if (to == null || to.length() == 0) {
      throw new MessagingException("The 'To' field should not be empty.");
    }
    setTo(to);

    if (from == null || from.length() == 0) {
      throw new MessagingException("The 'From' field should not be empty.");
    }
    setFrom(from);

    // optional field: ReplyTo
    if (replyTo != null && !replyTo.equals("")) {
      setReplyTo(replyTo);
    }

    // optional field: cc
    if (cc != null && !cc.equals("")) {
      setCc(cc);
    }

    // optional field: bcc
    if (bcc != null && !bcc.equals("")) {
      setBcc(bcc);
    }

    if (subject == null || subject.length() == 0) {
      throw new MessagingException("The 'Subject' field should not be empty.");
    }
    setSubject(subject);

    if (!CONTENT_TYPE_PLAIN_TEXT.equals(contentType) && !CONTENT_TYPE_HTML.equals(contentType)) {
      throw new MessagingException(
          "The 'ContentType' field should be '"
              + CONTENT_TYPE_PLAIN_TEXT
              + "' or '"
              + CONTENT_TYPE_HTML
              + "'.");
    }
    setContentType(contentType);

    if (content == null || content.length() == 0) {
      throw new MessagingException("The 'Content' field should not be empty.");
    }
    setContent(content);

    // optional importance
    if (importance != null && importance.length() > 0) {
      setImportance(importance);
    }

    // optional attachment file names (comma separated list)
    if (attachmentFileNames != null && attachmentFileNames.length() > 0) {
      List<String> filenames = Arrays.asList(attachmentFileNames.split(","));
      setattachmentFileNames(filenames);
    }

    send(username, password);

    logger.exiting(CLASS_NAME, METHOD_NAME);
  }

  /**
   * Set the list of attachment file names.
   *
   * @param attachmentFileNames List of file names.
   */
  private void setattachmentFileNames(List<String> attachmentFileNames) {
    _attachmentFileNames = attachmentFileNames;
  }

  /**
   * Set the bcc recipients list.
   *
   * @param addresses Comma separated list of email addresses.
   * @exception AddressException If the addresses cannot be parsed.
   */
  private void setBcc(String addresses) throws AddressException {
    // Parse the given sequence of addresses into InternetAddress objects.
    // If strict is true, many (but not all) of the RFC822 syntax
    // rules are enforced.
    _bcc = Arrays.asList(InternetAddress.parse(addresses, true));
  }

  /**
   * Set the cc recipients list.
   *
   * @param addresses Comma separated list of email addresses.
   * @exception AddressException If the addresses cannot be parsed.
   */
  private void setCc(String addresses) throws AddressException {
    // Parse the given sequence of addresses into InternetAddress objects.
    // If strict is true, many (but not all) of the RFC822 syntax
    // rules are enforced.
    _cc = Arrays.asList(InternetAddress.parse(addresses, true));
  }

  /**
   * Set message body.
   *
   * @param newContent Text of the message.
   */
  private void setContent(String newContent) {
    _content = newContent;
  }

  /**
   * Set the content type.
   *
   * @param contentType Mime type of the message @see ContentType.
   * @exception ParseException If the content type cannot be parsed.
   */
  private void setContentType(String contentType) throws ParseException {
    _contentType = new ContentType(contentType);
  }

  /**
   * Set the from.
   *
   * @param address Sender of the email.
   * @exception AddressException If the address cannot be parsed.
   */
  private void setFrom(String address) throws AddressException {
    _from = new InternetAddress(address);
  }

  /**
   * Set the importance.
   *
   * @param importance Should be high, normal, or low.
   */
  private void setImportance(String importance) {
    _importance = importance;
  }

  /**
   * Set the reply to.
   *
   * @param addresses Comma separated list of email addresses.
   * @exception AddressException If the addresses cannot be parsed.
   */
  private void setReplyTo(String addresses) throws AddressException {
    // Parse the given sequence of addresses into InternetAddress objects.
    // If strict is true, many (but not all) of the RFC822 syntax
    // rules are enforced.
    _replyTo = Arrays.asList(InternetAddress.parse(addresses, true));
  }

  /**
   * Set the SMTP host.
   *
   * @param host The host name.
   */
  private void setSMTPHost(String host) {
    _smtpHost = host;
  }

  /**
   * Set the subject.
   *
   * @param subject Subject of the message.
   */
  private void setSubject(String subject) {
    _subject = subject;
  }

  /**
   * Set the to.
   *
   * @param addresses Comma separated list of email addresses.
   * @exception AddressException If the addresses cannot be parsed.
   */
  private void setTo(String addresses) throws AddressException {
    // Parse the given sequence of addresses into InternetAddress objects.
    // If strict is true, many (but not all) of the RFC822 syntax
    // rules are enforced.
    _to = Arrays.asList(InternetAddress.parse(addresses, true));
  }

  /**
   * Send this message.
   *
   * @exception MessagingException If something went wrong.
   */
  private void send(String username, String password) throws MessagingException {
    final String METHOD_NAME = "send";
    logger.entering(CLASS_NAME, METHOD_NAME);

    Properties properties = new Properties();
    properties.put("mail.smtp.host", _smtpHost);

    Session session = Session.getInstance(properties);

    MimeMessage message = new MimeMessage(session);
    message.setSentDate(new Date());
    message.setFrom(_from);
    message.addRecipients(Message.RecipientType.TO, (Address[]) _to.toArray(new Address[0]));

    // optional field: _cc
    if (_cc != null && !_cc.isEmpty()) {
      message.addRecipients(Message.RecipientType.CC, (Address[]) _cc.toArray(new Address[0]));
    }

    // optional field: _bcc
    if (_bcc != null && !_bcc.isEmpty()) {
      message.addRecipients(Message.RecipientType.BCC, (Address[]) _bcc.toArray(new Address[0]));
    }
    message.setSubject(_subject);

    // optional field: _replyTo
    if (_replyTo != null && !_replyTo.isEmpty()) {
      message.setReplyTo((Address[]) _replyTo.toArray(new Address[0]));
    }

    // add attachments
    if (_attachmentFileNames == null || _attachmentFileNames.size() < 1) {
      // Single part Message
      message.setContent(_content, _contentType.toString());
    } else {
      // multipart message
      MimeMultipart multipart = new MimeMultipart();

      // Create a body part and attach it first, all subsequent parts
      // are attachments.
      MimeBodyPart bodyPart = new MimeBodyPart();
      bodyPart.setContent(_content, _contentType.toString());
      multipart.addBodyPart(bodyPart);

      MimeBodyPart attachmentBodyPart = null;
      // Attach each file
      for (int i = 0; i < _attachmentFileNames.size(); i++) {
        String filename = _attachmentFileNames.get(i);
        // Get the attachment
        DataSource source = new FileDataSource(filename);
        // Set the data handler to the attachment
        attachmentBodyPart = new MimeBodyPart();
        attachmentBodyPart.setDataHandler(new DataHandler(source));
        // The filename should usually be a simple name, not including
        // directory components
        attachmentBodyPart.setFileName((new File(filename)).getName());
        multipart.addBodyPart(attachmentBodyPart);
      }

      message.setContent(multipart);
    }

    // Only set the importance flag if it is specified as high or low.
    if (IMPORTANCE_LOW.equalsIgnoreCase(_importance)
        || IMPORTANCE_HIGH.equalsIgnoreCase(_importance)) {
      message.addHeader(IMPORTANCE_HEADER, _importance);
    }

    if (username != null && password != null) {
      _transportDelegator.send(message, username, password);
    } else {
      _transportDelegator.send(message);
    }

    logger.exiting(CLASS_NAME, METHOD_NAME);
  }
}
