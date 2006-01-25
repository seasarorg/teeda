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
import javax.faces.component.UIComponentBaseTest;

import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author manhole
 */
public class HtmlFormTest extends UIComponentBaseTest {

    public void testSetGetAccept() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setAccept("foo accept");
        assertEquals("foo accept", component.getAccept());
    }

    public void testSetGetAccept_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar accept");
        component.setValueBinding("accept", vb);
        assertEquals("bar accept", component.getAccept());
    }

    public void testSetGetAcceptcharset() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setAcceptcharset("foo acceptcharset");
        assertEquals("foo acceptcharset", component.getAcceptcharset());
    }

    public void testSetGetAcceptcharset_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar acceptcharset");
        component.setValueBinding("acceptcharset", vb);
        assertEquals("bar acceptcharset", component.getAcceptcharset());
    }

    public void testSetGetDir() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setDir("foo dir");
        assertEquals("foo dir", component.getDir());
    }

    public void testSetGetDir_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar dir");
        component.setValueBinding("dir", vb);
        assertEquals("bar dir", component.getDir());
    }

    public void testSetGetEnctype() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setEnctype("foo enctype");
        assertEquals("foo enctype", component.getEnctype());
    }

    public void testSetGetEnctype_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar enctype");
        component.setValueBinding("enctype", vb);
        assertEquals("bar enctype", component.getEnctype());
    }

    public void testSetGetLang() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setLang("foo lang");
        assertEquals("foo lang", component.getLang());
    }

    public void testSetGetLang_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar lang");
        component.setValueBinding("lang", vb);
        assertEquals("bar lang", component.getLang());
    }

    public void testSetGetOnclick() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setOnclick("foo onclick");
        assertEquals("foo onclick", component.getOnclick());
    }

    public void testSetGetOnclick_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onclick");
        component.setValueBinding("onclick", vb);
        assertEquals("bar onclick", component.getOnclick());
    }

    public void testSetGetOndblclick() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setOndblclick("foo ondblclick");
        assertEquals("foo ondblclick", component.getOndblclick());
    }

    public void testSetGetOndblclick_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar ondblclick");
        component.setValueBinding("ondblclick", vb);
        assertEquals("bar ondblclick", component.getOndblclick());
    }

    public void testSetGetOnkeydown() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setOnkeydown("foo onkeydown");
        assertEquals("foo onkeydown", component.getOnkeydown());
    }

    public void testSetGetOnkeydown_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onkeydown");
        component.setValueBinding("onkeydown", vb);
        assertEquals("bar onkeydown", component.getOnkeydown());
    }

    public void testSetGetOnkeypress() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setOnkeypress("foo onkeypress");
        assertEquals("foo onkeypress", component.getOnkeypress());
    }

    public void testSetGetOnkeypress_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onkeypress");
        component.setValueBinding("onkeypress", vb);
        assertEquals("bar onkeypress", component.getOnkeypress());
    }

    public void testSetGetOnkeyup() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setOnkeyup("foo onkeyup");
        assertEquals("foo onkeyup", component.getOnkeyup());
    }

    public void testSetGetOnkeyup_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onkeyup");
        component.setValueBinding("onkeyup", vb);
        assertEquals("bar onkeyup", component.getOnkeyup());
    }

    public void testSetGetOnmousedown() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setOnmousedown("foo onmousedown");
        assertEquals("foo onmousedown", component.getOnmousedown());
    }

    public void testSetGetOnmousedown_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmousedown");
        component.setValueBinding("onmousedown", vb);
        assertEquals("bar onmousedown", component.getOnmousedown());
    }

    public void testSetGetOnmousemove() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setOnmousemove("foo onmousemove");
        assertEquals("foo onmousemove", component.getOnmousemove());
    }

    public void testSetGetOnmousemove_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmousemove");
        component.setValueBinding("onmousemove", vb);
        assertEquals("bar onmousemove", component.getOnmousemove());
    }

    public void testSetGetOnmouseout() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setOnmouseout("foo onmouseout");
        assertEquals("foo onmouseout", component.getOnmouseout());
    }

    public void testSetGetOnmouseout_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmouseout");
        component.setValueBinding("onmouseout", vb);
        assertEquals("bar onmouseout", component.getOnmouseout());
    }

    public void testSetGetOnmouseover() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setOnmouseover("foo onmouseover");
        assertEquals("foo onmouseover", component.getOnmouseover());
    }

    public void testSetGetOnmouseover_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmouseover");
        component.setValueBinding("onmouseover", vb);
        assertEquals("bar onmouseover", component.getOnmouseover());
    }

    public void testSetGetOnmouseup() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setOnmouseup("foo onmouseup");
        assertEquals("foo onmouseup", component.getOnmouseup());
    }

    public void testSetGetOnmouseup_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmouseup");
        component.setValueBinding("onmouseup", vb);
        assertEquals("bar onmouseup", component.getOnmouseup());
    }

    public void testSetGetOnreset() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setOnreset("foo onreset");
        assertEquals("foo onreset", component.getOnreset());
    }

    public void testSetGetOnreset_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onreset");
        component.setValueBinding("onreset", vb);
        assertEquals("bar onreset", component.getOnreset());
    }

    public void testSetGetOnsubmit() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setOnsubmit("foo onsubmit");
        assertEquals("foo onsubmit", component.getOnsubmit());
    }

    public void testSetGetOnsubmit_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onsubmit");
        component.setValueBinding("onsubmit", vb);
        assertEquals("bar onsubmit", component.getOnsubmit());
    }

    public void testSetGetStyle() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setStyle("foo style");
        assertEquals("foo style", component.getStyle());
    }

    public void testSetGetStyle_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar style");
        component.setValueBinding("style", vb);
        assertEquals("bar style", component.getStyle());
    }

    public void testSetGetStyleClass() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setStyleClass("foo styleClass");
        assertEquals("foo styleClass", component.getStyleClass());
    }

    public void testSetGetStyleClass_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar styleClass");
        component.setValueBinding("styleClass", vb);
        assertEquals("bar styleClass", component.getStyleClass());
    }

    public void testSetGetTarget() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setTarget("foo target");
        assertEquals("foo target", component.getTarget());
    }

    public void testSetGetTarget_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar target");
        component.setValueBinding("target", vb);
        assertEquals("bar target", component.getTarget());
    }

    public void testSetGetTitle() throws Exception {
        HtmlForm component = createHtmlForm();
        component.setTitle("foo title");
        assertEquals("foo title", component.getTitle());
    }

    public void testSetGetTitle_ValueBinding() throws Exception {
        HtmlForm component = createHtmlForm();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar title");
        component.setValueBinding("title", vb);
        assertEquals("bar title", component.getTitle());
    }

    private HtmlForm createHtmlForm() {
        return (HtmlForm) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlForm();
    }

}
