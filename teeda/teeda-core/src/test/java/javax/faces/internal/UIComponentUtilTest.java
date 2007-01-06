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
package javax.faces.internal;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.internal.web.foo.FooPage;

import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.convention.impl.NamingConventionImpl;
import org.seasar.framework.util.ClassUtil;
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

    public void testGetLabel_labelProperties() {
        NamingConventionImpl nc = (NamingConventionImpl) getContainer()
                .getComponent(NamingConvention.class);
        nc.addRootPackageName(ClassUtil.getPackageName(getClass()));
        register(FooPage.class, "foo_fooPage");
        FacesConfigOptions.setDefaultSuffix(".html");
        getFacesContext().getViewRoot().setViewId(
                nc.getViewRootPath() + "/foo/foo.html");
        MockHtmlInputText component = new MockHtmlInputText();
        component.setId("aaa");
        assertEquals("AAA", UIComponentUtil.getLabel(component));
    }

    public void testGetLabel_idReturned() {
        NamingConventionImpl nc = (NamingConventionImpl) getContainer()
                .getComponent(NamingConvention.class);
        nc.addRootPackageName(ClassUtil.getPackageName(getClass()));
        getFacesContext().getViewRoot().setViewId(
                nc.getViewRootPath() + "/foo/foo.html");
        FacesConfigOptions.setDefaultSuffix(".html");
        UIComponent component = new HtmlInputText();
        component.setId("xxx");
        assertEquals("xxx", UIComponentUtil.getLabel(component));
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

}
