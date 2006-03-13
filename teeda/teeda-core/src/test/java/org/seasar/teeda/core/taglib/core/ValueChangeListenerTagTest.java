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
package org.seasar.teeda.core.taglib.core;

import java.util.ArrayList;
import java.util.List;

import javax.faces.internal.PageContextUtil;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.seasar.teeda.core.mock.MockPageContext;
import org.seasar.teeda.core.mock.NullPageContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author yone
 */
public class ValueChangeListenerTagTest extends TeedaTestCase {

    public void testDoStartTag_typeIsNull() throws Exception {
        // # Arrange #
        ValueChangeListenerTag tag = new ValueChangeListenerTag();

        // # Act #
        try {
            tag.doStartTag();
            fail();
        } catch (JspException e) {
            String message = e.getMessage();
            // # Assert #
            assertNotNull(message);
            assertTrue(message.trim().length() > 0);
        }
    }

    public void testDoStartTag_NotNestTag() throws Exception {
        // # Arrange #
        ValueChangeListenerTag tag = new ValueChangeListenerTag();
        tag.setType("hoge");
        tag.setPageContext(new NullPageContext());

        // # Act #
        try {
            tag.doStartTag();
            fail();
        } catch (JspException e) {
            String message = e.getMessage();
            // # Assert #
            assertNotNull(message);
            assertTrue(message.trim().length() > 0);
        }
    }

    public void testDoStartTag_returnSKIP_BODY() throws Exception {
        // # Arrange #
        ViewTag view = new ViewTag();
        List list = new ArrayList();
        list.add(view);
        PageContext pageContext = new MockPageContext();
        PageContextUtil.setComponentStackAttribute(pageContext, list);

        ValueChangeListenerTag tag = new ValueChangeListenerTag();
        tag.setType("hoge");
        tag.setPageContext(pageContext);

        // # Act #
        assertEquals(Tag.SKIP_BODY, tag.doStartTag());
    }

    public void testRelease() throws Exception {
        // # Arrange #
        ValueChangeListenerTag tag = new ValueChangeListenerTag();
        tag.setType("hoge");
        
        // # Act #
        tag.release();
        
        // # Assert #
        assertEquals(null, tag.getType());
    }
    
}
