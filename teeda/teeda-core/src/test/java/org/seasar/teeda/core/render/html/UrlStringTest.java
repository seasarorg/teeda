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
package org.seasar.teeda.core.render.html;

import java.util.Set;

import junit.framework.TestCase;

/**
 * @author manhole
 */
public class UrlStringTest extends TestCase {

    public void testParseAndGetParameter() throws Exception {
        // ## Arrange ##
        UrlString url = new UrlString();

        // ## Act ##
        url.parse("aaa?1=2&3=4");

        // ## Assert ##
        assertEquals("aaa", url.getPath());
        assertEquals("2", url.getParameter("1"));
        assertEquals("4", url.getParameter("3"));
        assertEquals(null, url.getParameter("2"));
    }

    public void testGetParameterNames() throws Exception {
        // ## Arrange ##
        UrlString url = new UrlString();
        url.parse("aaa?1=2&2=3&1=3");

        // ## Act ##
        Set parameterNames = url.getParameterNames();

        // ## Assert ##
        assertEquals(2, parameterNames.size());
        assertEquals(true, parameterNames.contains("1"));
        assertEquals(true, parameterNames.contains("2"));
        assertEquals(false, parameterNames.contains("3"));
    }

    public void testParseAndGetParameters() throws Exception {
        // ## Arrange ##
        UrlString url = new UrlString();

        // ## Act ##
        url.parse("aaa?1=2&1=3");

        // ## Assert ##
        assertEquals("aaa", url.getPath());
        assertEquals("2", url.getParameter("1"));
        String[] values = url.getParameters("1");
        assertEquals(2, values.length);
        assertEquals("2", values[0]);
        assertEquals("3", values[1]);
        assertEquals(null, url.getParameters("4"));
    }

    public void testIsIdentical_True1() throws Exception {
        // ## Arrange ##
        UrlString url1 = new UrlString();
        url1.parse("aa");
        UrlString url2 = new UrlString();
        url2.parse("aa");

        // ## Act & Assert ##
        assertEquals(true, url1.isIdentical(url2));
    }

    public void testIsIdentical_True2() throws Exception {
        // ## Arrange ##
        UrlString url1 = new UrlString();
        url1.parse("aa?1=2&b=c&1=2");
        UrlString url2 = new UrlString();
        url2.parse("aa?1=2&b=c&1=2");

        // ## Act & Assert ##
        assertEquals(true, url1.isIdentical(url2));
    }

    public void testIsIdentical_False1() throws Exception {
        // ## Arrange ##
        UrlString url1 = new UrlString();
        url1.parse("ab");
        UrlString url2 = new UrlString();
        url2.parse("aa");

        // ## Act & Assert ##
        assertEquals(false, url1.isIdentical(url2));
    }

    public void testIsIdentical_False2() throws Exception {
        // ## Arrange ##
        UrlString url1 = new UrlString();
        url1.parse("aa?1=2");
        UrlString url2 = new UrlString();
        url2.parse("aa?1=3");

        // ## Act & Assert ##
        assertEquals(false, url1.isIdentical(url2));
    }

    public void testIsIdentical_False3() throws Exception {
        // ## Arrange ##
        UrlString url1 = new UrlString();
        url1.parse("aa?1=1&1=2&1=4");
        UrlString url2 = new UrlString();
        url2.parse("aa?1=1&1=3&1=4");

        // ## Act & Assert ##
        assertEquals(false, url1.isIdentical(url2));
    }

    public void testIsIdentical_False4() throws Exception {
        // ## Arrange ##
        UrlString url1 = new UrlString();
        url1.parse("aa?1=1");
        UrlString url2 = new UrlString();
        url2.parse("aa?1=1&2=2");

        // ## Act & Assert ##
        assertEquals(false, url1.isIdentical(url2));
    }

}
