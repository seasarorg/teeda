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
package org.seasar.teeda.extension.html.impl;

import junit.framework.TestCase;

/**
 * @author higa
 *
 */
public class DefaultPageAutoNamingTest extends TestCase {
	
    public void testConvertToPageName() throws Exception {
        DefaultHtmlAutoNaming naming = new DefaultHtmlAutoNaming();
        assertEquals("1", "hogePage", naming.convertToPageName("/html/hoge.html"));
        assertEquals("2", "aaa_hogePage", naming.convertToPageName("/html/aaa/hoge.html"));
        assertEquals("3", "aaa_bbb_hogePage", naming.convertToPageName("/html/aaa/bbb/hoge.html"));
    }
    
    public void testDefault() throws Exception {
        DefaultHtmlAutoNaming naming = new DefaultHtmlAutoNaming();
        assertEquals("/html", naming.getHtmlRootPath());
        assertEquals(".html", naming.getHtmlExtension());
        assertEquals("Action", naming.getActionSuffix());
        assertEquals("Page", naming.getPageSuffix());
    }

    public void testConvertToPageNameAsDefault() throws Exception {
        DefaultHtmlAutoNaming naming = new DefaultHtmlAutoNaming();
        assertEquals("hogePage", naming.convertToPageName("/html/hoge.html"));
        assertEquals("hoge_fooPage", naming
                .convertToPageName("/html/hoge/foo.html"));
    }

    public void testConvertToActionNameAsDefault() throws Exception {
        DefaultHtmlAutoNaming naming = new DefaultHtmlAutoNaming();
        assertEquals("fooAction", naming.convertToActionName("/html/foo.html"));
        assertEquals("foo_barAction", naming
                .convertToActionName("/html/foo/bar.html"));
    }

    public void testConvertIfChange() throws Exception {
        DefaultHtmlAutoNaming naming = new DefaultHtmlAutoNaming();
        naming.setHtmlRootPath("/pages");
        naming.setHtmlExtension(".xhtml");
        naming.setActionSuffix("Executer");
        naming.setPageSuffix("Pages");
        assertEquals("hogePages", naming.convertToPageName("/pages/hoge.xhtml"));
        assertEquals("html_hoge_fooPages", naming
                .convertToPageName("/pages/html/hoge/foo.xhtml"));
        assertEquals("fooExecuter", naming.convertToActionName("/pages/foo.xhtml"));
        assertEquals("html_foo_barExecuter", naming
                .convertToActionName("/pages/html/foo/bar.xhtml"));
    }

}