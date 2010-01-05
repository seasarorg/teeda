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
import javax.faces.component.html.HtmlInputHidden;

import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.mock.MockMethodBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author yone
 */
public class InputHiddenTagTest extends TeedaTestCase {
    public void testGetComponentType() throws Exception {
        // # Arrange #
        InputHiddenTag tag = new InputHiddenTag();

        // # Act & Assert #
        assertEquals("javax.faces.HtmlInputHidden", tag.getComponentType());
    }

    public void testGetRenderType() throws Exception {
        // # Arrange #
        InputHiddenTag tag = new InputHiddenTag();

        // # Act & Assert #
        assertEquals("javax.faces.Hidden", tag.getRendererType());
    }

    public void testSetProperties_All() throws Exception {
        // # Arrange #
        HtmlInputHidden component = createHtmlInputHidden();
        InputHiddenTag tag = new InputHiddenTag();
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
    }

    private HtmlInputHidden createHtmlInputHidden() {
        return (HtmlInputHidden) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlInputHidden();
    }

}
