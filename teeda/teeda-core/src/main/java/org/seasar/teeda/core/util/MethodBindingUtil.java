/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.util;

import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;

import org.seasar.framework.util.AssertionUtil;

public class MethodBindingUtil {

    private MethodBindingUtil() {
    }

    public static String getComponentName(final String fromAction) {
        if (fromAction == null) {
            return null;
        }
        if (!BindingUtil.isValueReference(fromAction)) {
            return null;
        }
        int index = fromAction.indexOf('.');
        if (index > 0) {
            return fromAction.substring(2, index);
        }
        return null;
    }

    public static String getMethodName(final String fromAction) {
        if (fromAction == null) {
            return null;
        }
        if (!BindingUtil.isValueReference(fromAction)) {
            return null;
        }
        int index = fromAction.lastIndexOf('.');
        if (index > 0) {
            return fromAction.substring(index + 1, fromAction.length() - 1);
        }
        return null;
    }

    public static String getFromAction(String componentName, String methodName) {
        AssertionUtil.assertNotNull("componentName", componentName);
        AssertionUtil.assertNotNull("methodName", methodName);
        return "#{" + componentName + "." + methodName + "}";
    }

    public static String invoke(MethodBinding methodBinding,
            FacesContext context) {
        try {
            return (String) methodBinding.invoke(context, null);
        } catch (EvaluationException e) {
            Throwable cause = e.getCause();
            if (cause != null && cause instanceof AbortProcessingException) {
                throw (AbortProcessingException) cause;
            }
            throw e;
        }
    }
}
