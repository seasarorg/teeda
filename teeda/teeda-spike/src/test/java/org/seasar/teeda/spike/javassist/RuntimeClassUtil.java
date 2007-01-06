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
package org.seasar.teeda.spike.javassist;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import org.seasar.framework.aop.Aspect;
import org.seasar.framework.aop.Pointcut;
import org.seasar.framework.aop.impl.AspectImpl;
import org.seasar.framework.aop.impl.PointcutImpl;
import org.seasar.framework.aop.proxy.AopProxy;
import org.seasar.framework.exception.CannotCompileRuntimeException;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.exception.IllegalAccessRuntimeException;
import org.seasar.framework.exception.InvocationTargetRuntimeException;
import org.seasar.framework.exception.NoSuchMethodRuntimeException;
import org.seasar.framework.exception.NotFoundRuntimeException;
import org.seasar.framework.util.ClassPoolUtil;

/**
 * @author manhole
 */
public class RuntimeClassUtil {

    final static Method defineClassMethod;

    static {
        final Class targetClass = ClassLoader.class;
        final String methodName = "defineClass";
        final Class[] argType = new Class[] { String.class, byte[].class,
                int.class, int.class };
        try {
            Method m = targetClass.getDeclaredMethod(methodName, argType);
            m.setAccessible(true);
            defineClassMethod = m;
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodRuntimeException(targetClass, methodName,
                    argType, e);
        }
    }

    public static Object defineClass(final Class targetClass,
            final String enhancedClassName, final byte[] bytes) {
        try {
            final Object o = defineClassMethod.invoke(
                    getClassLoader(targetClass), new Object[] {
                            enhancedClassName, bytes, new Integer(0),
                            new Integer(bytes.length) });
            return o;
        } catch (InvocationTargetException e) {
            throw new InvocationTargetRuntimeException(targetClass, e);
        } catch (IllegalAccessException e) {
            throw new IllegalAccessRuntimeException(targetClass, e);
        }
    }

    private static ClassLoader getClassLoader(Class targetClass) {
        return Thread.currentThread().getContextClassLoader();
    }

    private static long counter_;

    private static Map aopProxyCache_ = new HashMap();

    public static Object createInstance(final Class targetClass) {
        AopProxy p = (AopProxy) aopProxyCache_.get(targetClass);
        if (p != null) {
            return p.create();
        }

        final ClassPool cp = ClassPoolUtil.getClassPool(targetClass);
        try {
            final CtClass cc = cp.get(targetClass.getName());
            final String enhancedClassName = targetClass.getName()
                    + "__Enhanced__" + counter_++ + "__";
            final CtClass enhancedCtClass = cp.makeClass(enhancedClassName, cc);
            final CtClass ifs = cp.get(RuntimeClass.class.getName());
            enhancedCtClass.setInterfaces(new CtClass[] { ifs });

            final byte[] bytes = enhancedCtClass.toBytecode();
            final Object o = defineClass(targetClass, enhancedClassName, bytes);
            Class enhancedClass = (Class) o;

            String[] methodNames = getPointcutMethodNames(enhancedClass);
            Pointcut pointcut = new PointcutImpl(methodNames);
            Aspect aspect = new AspectImpl(new RuntimeClassInterceptor(),
                    pointcut);
            AopProxy aopProxy = new AopProxy(enhancedClass,
                    new Aspect[] { aspect });
            aopProxyCache_.put(targetClass, aopProxy);
            Object instance = aopProxy.create();
            enhancedCtClass.detach();
            return instance;
        } catch (NotFoundException e) {
            throw new NotFoundRuntimeException(e);
        } catch (CannotCompileException e) {
            throw new CannotCompileRuntimeException(e);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    static String[] getPointcutMethodNames(Class enhancedClass) {
        List enhanceMethodNames = new ArrayList();
        enhanceMethodNames.add("defineMethod");

        for (Class clazz = enhancedClass; clazz != Object.class; clazz = clazz
                .getSuperclass()) {

            final Method[] methods = clazz.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                final int modifiers = method.getModifiers();
                // cannot override "final" or "static" methods.
                if (!Modifier.isFinal(modifiers)
                        && !Modifier.isStatic(modifiers)) {
                    enhanceMethodNames.add(method.getName());
                }
            }
        }
        if (enhanceMethodNames.isEmpty()) {
            return new String[] { ".*" };
        } else {
            String[] methodNames = new String[enhanceMethodNames.size()];
            enhanceMethodNames.toArray(methodNames);
            return methodNames;
        }
    }

}
