/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.html.impl;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.seasar.framework.util.MethodUtil;

/**
 * @author higa
 *
 */
public class ActionDescUtil {

    protected ActionDescUtil() {
    }

    public static Set getActionMethodNames(Class actionClass) {
        Set methodNames = new HashSet();
        Method[] methods = actionClass.getMethods();
        for (int i = 0; i < methods.length; ++i) {
            Method m = methods[i];
            if (MethodUtil.isBridgeMethod(m) || MethodUtil.isSyntheticMethod(m)) {
                continue;
            }
            if (isActionMethodCandidate(m)) {
                methodNames.add(m.getName());
            }
        }
        return methodNames;
    }

    protected static boolean isActionMethodCandidate(Method method) {
        final Class returnType = method.getReturnType();
        final Class[] parameterTypes = method.getParameterTypes();
        return (returnType.equals(String.class)
                || returnType.equals(Class.class) || returnType
                .equals(Void.TYPE))
                && parameterTypes.length == 0;
    }
}