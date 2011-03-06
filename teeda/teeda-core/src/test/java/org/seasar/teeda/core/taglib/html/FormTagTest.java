/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
import javax.faces.component.html.HtmlForm;

import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author yone
 */
public class FormTagTest extends TeedaTestCase {

    public void testGetComponentType() throws Exception {
        // # Arrange #
        FormTag tag = new FormTag();

        // # Act & Assert #
        assertEquals("javax.faces.HtmlForm", tag.getComponentType());
    }

    public void testGetRenderType() throws Exception {
        // # Arrange #
        FormTag tag = new FormTag();

        // # Act & Assert #
        assertEquals("javax.faces.Form", tag.getRendererType());
    }

    public void testSetProperties_All() throws Exception {
        // # Arrange #
        HtmlForm component = createHtmlForm();
        FormTag tag = new FormTag();
        tag.setAccept("accept");
        tag.setAcceptcharset("acceptcharset");
        tag.setDir("dir");
        tag.setEnctype("enctype");
        tag.setLang("lang");
        tag.setOnclick("onclick");
        tag.setOndblclick("ondblclick");
        tag.setOnkeydown("onkeydown");
        tag.setOnkeypress("onkeypress");
        tag.setOnkeyup("onkeyup");
        tag.setOnmousedown("onmousedown");
        tag.setOnmousemove("onmousemove");
        tag.setOnmouseout("onmouseout");
        tag.setOnmouseover("onmouseover");
        tag.setOnmouseup("onmouseup");
        tag.setOnreset("onreset");
        tag.setOnsubmit("onsubmit");
        tag.setStyle("style");
        tag.setStyleClass("styleclass");
        tag.setTitle("title");
        tag.setTarget("_blank");

        // # Act #
        tag.setProperties(component);

        // # Assert #
        assertEquals("accept", component.getAccept());
        assertEquals("acceptcharset", component.getAcceptcharset());
        assertEquals("dir", component.getDir());
        assertEquals("enctype", component.getEnctype());
        assertEquals("lang", component.getLang());
        assertEquals("onclick", component.getOnclick());
        assertEquals("ondblclick", component.getOndblclick());
        assertEquals("onkeydown", component.getOnkeydown());
        assertEquals("onkeypress", component.getOnkeypress());
        assertEquals("onkeyup", component.getOnkeyup());
        assertEquals("onmousedown", component.getOnmousedown());
        assertEquals("onmousemove", component.getOnmousemove());
        assertEquals("onmouseout", component.getOnmouseout());
        assertEquals("onmouseover", component.getOnmouseover());
        assertEquals("onmouseup", component.getOnmouseup());
        assertEquals("onreset", component.getOnreset());
        assertEquals("onsubmit", component.getOnsubmit());
        assertEquals("style", component.getStyle());
        assertEquals("styleclass", component.getStyleClass());
        assertEquals("title", component.getTitle());
        assertEquals("_blank", component.getTarget());
    }

    public void testSetEnctype_default() throws Exception {
        // # Arrange #
        HtmlForm component = createHtmlForm();
        FormTag tag = new FormTag();

        // # Act #
        tag.setProperties(component);

        // # Assert #
        assertEquals("application/x-www-form-urlencoded", component
                .getEnctype());
    }

    private HtmlForm createHtmlForm() {
        return (HtmlForm) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlForm();
    }

}