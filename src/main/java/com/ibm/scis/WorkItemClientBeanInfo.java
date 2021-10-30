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
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/** The bean information class WorkItemClient. */
public class WorkItemClientBeanInfo extends java.beans.SimpleBeanInfo {

  private static final String CLASS_NAME = WorkItemClientBeanInfo.class.getName();
  private static final Logger logger = Logger.getLogger(CLASS_NAME);

  @SuppressWarnings("rawtypes")
  private final Class beanClass = WorkItemClient.class;

  @Override
  public MethodDescriptor[] getMethodDescriptors() {
    final String METHOD_NAME = "getMethodDescriptors";
    try {
      MethodDescriptor descriptorList[] = {
        createWorkItemMethodDescriptor(), updateWorkItemMethodDescriptor()
      };
      return descriptorList;
    } catch (Exception e) {
      logger.logp(
          Level.SEVERE, CLASS_NAME, METHOD_NAME, "Creating the method descriptor list failed.", e);
      return super.getMethodDescriptors();
    }
  }

  @SuppressWarnings("unchecked")
  private MethodDescriptor createWorkItemMethodDescriptor() throws NoSuchMethodException {
    final String METHOD_NAME = "createWorkItemMethodDescriptor";
    logger.entering(CLASS_NAME, METHOD_NAME);

    Method method =
        beanClass.getMethod(
            "createWorkItem", String.class, String.class, String.class, String.class);

    ParameterDescriptor param1 = new ParameterDescriptor();
    param1.setShortDescription("The url of work item client service.");
    param1.setDisplayName("endpoint");

    ParameterDescriptor param2 = new ParameterDescriptor();
    param2.setShortDescription("The client id of work item client service.");
    param2.setDisplayName("client id");

    ParameterDescriptor param3 = new ParameterDescriptor();
    param3.setShortDescription("The client secret of work item client service.");
    param3.setDisplayName("client secret");

    ParameterDescriptor param4 = new ParameterDescriptor();
    param4.setShortDescription("The work item to create in work item client service.");
    param4.setDisplayName("work item");

    MethodDescriptor methodDescriptor =
        new MethodDescriptor(method, new ParameterDescriptor[] {param1, param2, param3, param4});

    logger.exiting(CLASS_NAME, METHOD_NAME, methodDescriptor);
    return methodDescriptor;
  }

  @SuppressWarnings("unchecked")
  private MethodDescriptor updateWorkItemMethodDescriptor() throws NoSuchMethodException {
    final String METHOD_NAME = "updateWorkItemMethodDescriptor";
    logger.entering(CLASS_NAME, METHOD_NAME);

    Method method =
        beanClass.getMethod(
            "updateWorkItem", String.class, String.class, String.class, String.class, String.class);

    ParameterDescriptor param1 = new ParameterDescriptor();
    param1.setShortDescription("The url of work item client service.");
    param1.setDisplayName("endpoint");

    ParameterDescriptor param2 = new ParameterDescriptor();
    param2.setShortDescription("The work item id to update in work item client service.");
    param2.setDisplayName("work item id");

    ParameterDescriptor param3 = new ParameterDescriptor();
    param3.setShortDescription("The client id of work item client service.");
    param3.setDisplayName("client id");

    ParameterDescriptor param4 = new ParameterDescriptor();
    param4.setShortDescription("The client secret of work item client service.");
    param4.setDisplayName("client secret");

    ParameterDescriptor param5 = new ParameterDescriptor();
    param5.setShortDescription("The work item content to update in work item client service.");
    param5.setDisplayName("work item updates");

    MethodDescriptor methodDescriptor =
        new MethodDescriptor(
            method, new ParameterDescriptor[] {param1, param2, param3, param4, param5});

    logger.exiting(CLASS_NAME, METHOD_NAME, methodDescriptor);
    return methodDescriptor;
  }
}
