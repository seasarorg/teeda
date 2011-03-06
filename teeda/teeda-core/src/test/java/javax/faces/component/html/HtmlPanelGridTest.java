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
package javax.faces.component.html;

import javax.faces.component.UIComponent;
import javax.faces.component.UIPanelTest;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author manhole
 */
public class HtmlPanelGridTest extends UIPanelTest {

    public void testSetGetBgcolor() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setBgcolor("foo bgcolor");
        assertEquals("foo bgcolor", component.getBgcolor());
    }

    public void testSetGetBgcolor_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar bgcolor");
        component.setValueBinding("bgcolor", vb);
        assertEquals("bar bgcolor", component.getBgcolor());
        assertEquals("bar bgcolor", component.getValueBinding("bgcolor")
                .getValue(context));
    }

    public void testSetGetBorder() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setBorder(123);
        assertEquals(123, component.getBorder());
    }

    public void testSetGetBorder_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, new Integer(234));
        component.setValueBinding("border", vb);
        assertEquals(234, component.getBorder());
        assertEquals(new Integer(234), component.getValueBinding("border")
                .getValue(context));
    }

    public void testSetGetCellpadding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setCellpadding("foo cellpadding");
        assertEquals("foo cellpadding", component.getCellpadding());
    }

    public void testSetGetCellpadding_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar cellpadding");
        component.setValueBinding("cellpadding", vb);
        assertEquals("bar cellpadding", component.getCellpadding());
        assertEquals("bar cellpadding", component
                .getValueBinding("cellpadding").getValue(context));
    }

    public void testSetGetCellspacing() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setCellspacing("foo cellspacing");
        assertEquals("foo cellspacing", component.getCellspacing());
    }

    public void testSetGetCellspacing_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar cellspacing");
        component.setValueBinding("cellspacing", vb);
        assertEquals("bar cellspacing", component.getCellspacing());
        assertEquals("bar cellspacing", component
                .getValueBinding("cellspacing").getValue(context));
    }

    public void testSetGetColumnClasses() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setColumnClasses("foo columnClasses");
        assertEquals("foo columnClasses", component.getColumnClasses());
    }

    public void testSetGetColumnClasses_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar columnClasses");
        component.setValueBinding("columnClasses", vb);
        assertEquals("bar columnClasses", component.getColumnClasses());
        assertEquals("bar columnClasses", component.getValueBinding(
                "columnClasses").getValue(context));
    }

    public void testSetGetColumns() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setColumns(3);
        assertEquals(3, component.getColumns());
    }

    public void testSetGetColumns_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, new Integer(21));
        component.setValueBinding("columns", vb);
        assertEquals(21, component.getColumns());
        assertEquals(new Integer(21), component.getValueBinding("columns")
                .getValue(context));
    }

    public void testSetGetDir() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setDir("foo dir");
        assertEquals("foo dir", component.getDir());
    }

    public void testSetGetDir_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar dir");
        component.setValueBinding("dir", vb);
        assertEquals("bar dir", component.getDir());
        assertEquals("bar dir", component.getValueBinding("dir").getValue(
                context));
    }

    public void testSetGetFooterClass() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setFooterClass("foo footerClass");
        assertEquals("foo footerClass", component.getFooterClass());
    }

    public void testSetGetFooterClass_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar footerClass");
        component.setValueBinding("footerClass", vb);
        assertEquals("bar footerClass", component.getFooterClass());
        assertEquals("bar footerClass", component
                .getValueBinding("footerClass").getValue(context));
    }

    public void testSetGetFrame() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setFrame("foo frame");
        assertEquals("foo frame", component.getFrame());
    }

    public void testSetGetFrame_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar frame");
        component.setValueBinding("frame", vb);
        assertEquals("bar frame", component.getFrame());
        assertEquals("bar frame", component.getValueBinding("frame").getValue(
                context));
    }

    public void testSetGetHeaderClass() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setHeaderClass("foo headerClass");
        assertEquals("foo headerClass", component.getHeaderClass());
    }

    public void testSetGetHeaderClass_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar headerClass");
        component.setValueBinding("headerClass", vb);
        assertEquals("bar headerClass", component.getHeaderClass());
        assertEquals("bar headerClass", component
                .getValueBinding("headerClass").getValue(context));
    }

    public void testSetGetLang() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setLang("foo lang");
        assertEquals("foo lang", component.getLang());
    }

    public void testSetGetLang_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar lang");
        component.setValueBinding("lang", vb);
        assertEquals("bar lang", component.getLang());
        assertEquals("bar lang", component.getValueBinding("lang").getValue(
                context));
    }

    public void testSetGetOnclick() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setOnclick("foo onclick");
        assertEquals("foo onclick", component.getOnclick());
    }

    public void testSetGetOnclick_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onclick");
        component.setValueBinding("onclick", vb);
        assertEquals("bar onclick", component.getOnclick());
        assertEquals("bar onclick", component.getValueBinding("onclick")
                .getValue(context));
    }

    public void testSetGetOndblclick() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setOndblclick("foo ondblclick");
        assertEquals("foo ondblclick", component.getOndblclick());
    }

    public void testSetGetOndblclick_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar ondblclick");
        component.setValueBinding("ondblclick", vb);
        assertEquals("bar ondblclick", component.getOndblclick());
        assertEquals("bar ondblclick", component.getValueBinding("ondblclick")
                .getValue(context));
    }

    public void testSetGetOnkeydown() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setOnkeydown("foo onkeydown");
        assertEquals("foo onkeydown", component.getOnkeydown());
    }

    public void testSetGetOnkeydown_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onkeydown");
        component.setValueBinding("onkeydown", vb);
        assertEquals("bar onkeydown", component.getOnkeydown());
        assertEquals("bar onkeydown", component.getValueBinding("onkeydown")
                .getValue(context));
    }

    public void testSetGetOnkeypress() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setOnkeypress("foo onkeypress");
        assertEquals("foo onkeypress", component.getOnkeypress());
    }

    public void testSetGetOnkeypress_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onkeypress");
        component.setValueBinding("onkeypress", vb);
        assertEquals("bar onkeypress", component.getOnkeypress());
        assertEquals("bar onkeypress", component.getValueBinding("onkeypress")
                .getValue(context));
    }

    public void testSetGetOnkeyup() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setOnkeyup("foo onkeyup");
        assertEquals("foo onkeyup", component.getOnkeyup());
    }

    public void testSetGetOnkeyup_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onkeyup");
        component.setValueBinding("onkeyup", vb);
        assertEquals("bar onkeyup", component.getOnkeyup());
        assertEquals("bar onkeyup", component.getValueBinding("onkeyup")
                .getValue(context));
    }

    public void testSetGetOnmousedown() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setOnmousedown("foo onmousedown");
        assertEquals("foo onmousedown", component.getOnmousedown());
    }

    public void testSetGetOnmousedown_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmousedown");
        component.setValueBinding("onmousedown", vb);
        assertEquals("bar onmousedown", component.getOnmousedown());
        assertEquals("bar onmousedown", component
                .getValueBinding("onmousedown").getValue(context));
    }

    public void testSetGetOnmousemove() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setOnmousemove("foo onmousemove");
        assertEquals("foo onmousemove", component.getOnmousemove());
    }

    public void testSetGetOnmousemove_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmousemove");
        component.setValueBinding("onmousemove", vb);
        assertEquals("bar onmousemove", component.getOnmousemove());
        assertEquals("bar onmousemove", component
                .getValueBinding("onmousemove").getValue(context));
    }

    public void testSetGetOnmouseout() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setOnmouseout("foo onmouseout");
        assertEquals("foo onmouseout", component.getOnmouseout());
    }

    public void testSetGetOnmouseout_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmouseout");
        component.setValueBinding("onmouseout", vb);
        assertEquals("bar onmouseout", component.getOnmouseout());
        assertEquals("bar onmouseout", component.getValueBinding("onmouseout")
                .getValue(context));
    }

    public void testSetGetOnmouseover() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setOnmouseover("foo onmouseover");
        assertEquals("foo onmouseover", component.getOnmouseover());
    }

    public void testSetGetOnmouseover_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmouseover");
        component.setValueBinding("onmouseover", vb);
        assertEquals("bar onmouseover", component.getOnmouseover());
        assertEquals("bar onmouseover", component
                .getValueBinding("onmouseover").getValue(context));
    }

    public void testSetGetOnmouseup() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setOnmouseup("foo onmouseup");
        assertEquals("foo onmouseup", component.getOnmouseup());
    }

    public void testSetGetOnmouseup_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmouseup");
        component.setValueBinding("onmouseup", vb);
        assertEquals("bar onmouseup", component.getOnmouseup());
        assertEquals("bar onmouseup", component.getValueBinding("onmouseup")
                .getValue(context));
    }

    public void testSetGetRowClasses() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setRowClasses("foo rowClasses");
        assertEquals("foo rowClasses", component.getRowClasses());
    }

    public void testSetGetRowClasses_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar rowClasses");
        component.setValueBinding("rowClasses", vb);
        assertEquals("bar rowClasses", component.getRowClasses());
        assertEquals("bar rowClasses", component.getValueBinding("rowClasses")
                .getValue(context));
    }

    public void testSetGetRules() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setRules("foo rules");
        assertEquals("foo rules", component.getRules());
    }

    public void testSetGetRules_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar rules");
        component.setValueBinding("rules", vb);
        assertEquals("bar rules", component.getRules());
        assertEquals("bar rules", component.getValueBinding("rules").getValue(
                context));
    }

    public void testSetGetStyle() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setStyle("foo style");
        assertEquals("foo style", component.getStyle());
    }

    public void testSetGetStyle_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar style");
        component.setValueBinding("style", vb);
        assertEquals("bar style", component.getStyle());
        assertEquals("bar style", component.getValueBinding("style").getValue(
                context));
    }

    public void testSetGetStyleClass() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setStyleClass("foo styleClass");
        assertEquals("foo styleClass", component.getStyleClass());
    }

    public void testSetGetStyleClass_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar styleClass");
        component.setValueBinding("styleClass", vb);
        assertEquals("bar styleClass", component.getStyleClass());
        assertEquals("bar styleClass", component.getValueBinding("styleClass")
                .getValue(context));
    }

    public void testSetGetSummary() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setSummary("foo summary");
        assertEquals("foo summary", component.getSummary());
    }

    public void testSetGetSummary_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar summary");
        component.setValueBinding("summary", vb);
        assertEquals("bar summary", component.getSummary());
        assertEquals("bar summary", component.getValueBinding("summary")
                .getValue(context));
    }

    public void testSetGetTitle() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setTitle("foo title");
        assertEquals("foo title", component.getTitle());
    }

    public void testSetGetTitle_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar title");
        component.setValueBinding("title", vb);
        assertEquals("bar title", component.getTitle());
        assertEquals("bar title", component.getValueBinding("title").getValue(
                context));
    }

    public void testSetGetWidth() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        component.setWidth("foo width");
        assertEquals("foo width", component.getWidth());
    }

    public void testSetGetWidth_ValueBinding() throws Exception {
        HtmlPanelGrid component = createHtmlPanelGrid();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar width");
        component.setValueBinding("width", vb);
        assertEquals("bar width", component.getWidth());
        assertEquals("bar width", component.getValueBinding("width").getValue(
                context));
    }

    private HtmlPanelGrid createHtmlPanelGrid() {
        return (HtmlPanelGrid) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlPanelGrid();
    }

}
