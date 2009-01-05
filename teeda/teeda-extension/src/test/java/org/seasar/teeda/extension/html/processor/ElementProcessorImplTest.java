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
package org.seasar.teeda.extension.html.processor;

import java.util.HashMap;
import java.util.Map;

import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.extension.jsp.PageContextImpl;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author shot
 * 
 */
public class ElementProcessorImplTest extends TeedaExtensionTestCase {

    public void testComposeComponentTree() throws Exception {
        Map map = new HashMap();
        map.put("name", "aaa");
        MockTagSupport m = new MockTagSupport();
        ElementProcessorImpl processor = new ElementProcessorImpl(m.getClass(),
                map);
        MockFacesContext context = getFacesContext();
        PageContextImpl pageContext = getPageContext();
        MockUIComponentTag parentTag = new MockUIComponentTag();
        processor.composeComponentTree(context, pageContext, parentTag);
        Tag child = parentTag.getChild();
        assertTrue(child instanceof MockTagSupport);
        assertEquals("aaa", ((MockTagSupport) child).getName());
        parentTag = null;
        child = null;
    }

    public static class MockUIComponentTag extends UIComponentTag {

        private Tag child;

        public String getComponentType() {
            return null;
        }

        public String getRendererType() {
            return null;
        }

        public void setChild(Tag child) {
            this.child = child;
        }

        public Tag getChild() {
            return child;
        }
    }

    public static class MockTagSupport extends TagSupport {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setParent(Tag tag) {
            super.setParent(tag);
            ((MockUIComponentTag) tag).setChild(this);
        }

    }
}
