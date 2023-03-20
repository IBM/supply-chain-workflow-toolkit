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

import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/** The bean information class UserManageClient. */
public class UserManageClientBeanInfo extends java.beans.SimpleBeanInfo {

  private static final String CLASS_NAME = UserManageClientBeanInfo.class.getName();
  private static final Logger logger = Logger.getLogger(CLASS_NAME);

  @SuppressWarnings("rawtypes")
  private final Class beanClass = UserManageClient.class;

  @Override
  public MethodDescriptor[] getMethodDescriptors() {
    final String METHOD_NAME = "getMethodDescriptors";
    try {
      MethodDescriptor descriptorList[] = {
        getCSRFTokenMethodDescriptor(),
        addNewUserMethodDescriptor(),
        updateExistingUserMethodDescriptor(),
        checkUserByUserIdMethodDescriptor()
      };
      return descriptorList;
    } catch (Exception e) {
      logger.logp(
          Level.SEVERE, CLASS_NAME, METHOD_NAME, "Creating the method descriptor list failed.", e);
      return super.getMethodDescriptors();
    }
  }

  @SuppressWarnings("unchecked")
  private MethodDescriptor getCSRFTokenMethodDescriptor() throws NoSuchMethodException {
    final String METHOD_NAME = "getCSRFTokenMethodDescriptor";
    logger.entering(CLASS_NAME, METHOD_NAME);

    Method method =
        beanClass.getMethod(
            "getCSRFToken", String.class, String.class, String.class, Integer.class);

    ParameterDescriptor param1 = new ParameterDescriptor();
    param1.setShortDescription("The url of getting ibm csrf_token service.");
    param1.setDisplayName("ibm csrf token url");

    ParameterDescriptor param2 = new ParameterDescriptor();
    param2.setShortDescription("The functional username to login BAW.");
    param2.setDisplayName("baw username");

    ParameterDescriptor param3 = new ParameterDescriptor();
    param3.setShortDescription("The functional password to login BAW.");
    param3.setDisplayName("baw password");

    ParameterDescriptor param4 = new ParameterDescriptor();
    param4.setShortDescription("The lifetime of the csrf token.");
    param4.setDisplayName("csrf token lifetime");

    MethodDescriptor methodDescriptor =
        new MethodDescriptor(method, new ParameterDescriptor[] {param1, param2, param3, param4});

    logger.exiting(CLASS_NAME, METHOD_NAME, methodDescriptor);
    return methodDescriptor;
  }

  @SuppressWarnings("unchecked")
  private MethodDescriptor addNewUserMethodDescriptor() throws NoSuchMethodException {
    final String METHOD_NAME = "addNewUserMethodDescriptor";
    logger.entering(CLASS_NAME, METHOD_NAME);

    Method method =
        beanClass.getMethod(
            "addNewUser", String.class, String.class, String.class, String.class, String.class);

    ParameterDescriptor param1 = new ParameterDescriptor();
    param1.setShortDescription("The url of adding baw user service.");
    param1.setDisplayName("user service url");

    ParameterDescriptor param2 = new ParameterDescriptor();
    param2.setShortDescription("The functional username to login BAW.");
    param2.setDisplayName("baw username");

    ParameterDescriptor param3 = new ParameterDescriptor();
    param3.setShortDescription("The functional password to login BAW.");
    param3.setDisplayName("baw password");

    ParameterDescriptor param4 = new ParameterDescriptor();
    param4.setShortDescription("The csrf token.");
    param4.setDisplayName("csrf token");

    ParameterDescriptor param5 = new ParameterDescriptor();
    param5.setShortDescription("The user information.");
    param5.setDisplayName("user information");

    MethodDescriptor methodDescriptor =
        new MethodDescriptor(
            method, new ParameterDescriptor[] {param1, param2, param3, param4, param5});

    logger.exiting(CLASS_NAME, METHOD_NAME, methodDescriptor);
    return methodDescriptor;
  }

  @SuppressWarnings("unchecked")
  private MethodDescriptor updateExistingUserMethodDescriptor() throws NoSuchMethodException {
    final String METHOD_NAME = "updateExistingUserMethodDescriptor";
    logger.entering(CLASS_NAME, METHOD_NAME);

    Method method =
        beanClass.getMethod(
            "updateExistingUser",
            String.class,
            String.class,
            String.class,
            String.class,
            String.class,
            String.class);

    ParameterDescriptor param1 = new ParameterDescriptor();
    param1.setShortDescription("The url of adding baw user service.");
    param1.setDisplayName("user service url");

    ParameterDescriptor param2 = new ParameterDescriptor();
    param2.setShortDescription("The functional username to login BAW.");
    param2.setDisplayName("baw username");

    ParameterDescriptor param3 = new ParameterDescriptor();
    param3.setShortDescription("The functional password to login BAW.");
    param3.setDisplayName("baw password");

    ParameterDescriptor param4 = new ParameterDescriptor();
    param4.setShortDescription("The csrf token.");
    param4.setDisplayName("csrf token");

    ParameterDescriptor param5 = new ParameterDescriptor();
    param5.setShortDescription("The user id.");
    param5.setDisplayName("user id");

    ParameterDescriptor param6 = new ParameterDescriptor();
    param6.setShortDescription("The user information.");
    param6.setDisplayName("updated user information");

    MethodDescriptor methodDescriptor =
        new MethodDescriptor(
            method, new ParameterDescriptor[] {param1, param2, param3, param4, param5, param6});

    logger.exiting(CLASS_NAME, METHOD_NAME, methodDescriptor);
    return methodDescriptor;
  }

  @SuppressWarnings("unchecked")
  private MethodDescriptor checkUserByUserIdMethodDescriptor() throws NoSuchMethodException {
    final String METHOD_NAME = "checkUserByUserIdMethodDescriptor";
    logger.entering(CLASS_NAME, METHOD_NAME);

    Method method =
        beanClass.getMethod(
            "checkUserByUserId",
            String.class,
            String.class,
            String.class,
            String.class,
            String.class);

    ParameterDescriptor param1 = new ParameterDescriptor();
    param1.setShortDescription("The url of user service.");
    param1.setDisplayName("user service url");

    ParameterDescriptor param2 = new ParameterDescriptor();
    param2.setShortDescription("The functional username to login BAW.");
    param2.setDisplayName("baw username");

    ParameterDescriptor param3 = new ParameterDescriptor();
    param3.setShortDescription("The functional password to login BAW.");
    param3.setDisplayName("baw password");

    ParameterDescriptor param4 = new ParameterDescriptor();
    param4.setShortDescription("The ibm csrf token.");
    param4.setDisplayName("csrf token");

    ParameterDescriptor param5 = new ParameterDescriptor();
    param5.setShortDescription("The user id.");
    param5.setDisplayName("user id");

    MethodDescriptor methodDescriptor =
        new MethodDescriptor(
            method, new ParameterDescriptor[] {param1, param2, param3, param4, param5});

    logger.exiting(CLASS_NAME, METHOD_NAME, methodDescriptor);
    return methodDescriptor;
  }
}
