/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.spike.javassist;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;

/**
 * @author manhole
 */
class RuntimeClassInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 1L;

    private Map mathods_ = new HashMap();

    void defineMethod(Object o, String methodName) {
        mathods_.put(methodName, o);
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        final Method method = invocation.getMethod();
        final String methodName = method.getName();
        final Object[] arguments = invocation.getArguments();
        if ("defineMethod".equals(methodName)) {
            defineMethod(arguments[0], (String) arguments[1]);
            return null;
        }
        Class[] parameterTypes = method.getParameterTypes();
        Object o = mathods_.get(methodName);
        if (o != null) {
            try {
                final Method synthesisMethod = o.getClass().getMethod(
                        methodName, parameterTypes);
                synthesisMethod.setAccessible(true);
                return synthesisMethod.invoke(o, arguments);
            } catch (NoSuchMethodException e) {
            }
            try {
                final Method synthesisMethod = o.getClass().getMethod(
                        "execute", parameterTypes);
                return synthesisMethod.invoke(o, arguments);
            } catch (NoSuchMethodException e) {
            }
        }
        return invocation.proceed();
    }

}
