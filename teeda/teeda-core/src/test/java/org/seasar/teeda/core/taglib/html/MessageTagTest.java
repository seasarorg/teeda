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
package org.seasar.teeda.core.taglib.html;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlMessage;

import junit.framework.TestCase;

/**
 * @author yone
 */
public class MessageTagTest extends TestCase {
    public void testGetComponentType() throws Exception {
        // # Arrange #
        MessageTag tag = new MessageTag();

        // # Act & Assert #
        assertEquals("javax.faces.HtmlMessage", tag.getComponentType());
    }

    public void testGetRenderType() throws Exception {
        // # Arrange #
        MessageTag tag = new MessageTag();

        // # Act & Assert #
        assertEquals("javax.faces.Message", tag.getRendererType());
    }

    public void testSetProperties_All() throws Exception {
        // # Arrange #
        HtmlMessage component = createHtmlMessage();
        MessageTag tag = new MessageTag();

        tag.setFor("ForId");
        tag.setShowDetail("true");
        tag.setShowSummary("false");
        tag.setErrorClass("ErrorClass");
        tag.setErrorStyle("ErrorStyle");
        tag.setFatalClass("FatalClass");
        tag.setFatalStyle("FatalStyle");
        tag.setInfoClass("InfoClass");
        tag.setInfoStyle("InfoStyle");
        tag.setStyle("style");
        tag.setStyleClass("styleclass");
        tag.setTitle("title");
        tag.setTooltip("true");
        tag.setWarnClass("WarnClass");
        tag.setWarnStyle("WarnStyle");

        // # Act #
        tag.setProperties(component);

        // # Assert #
        assertEquals("ForId", component.getFor());
        assertTrue(component.isShowDetail());
        assertFalse(component.isShowSummary());
        assertEquals("ErrorClass", component.getErrorClass());
        assertEquals("ErrorStyle", component.getErrorStyle());
        assertEquals("FatalClass", component.getFatalClass());
        assertEquals("FatalStyle", component.getFatalStyle());
        assertEquals("InfoClass", component.getInfoClass());
        assertEquals("InfoStyle", component.getInfoStyle());
        assertEquals("style", component.getStyle());
        assertEquals("styleclass", component.getStyleClass());
        assertEquals("title", component.getTitle());
        assertTrue(component.isTooltip());
        assertEquals("WarnClass", component.getWarnClass());
        assertEquals("WarnStyle", component.getWarnStyle());
    }

    private HtmlMessage createHtmlMessage() {
        return (HtmlMessage) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlMessage();
    }

}