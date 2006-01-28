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
package org.seasar.teeda.core.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.FacesException;

import org.seasar.framework.exception.ClassNotFoundRuntimeException;
import org.seasar.framework.exception.IllegalAccessRuntimeException;
import org.seasar.framework.exception.InstantiationRuntimeException;
import org.seasar.framework.exception.InvocationTargetRuntimeException;

public final class ClassUtil {

    private static Map typeNameToPrimitiveMap_ = new HashMap();

    static {
        typeNameToPrimitiveMap_.put(Character.TYPE.getName(), Character.TYPE);
        typeNameToPrimitiveMap_.put(Byte.TYPE.getName(), Byte.TYPE);
        typeNameToPrimitiveMap_.put(Short.TYPE.getName(), Short.TYPE);
        typeNameToPrimitiveMap_.put(Integer.TYPE.getName(), Integer.TYPE);
        typeNameToPrimitiveMap_.put(Long.TYPE.getName(), Long.TYPE);
        typeNameToPrimitiveMap_.put(Double.TYPE.getName(), Double.TYPE);
        typeNameToPrimitiveMap_.put(Float.TYPE.getName(), Float.TYPE);
        typeNameToPrimitiveMap_.put(Boolean.TYPE.getName(), Boolean.TYPE);
    }

    private ClassUtil() {
    }

    public static Class forName(String className)
            throws ClassNotFoundRuntimeException {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            return Class.forName(className, true, loader);
        } catch (ClassNotFoundException ignore) {
            try {
                return Class.forName(className, true, ClassUtil.class
                        .getClassLoader());
            } catch (ClassNotFoundException e) {
                throw new ClassNotFoundRuntimeException(e);
            }
        }
    }

    public static Object newInstance(Class clazz)
            throws InstantiationRuntimeException, IllegalAccessRuntimeException {
        return org.seasar.framework.util.ClassUtil.newInstance(clazz);
    }

    public static Object newInstance(String className)
            throws ClassNotFoundRuntimeException,
            InstantiationRuntimeException, IllegalAccessRuntimeException {

        return newInstance(forName(className));
    }

    public static boolean isAssignableFrom(Class toClass, Class fromClass) {
        return org.seasar.framework.util.ClassUtil.isAssignableFrom(toClass,
                fromClass);
    }

    public static Class getPrimitiveClass(Class clazz) {
        return org.seasar.framework.util.ClassUtil.getPrimitiveClass(clazz);
    }

    public static Class getWrapperClass(Class clazz) {
        return org.seasar.framework.util.ClassUtil.getWrapperClass(clazz);
    }
    
    public static Class getWrapperClassByTypeName(String typeName) {
        return (Class) typeNameToPrimitiveMap_.get(typeName);
    }

    public static Class getWrapperClassIfPrimitiveTypeName(String typeName){
        Class ret = getWrapperClassByTypeName(typeName);
        if (ret != null) {
            return ret;
        }
        return forName(typeName);
    }

    public static Object createMarshalInstance(String className,
            Class beforeClass, Object current) {
        return createMarshalInstance(forName(className), beforeClass, current);
    }

    public static Object createMarshalInstance(Class clazz, Class beforeClass,
            Object current) {
        if (clazz == null) {
            if (current == null) {
                throw new IllegalArgumentException();
            } else {
                return current;
            }
        }
        Constructor constructor = getConstructorWithNoException(clazz,
                new Class[] { beforeClass });
        if (constructor != null) {
            current = newInstanceByConstructor(constructor,
                    new Object[] { current });
        } else {
            current = newInstance(clazz);
        }
        return current;
    }

    public static Constructor getConstructorWithNoException(Class clazz,
            Class[] argTypes) {
        if (clazz == null || argTypes == null) {
            throw new IllegalArgumentException();
        }
        try {
            return clazz.getConstructor(argTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Object newInstanceByConstructor(Constructor constructor,
            Object[] args) {
        if (constructor == null || args == null) {
            throw new IllegalArgumentException();
        }
        try {
            return constructor.newInstance(args);
        } catch (IllegalArgumentException e) {
            throw new FacesException(e);
        } catch (InstantiationException e) {
            throw new InstantiationRuntimeException(constructor.getClass(), e);
        } catch (IllegalAccessException e) {
            throw new IllegalAccessRuntimeException(constructor.getClass(), e);
        } catch (InvocationTargetException e) {
            throw new InvocationTargetRuntimeException(constructor.getClass(),
                    e);
        }
    }

}
