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
import javax.faces.component.html.HtmlDataTable;

import junit.framework.TestCase;

/**
 * @author yone
 */
public class DataTableTagTest extends TestCase {

    public void testGetComponentType() throws Exception {
        // # Arrange #
        DataTableTag tag = new DataTableTag();

        // # Act & Assert #
        assertEquals("javax.faces.HtmlDataTable", tag.getComponentType());
    }

    public void testGetRenderType() throws Exception {
        // # Arrange #
        DataTableTag tag = new DataTableTag();

        // # Act & Assert #
        assertEquals("javax.faces.Table", tag.getRendererType());
    }

    public void testSetProperties_All() throws Exception {
        // # Arrange #
        HtmlDataTable table = createHtmlDataTable();
        DataTableTag tag = new DataTableTag();

        tag.setFirst("2");
        tag.setRows("33");
        tag.setValue("value");
        tag.setVar("varHoge");
        tag.setBgcolor("bgcolorRed");
        tag.setBorder("5");
        tag.setCellpadding("3");
        tag.setCellspacing("2");
        tag.setColumnClasses("columnClasses");
        tag.setDir("dir");
        tag.setFooterClass("footerClass");
        tag.setFrame("frame1");
        tag.setHeaderClass("headerClass2");
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
        tag.setRowClasses("rowClasses");
        tag.setRules("all");
        tag.setStyle("style");
        tag.setStyleClass("styleclass");
        tag.setSummary("summary");
        tag.setTitle("title");
        tag.setWidth("600");

        // # Act #
        tag.setProperties(table);

        // # Assert #
        assertEquals(2, table.getFirst());
        assertEquals(33, table.getRows());
        assertEquals("value", table.getValue());
        assertEquals("varHoge", table.getVar());
        assertEquals("bgcolorRed", table.getBgcolor());
        assertEquals(5, table.getBorder());
        assertEquals("3", table.getCellpadding());
        assertEquals("2", table.getCellspacing());
        assertEquals("columnClasses", table.getColumnClasses());
        assertEquals("dir", table.getDir());
        assertEquals("footerClass", table.getFooterClass());
        assertEquals("frame1", table.getFrame());
        assertEquals("headerClass2", table.getHeaderClass());
        assertEquals("lang", table.getLang());
        assertEquals("onclick", table.getOnclick());
        assertEquals("ondblclick", table.getOndblclick());
        assertEquals("onkeydown", table.getOnkeydown());
        assertEquals("onkeypress", table.getOnkeypress());
        assertEquals("onkeyup", table.getOnkeyup());
        assertEquals("onmousedown", table.getOnmousedown());
        assertEquals("onmousemove", table.getOnmousemove());
        assertEquals("onmouseout", table.getOnmouseout());
        assertEquals("onmouseover", table.getOnmouseover());
        assertEquals("onmouseup", table.getOnmouseup());
        assertEquals("rowClasses", table.getRowClasses());
        assertEquals("all", table.getRules());
        assertEquals("style", table.getStyle());
        assertEquals("styleclass", table.getStyleClass());
        assertEquals("summary", table.getSummary());
        assertEquals("title", table.getTitle());
        assertEquals("600", table.getWidth());
    }

    public void testRelease() throws Exception {
        // # Arrange #
        DataTableTag tag = new DataTableTag();
        tag.setFirst("2");
        tag.setVar("varHoge");
        tag.setColumnClasses("columnClasses");
        tag.setFooterClass("footerClass");
        tag.setHeaderClass("headerClass2");
        tag.setRowClasses("rowClasses");

        // # Act #
        tag.release();

        // # Assert #
        assertEquals(null, tag.getFirst());
        assertEquals(null, tag.getVar());
        assertEquals(null, tag.getColumnClasses());
        assertEquals(null, tag.getFooterClass());
        assertEquals(null, tag.getHeaderClass());
        assertEquals(null, tag.getRowClasses());
    }

    private HtmlDataTable createHtmlDataTable() {
        return (HtmlDataTable) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlDataTable();
    }
}
