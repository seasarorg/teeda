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
package org.seasar.teeda.core.taglib.html;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputSecret;

import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.mock.MockMethodBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author yone
 */
public class InputSecretTagTest extends TeedaTestCase {

    public void testGetComponentType() throws Exception {
        // # Arrange #
        InputSecretTag tag = new InputSecretTag();

        // # Act & Assert #
        assertEquals("javax.faces.HtmlInputSecret", tag.getComponentType());
    }

    public void testGetRenderType() throws Exception {
        // # Arrange #
        InputSecretTag tag = new InputSecretTag();

        // # Act & Assert #
        assertEquals("javax.faces.Secret", tag.getRendererType());
    }

    public void testSetProperties_All() throws Exception {
        // # Arrange #
        HtmlInputSecret component = createHtmlInputSecret();
        InputSecretTag tag = new InputSecretTag();
        MockApplication app = new MockApplicationImpl();
        app.addConverter("mock.converter",
                "org.seasar.teeda.core.mock.MockConverter");
        setApplication(app);
        app.addValidator("mock.validator",
                "org.seasar.teeda.core.mock.MockValidator");

        tag.setConverter("mock.converter");
        tag.setImmediate("true");
        tag.setRequired("true");
        tag.setValidator("#{mock.validator}");
        tag.setValue("value");
        tag.setValueChangeListener("#{mock.listener}");
        tag.setAccesskey("accesskey");
        tag.setAlt("alt");
        tag.setDir("dir");
        tag.setDisabled("true");
        tag.setLang("lang");
        tag.setOnblur("onblur");
        tag.setOnchange("onchange");
        tag.setOnclick("onclick");
        tag.setOndblclick("ondblclick");
        tag.setOnfocus("onfocus");
        tag.setOnkeydown("onkeydown");
        tag.setOnkeypress("onkeypress");
        tag.setOnkeyup("onkeyup");
        tag.setOnmousedown("onmousedown");
        tag.setOnmousemove("onmousemove");
        tag.setOnmouseout("onmouseout");
        tag.setOnmouseover("onmouseover");
        tag.setOnmouseup("onmouseup");
        tag.setOnselect("onselect");
        tag.setReadonly("true");
        tag.setRedisplay("true");
        tag.setSize("7");
        tag.setStyle("style");
        tag.setStyleClass("styleclass");
        tag.setTabindex("13");
        tag.setTitle("title");

        // # Act #
        tag.setProperties(component);

        assertTrue(component.getConverter() instanceof MockConverter);
        assertTrue(component.isImmediate());
        assertTrue(component.isRequired());
        assertTrue(component.getValidator() instanceof MockMethodBinding);
        assertEquals("#{mock.validator}", component.getValidator()
                .getExpressionString());
        assertEquals("value", component.getValue());
        assertTrue(component.getValueChangeListener() instanceof MockMethodBinding);
        assertEquals("#{mock.listener}", component.getValueChangeListener()
                .getExpressionString());
        assertEquals("accesskey", component.getAccesskey());
        assertEquals("alt", component.getAlt());
        assertEquals("dir", component.getDir());
        assertTrue(component.isDisabled());
        assertEquals("lang", component.getLang());
        assertEquals("onblur", component.getOnblur());
        assertEquals("onchange", component.getOnchange());
        assertEquals("onclick", component.getOnclick());
        assertEquals("ondblclick", component.getOndblclick());
        assertEquals("onfocus", component.getOnfocus());
        assertEquals("onkeydown", component.getOnkeydown());
        assertEquals("onkeypress", component.getOnkeypress());
        assertEquals("onkeyup", component.getOnkeyup());
        assertEquals("onmousedown", component.getOnmousedown());
        assertEquals("onmousemove", component.getOnmousemove());
        assertEquals("onmouseout", component.getOnmouseout());
        assertEquals("onmouseover", component.getOnmouseover());
        assertEquals("onmouseup", component.getOnmouseup());
        assertEquals("onselect", component.getOnselect());
        assertTrue(component.isReadonly());
        assertTrue(component.isRedisplay());
        assertEquals(7, component.getSize());
        assertEquals("style", component.getStyle());
        assertEquals("styleclass", component.getStyleClass());
        assertEquals("13", component.getTabindex());
        assertEquals("title", component.getTitle());
    }

    public void testRelease() throws Exception {
        // # Arrange #
        InputSecretTag tag = new InputSecretTag();
        tag.setRedisplay("true");

        // # Act #
        tag.release();

        // # Assert #
        assertEquals(null, tag.getRedisplay());
    }

    private HtmlInputSecret createHtmlInputSecret() {
        return (HtmlInputSecret) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlInputSecret();
    }
}
