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
import javax.faces.component.UIOutputTest;

import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author manhole
 */
public class HtmlOutputLinkTest extends UIOutputTest {

    public void testSetGetAccesskey() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setAccesskey("foo accesskey");
        assertEquals("foo accesskey", component.getAccesskey());
    }

    public void testSetGetAccesskey_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar accesskey");
        component.setValueBinding("accesskey", vb);
        assertEquals("bar accesskey", component.getAccesskey());
    }

    public void testSetGetCharset() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setCharset("foo charset");
        assertEquals("foo charset", component.getCharset());
    }

    public void testSetGetCharset_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar charset");
        component.setValueBinding("charset", vb);
        assertEquals("bar charset", component.getCharset());
    }

    public void testSetGetCoords() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setCoords("foo coords");
        assertEquals("foo coords", component.getCoords());
    }

    public void testSetGetCoords_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar coords");
        component.setValueBinding("coords", vb);
        assertEquals("bar coords", component.getCoords());
    }

    public void testSetGetDir() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setDir("foo dir");
        assertEquals("foo dir", component.getDir());
    }

    public void testSetGetDir_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar dir");
        component.setValueBinding("dir", vb);
        assertEquals("bar dir", component.getDir());
    }

    public void testSetGetLang() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setLang("foo lang");
        assertEquals("foo lang", component.getLang());
    }

    public void testSetGetLang_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar lang");
        component.setValueBinding("lang", vb);
        assertEquals("bar lang", component.getLang());
    }

    public void testSetGetOnblur() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setOnblur("foo onblur");
        assertEquals("foo onblur", component.getOnblur());
    }

    public void testSetGetOnblur_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onblur");
        component.setValueBinding("onblur", vb);
        assertEquals("bar onblur", component.getOnblur());
    }

    public void testSetGetOnclick() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setOnclick("foo onclick");
        assertEquals("foo onclick", component.getOnclick());
    }

    public void testSetGetOnclick_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onclick");
        component.setValueBinding("onclick", vb);
        assertEquals("bar onclick", component.getOnclick());
    }

    public void testSetGetOndblclick() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setOndblclick("foo ondblclick");
        assertEquals("foo ondblclick", component.getOndblclick());
    }

    public void testSetGetOndblclick_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar ondblclick");
        component.setValueBinding("ondblclick", vb);
        assertEquals("bar ondblclick", component.getOndblclick());
    }

    public void testSetGetOnfocus() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setOnfocus("foo onfocus");
        assertEquals("foo onfocus", component.getOnfocus());
    }

    public void testSetGetOnfocus_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onfocus");
        component.setValueBinding("onfocus", vb);
        assertEquals("bar onfocus", component.getOnfocus());
    }

    public void testSetGetOnkeydown() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setOnkeydown("foo onkeydown");
        assertEquals("foo onkeydown", component.getOnkeydown());
    }

    public void testSetGetOnkeydown_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onkeydown");
        component.setValueBinding("onkeydown", vb);
        assertEquals("bar onkeydown", component.getOnkeydown());
    }

    public void testSetGetOnkeypress() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setOnkeypress("foo onkeypress");
        assertEquals("foo onkeypress", component.getOnkeypress());
    }

    public void testSetGetOnkeypress_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onkeypress");
        component.setValueBinding("onkeypress", vb);
        assertEquals("bar onkeypress", component.getOnkeypress());
    }

    public void testSetGetOnkeyup() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setOnkeyup("foo onkeyup");
        assertEquals("foo onkeyup", component.getOnkeyup());
    }

    public void testSetGetOnkeyup_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onkeyup");
        component.setValueBinding("onkeyup", vb);
        assertEquals("bar onkeyup", component.getOnkeyup());
    }

    public void testSetGetOnmousedown() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setOnmousedown("foo onmousedown");
        assertEquals("foo onmousedown", component.getOnmousedown());
    }

    public void testSetGetOnmousedown_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmousedown");
        component.setValueBinding("onmousedown", vb);
        assertEquals("bar onmousedown", component.getOnmousedown());
    }

    public void testSetGetOnmousemove() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setOnmousemove("foo onmousemove");
        assertEquals("foo onmousemove", component.getOnmousemove());
    }

    public void testSetGetOnmousemove_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmousemove");
        component.setValueBinding("onmousemove", vb);
        assertEquals("bar onmousemove", component.getOnmousemove());
    }

    public void testSetGetOnmouseout() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setOnmouseout("foo onmouseout");
        assertEquals("foo onmouseout", component.getOnmouseout());
    }

    public void testSetGetOnmouseout_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmouseout");
        component.setValueBinding("onmouseout", vb);
        assertEquals("bar onmouseout", component.getOnmouseout());
    }

    public void testSetGetOnmouseover() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setOnmouseover("foo onmouseover");
        assertEquals("foo onmouseover", component.getOnmouseover());
    }

    public void testSetGetOnmouseover_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmouseover");
        component.setValueBinding("onmouseover", vb);
        assertEquals("bar onmouseover", component.getOnmouseover());
    }

    public void testSetGetOnmouseup() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setOnmouseup("foo onmouseup");
        assertEquals("foo onmouseup", component.getOnmouseup());
    }

    public void testSetGetOnmouseup_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmouseup");
        component.setValueBinding("onmouseup", vb);
        assertEquals("bar onmouseup", component.getOnmouseup());
    }

    public void testSetGetRel() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setRel("foo rel");
        assertEquals("foo rel", component.getRel());
    }

    public void testSetGetRel_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar rel");
        component.setValueBinding("rel", vb);
        assertEquals("bar rel", component.getRel());
    }

    public void testSetGetRev() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setRev("foo rev");
        assertEquals("foo rev", component.getRev());
    }

    public void testSetGetRev_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar rev");
        component.setValueBinding("rev", vb);
        assertEquals("bar rev", component.getRev());
    }

    public void testSetGetShape() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setShape("foo shape");
        assertEquals("foo shape", component.getShape());
    }

    public void testSetGetShape_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar shape");
        component.setValueBinding("shape", vb);
        assertEquals("bar shape", component.getShape());
    }

    public void testSetGetStyle() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setStyle("foo style");
        assertEquals("foo style", component.getStyle());
    }

    public void testSetGetStyle_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar style");
        component.setValueBinding("style", vb);
        assertEquals("bar style", component.getStyle());
    }

    public void testSetGetStyleClass() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setStyleClass("foo styleClass");
        assertEquals("foo styleClass", component.getStyleClass());
    }

    public void testSetGetStyleClass_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar styleClass");
        component.setValueBinding("styleClass", vb);
        assertEquals("bar styleClass", component.getStyleClass());
    }

    public void testSetGetTabindex() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setTabindex("foo tabindex");
        assertEquals("foo tabindex", component.getTabindex());
    }

    public void testSetGetTabindex_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar tabindex");
        component.setValueBinding("tabindex", vb);
        assertEquals("bar tabindex", component.getTabindex());
    }

    public void testSetGetTarget() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setTarget("foo target");
        assertEquals("foo target", component.getTarget());
    }

    public void testSetGetTarget_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar target");
        component.setValueBinding("target", vb);
        assertEquals("bar target", component.getTarget());
    }

    public void testSetGetTitle() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setTitle("foo title");
        assertEquals("foo title", component.getTitle());
    }

    public void testSetGetTitle_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar title");
        component.setValueBinding("title", vb);
        assertEquals("bar title", component.getTitle());
    }

    public void testSetGetType() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        component.setType("foo type");
        assertEquals("foo type", component.getType());
    }

    public void testSetGetType_ValueBinding() throws Exception {
        HtmlOutputLink component = createHtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar type");
        component.setValueBinding("type", vb);
        assertEquals("bar type", component.getType());
    }

    private HtmlOutputLink createHtmlOutputLink() {
        return (HtmlOutputLink) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlOutputLink();
    }

}
