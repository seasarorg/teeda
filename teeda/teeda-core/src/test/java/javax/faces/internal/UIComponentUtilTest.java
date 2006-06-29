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
package javax.faces.internal;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.html.HtmlInputText;

import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author manhole
 */
public class UIComponentUtilTest extends TeedaTestCase {

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
        assertEquals(true, UIComponentUtil.getPrimitiveBooleanAttribute(
                component, "readonly"));
    }

    public void testGetPrimitiveIntAttribute() throws Exception {
        HtmlInputText component = new HtmlInputText();
        assertEquals(UIDefaultAttribute.DEFAULT_INT, UIComponentUtil
                .getPrimitiveIntAttribute(component, "size"));
        component.setSize(321);
        assertEquals(321, UIComponentUtil.getPrimitiveIntAttribute(component,
                "size"));
    }

    public void testGetLabel_labelReturned() {
        MockHtmlInputText component = new MockHtmlInputText();
        component.setId("aaa");
        component.setLabel("bbb");
        assertEquals("bbb", UIComponentUtil.getLabel(component));
    }

    public void testGetLabel_idReturned() {
        UIComponent component = new HtmlInputText();
        component.setId("aaa");
        assertEquals("aaa", UIComponentUtil.getLabel(component));
    }

    public void testGetAllAttributesAndProperties() throws Exception {
        MockNoComponent component = new MockNoComponent();
        component.setHoge("aaa");
        Map map = UIComponentUtil.getAllAttributesAndProperties(component);
        assertEquals("aaa", map.get("hoge"));
    }

    public void testGetAllAttributesAndProperties_setIgnore() throws Exception {
        MockNoComponent component = new MockNoComponent();
        component.setHoge("aaa");
        IgnoreComponent ignore = new IgnoreComponent();
        ignore.addIgnoreComponentName("hoge");
        ignore.addIgnoreComponentName("id");
        Map map = UIComponentUtil.getAllAttributesAndProperties(component, ignore);
        assertTrue(map.size() == 0);
    }

    public static class MockHtmlInputText extends HtmlInputText {

        private String label_;

        public String getLabel() {
            return label_;
        }

        public void setLabel(String label) {
            label_ = label;
        }
    }

    public static class MockNoComponent extends UIComponentBase {

        private String hoge;

        public String getHoge() {
            return hoge;
        }

        public void setHoge(String hoge) {
            this.hoge = hoge;
        }

        public String getFamily() {
            return "none";
        }

    }
}
