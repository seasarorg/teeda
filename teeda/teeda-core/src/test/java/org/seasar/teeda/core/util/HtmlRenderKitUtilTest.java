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

import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class HtmlRenderKitUtilTest extends TeedaTestCase {

    public void testRemoveSemiColon_singleValues() throws Exception {
        String[] strs = HtmlRenderKitUtil
                .removeSemiColon(new String[] { "aaa;" });
        assertEquals("aaa", strs[0]);
    }

    public void testRemoveSemiColon_multipleValues() throws Exception {
        String[] strs = HtmlRenderKitUtil.removeSemiColon(new String[] { "aaa",
                "bbb;" });
        assertEquals("aaa", strs[0]);
        assertEquals("bbb", strs[1]);
    }

    public void testGetContentTypeFromFacesContext() throws Exception {
        getExternalContext().getRequestMap().put("Accept", "hoge");
        assertEquals("hoge", getExternalContext().getRequestMap().get("Accept"));
    }

    public void testIsHtmlContentType() throws Exception {
        assertFalse(HtmlRenderKitUtil.isHtmlContentType("hoge"));
        assertTrue(HtmlRenderKitUtil.isHtmlContentType("text/html"));
        assertTrue(HtmlRenderKitUtil.isHtmlContentType("*/*"));
        assertTrue(HtmlRenderKitUtil.isHtmlContentType("html/foo"));
    }

    public void testIsXmlContentType() throws Exception {
        assertFalse(HtmlRenderKitUtil.isXmlContentType("hoge"));
        assertTrue(HtmlRenderKitUtil.isXmlContentType("application/xhtml+xml"));
        assertTrue(HtmlRenderKitUtil.isXmlContentType("application/xml"));
        assertTrue(HtmlRenderKitUtil.isXmlContentType("text/xml"));
    }

    public void testGetContentType() throws Exception {
        assertNull(HtmlRenderKitUtil.getContentType("hoge"));
        assertEquals("text/html", HtmlRenderKitUtil
                .getContentType("hoge, text/html, foo"));
    }
}
