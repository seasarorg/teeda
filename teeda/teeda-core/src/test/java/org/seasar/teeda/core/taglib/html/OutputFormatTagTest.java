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
package org.seasar.teeda.core.taglib.html;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputFormat;

import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author yone
 */
public class OutputFormatTagTest extends TeedaTestCase {

    public void testGetComponentType() throws Exception {
        // # Arrange #
        OutputFormatTag tag = new OutputFormatTag();

        // # Act & Assert #
        assertEquals("javax.faces.HtmlOutputFormat", tag.getComponentType());
    }

    public void testGetRenderType() throws Exception {
        // # Arrange #
        OutputFormatTag tag = new OutputFormatTag();

        // # Act & Assert #
        assertEquals("javax.faces.Format", tag.getRendererType());
    }

    public void testSetProperties_All() throws Exception {
        // # Arrange #
        HtmlOutputFormat component = createHtmlOutputFormat();
        OutputFormatTag tag = new OutputFormatTag();
        MockApplication app = new MockApplicationImpl();
        app.addConverter("mock.converter",
                "org.seasar.teeda.core.mock.MockConverter");
        setApplication(app);

        tag.setConverter("mock.converter");
        tag.setEscape("true");
        tag.setValue("value");
        tag.setStyle("style");
        tag.setStyleClass("styleclass");
        tag.setTitle("title");

        // # Act #
        tag.setProperties(component);

        // # Assert #
        assertTrue(component.getConverter() instanceof MockConverter);
        assertTrue(component.isEscape());
        assertEquals("value", component.getValue());
        assertEquals("style", component.getStyle());
        assertEquals("styleclass", component.getStyleClass());
        assertEquals("title", component.getTitle());
    }

    public void testRelease() throws Exception {
        // # Arrange #
        OutputFormatTag tag = new OutputFormatTag();
        tag.setEscape("true");

        // # Act #
        tag.release();

        // # Assert #
        assertEquals(null, tag.getEscape());
    }

    private HtmlOutputFormat createHtmlOutputFormat() {
        return (HtmlOutputFormat) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlOutputFormat();
    }
}
