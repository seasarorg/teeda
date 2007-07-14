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
package org.seasar.teeda.ajax;

import junit.framework.TestCase;

import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.framework.unit.S2FrameworkTestCase;

/**
 * @author yone
 */
public class AjaxUtilTest extends S2FrameworkTestCase {
    /**
     * @see TestCase#setUp()
     */
    //protected void setUp() throws Exception {
    //super.setUp();
    //super.include("ajaxTest.dicon");
    //}
    protected void tearDown() throws Exception {
        super.tearDown();
        AjaxUtil.clear();
    }

    public void testSetContentType() throws Exception {
        MockHttpServletResponse response = getResponse();
        AjaxUtil.setContentType(response, null);
        assertEquals(AjaxConstants.CONTENT_TYPE_JSON, response.getContentType());

        response.setContentType(null);
        AjaxUtil.setContentType(response, "{\"value\":2,\"name\":\"a\"}");
        assertEquals(AjaxConstants.CONTENT_TYPE_JSON, response.getContentType());

        response.setContentType(null);
        AjaxUtil.setContentType(response,
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        assertEquals(AjaxConstants.CONTENT_TYPE_XML, response.getContentType());

        response.setContentType(null);
        AjaxUtil.setContentType(response, "<html><body></body></html>");
        assertEquals(AjaxConstants.CONTENT_TYPE_HTML, response.getContentType());

        response.setContentType(null);
        AjaxUtil.setContentType(response, "aaaaa");
        assertEquals(AjaxConstants.CONTENT_TYPE_TEXT, response.getContentType());
    }

    public void setUpSetContentTypeDicon() {
        include("contentTypeTest.dicon");
    }

    public void testSetContentTypeDicon() throws Exception {
        MockHttpServletResponse response = getResponse();
        AjaxUtil.setContentType(response, null);
        assertEquals("json/org; charset=UTF-8", response.getContentType());

        response.setContentType(null);
        AjaxUtil.setContentType(response, "{\"value\":2,\"name\":\"a\"}");
        assertEquals("json/org; charset=UTF-8", response.getContentType());

        response.setContentType(null);
        AjaxUtil.setContentType(response,
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        assertEquals("xml/org; charset=UTF-8", response.getContentType());

        response.setContentType(null);
        AjaxUtil.setContentType(response, "<html><body></body></html>");
        assertEquals("html/org; charset=UTF-8", response.getContentType());

        response.setContentType(null);
        AjaxUtil.setContentType(response, "aaaaa");
        assertEquals("text/org; charset=UTF-8", response.getContentType());
    }

}