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

/** The bean information class UserGroupSyncClient. */
public class UserGroupSyncClientBeanInfo extends java.beans.SimpleBeanInfo {

  private static final String CLASS_NAME = UserGroupSyncClient.class.getName();
  private static final Logger logger = Logger.getLogger(CLASS_NAME);

  @SuppressWarnings("rawtypes")
  private final Class beanClass = UserGroupSyncClient.class;

  @Override
  public MethodDescriptor[] getMethodDescriptors() {
    final String METHOD_NAME = "getMethodDescriptors";
    try {
      MethodDescriptor descriptorList[] = {
        getBPMCSRFTokenMethodDescriptor(),
        usersSyncMethodDescriptor(),
        groupsSyncMethodDescriptor(),
        checkSyncResultDescriptor()
      };
      return descriptorList;
    } catch (Exception e) {
      logger.logp(
          Level.SEVERE, CLASS_NAME, METHOD_NAME, "Creating the method descriptor list failed.", e);
      return super.getMethodDescriptors();
    }
  }

  @SuppressWarnings("unchecked")
  private MethodDescriptor getBPMCSRFTokenMethodDescriptor() throws NoSuchMethodException {
    final String METHOD_NAME = "getBPMCSRFTokenMethodDescriptor";
    logger.entering(CLASS_NAME, METHOD_NAME);

    Method method =
        beanClass.getMethod(
            "getBPMCSRFToken", String.class, String.class, String.class, Integer.class);

    ParameterDescriptor param1 = new ParameterDescriptor();
    param1.setShortDescription("The url of getting bpm csrf_token service.");
    param1.setDisplayName("bpm csrf token url");

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
  private MethodDescriptor usersSyncMethodDescriptor() throws NoSuchMethodException {
    final String METHOD_NAME = "usersSyncMethodDescriptor";
    logger.entering(CLASS_NAME, METHOD_NAME);

    Method method =
        beanClass.getMethod(
            "usersSync", String.class, String.class, String.class, String.class, String.class);

    ParameterDescriptor param1 = new ParameterDescriptor();
    param1.setShortDescription("The url of sync user service.");
    param1.setDisplayName("user sync service url");

    ParameterDescriptor param2 = new ParameterDescriptor();
    param2.setShortDescription("The functional username to login BAW.");
    param2.setDisplayName("baw username");

    ParameterDescriptor param3 = new ParameterDescriptor();
    param3.setShortDescription("The functional password to login BAW.");
    param3.setDisplayName("baw password");

    ParameterDescriptor param4 = new ParameterDescriptor();
    param4.setShortDescription("The bpm csrf token.");
    param4.setDisplayName("bpm csrf token");

    ParameterDescriptor param5 = new ParameterDescriptor();
    param5.setShortDescription("The user array list.");
    param5.setDisplayName("user array list");

    MethodDescriptor methodDescriptor =
        new MethodDescriptor(
            method, new ParameterDescriptor[] {param1, param2, param3, param4, param5});

    logger.exiting(CLASS_NAME, METHOD_NAME, methodDescriptor);
    return methodDescriptor;
  }

  @SuppressWarnings("unchecked")
  private MethodDescriptor groupsSyncMethodDescriptor() throws NoSuchMethodException {
    final String METHOD_NAME = "groupsSyncMethodDescriptor";
    logger.entering(CLASS_NAME, METHOD_NAME);

    Method method =
        beanClass.getMethod(
            "groupsSync", String.class, String.class, String.class, String.class, String.class);

    ParameterDescriptor param1 = new ParameterDescriptor();
    param1.setShortDescription("The url of sync group service.");
    param1.setDisplayName("group sync service url");

    ParameterDescriptor param2 = new ParameterDescriptor();
    param2.setShortDescription("The functional username to login BAW.");
    param2.setDisplayName("baw username");

    ParameterDescriptor param3 = new ParameterDescriptor();
    param3.setShortDescription("The functional password to login BAW.");
    param3.setDisplayName("baw password");

    ParameterDescriptor param4 = new ParameterDescriptor();
    param4.setShortDescription("The bpm csrf token.");
    param4.setDisplayName("bpm csrf token");

    ParameterDescriptor param5 = new ParameterDescriptor();
    param5.setShortDescription("The group array list.");
    param5.setDisplayName("group array list");

    MethodDescriptor methodDescriptor =
        new MethodDescriptor(
            method, new ParameterDescriptor[] {param1, param2, param3, param4, param5});

    logger.exiting(CLASS_NAME, METHOD_NAME, methodDescriptor);
    return methodDescriptor;
  }

  @SuppressWarnings("unchecked")
  private MethodDescriptor checkSyncResultDescriptor() throws NoSuchMethodException {
    final String METHOD_NAME = "checkSyncResultDescriptor";
    logger.entering(CLASS_NAME, METHOD_NAME);

    Method method =
        beanClass.getMethod(
            "checkSyncResult", String.class, String.class, String.class, String.class);

    ParameterDescriptor param1 = new ParameterDescriptor();
    param1.setShortDescription("The url from the sync result.");
    param1.setDisplayName("sync result url");

    ParameterDescriptor param2 = new ParameterDescriptor();
    param2.setShortDescription("The functional username to login BAW.");
    param2.setDisplayName("baw username");

    ParameterDescriptor param3 = new ParameterDescriptor();
    param3.setShortDescription("The functional password to login BAW.");
    param3.setDisplayName("baw password");

    ParameterDescriptor param4 = new ParameterDescriptor();
    param4.setShortDescription("The bpm csrf token.");
    param4.setDisplayName("bpm csrf token");

    MethodDescriptor methodDescriptor =
        new MethodDescriptor(method, new ParameterDescriptor[] {param1, param2, param3, param4});

    logger.exiting(CLASS_NAME, METHOD_NAME, methodDescriptor);
    return methodDescriptor;
  }
}
