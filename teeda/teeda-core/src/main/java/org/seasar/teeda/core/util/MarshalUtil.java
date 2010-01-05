/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import javax.faces.FacesException;

import org.seasar.framework.exception.IllegalAccessRuntimeException;
import org.seasar.framework.exception.InstantiationRuntimeException;
import org.seasar.framework.exception.InvocationTargetRuntimeException;
import org.seasar.framework.util.ClassUtil;

/**
 * @author shot
 */
public class MarshalUtil {

    public static Object createMarshalInstance(String className,
            Class beforeClass, Object current) {
        return createMarshalInstance(ClassUtil.forName(className), beforeClass,
                current);
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
            current = ClassUtil.newInstance(clazz);
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
