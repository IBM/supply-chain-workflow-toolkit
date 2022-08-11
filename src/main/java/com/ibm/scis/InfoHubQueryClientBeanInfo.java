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

/** The bean information class for InfoHubQueryClient. */
public class InfoHubQueryClientBeanInfo extends java.beans.SimpleBeanInfo {

  private static final String CLASS_NAME = InfoHubQueryClientBeanInfo.class.getName();
  private static final Logger logger = Logger.getLogger(CLASS_NAME);

  @SuppressWarnings("rawtypes")
  private final Class beanClass = InfoHubQueryClient.class;

  @Override
  public MethodDescriptor[] getMethodDescriptors() {
    final String METHOD_NAME = "getMethodDescriptors";
    try {
      MethodDescriptor descriptorList[] = {executeQueryMethodDescriptor()};
      return descriptorList;
    } catch (Exception e) {
      logger.logp(
          Level.SEVERE, CLASS_NAME, METHOD_NAME, "Creating the method descriptor list failed.", e);
      return super.getMethodDescriptors();
    }
  }

  @SuppressWarnings("unchecked")
  private MethodDescriptor executeQueryMethodDescriptor() throws NoSuchMethodException {
    final String METHOD_NAME = "executeQueryMethodDescriptor";
    logger.entering(CLASS_NAME, METHOD_NAME);

    Method method =
        beanClass.getMethod(
            "executeQuery", String.class, String.class, String.class, String.class, String.class);

    ParameterDescriptor param1 = new ParameterDescriptor();
    param1.setShortDescription("The url of the info hub query client.");
    param1.setDisplayName("endpoint");

    ParameterDescriptor param2 = new ParameterDescriptor();
    param2.setShortDescription("The GQL of the info hub query client.");
    param2.setDisplayName("query");

    ParameterDescriptor param3 = new ParameterDescriptor();
    param3.setShortDescription("The client id of the info hub query client.");
    param3.setDisplayName("client id");

    ParameterDescriptor param4 = new ParameterDescriptor();
    param4.setShortDescription("The client secret of the info hub query client.");
    param4.setDisplayName("client secret");

    ParameterDescriptor param5 = new ParameterDescriptor();
    param5.setShortDescription("IBM identity representing workflow functional user.");
    param5.setDisplayName("username");

    MethodDescriptor methodDescriptor =
        new MethodDescriptor(
            method, new ParameterDescriptor[] {param1, param2, param3, param4, param5});

    logger.exiting(CLASS_NAME, METHOD_NAME, methodDescriptor);
    return methodDescriptor;
  }
}
