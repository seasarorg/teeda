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
package javax.faces.component.html;

import javax.faces.component.UIComponent;
import javax.faces.component.UIDataTest;

import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author manhole
 */
public class HtmlDataTableTest extends UIDataTest {

    public void testSetGetBgcolor() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setBgcolor("foo bgcolor");
        assertEquals("foo bgcolor", component.getBgcolor());
    }

    public void testSetGetBgcolor_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar bgcolor");
        component.setValueBinding("bgcolor", vb);
        assertEquals("bar bgcolor", component.getBgcolor());
    }

    public void testSetGetBorder() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setBorder(123);
        assertEquals(123, component.getBorder());
    }

    public void testSetGetBorder_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Integer(234));
        component.setValueBinding("border", vb);
        assertEquals(234, component.getBorder());
    }

    public void testSetGetCellpadding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setCellpadding("foo cellpadding");
        assertEquals("foo cellpadding", component.getCellpadding());
    }

    public void testSetGetCellpadding_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar cellpadding");
        component.setValueBinding("cellpadding", vb);
        assertEquals("bar cellpadding", component.getCellpadding());
    }

    public void testSetGetCellspacing() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setCellspacing("foo cellspacing");
        assertEquals("foo cellspacing", component.getCellspacing());
    }

    public void testSetGetCellspacing_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar cellspacing");
        component.setValueBinding("cellspacing", vb);
        assertEquals("bar cellspacing", component.getCellspacing());
    }

    public void testSetGetColumnClasses() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setColumnClasses("foo columnClasses");
        assertEquals("foo columnClasses", component.getColumnClasses());
    }

    public void testSetGetColumnClasses_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar columnClasses");
        component.setValueBinding("columnClasses", vb);
        assertEquals("bar columnClasses", component.getColumnClasses());
    }

    public void testSetGetDir() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setDir("foo dir");
        assertEquals("foo dir", component.getDir());
    }

    public void testSetGetDir_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar dir");
        component.setValueBinding("dir", vb);
        assertEquals("bar dir", component.getDir());
    }

    public void testSetGetFooterClass() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setFooterClass("foo footerClass");
        assertEquals("foo footerClass", component.getFooterClass());
    }

    public void testSetGetFooterClass_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar footerClass");
        component.setValueBinding("footerClass", vb);
        assertEquals("bar footerClass", component.getFooterClass());
    }

    public void testSetGetFrame() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setFrame("foo frame");
        assertEquals("foo frame", component.getFrame());
    }

    public void testSetGetFrame_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar frame");
        component.setValueBinding("frame", vb);
        assertEquals("bar frame", component.getFrame());
    }

    public void testSetGetHeaderClass() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setHeaderClass("foo headerClass");
        assertEquals("foo headerClass", component.getHeaderClass());
    }

    public void testSetGetHeaderClass_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar headerClass");
        component.setValueBinding("headerClass", vb);
        assertEquals("bar headerClass", component.getHeaderClass());
    }

    public void testSetGetLang() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setLang("foo lang");
        assertEquals("foo lang", component.getLang());
    }

    public void testSetGetLang_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar lang");
        component.setValueBinding("lang", vb);
        assertEquals("bar lang", component.getLang());
    }

    public void testSetGetOnclick() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setOnclick("foo onclick");
        assertEquals("foo onclick", component.getOnclick());
    }

    public void testSetGetOnclick_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onclick");
        component.setValueBinding("onclick", vb);
        assertEquals("bar onclick", component.getOnclick());
    }

    public void testSetGetOndblclick() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setOndblclick("foo ondblclick");
        assertEquals("foo ondblclick", component.getOndblclick());
    }

    public void testSetGetOndblclick_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar ondblclick");
        component.setValueBinding("ondblclick", vb);
        assertEquals("bar ondblclick", component.getOndblclick());
    }

    public void testSetGetOnkeydown() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setOnkeydown("foo onkeydown");
        assertEquals("foo onkeydown", component.getOnkeydown());
    }

    public void testSetGetOnkeydown_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onkeydown");
        component.setValueBinding("onkeydown", vb);
        assertEquals("bar onkeydown", component.getOnkeydown());
    }

    public void testSetGetOnkeypress() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setOnkeypress("foo onkeypress");
        assertEquals("foo onkeypress", component.getOnkeypress());
    }

    public void testSetGetOnkeypress_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onkeypress");
        component.setValueBinding("onkeypress", vb);
        assertEquals("bar onkeypress", component.getOnkeypress());
    }

    public void testSetGetOnkeyup() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setOnkeyup("foo onkeyup");
        assertEquals("foo onkeyup", component.getOnkeyup());
    }

    public void testSetGetOnkeyup_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onkeyup");
        component.setValueBinding("onkeyup", vb);
        assertEquals("bar onkeyup", component.getOnkeyup());
    }

    public void testSetGetOnmousedown() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setOnmousedown("foo onmousedown");
        assertEquals("foo onmousedown", component.getOnmousedown());
    }

    public void testSetGetOnmousedown_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmousedown");
        component.setValueBinding("onmousedown", vb);
        assertEquals("bar onmousedown", component.getOnmousedown());
    }

    public void testSetGetOnmousemove() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setOnmousemove("foo onmousemove");
        assertEquals("foo onmousemove", component.getOnmousemove());
    }

    public void testSetGetOnmousemove_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmousemove");
        component.setValueBinding("onmousemove", vb);
        assertEquals("bar onmousemove", component.getOnmousemove());
    }

    public void testSetGetOnmouseout() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setOnmouseout("foo onmouseout");
        assertEquals("foo onmouseout", component.getOnmouseout());
    }

    public void testSetGetOnmouseout_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmouseout");
        component.setValueBinding("onmouseout", vb);
        assertEquals("bar onmouseout", component.getOnmouseout());
    }

    public void testSetGetOnmouseover() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setOnmouseover("foo onmouseover");
        assertEquals("foo onmouseover", component.getOnmouseover());
    }

    public void testSetGetOnmouseover_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmouseover");
        component.setValueBinding("onmouseover", vb);
        assertEquals("bar onmouseover", component.getOnmouseover());
    }

    public void testSetGetOnmouseup() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setOnmouseup("foo onmouseup");
        assertEquals("foo onmouseup", component.getOnmouseup());
    }

    public void testSetGetOnmouseup_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmouseup");
        component.setValueBinding("onmouseup", vb);
        assertEquals("bar onmouseup", component.getOnmouseup());
    }

    public void testSetGetRowClasses() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setRowClasses("foo rowClasses");
        assertEquals("foo rowClasses", component.getRowClasses());
    }

    public void testSetGetRowClasses_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar rowClasses");
        component.setValueBinding("rowClasses", vb);
        assertEquals("bar rowClasses", component.getRowClasses());
    }

    public void testSetGetRules() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setRules("foo rules");
        assertEquals("foo rules", component.getRules());
    }

    public void testSetGetRules_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar rules");
        component.setValueBinding("rules", vb);
        assertEquals("bar rules", component.getRules());
    }

    public void testSetGetStyle() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setStyle("foo style");
        assertEquals("foo style", component.getStyle());
    }

    public void testSetGetStyle_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar style");
        component.setValueBinding("style", vb);
        assertEquals("bar style", component.getStyle());
    }

    public void testSetGetStyleClass() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setStyleClass("foo styleClass");
        assertEquals("foo styleClass", component.getStyleClass());
    }

    public void testSetGetStyleClass_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar styleClass");
        component.setValueBinding("styleClass", vb);
        assertEquals("bar styleClass", component.getStyleClass());
    }

    public void testSetGetSummary() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setSummary("foo summary");
        assertEquals("foo summary", component.getSummary());
    }

    public void testSetGetSummary_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar summary");
        component.setValueBinding("summary", vb);
        assertEquals("bar summary", component.getSummary());
    }

    public void testSetGetTitle() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setTitle("foo title");
        assertEquals("foo title", component.getTitle());
    }

    public void testSetGetTitle_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar title");
        component.setValueBinding("title", vb);
        assertEquals("bar title", component.getTitle());
    }

    public void testSetGetWidth() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        component.setWidth("foo width");
        assertEquals("foo width", component.getWidth());
    }

    public void testSetGetWidth_ValueBinding() throws Exception {
        HtmlDataTable component = createHtmlDataTable();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar width");
        component.setValueBinding("width", vb);
        assertEquals("bar width", component.getWidth());
    }

    private HtmlDataTable createHtmlDataTable() {
        return (HtmlDataTable) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlDataTable();
    }

}
