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

import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.SimpleBeanInfo;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/** The bean information class for com.ibm.scis.MailPlus. */
public class MailPlusBeanInfo extends SimpleBeanInfo {

  private static final String CLASS_NAME = MailPlusBeanInfo.class.getName();
  private static final Logger logger = Logger.getLogger(CLASS_NAME);

  public static Method findMethod(Class aClass, String methodName, int parameterCount) {
    try {
      Method[] methods = aClass.getMethods();
      for (int index = 0; index < methods.length; index++) {
        Method method = methods[index];
        if ((method.getParameterTypes()).length == parameterCount
            && method.getName().equals(methodName)) {
          return method;
        }
      }
    } catch (Throwable exception) {
      return null;
    }
    return null;
  }

  public static Class getBeanClass() {
    return MailPlus.class;
  }

  public static String getBeanClassName() {
    return "com.ibm.scis.MailPlus";
  }

  @Override
  public MethodDescriptor[] getMethodDescriptors() {
    final String METHOD_NAME = "getMethodDescriptors";
    try {
      MethodDescriptor descriptorList[] = {sendMessageWithCredentialsMethodDescriptor()};
      return descriptorList;
    } catch (Exception e) {
      logger.logp(
          Level.SEVERE, CLASS_NAME, METHOD_NAME, "Creating the method descriptor list failed.", e);
      return super.getMethodDescriptors();
    }
  }

  @SuppressWarnings("unchecked")
  private MethodDescriptor sendMessageMethodDescriptor() throws NoSuchMethodException {
    final String METHOD_NAME = "sendMessageMethodDescriptor";
    logger.entering(CLASS_NAME, METHOD_NAME);

    Method method =
        getBeanClass()
            .getMethod(
                "sendMessage",
                String.class,
                String.class,
                String.class,
                String.class,
                String.class,
                String.class,
                String.class,
                String.class,
                String.class,
                String.class,
                String.class);

    ParameterDescriptor param1 = new ParameterDescriptor();
    param1.setShortDescription("The host to connect to.");
    param1.setDisplayName("smtpHost");

    ParameterDescriptor param2 = new ParameterDescriptor();
    param2.setShortDescription("Comma separated list of email addresses of the 'To' recipients.");
    param2.setDisplayName("to");

    ParameterDescriptor param3 = new ParameterDescriptor();
    param3.setShortDescription("The email address of the sender.");
    param3.setDisplayName("from");

    ParameterDescriptor param4 = new ParameterDescriptor();
    param4.setShortDescription(
        "Comma separated list of email addresses to which replies should be directed.");
    param4.setDisplayName("replyTo");

    ParameterDescriptor param5 = new ParameterDescriptor();
    param5.setShortDescription("Comma separated list of email addresses of the 'Cc' recipients.");
    param5.setDisplayName("cc");

    ParameterDescriptor param6 = new ParameterDescriptor();
    param6.setShortDescription("Comma separated list of email addresses of the 'Bcc' recipients.");
    param6.setDisplayName("bcc");

    ParameterDescriptor param7 = new ParameterDescriptor();
    param7.setShortDescription("The subject of the email.");
    param7.setDisplayName("subject");

    ParameterDescriptor param8 = new ParameterDescriptor();
    param8.setShortDescription("The MIME content type; i.e. 'text/html' or 'text/plain'.");
    param8.setDisplayName("contentType");

    ParameterDescriptor param9 = new ParameterDescriptor();
    param9.setShortDescription("The body of the email.");
    param9.setDisplayName("body");

    ParameterDescriptor param10 = new ParameterDescriptor();
    param10.setShortDescription(
        "The importance of the email; i.e. 'high', 'normal', or 'low'. Invalid values are ignored.");
    param10.setDisplayName("importance");

    ParameterDescriptor param11 = new ParameterDescriptor();
    param11.setShortDescription("Comma separated list of file names to be attached to the email.");
    param11.setDisplayName("attachmentFileNames");

    ParameterDescriptor params[] = {
      param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11
    };
    MethodDescriptor methodDescriptor = new MethodDescriptor(method, params);
    logger.exiting(CLASS_NAME, METHOD_NAME, methodDescriptor);
    return methodDescriptor;
  }

  @SuppressWarnings("unchecked")
  private MethodDescriptor sendMessageWithCredentialsMethodDescriptor()
      throws NoSuchMethodException {
    final String METHOD_NAME = "sendMessageWithCredentialsMethodDescriptor";
    logger.entering(CLASS_NAME, METHOD_NAME);

    Method method =
        getBeanClass()
            .getMethod(
                "sendMessageWithCredentials",
                String.class,
                String.class,
                String.class,
                String.class,
                String.class,
                String.class,
                String.class,
                String.class,
                String.class,
                String.class,
                String.class,
                String.class,
                String.class);

    ParameterDescriptor param1 = new ParameterDescriptor();
    param1.setShortDescription("The host to connect to.");
    param1.setDisplayName("smtpHost");

    ParameterDescriptor param12 = new ParameterDescriptor();
    param12.setShortDescription("The username to connect to.");
    param12.setDisplayName("username");

    ParameterDescriptor param13 = new ParameterDescriptor();
    param13.setShortDescription("The password to connect to.");
    param13.setDisplayName("password");

    ParameterDescriptor param2 = new ParameterDescriptor();
    param2.setShortDescription("Comma separated list of email addresses of the 'To' recipients.");
    param2.setDisplayName("to");

    ParameterDescriptor param3 = new ParameterDescriptor();
    param3.setShortDescription("The email address of the sender.");
    param3.setDisplayName("from");

    ParameterDescriptor param4 = new ParameterDescriptor();
    param4.setShortDescription(
        "Comma separated list of email addresses to which replies should be directed.");
    param4.setDisplayName("replyTo");

    ParameterDescriptor param5 = new ParameterDescriptor();
    param5.setShortDescription("Comma separated list of email addresses of the 'Cc' recipients.");
    param5.setDisplayName("cc");

    ParameterDescriptor param6 = new ParameterDescriptor();
    param6.setShortDescription("Comma separated list of email addresses of the 'Bcc' recipients.");
    param6.setDisplayName("bcc");

    ParameterDescriptor param7 = new ParameterDescriptor();
    param7.setShortDescription("The subject of the email.");
    param7.setDisplayName("subject");

    ParameterDescriptor param8 = new ParameterDescriptor();
    param8.setShortDescription("The MIME content type; i.e. 'text/html' or 'text/plain'.");
    param8.setDisplayName("contentType");

    ParameterDescriptor param9 = new ParameterDescriptor();
    param9.setShortDescription("The body of the email.");
    param9.setDisplayName("body");

    ParameterDescriptor param10 = new ParameterDescriptor();
    param10.setShortDescription(
        "The importance of the email; i.e. 'high', 'normal', or 'low'. Invalid values are ignored.");
    param10.setDisplayName("importance");

    ParameterDescriptor param11 = new ParameterDescriptor();
    param11.setShortDescription("Comma separated list of file names to be attached to the email.");
    param11.setDisplayName("attachmentFileNames");

    ParameterDescriptor params[] = {
      param1, param12, param13, param2, param3, param4, param5, param6, param7, param8, param9,
      param10, param11
    };
    MethodDescriptor methodDescriptor = new MethodDescriptor(method, params);
    logger.exiting(CLASS_NAME, METHOD_NAME, methodDescriptor);
    return methodDescriptor;
  }
}
