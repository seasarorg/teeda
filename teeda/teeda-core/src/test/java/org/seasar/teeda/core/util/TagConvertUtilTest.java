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
package org.seasar.teeda.core.util;

import junit.framework.TestCase;

public class TagConvertUtilTest extends TestCase {

    public void testConvertToSetter() throws Exception {
        String[] strs = TagConvertUtil.convertToSetter("converter-for-class");
        assertEquals("setConverterForClass", strs[0]);
        assertEquals("addConverterForClass", strs[1]);
    }

    public void testWithoutAnySetter() throws Exception {
        String s = "converter";
        String[] strs = TagConvertUtil.convertToSetter(s);
        assertEquals("setConverter", strs[0]);
        assertEquals("addConverter", strs[1]);
    }

    public void testSimple() throws Exception {
        String s = "redirect";
        String[] strs = TagConvertUtil.convertToSetter(s);
        assertEquals("setRedirect", strs[0]);
        assertEquals("addRedirect", strs[1]);
    }

}
