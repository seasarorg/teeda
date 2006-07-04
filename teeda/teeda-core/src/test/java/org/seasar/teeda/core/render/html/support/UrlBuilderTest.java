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
package org.seasar.teeda.core.render.html.support;

import junit.framework.TestCase;

import org.seasar.teeda.core.render.html.support.UrlBuilder;
import org.seasar.teeda.core.unit.UrlDiff;

/**
 * @author manhole
 */
public class UrlBuilderTest extends TestCase {

    public void testBase() throws Exception {
        // ## Arrange ##
        UrlBuilder urlBuilder = new UrlBuilder();
        urlBuilder.setBase("a");

        // ## Act & Assert ##
        assertEquals("a", urlBuilder.build());
    }

    public void testBuildWithParams() throws Exception {
        // ## Arrange ##
        UrlBuilder urlBuilder = new UrlBuilder();
        urlBuilder.setBase("a");

        // ## Act ##
        urlBuilder.add("1", "2");
        urlBuilder.add("3", "4");
        String actual = urlBuilder.build();

        // ## Assert ##
        UrlDiff urlDiff = new UrlDiff("a?1=2&3=4", actual);
        assertEquals(actual, true, urlDiff.isIdentical());
    }

    public void testBuildWithParamsWhenBaseContainsQueryString()
            throws Exception {
        // ## Arrange ##
        UrlBuilder urlBuilder = new UrlBuilder();
        urlBuilder.setBase("a?b=c");

        // ## Act ##
        urlBuilder.add("1", "2");
        urlBuilder.add("3", "4");
        String actual = urlBuilder.build();

        // ## Assert ##
        assertEquals("a?b=c&1=2&3=4", actual);
    }

    public void testBuildWithParams_SameKey() throws Exception {
        // ## Arrange ##
        UrlBuilder urlBuilder = new UrlBuilder();
        urlBuilder.setBase("a");

        // ## Act ##
        urlBuilder.add("1", "2");
        urlBuilder.add("1", "4");
        String actual = urlBuilder.build();

        // ## Assert ##
        UrlDiff urlDiff = new UrlDiff("a?1=2&1=4", actual);
        assertEquals(actual, true, urlDiff.isIdentical());
    }

}
