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

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockUIComponentBase;

/**
 * @author manhole
 */
public class UIComponentUtilTest extends TestCase {

    public void testContainsAttributes_false() throws Exception {
        UIComponent component = new MockUIComponentBase();
        assertEquals(false, UIComponentUtil.containsAttributes(component,
                new String[] { "foo" }));
    }

    public void testContainsAttributes_true() throws Exception {
        UIComponent component = new MockUIComponentBase();
        component.getAttributes().put("foo", "something");
        assertEquals(true, UIComponentUtil.containsAttributes(component,
                new String[] { "foo" }));
    }

    public void testIsDisabled() throws Exception {
        {
            UIComponent component = new MockUIComponentBase();
            assertEquals(false, UIComponentUtil.isDisabled(component));
        }
        {
            HtmlInputText component = new HtmlInputText();
            component.setDisabled(true);
            assertEquals(true, UIComponentUtil.isDisabled(component));
        }
        {
            HtmlInputText component = new HtmlInputText();
            component.setDisabled(false);
            assertEquals(false, UIComponentUtil.isDisabled(component));
        }
        {
            HtmlInputText component = new HtmlInputText();
            component.getAttributes().put("disabled", Boolean.TRUE);
            assertEquals(true, UIComponentUtil.isDisabled(component));
        }
    }

    public void testGetStringAttribute() throws Exception {
        HtmlInputText component = new HtmlInputText();
        component.getAttributes().put("onblur", "aaaa");
        assertEquals("aaaa", UIComponentUtil.getStringAttribute(component,
                "onblur"));
    }

    public void testGetPrimitiveBooleanAttribute() throws Exception {
        HtmlInputText component = new HtmlInputText();
        component.getAttributes().put("readonly", Boolean.TRUE);
        assertEquals(true, UIComponentUtil.getPrimitiveBooleanAttribute(component,
                "readonly"));
    }

}
