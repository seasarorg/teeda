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

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockUIComponentBase;

/**
 * @author manhole
 */
public class RuntimeClassTest extends TestCase {

    public void testCreateRuntimeInstance() throws Exception {
        Object instance = RuntimeClassUtil
                .createInstance(MockUIComponentBase.class);
        assertNotNull(instance);

        MockUIComponentBase component = (MockUIComponentBase) instance;
        RuntimeClass rc = (RuntimeClass) instance;

        component.setId("aa");
        assertEquals("aa", component.getId());

        rc.defineMethod(new Object() {
            public String execute() {
                return "abc123";
            }
        }, "getRendererType");
        assertEquals("abc123", component.getRendererType());
    }

    public static class A {
        public void foo() {
            bar("a");
        }

        public void bar(String s) {
        }

        protected String aaa() {
            return "aaa";
        }
    }

    public static class B extends A {
    }

    public void testFooByExecute() throws Exception {
        Object instance = RuntimeClassUtil.createInstance(A.class);
        assertNotNull(instance);

        final boolean[] calls = { false };
        RuntimeClass rt = (RuntimeClass) instance;
        rt.defineMethod(new Object() {
            public void execute(String s) {
                calls[0] = true;
            }
        }, "bar");

        A a = (A) instance;
        a.foo();

        assertEquals(true, calls[0]);
    }

    public void testFooByBar() throws Exception {
        Object instance = RuntimeClassUtil.createInstance(A.class);
        assertNotNull(instance);

        final boolean[] calls = { false };
        RuntimeClass rt = (RuntimeClass) instance;
        rt.defineMethod(new Object() {
            public void bar(String s) {
                calls[0] = true;
            }
        }, "bar");

        A a = (A) instance;
        a.foo();

        assertEquals(true, calls[0]);
    }

    // XXX
    public void pend_testProtected() throws Exception {
        Object instance = RuntimeClassUtil.createInstance(A.class);

        RuntimeClass rt = (RuntimeClass) instance;
        rt.defineMethod(new Object() {
            public String aaa() {
                return "abc";
            }
        }, "aaa");

        A a = (A) instance;
        String result = a.aaa();

        assertEquals("abc", result);
    }

    public void testFooByExecuteExtends() throws Exception {
        Object instance = RuntimeClassUtil.createInstance(B.class);
        assertNotNull(instance);

        final boolean[] calls = { false };
        RuntimeClass rt = (RuntimeClass) instance;
        rt.defineMethod(new Object() {
            public void execute(String s) {
                calls[0] = true;
            }
        }, "bar");

        B b = (B) instance;
        b.foo();

        assertEquals(true, calls[0]);
    }

}
