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
 * 
 * TODO test: save, restoreState
 */
public class HtmlOutputLinkTest extends UIOutputTest {

    public void testDefaultRendererType() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        assertEquals("javax.faces.Link", htmlOutputLink.getRendererType());
    }

    public void testSetGetAccesskey() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setAccesskey("foo accesskey");
        assertEquals("foo accesskey", htmlOutputLink.getAccesskey());
    }

    public void testSetGetAccesskey_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar accesskey");
        htmlOutputLink.setValueBinding("accesskey", vb);
        assertEquals("bar accesskey", htmlOutputLink.getAccesskey());
    }

    public void testSetGetCharset() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setCharset("foo charset");
        assertEquals("foo charset", htmlOutputLink.getCharset());
    }

    public void testSetGetCharset_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar charset");
        htmlOutputLink.setValueBinding("charset", vb);
        assertEquals("bar charset", htmlOutputLink.getCharset());
    }

    public void testSetGetCoords() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setCoords("foo coords");
        assertEquals("foo coords", htmlOutputLink.getCoords());
    }

    public void testSetGetCoords_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar coords");
        htmlOutputLink.setValueBinding("coords", vb);
        assertEquals("bar coords", htmlOutputLink.getCoords());
    }

    public void testSetGetDir() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setDir("foo dir");
        assertEquals("foo dir", htmlOutputLink.getDir());
    }

    public void testSetGetDir_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar dir");
        htmlOutputLink.setValueBinding("dir", vb);
        assertEquals("bar dir", htmlOutputLink.getDir());
    }

    public void testSetGetLang() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setLang("foo lang");
        assertEquals("foo lang", htmlOutputLink.getLang());
    }

    public void testSetGetLang_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar lang");
        htmlOutputLink.setValueBinding("lang", vb);
        assertEquals("bar lang", htmlOutputLink.getLang());
    }

    public void testSetGetOnblur() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setOnblur("foo onblur");
        assertEquals("foo onblur", htmlOutputLink.getOnblur());
    }

    public void testSetGetOnblur_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onblur");
        htmlOutputLink.setValueBinding("onblur", vb);
        assertEquals("bar onblur", htmlOutputLink.getOnblur());
    }

    public void testSetGetOnclick() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setOnclick("foo onclick");
        assertEquals("foo onclick", htmlOutputLink.getOnclick());
    }

    public void testSetGetOnclick_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onclick");
        htmlOutputLink.setValueBinding("onclick", vb);
        assertEquals("bar onclick", htmlOutputLink.getOnclick());
    }

    public void testSetGetOndblclick() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setOndblclick("foo ondblclick");
        assertEquals("foo ondblclick", htmlOutputLink.getOndblclick());
    }

    public void testSetGetOndblclick_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar ondblclick");
        htmlOutputLink.setValueBinding("ondblclick", vb);
        assertEquals("bar ondblclick", htmlOutputLink.getOndblclick());
    }

    public void testSetGetOnfocus() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setOnfocus("foo onfocus");
        assertEquals("foo onfocus", htmlOutputLink.getOnfocus());
    }

    public void testSetGetOnfocus_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onfocus");
        htmlOutputLink.setValueBinding("onfocus", vb);
        assertEquals("bar onfocus", htmlOutputLink.getOnfocus());
    }

    public void testSetGetOnkeydown() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setOnkeydown("foo onkeydown");
        assertEquals("foo onkeydown", htmlOutputLink.getOnkeydown());
    }

    public void testSetGetOnkeydown_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onkeydown");
        htmlOutputLink.setValueBinding("onkeydown", vb);
        assertEquals("bar onkeydown", htmlOutputLink.getOnkeydown());
    }

    public void testSetGetOnkeypress() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setOnkeypress("foo onkeypress");
        assertEquals("foo onkeypress", htmlOutputLink.getOnkeypress());
    }

    public void testSetGetOnkeypress_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onkeypress");
        htmlOutputLink.setValueBinding("onkeypress", vb);
        assertEquals("bar onkeypress", htmlOutputLink.getOnkeypress());
    }

    public void testSetGetOnkeyup() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setOnkeyup("foo onkeyup");
        assertEquals("foo onkeyup", htmlOutputLink.getOnkeyup());
    }

    public void testSetGetOnkeyup_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onkeyup");
        htmlOutputLink.setValueBinding("onkeyup", vb);
        assertEquals("bar onkeyup", htmlOutputLink.getOnkeyup());
    }

    public void testSetGetOnmousedown() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setOnmousedown("foo onmousedown");
        assertEquals("foo onmousedown", htmlOutputLink.getOnmousedown());
    }

    public void testSetGetOnmousedown_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmousedown");
        htmlOutputLink.setValueBinding("onmousedown", vb);
        assertEquals("bar onmousedown", htmlOutputLink.getOnmousedown());
    }

    public void testSetGetOnmousemove() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setOnmousemove("foo onmousemove");
        assertEquals("foo onmousemove", htmlOutputLink.getOnmousemove());
    }

    public void testSetGetOnmousemove_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmousemove");
        htmlOutputLink.setValueBinding("onmousemove", vb);
        assertEquals("bar onmousemove", htmlOutputLink.getOnmousemove());
    }

    public void testSetGetOnmouseout() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setOnmouseout("foo onmouseout");
        assertEquals("foo onmouseout", htmlOutputLink.getOnmouseout());
    }

    public void testSetGetOnmouseout_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmouseout");
        htmlOutputLink.setValueBinding("onmouseout", vb);
        assertEquals("bar onmouseout", htmlOutputLink.getOnmouseout());
    }

    public void testSetGetOnmouseover() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setOnmouseover("foo onmouseover");
        assertEquals("foo onmouseover", htmlOutputLink.getOnmouseover());
    }

    public void testSetGetOnmouseover_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmouseover");
        htmlOutputLink.setValueBinding("onmouseover", vb);
        assertEquals("bar onmouseover", htmlOutputLink.getOnmouseover());
    }

    public void testSetGetOnmouseup() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setOnmouseup("foo onmouseup");
        assertEquals("foo onmouseup", htmlOutputLink.getOnmouseup());
    }

    public void testSetGetOnmouseup_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmouseup");
        htmlOutputLink.setValueBinding("onmouseup", vb);
        assertEquals("bar onmouseup", htmlOutputLink.getOnmouseup());
    }

    public void testSetGetRel() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setRel("foo rel");
        assertEquals("foo rel", htmlOutputLink.getRel());
    }

    public void testSetGetRel_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar rel");
        htmlOutputLink.setValueBinding("rel", vb);
        assertEquals("bar rel", htmlOutputLink.getRel());
    }

    public void testSetGetRev() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setRev("foo rev");
        assertEquals("foo rev", htmlOutputLink.getRev());
    }

    public void testSetGetRev_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar rev");
        htmlOutputLink.setValueBinding("rev", vb);
        assertEquals("bar rev", htmlOutputLink.getRev());
    }

    public void testSetGetShape() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setShape("foo shape");
        assertEquals("foo shape", htmlOutputLink.getShape());
    }

    public void testSetGetShape_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar shape");
        htmlOutputLink.setValueBinding("shape", vb);
        assertEquals("bar shape", htmlOutputLink.getShape());
    }

    public void testSetGetStyle() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setStyle("foo style");
        assertEquals("foo style", htmlOutputLink.getStyle());
    }

    public void testSetGetStyle_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar style");
        htmlOutputLink.setValueBinding("style", vb);
        assertEquals("bar style", htmlOutputLink.getStyle());
    }

    public void testSetGetStyleClass() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setStyleClass("foo styleClass");
        assertEquals("foo styleClass", htmlOutputLink.getStyleClass());
    }

    public void testSetGetStyleClass_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar styleClass");
        htmlOutputLink.setValueBinding("styleClass", vb);
        assertEquals("bar styleClass", htmlOutputLink.getStyleClass());
    }

    public void testSetGetTabindex() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setTabindex("foo tabindex");
        assertEquals("foo tabindex", htmlOutputLink.getTabindex());
    }

    public void testSetGetTabindex_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar tabindex");
        htmlOutputLink.setValueBinding("tabindex", vb);
        assertEquals("bar tabindex", htmlOutputLink.getTabindex());
    }

    public void testSetGetTarget() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setTarget("foo target");
        assertEquals("foo target", htmlOutputLink.getTarget());
    }

    public void testSetGetTarget_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar target");
        htmlOutputLink.setValueBinding("target", vb);
        assertEquals("bar target", htmlOutputLink.getTarget());
    }

    public void testSetGetTitle() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setTitle("foo title");
        assertEquals("foo title", htmlOutputLink.getTitle());
    }

    public void testSetGetTitle_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar title");
        htmlOutputLink.setValueBinding("title", vb);
        assertEquals("bar title", htmlOutputLink.getTitle());
    }

    public void testSetGetType() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        htmlOutputLink.setType("foo type");
        assertEquals("foo type", htmlOutputLink.getType());
    }

    public void testSetGetType_ValueBinding() throws Exception {
        HtmlOutputLink htmlOutputLink = createhtmlOutputLink();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar type");
        htmlOutputLink.setValueBinding("type", vb);
        assertEquals("bar type", htmlOutputLink.getType());
    }

    private HtmlOutputLink createhtmlOutputLink() {
        return (HtmlOutputLink) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlOutputLink();
    }

}
