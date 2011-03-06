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
import javax.faces.component.html.HtmlMessages;

import junit.framework.TestCase;

/**
 * @author yone
 */
public class MessagesTagTest extends TestCase {

    public void testGetComponentType() throws Exception {
        // # Arrange #
        MessagesTag tag = new MessagesTag();

        // # Act & Assert #
        assertEquals("javax.faces.HtmlMessages", tag.getComponentType());
    }

    public void testGetRenderType() throws Exception {
        // # Arrange #
        MessagesTag tag = new MessagesTag();

        // # Act & Assert #
        assertEquals("javax.faces.Messages", tag.getRendererType());
    }

    public void testSetProperties_All() throws Exception {
        // # Arrange #
        HtmlMessages component = createHtmlMessages();
        MessagesTag tag = new MessagesTag();

        tag.setGlobalOnly("true");
        tag.setShowDetail("true");
        tag.setShowSummary("false");
        tag.setErrorClass("ErrorClass");
        tag.setErrorStyle("ErrorStyle");
        tag.setFatalClass("FatalClass");
        tag.setFatalStyle("FatalStyle");
        tag.setInfoClass("InfoClass");
        tag.setInfoStyle("InfoStyle");
        tag.setLayout("list");
        tag.setStyle("style");
        tag.setStyleClass("styleclass");
        tag.setTitle("title");
        tag.setTooltip("true");
        tag.setWarnClass("WarnClass");
        tag.setWarnStyle("WarnStyle");

        // # Act #
        tag.setProperties(component);

        // # Assert #
        assertTrue(component.isGlobalOnly());
        assertTrue(component.isShowDetail());
        assertFalse(component.isShowSummary());
        assertEquals("ErrorClass", component.getErrorClass());
        assertEquals("ErrorStyle", component.getErrorStyle());
        assertEquals("FatalClass", component.getFatalClass());
        assertEquals("FatalStyle", component.getFatalStyle());
        assertEquals("InfoClass", component.getInfoClass());
        assertEquals("InfoStyle", component.getInfoStyle());
        assertEquals("list", component.getLayout());
        assertEquals("style", component.getStyle());
        assertEquals("styleclass", component.getStyleClass());
        assertEquals("title", component.getTitle());
        assertTrue(component.isTooltip());
        assertEquals("WarnClass", component.getWarnClass());
        assertEquals("WarnStyle", component.getWarnStyle());
    }

    public void testRelease() throws Exception {
        // # Arrange #
        MessagesTag tag = new MessagesTag();
        tag.setGlobalOnly("true");
        tag.setShowDetail("true");
        tag.setShowSummary("false");
        tag.setErrorClass("ErrorClass");
        tag.setErrorStyle("ErrorStyle");
        tag.setFatalClass("FatalClass");
        tag.setFatalStyle("FatalStyle");
        tag.setInfoClass("InfoClass");
        tag.setInfoStyle("InfoStyle");
        tag.setLayout("list");
        tag.setTooltip("true");
        tag.setWarnClass("WarnClass");
        tag.setWarnStyle("WarnStyle");

        // # Act #
        tag.release();

        // # Assert #
        assertEquals(null, tag.getGlobalOnly());
        assertEquals(null, tag.getShowDetail());
        assertEquals(null, tag.getShowSummary());
        assertEquals(null, tag.getErrorClass());
        assertEquals(null, tag.getErrorStyle());
        assertEquals(null, tag.getFatalClass());
        assertEquals(null, tag.getFatalStyle());
        assertEquals(null, tag.getInfoClass());
        assertEquals(null, tag.getInfoStyle());
        assertEquals(null, tag.getLayout());
        assertEquals(null, tag.getTooltip());
        assertEquals(null, tag.getWarnClass());
        assertEquals(null, tag.getWarnStyle());
    }

    private HtmlMessages createHtmlMessages() {
        return (HtmlMessages) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlMessages();
    }

}
