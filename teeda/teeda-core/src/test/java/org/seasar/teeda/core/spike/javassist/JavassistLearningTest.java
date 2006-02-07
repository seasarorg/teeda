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
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.LoaderClassPath;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import junit.framework.TestCase;

import org.seasar.framework.aop.Aspect;
import org.seasar.framework.aop.Pointcut;
import org.seasar.framework.aop.impl.AspectImpl;
import org.seasar.framework.aop.impl.PointcutImpl;
import org.seasar.framework.aop.javassist.ClassPoolUtil;
import org.seasar.framework.aop.proxy.AopProxy;
import org.seasar.teeda.core.mock.MockUIComponentBase;

/**
 * @author manhole
 */
public class JavassistLearningTest extends TestCase {

    private ClassLoader classLoader_;

    protected void setUp() throws Exception {
        super.setUp();
        classLoader_ = new MyClassLoader(Thread.currentThread()
                .getContextClassLoader());
    }

    public void testGetSuperClass() throws Exception {
        ClassPool cp = getClassPool();
        CtClass cc = cp.get("javax.faces.component.UIComponentBase");
        assertEquals("javax.faces.component.UIComponentBase", cc.getName());
        CtClass superClass = cc.getSuperclass();
        assertEquals("javax.faces.component.UIComponent", superClass.getName());
    }

    public void testGetSuperClassByReflectionAPI() throws Exception {
        Class clazz = Class.forName("javax.faces.component.UIComponentBase");
        assertEquals("javax.faces.component.UIComponentBase", clazz.getName());
        Class superClass = clazz.getSuperclass();
        assertEquals("javax.faces.component.UIComponent", superClass.getName());
    }

    public void testModifyToStringMethod() throws Exception {
        ClassPool cp = getClassPool();
        CtClass cc = cp.get("org.seasar.teeda.core.mock.MockUIComponentBase");

        CtMethod m = CtNewMethod.make("public String toString() {"
                + "  return \"abcde\"; }", cc);
        cc.addMethod(m);

        UIComponentBase component = (UIComponentBase) cc.toClass(classLoader_)
                .newInstance();
        assertEquals("abcde", component.toString());
    }

    public void testAddFieldAndMethod() throws Exception {
        ClassPool cp = getClassPool();
        CtClass cc = cp.get("org.seasar.teeda.core.mock.MockUIComponentBase");

        CtField f1 = CtField.make("private int calls_ = 0;", cc);
        cc.addField(f1);

        CtMethod m = CtNewMethod.make("public String toString() {"
                + "  return \"\" + calls_++; }", cc);
        cc.addMethod(m);

        UIComponentBase component = (UIComponentBase) cc.toClass(classLoader_)
                .newInstance();
        assertEquals("0", component.toString());
        assertEquals("1", component.toString());
        assertEquals("2", component.toString());
    }

    public void testModifyMethod() throws Exception {
        ClassPool cp = getClassPool();
        CtClass cc = cp.get("org.seasar.teeda.core.mock.MockUIComponentBase");

        {
            CtField f1 = CtField.make("private java.util.Map callsMap_ = "
                    + "new java.util.HashMap();", cc);
            cc.addField(f1);
        }
        {
            CtMethod m = CtNewMethod.make(
                    "public java.util.Map getCallsMap() {"
                            + "  return callsMap_;" + "}", cc);
            cc.addMethod(m);
        }
        {
            CtMethod m = CtNewMethod.make(
                    "public void decode(javax.faces.context.FacesContext context) {"
                            + "callsMap_.put(\"decode\", new Integer(1));"
                            + "}", cc);
            cc.addMethod(m);
        }

        UIComponentBase component = (UIComponentBase) cc.toClass(classLoader_)
                .newInstance();

        Method getCallsMapMethod = component.getClass().getMethod(
                "getCallsMap", new Class[] {});
        Map callsMap = (Map) getCallsMapMethod.invoke(component, null);
        assertNotNull(callsMap);
        assertEquals(true, callsMap.isEmpty());

        component.decode((FacesContext) null);
        assertEquals(false, callsMap.isEmpty());
    }

    public void testEnhanceClass() throws Exception {
        // ## Arrange ##
        final Class targetClass = MockUIComponentBase.class;
        ClassPool cp = ClassPoolUtil.getClassPool(targetClass);
        CtClass cc = cp.get(targetClass.getName());
        final String enhancedClassName = targetClass.getName() + "__E1__";
        CtClass enhancedCtClass = cp.makeClass(enhancedClassName, cc);
        CtClass ifs = cp.get(Foo.class.getName());
        enhancedCtClass.setInterfaces(new CtClass[] { ifs });

        final byte[] bytes = enhancedCtClass.toBytecode();
        final Object o = RuntimeClassUtil.defineClass(targetClass,
                enhancedClassName, bytes);
        Class enhancedClass = (Class) o;
        enhancedCtClass.detach();
        Object instance = enhancedClass.newInstance();

        MockUIComponentBase component = (MockUIComponentBase) instance;
        Foo foo = (Foo) component;

        component.setId("abc");
        assertEquals("abc", component.getId());
        try {
            foo.foo();
            fail();
        } catch (AbstractMethodError e) {
        }
    }

    public void testEnhanceClassAndWeaveInterceptor() throws Exception {
        // ## Arrange ##
        final Class targetClass = MockUIComponentBase.class;
        ClassPool cp = ClassPoolUtil.getClassPool(targetClass);
        CtClass cc = cp.get(targetClass.getName());
        final String enhancedClassName = targetClass.getName() + "__E2__";
        CtClass enhancedCtClass = cp.makeClass(enhancedClassName, cc);
        CtClass ifs = cp.get(Foo.class.getName());
        enhancedCtClass.setInterfaces(new CtClass[] { ifs });

        final byte[] bytes = enhancedCtClass.toBytecode();
        final Object o = RuntimeClassUtil.defineClass(targetClass,
                enhancedClassName, bytes);
        Class enhancedClass = (Class) o;

        final SnatchInterceptor snatchInterceptor = new SnatchInterceptor();
        snatchInterceptor.setTarget(new FooImpl());
        Pointcut pointcut = new PointcutImpl(new String[] { ".*" });
        Aspect aspect = new AspectImpl(snatchInterceptor, pointcut);
        AopProxy aopProxy = new AopProxy(enhancedClass, new Aspect[] { aspect });

        Object instance = aopProxy.create();
        enhancedCtClass.detach();

        MockUIComponentBase component = (MockUIComponentBase) instance;
        Foo foo = (Foo) instance;

        component.setId("abc");
        assertEquals("abc", component.getId());
        assertEquals("fooo123", foo.foo());
    }

    private ClassPool getClassPool() {
        ClassPool classPool = new ClassPool(null);
        classPool.appendClassPath(new LoaderClassPath(Thread.currentThread()
                .getContextClassLoader()));
        // classPool.appendSystemPath();
        return classPool;
    }

    public static interface Foo {
        public String foo();
    }

    private static class FooImpl implements Foo {
        public String foo() {
            return "fooo123";
        }
    }

    private static class MyClassLoader extends ClassLoader {
        public MyClassLoader() {
        }

        public MyClassLoader(ClassLoader cl) {
            super(cl);
        }
    }

}
