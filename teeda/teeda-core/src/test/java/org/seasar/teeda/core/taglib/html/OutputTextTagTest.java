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
import javax.faces.component.html.HtmlOutputText;

import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author yone
 */
public class OutputTextTagTest extends TeedaTestCase {

    public void testGetComponentType() throws Exception {
        // # Arrange #
        OutputTextTag tag = new OutputTextTag();

        // # Act & Assert #
        assertEquals("javax.faces.HtmlOutputText", tag.getComponentType());
    }

    public void testGetRenderType() throws Exception {
        // # Arrange #
        OutputTextTag tag = new OutputTextTag();

        // # Act & Assert #
        assertEquals("javax.faces.Text", tag.getRendererType());
    }

    public void testSetProperties_All() throws Exception {
        // # Arrange #
        HtmlOutputText component = createHtmlOutputText();
        OutputTextTag tag = new OutputTextTag();
        MockApplication app = new MockApplicationImpl();
        app.addConverter("mock.converter",
                "org.seasar.teeda.core.mock.MockConverter");
        setApplication(app);

        tag.setConverter("mock.converter");
        tag.setValue("value");
        tag.setEscape("false");
        tag.setStyle("style");
        tag.setStyleClass("styleclass");
        tag.setTitle("title");

        // # Act #
        tag.setProperties(component);

        // # Assert #
        assertTrue(component.getConverter() instanceof MockConverter);
        assertEquals("value", component.getValue());
        assertFalse(component.isEscape());
        assertEquals("style", component.getStyle());
        assertEquals("styleclass", component.getStyleClass());
        assertEquals("title", component.getTitle());
    }

    public void testRelease() throws Exception {
        // # Arrange #
        OutputTextTag tag = new OutputTextTag();
        tag.setEscape("true");

        // # Act #
        tag.release();

        // # Assert #
        assertEquals(null, tag.getEscape());
    }

    private HtmlOutputText createHtmlOutputText() {
        return (HtmlOutputText) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlOutputText();
    }

}
