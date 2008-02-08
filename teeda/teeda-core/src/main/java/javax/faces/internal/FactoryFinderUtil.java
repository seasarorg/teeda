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
package javax.faces.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.render.RenderKitFactory;

/**
 * @author shot
 *
 * This class might be changed without notice. Please do not use it
 * excluding the JSF specification part.
 */
public class FactoryFinderUtil {

    private static final List VALID_FACTORY_NAMES = new ArrayList();

    private static final Map ABSTRACT_FACTORY_CLASSES = new HashMap();

    static {
        VALID_FACTORY_NAMES.add(FactoryFinder.APPLICATION_FACTORY);
        VALID_FACTORY_NAMES.add(FactoryFinder.FACES_CONTEXT_FACTORY);
        VALID_FACTORY_NAMES.add(FactoryFinder.LIFECYCLE_FACTORY);
        VALID_FACTORY_NAMES.add(FactoryFinder.RENDER_KIT_FACTORY);
        ABSTRACT_FACTORY_CLASSES.put(FactoryFinder.APPLICATION_FACTORY,
                ApplicationFactory.class);
        ABSTRACT_FACTORY_CLASSES.put(FactoryFinder.FACES_CONTEXT_FACTORY,
                FacesContextFactory.class);
        ABSTRACT_FACTORY_CLASSES.put(FactoryFinder.LIFECYCLE_FACTORY,
                LifecycleFactory.class);
        ABSTRACT_FACTORY_CLASSES.put(FactoryFinder.RENDER_KIT_FACTORY,
                RenderKitFactory.class);
    }

    private FactoryFinderUtil() {
    }

    public static void checkValidFactoryNames(String factoryName) {
        if (!VALID_FACTORY_NAMES.contains(factoryName)) {
            throw new IllegalArgumentException(
                    "Factory name can not be identified.");
        }
    }

    public static ClassLoader getClassLoader() throws FacesException {
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        if (classLoader == null) {
            classLoader = FactoryFinderUtil.class.getClassLoader();
        }
        if (classLoader == null) {
            throw new FacesException();
        }
        return classLoader;
    }

    public static Class getAbstractFactoryClass(String factoryName) {
        return (Class) ABSTRACT_FACTORY_CLASSES.get(factoryName);
    }

    public static Object createFactoryInstance(String factoryName,
            List classNames) {

        ClassLoader loader = getClassLoader();
        Class abstractFactoryClass = getAbstractFactoryClass(factoryName);
        Object current = null;
        Class implClass = null;
        for (Iterator classNamesIterator = classNames.iterator(); classNamesIterator
                .hasNext();) {
            String implClassName = (String) classNamesIterator.next();
            try {
                implClass = loader.loadClass(implClassName);
            } catch (ClassNotFoundException e) {
                throw new FacesException(e);
            }
            if (!abstractFactoryClass.isAssignableFrom(implClass)) {
                throw new IllegalArgumentException("Class " + implClassName
                        + " is no " + abstractFactoryClass.getName());
            }
            current = getCurrentFactoryInstance(implClass,
                    abstractFactoryClass, current);
        }
        return current;
    }

    public static Object getCurrentFactoryInstance(Class implClass,
            Class abstractFactoryClass, Object current) {
        if (implClass == null) {
            if (current == null) {
                throw new IllegalArgumentException();
            } else {
                return current;
            }
        }
        Constructor constructor = getConstructor(implClass,
                new Class[] { abstractFactoryClass });
        if (constructor != null) {
            current = newInstanceByConstructor(constructor,
                    new Object[] { current });
        } else {
            current = newInstance(implClass);
        }
        return current;
    }

    public static Constructor getConstructor(Class clazz, Class[] argTypes) {
        if (clazz == null || argTypes == null) {
            throw new IllegalArgumentException();
        }
        try {
            return clazz.getConstructor(argTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Object newInstance(Class clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException();
        }
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new FacesException(e);
        } catch (IllegalAccessException e) {
            throw new FacesException(e);
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
            throw new FacesException(e);
        } catch (IllegalAccessException e) {
            throw new FacesException(e);
        } catch (InvocationTargetException e) {
            throw new FacesException(e);
        }
    }

    public static boolean isAlreadySetFactory(Map factoryMap, String factoryName) {
        return (factoryMap != null && factoryMap.containsKey(factoryName));
    }

    public static ApplicationFactory getApplicationFactory() {
        return (ApplicationFactory) FactoryFinder
                .getFactory(FactoryFinder.APPLICATION_FACTORY);
    }

    public static FacesContextFactory getFacesContextFactory() {
        return (FacesContextFactory) FactoryFinder
                .getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
    }

    public static LifecycleFactory getLifecycleFactory() {
        return (LifecycleFactory) FactoryFinder
                .getFactory(FactoryFinder.LIFECYCLE_FACTORY);
    }

    public static RenderKitFactory getRenderKitFactory() {
        return (RenderKitFactory) FactoryFinder
                .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
    }

}
