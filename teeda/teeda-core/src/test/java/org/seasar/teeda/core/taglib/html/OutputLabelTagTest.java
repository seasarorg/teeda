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
package org.seasar.teeda.core.taglib.html;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLabel;

import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author yone
 */
public class OutputLabelTagTest extends TeedaTestCase {

    public void testGetComponentType() throws Exception {
        // # Arrange #
        OutputLabelTag tag = new OutputLabelTag();

        // # Act & Assert #
        assertEquals("javax.faces.HtmlOutputLabel", tag.getComponentType());
    }

    public void testGetRenderType() throws Exception {
        // # Arrange #
        OutputLabelTag tag = new OutputLabelTag();

        // # Act & Assert #
        assertEquals("javax.faces.Label", tag.getRendererType());
    }

    public void testSetProperties_All() throws Exception {
        // # Arrange #
        HtmlOutputLabel component = createHtmlOutputLabel();
        OutputLabelTag tag = new OutputLabelTag();
        MockApplication app = new MockApplicationImpl();
        app.addConverter("mock.converter",
                "org.seasar.teeda.core.mock.MockConverter");
        setApplication(app);

        tag.setConverter("mock.converter");
        tag.setValue("value");
        tag.setAccesskey("accessKey");
        tag.setDir("rtl");
        tag.setFor("forId");
        tag.setLang("lang");
        tag.setOnblur("onblur");
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
        tag.setStyle("style");
        tag.setStyleClass("styleclass");
        tag.setTabindex("70");
        tag.setTitle("title");

        // # Act #
        tag.setProperties(component);

        // # Assert #
        assertTrue(component.getConverter() instanceof MockConverter);
        assertEquals("value", component.getValue());
        assertEquals("accessKey", component.getAccesskey());
        assertEquals("rtl", component.getDir());
        assertEquals("forId", component.getFor());
        assertEquals("lang", component.getLang());
        assertEquals("onblur", component.getOnblur());
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
        assertEquals("style", component.getStyle());
        assertEquals("styleclass", component.getStyleClass());
        assertEquals("70", component.getTabindex());
        assertEquals("title", component.getTitle());
    }

    public void testRelease() throws Exception {
        // # Arrange #
        OutputLabelTag tag = new OutputLabelTag();
        tag.setFor("forId");

        // # Act #
        tag.release();

        // # Assert #
        assertEquals(null, tag.getFor());
    }

    private HtmlOutputLabel createHtmlOutputLabel() {
        return (HtmlOutputLabel) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlOutputLabel();
    }

}
