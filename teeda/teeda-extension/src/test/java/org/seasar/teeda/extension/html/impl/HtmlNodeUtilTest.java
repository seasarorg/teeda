/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * @author higa
 *
 */
public class HtmlNodeUtilTest extends TestCase {

    public void testGetCompleteTagString() throws Exception {
        assertEquals("<hoge></hoge>", HtmlNodeUtil.getCompleteTagString("hoge",
                new HashMap()));

        Map props = new HashMap();
        props.put("type", "text");
        assertEquals("2", "<input type=\"text\" />", HtmlNodeUtil
                .getCompleteTagString("input", props));
    }

    public void testStartTagString() throws Exception {
        assertEquals("1", "<hoge>", HtmlNodeUtil.getStartTagString("hoge",
                new HashMap()));

        Map props = new HashMap();
        props.put("id", "aaa");
        assertEquals("2", "<hoge id=\"aaa\">", HtmlNodeUtil.getStartTagString(
                "hoge", props));
    }

    public void testEndTagString() throws Exception {
        assertEquals("1", "</hoge>", HtmlNodeUtil.getEndTagString("hoge"));
    }

    public void testStartTagString_emptyElementSupport() throws Exception {
        assertEquals("1", "<br />", HtmlNodeUtil.getStartTagString("br",
                new HashMap()));
        Map props = new HashMap();
        props.put("clear", "none");
        assertEquals("2", "<br clear=\"none\" />", HtmlNodeUtil
                .getCompleteTagString("br", props));
    }
}