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
import javax.faces.component.html.HtmlPanelGrid;

import junit.framework.TestCase;

/**
 * @author yone
 */
public class PanelGridTagTest extends TestCase {

    public void testGetComponentType() throws Exception {
        // # Arrange #
        PanelGridTag tag = new PanelGridTag();

        // # Act & Assert #
        assertEquals("javax.faces.HtmlPanelGrid", tag.getComponentType());
    }

    public void testGetRenderType() throws Exception {
        // # Arrange #
        PanelGridTag tag = new PanelGridTag();

        // # Act & Assert #
        assertEquals("javax.faces.Grid", tag.getRendererType());
    }

    public void testSetProperties_All() throws Exception {
        // # Arrange #
        HtmlPanelGrid component = createHtmlPanelGrid();
        PanelGridTag tag = new PanelGridTag();

        tag.setBgcolor("blue");
        tag.setBorder("9");
        tag.setCellpadding("5");
        tag.setCellspacing("8");
        tag.setColumnClasses("columnClass");
        tag.setColumns("99");
        tag.setDir("dir");
        tag.setFooterClass("FooterClass");
        tag.setFrame("FRAME");
        tag.setHeaderClass("HeaderClass");
        tag.setLang("Lang");
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
        tag.setRowClasses("RowClass");
        tag.setRules("Rules");
        tag.setStyle("style");
        tag.setStyleClass("styleclass");
        tag.setSummary("Summary");
        tag.setWidth("650");
        tag.setTitle("title");

        // # Act #
        tag.setProperties(component);

        // # Assert #
        assertEquals("blue", component.getBgcolor());
        assertEquals(9, component.getBorder());
        assertEquals("5", component.getCellpadding());
        assertEquals("8", component.getCellspacing());
        assertEquals("columnClass", component.getColumnClasses());
        assertEquals(99, component.getColumns());
        assertEquals("dir", component.getDir());
        assertEquals("FooterClass", component.getFooterClass());
        assertEquals("FRAME", component.getFrame());
        assertEquals("HeaderClass", component.getHeaderClass());
        assertEquals("Lang", component.getLang());
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
        assertEquals("RowClass", component.getRowClasses());
        assertEquals("Rules", component.getRules());
        assertEquals("style", component.getStyle());
        assertEquals("styleclass", component.getStyleClass());
        assertEquals("Summary", component.getSummary());
        assertEquals("title", component.getTitle());
        assertEquals("650", component.getWidth());
    }

    public void testRelease() throws Exception {
        // # Arrange #
        PanelGridTag tag = new PanelGridTag();
        tag.setColumnClasses("columnClass");
        tag.setColumns("99");
        tag.setFooterClass("FooterClass");
        tag.setHeaderClass("HeaderClass");
        tag.setRowClasses("RowClass");
        
        // # Act #
        tag.release();
        
        // # Assert #
        assertEquals(null, tag.getColumnClasses());
        assertEquals(null, tag.getColumns());
        assertEquals(null, tag.getFooterClass());
        assertEquals(null, tag.getHeaderClass());
        assertEquals(null, tag.getRowClasses());
    }

    private HtmlPanelGrid createHtmlPanelGrid() {
        return (HtmlPanelGrid) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlPanelGrid();
    }
}
