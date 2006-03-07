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
import javax.faces.component.html.HtmlPanelGroup;

import junit.framework.TestCase;

/**
 * @author yone
 */
public class PanelGroupTagTest extends TestCase {

    public void testGetComponentType() throws Exception {
        // # Arrange #
        PanelGroupTag tag = new PanelGroupTag();

        // # Act & Assert #
        assertEquals("javax.faces.HtmlPanelGroup", tag.getComponentType());
    }

    public void testGetRenderType() throws Exception {
        // # Arrange #
        PanelGroupTag tag = new PanelGroupTag();

        // # Act & Assert #
        assertEquals("javax.faces.Group", tag.getRendererType());
    }

    public void testSetProperties_All() throws Exception {
        // # Arrange #
        HtmlPanelGroup component = createHtmlPanelGroup();
        PanelGroupTag tag = new PanelGroupTag();

        tag.setStyle("style");
        tag.setStyleClass("styleclass");

        // # Act #
        tag.setProperties(component);

        // # Assert #
        assertEquals("style", component.getStyle());
        assertEquals("styleclass", component.getStyleClass());
    }

    private HtmlPanelGroup createHtmlPanelGroup() {
        return (HtmlPanelGroup) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlPanelGroup();
    }

}
