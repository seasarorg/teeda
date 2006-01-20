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

import javax.faces.component.UICommandTeedaTest;
import javax.faces.component.UIComponent;

import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author manhole
 */
public class HtmlCommandButtonTest extends UICommandTeedaTest {

    public void testSetGetAccesskey() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setAccesskey("foo accesskey");
        assertEquals("foo accesskey", component.getAccesskey());
    }

    public void testSetGetAccesskey_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar accesskey");
        component.setValueBinding("accesskey", vb);
        assertEquals("bar accesskey", component.getAccesskey());
    }

    public void testSetGetAlt() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setAlt("foo alt");
        assertEquals("foo alt", component.getAlt());
    }

    public void testSetGetAlt_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar alt");
        component.setValueBinding("alt", vb);
        assertEquals("bar alt", component.getAlt());
    }

    public void testSetGetDir() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setDir("foo dir");
        assertEquals("foo dir", component.getDir());
    }

    public void testSetGetDir_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar dir");
        component.setValueBinding("dir", vb);
        assertEquals("bar dir", component.getDir());
    }

    public void testSetGetDisabled() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setDisabled(true);
        assertEquals(true, component.isDisabled());
    }

    public void testSetGetDisabled_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), Boolean.TRUE);
        component.setValueBinding("disabled", vb);
        assertEquals(true, component.isDisabled());
    }

    public void testSetGetImage() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setImage("foo image");
        assertEquals("foo image", component.getImage());
    }

    public void testSetGetImage_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar image");
        component.setValueBinding("image", vb);
        assertEquals("bar image", component.getImage());
    }

    public void testSetGetLang() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setLang("foo lang");
        assertEquals("foo lang", component.getLang());
    }

    public void testSetGetLang_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar lang");
        component.setValueBinding("lang", vb);
        assertEquals("bar lang", component.getLang());
    }

    public void testSetGetOnblur() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setOnblur("foo onblur");
        assertEquals("foo onblur", component.getOnblur());
    }

    public void testSetGetOnblur_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onblur");
        component.setValueBinding("onblur", vb);
        assertEquals("bar onblur", component.getOnblur());
    }

    public void testSetGetOnchange() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setOnchange("foo onchange");
        assertEquals("foo onchange", component.getOnchange());
    }

    public void testSetGetOnchange_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onchange");
        component.setValueBinding("onchange", vb);
        assertEquals("bar onchange", component.getOnchange());
    }

    public void testSetGetOnclick() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setOnclick("foo onclick");
        assertEquals("foo onclick", component.getOnclick());
    }

    public void testSetGetOnclick_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onclick");
        component.setValueBinding("onclick", vb);
        assertEquals("bar onclick", component.getOnclick());
    }

    public void testSetGetOndblclick() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setOndblclick("foo ondblclick");
        assertEquals("foo ondblclick", component.getOndblclick());
    }

    public void testSetGetOndblclick_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar ondblclick");
        component.setValueBinding("ondblclick", vb);
        assertEquals("bar ondblclick", component.getOndblclick());
    }

    public void testSetGetOnfocus() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setOnfocus("foo onfocus");
        assertEquals("foo onfocus", component.getOnfocus());
    }

    public void testSetGetOnfocus_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onfocus");
        component.setValueBinding("onfocus", vb);
        assertEquals("bar onfocus", component.getOnfocus());
    }

    public void testSetGetOnkeydown() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setOnkeydown("foo onkeydown");
        assertEquals("foo onkeydown", component.getOnkeydown());
    }

    public void testSetGetOnkeydown_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onkeydown");
        component.setValueBinding("onkeydown", vb);
        assertEquals("bar onkeydown", component.getOnkeydown());
    }

    public void testSetGetOnkeypress() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setOnkeypress("foo onkeypress");
        assertEquals("foo onkeypress", component.getOnkeypress());
    }

    public void testSetGetOnkeypress_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onkeypress");
        component.setValueBinding("onkeypress", vb);
        assertEquals("bar onkeypress", component.getOnkeypress());
    }

    public void testSetGetOnkeyup() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setOnkeyup("foo onkeyup");
        assertEquals("foo onkeyup", component.getOnkeyup());
    }

    public void testSetGetOnkeyup_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onkeyup");
        component.setValueBinding("onkeyup", vb);
        assertEquals("bar onkeyup", component.getOnkeyup());
    }

    public void testSetGetOnmousedown() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setOnmousedown("foo onmousedown");
        assertEquals("foo onmousedown", component.getOnmousedown());
    }

    public void testSetGetOnmousedown_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmousedown");
        component.setValueBinding("onmousedown", vb);
        assertEquals("bar onmousedown", component.getOnmousedown());
    }

    public void testSetGetOnmousemove() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setOnmousemove("foo onmousemove");
        assertEquals("foo onmousemove", component.getOnmousemove());
    }

    public void testSetGetOnmousemove_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmousemove");
        component.setValueBinding("onmousemove", vb);
        assertEquals("bar onmousemove", component.getOnmousemove());
    }

    public void testSetGetOnmouseout() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setOnmouseout("foo onmouseout");
        assertEquals("foo onmouseout", component.getOnmouseout());
    }

    public void testSetGetOnmouseout_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmouseout");
        component.setValueBinding("onmouseout", vb);
        assertEquals("bar onmouseout", component.getOnmouseout());
    }

    public void testSetGetOnmouseover() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setOnmouseover("foo onmouseover");
        assertEquals("foo onmouseover", component.getOnmouseover());
    }

    public void testSetGetOnmouseover_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmouseover");
        component.setValueBinding("onmouseover", vb);
        assertEquals("bar onmouseover", component.getOnmouseover());
    }

    public void testSetGetOnmouseup() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setOnmouseup("foo onmouseup");
        assertEquals("foo onmouseup", component.getOnmouseup());
    }

    public void testSetGetOnmouseup_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmouseup");
        component.setValueBinding("onmouseup", vb);
        assertEquals("bar onmouseup", component.getOnmouseup());
    }

    public void testSetGetOnselect() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setOnselect("foo onselect");
        assertEquals("foo onselect", component.getOnselect());
    }

    public void testSetGetOnselect_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onselect");
        component.setValueBinding("onselect", vb);
        assertEquals("bar onselect", component.getOnselect());
    }

    public void testSetGetReadonly() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setReadonly(true);
        assertEquals(true, component.isReadonly());
    }

    public void testSetGetReadonly_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), Boolean.TRUE);
        component.setValueBinding("readonly", vb);
        assertEquals(true, component.isReadonly());
    }

    public void testSetGetStyle() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setStyle("foo style");
        assertEquals("foo style", component.getStyle());
    }

    public void testSetGetStyle_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar style");
        component.setValueBinding("style", vb);
        assertEquals("bar style", component.getStyle());
    }

    public void testSetGetStyleClass() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setStyleClass("foo styleClass");
        assertEquals("foo styleClass", component.getStyleClass());
    }

    public void testSetGetStyleClass_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar styleClass");
        component.setValueBinding("styleClass", vb);
        assertEquals("bar styleClass", component.getStyleClass());
    }

    public void testSetGetTabindex() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setTabindex("foo tabindex");
        assertEquals("foo tabindex", component.getTabindex());
    }

    public void testSetGetTabindex_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar tabindex");
        component.setValueBinding("tabindex", vb);
        assertEquals("bar tabindex", component.getTabindex());
    }

    public void testSetGetTitle() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setTitle("foo title");
        assertEquals("foo title", component.getTitle());
    }

    public void testSetGetTitle_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar title");
        component.setValueBinding("title", vb);
        assertEquals("bar title", component.getTitle());
    }

    // ----

    public void testSetGetType_SubmitIfNull() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setType(null);
        assertEquals("submit", component.getType());
    }

    public void testSetGetType_SubmitIfInvalid() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setType("aaa");
        assertEquals("submit", component.getType());
    }

    public void testSetGetType_Reset() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        component.setType("reset");
        assertEquals("reset", component.getType());
    }

    public void testSetGetType_ValueBinding() throws Exception {
        HtmlCommandButton component = createHtmlCommandButton();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "reset");
        component.setValueBinding("type", vb);
        assertEquals("reset", component.getType());
    }

    private HtmlCommandButton createHtmlCommandButton() {
        return (HtmlCommandButton) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlCommandButton();
    }

}
