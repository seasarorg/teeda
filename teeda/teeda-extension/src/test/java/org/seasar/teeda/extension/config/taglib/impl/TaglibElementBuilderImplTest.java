/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.config.taglib.impl;

import java.io.InputStream;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.seasar.framework.unit.S2FrameworkTestCase;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.extension.config.taglib.TaglibElementBuilder;
import org.seasar.teeda.extension.config.taglib.element.TagElement;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;

/**
 * @author higa
 *
 */
public class TaglibElementBuilderImplTest extends S2FrameworkTestCase {

    private static final String TLD = "TaglibElementBuilderImplTest.tld";

    public void testBuild() throws Exception {
        TaglibElementBuilder builder = new TaglibElementBuilderImpl();
        final String path = convertPath(TLD);
        InputStream is = ResourceUtil.getResourceAsStream(path);
        TaglibElement taglibElement = builder.build(is, path);
        assertNotNull("1", taglibElement);
        assertEquals("2", "http://example.org/example", taglibElement.getUri());
        TagElement tagElement = taglibElement.getTagElement("foo");
        assertEquals("3", FooTag.class, tagElement.getTagClass());
    }

    public static class FooTag implements Tag {

        public void setPageContext(PageContext pageContext) {
        }

        public void setParent(Tag tag) {
        }

        public Tag getParent() {
            return null;
        }

        public int doStartTag() throws JspException {
            return SKIP_BODY;
        }

        public int doEndTag() throws JspException {
            return SKIP_BODY;
        }

        public void release() {
        }
    }

}
