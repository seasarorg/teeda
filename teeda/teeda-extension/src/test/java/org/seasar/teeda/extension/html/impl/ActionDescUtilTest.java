/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import java.util.Set;

import junit.framework.TestCase;

import org.seasar.teeda.extension.html.impl.page.FooAction;

/**
 * @author higa
 *
 */
public class ActionDescUtilTest extends TestCase {

    public void testGetActionMethodNames() throws Exception {
        Set names = ActionDescUtil.getActionMethodNames(FooAction.class);
        assertTrue(names.contains("doAaa"));
        assertFalse(names.contains("xxx"));
    }

    public void testGetActionMethodNames_2() throws Exception {
        Set names = ActionDescUtil.getActionMethodNames(Hoge.class);
        assertTrue(names.contains("aaa"));
        assertTrue(names.contains("bbb"));
        assertFalse(names.contains("xxx"));
    }

    public static class Hoge {
        public void aaa() {
        }

        public String bbb() {
            return null;
        }
    }
}