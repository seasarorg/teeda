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
package javax.faces.component.html;

import javax.faces.component.UIComponent;
import javax.faces.component.UIMessageTest;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author manhole
 */
public class HtmlMessageTest extends UIMessageTest {

    public void testSetGetErrorClass() throws Exception {
        HtmlMessage component = createHtmlMessage();
        component.setErrorClass("foo errorClass");
        assertEquals("foo errorClass", component.getErrorClass());
    }

    public void testSetGetErrorClass_ValueBinding() throws Exception {
        HtmlMessage component = createHtmlMessage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar errorClass");
        component.setValueBinding("errorClass", vb);
        assertEquals("bar errorClass", component.getErrorClass());
        assertEquals("bar errorClass", component.getValueBinding("errorClass")
                .getValue(context));
    }

    public void testSetGetErrorStyle() throws Exception {
        HtmlMessage component = createHtmlMessage();
        component.setErrorStyle("foo errorStyle");
        assertEquals("foo errorStyle", component.getErrorStyle());
    }

    public void testSetGetErrorStyle_ValueBinding() throws Exception {
        HtmlMessage component = createHtmlMessage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar errorStyle");
        component.setValueBinding("errorStyle", vb);
        assertEquals("bar errorStyle", component.getErrorStyle());
        assertEquals("bar errorStyle", component.getValueBinding("errorStyle")
                .getValue(context));
    }

    public void testSetGetFatalClass() throws Exception {
        HtmlMessage component = createHtmlMessage();
        component.setFatalClass("foo fatalClass");
        assertEquals("foo fatalClass", component.getFatalClass());
    }

    public void testSetGetFatalClass_ValueBinding() throws Exception {
        HtmlMessage component = createHtmlMessage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar fatalClass");
        component.setValueBinding("fatalClass", vb);
        assertEquals("bar fatalClass", component.getFatalClass());
        assertEquals("bar fatalClass", component.getValueBinding("fatalClass")
                .getValue(context));
    }

    public void testSetGetFatalStyle() throws Exception {
        HtmlMessage component = createHtmlMessage();
        component.setFatalStyle("foo fatalStyle");
        assertEquals("foo fatalStyle", component.getFatalStyle());
    }

    public void testSetGetFatalStyle_ValueBinding() throws Exception {
        HtmlMessage component = createHtmlMessage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar fatalStyle");
        component.setValueBinding("fatalStyle", vb);
        assertEquals("bar fatalStyle", component.getFatalStyle());
        assertEquals("bar fatalStyle", component.getValueBinding("fatalStyle")
                .getValue(context));
    }

    public void testSetGetInfoClass() throws Exception {
        HtmlMessage component = createHtmlMessage();
        component.setInfoClass("foo infoClass");
        assertEquals("foo infoClass", component.getInfoClass());
    }

    public void testSetGetInfoClass_ValueBinding() throws Exception {
        HtmlMessage component = createHtmlMessage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar infoClass");
        component.setValueBinding("infoClass", vb);
        assertEquals("bar infoClass", component.getInfoClass());
        assertEquals("bar infoClass", component.getValueBinding("infoClass")
                .getValue(context));
    }

    public void testSetGetInfoStyle() throws Exception {
        HtmlMessage component = createHtmlMessage();
        component.setInfoStyle("foo infoStyle");
        assertEquals("foo infoStyle", component.getInfoStyle());
    }

    public void testSetGetInfoStyle_ValueBinding() throws Exception {
        HtmlMessage component = createHtmlMessage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar infoStyle");
        component.setValueBinding("infoStyle", vb);
        assertEquals("bar infoStyle", component.getInfoStyle());
        assertEquals("bar infoStyle", component.getValueBinding("infoStyle")
                .getValue(context));
    }

    public void testSetGetStyle() throws Exception {
        HtmlMessage component = createHtmlMessage();
        component.setStyle("foo style");
        assertEquals("foo style", component.getStyle());
    }

    public void testSetGetStyle_ValueBinding() throws Exception {
        HtmlMessage component = createHtmlMessage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar style");
        component.setValueBinding("style", vb);
        assertEquals("bar style", component.getStyle());
        assertEquals("bar style", component.getValueBinding("style").getValue(
                context));
    }

    public void testSetGetStyleClass() throws Exception {
        HtmlMessage component = createHtmlMessage();
        component.setStyleClass("foo styleClass");
        assertEquals("foo styleClass", component.getStyleClass());
    }

    public void testSetGetStyleClass_ValueBinding() throws Exception {
        HtmlMessage component = createHtmlMessage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar styleClass");
        component.setValueBinding("styleClass", vb);
        assertEquals("bar styleClass", component.getStyleClass());
        assertEquals("bar styleClass", component.getValueBinding("styleClass")
                .getValue(context));
    }

    public void testSetGetTitle() throws Exception {
        HtmlMessage component = createHtmlMessage();
        component.setTitle("foo title");
        assertEquals("foo title", component.getTitle());
    }

    public void testSetGetTitle_ValueBinding() throws Exception {
        HtmlMessage component = createHtmlMessage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar title");
        component.setValueBinding("title", vb);
        assertEquals("bar title", component.getTitle());
        assertEquals("bar title", component.getValueBinding("title").getValue(
                context));
    }

    public void testSetGetTooltip() throws Exception {
        HtmlMessage component = createHtmlMessage();
        component.setTooltip(true);
        assertEquals(true, component.isTooltip());
    }

    public void testSetGetTooltip_ValueBinding() throws Exception {
        HtmlMessage component = createHtmlMessage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, Boolean.TRUE);
        component.setValueBinding("tooltip", vb);
        assertEquals(true, component.isTooltip());
        assertEquals(Boolean.TRUE, component.getValueBinding("tooltip")
                .getValue(context));
    }

    public void testSetGetWarnClass() throws Exception {
        HtmlMessage component = createHtmlMessage();
        component.setWarnClass("foo warnClass");
        assertEquals("foo warnClass", component.getWarnClass());
    }

    public void testSetGetWarnClass_ValueBinding() throws Exception {
        HtmlMessage component = createHtmlMessage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar warnClass");
        component.setValueBinding("warnClass", vb);
        assertEquals("bar warnClass", component.getWarnClass());
        assertEquals("bar warnClass", component.getValueBinding("warnClass")
                .getValue(context));
    }

    public void testSetGetWarnStyle() throws Exception {
        HtmlMessage component = createHtmlMessage();
        component.setWarnStyle("foo warnStyle");
        assertEquals("foo warnStyle", component.getWarnStyle());
    }

    public void testSetGetWarnStyle_ValueBinding() throws Exception {
        HtmlMessage component = createHtmlMessage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar warnStyle");
        component.setValueBinding("warnStyle", vb);
        assertEquals("bar warnStyle", component.getWarnStyle());
        assertEquals("bar warnStyle", component.getValueBinding("warnStyle")
                .getValue(context));
    }

    private HtmlMessage createHtmlMessage() {
        return (HtmlMessage) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlMessage();
    }

}
