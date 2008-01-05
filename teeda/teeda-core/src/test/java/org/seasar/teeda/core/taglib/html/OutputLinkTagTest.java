/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import javax.faces.component.html.HtmlOutputLink;

import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author yone
 */
public class OutputLinkTagTest extends TeedaTestCase {

    public void testGetComponentType() throws Exception {
        // # Arrange #
        OutputLinkTag tag = new OutputLinkTag();

        // # Act & Assert #
        assertEquals("javax.faces.HtmlOutputLink", tag.getComponentType());
    }

    public void testGetRenderType() throws Exception {
        // # Arrange #
        OutputLinkTag tag = new OutputLinkTag();

        // # Act & Assert #
        assertEquals("javax.faces.Link", tag.getRendererType());
    }

    public void testSetProperties_All() throws Exception {
        // # Arrange #
        HtmlOutputLink component = createHtmlOutputLink();
        OutputLinkTag tag = new OutputLinkTag();
        MockApplication app = new MockApplicationImpl();
        app.addConverter("mock.converter",
                "org.seasar.teeda.core.mock.MockConverter");
        setApplication(app);

        tag.setConverter("mock.converter");
        tag.setValue("value");
        tag.setAccesskey("accessKey");
        tag.setCharset("charset");
        tag.setCoords("coords");
        tag.setDir("ltr");
        tag.setHreflang("hreflang");
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
        tag.setRel("rel");
        tag.setRev("rev");
        tag.setShape("shape");
        tag.setStyle("style");
        tag.setStyleClass("styleclass");
        tag.setTabindex("70");
        tag.setTarget("_blank");
        tag.setTitle("title");
        tag.setType("contentType");

        // # Act #
        tag.setProperties(component);

        // # Assert #
        assertTrue(component.getConverter() instanceof MockConverter);
        assertEquals("value", component.getValue());
        assertEquals("accessKey", component.getAccesskey());
        assertEquals("charset", component.getCharset());
        assertEquals("coords", component.getCoords());
        assertEquals("ltr", component.getDir());
        assertEquals("hreflang", component.getHreflang());
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
        assertEquals("rel", component.getRel());
        assertEquals("rev", component.getRev());
        assertEquals("shape", component.getShape());
        assertEquals("style", component.getStyle());
        assertEquals("styleclass", component.getStyleClass());
        assertEquals("70", component.getTabindex());
        assertEquals("_blank", component.getTarget());
        assertEquals("title", component.getTitle());
        assertEquals("contentType", component.getType());
    }

    private HtmlOutputLink createHtmlOutputLink() {
        return (HtmlOutputLink) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlOutputLink();
    }

}
