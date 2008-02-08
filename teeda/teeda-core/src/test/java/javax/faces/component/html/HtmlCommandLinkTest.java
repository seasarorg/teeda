/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import javax.faces.component.UICommandTest;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author manhole
 */
public class HtmlCommandLinkTest extends UICommandTest {

    public void testSetGetAccesskey() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setAccesskey("foo accesskey");
        assertEquals("foo accesskey", component.getAccesskey());
    }

    public void testSetGetAccesskey_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar accesskey");
        component.setValueBinding("accesskey", vb);
        assertEquals("bar accesskey", component.getAccesskey());
        assertEquals("bar accesskey", component.getValueBinding("accesskey")
                .getValue(context));
    }

    public void testSetGetCharset() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setCharset("foo charset");
        assertEquals("foo charset", component.getCharset());
    }

    public void testSetGetCharset_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar charset");
        component.setValueBinding("charset", vb);
        assertEquals("bar charset", component.getCharset());
        assertEquals("bar charset", component.getValueBinding("charset")
                .getValue(context));
    }

    public void testSetGetCoords() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setCoords("foo coords");
        assertEquals("foo coords", component.getCoords());
    }

    public void testSetGetCoords_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar coords");
        component.setValueBinding("coords", vb);
        assertEquals("bar coords", component.getCoords());
        assertEquals("bar coords", component.getValueBinding("coords")
                .getValue(context));
    }

    public void testSetGetDir() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setDir("foo dir");
        assertEquals("foo dir", component.getDir());
    }

    public void testSetGetDir_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar dir");
        component.setValueBinding("dir", vb);
        assertEquals("bar dir", component.getDir());
        assertEquals("bar dir", component.getValueBinding("dir").getValue(
                context));
    }

    public void testSetGetHreflang() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setHreflang("foo hreflang");
        assertEquals("foo hreflang", component.getHreflang());
    }

    public void testSetGetHreflang_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar hreflang");
        component.setValueBinding("hreflang", vb);
        assertEquals("bar hreflang", component.getHreflang());
        assertEquals("bar hreflang", component.getValueBinding("hreflang")
                .getValue(context));
    }

    public void testSetGetLang() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setLang("foo lang");
        assertEquals("foo lang", component.getLang());
    }

    public void testSetGetLang_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar lang");
        component.setValueBinding("lang", vb);
        assertEquals("bar lang", component.getLang());
        assertEquals("bar lang", component.getValueBinding("lang").getValue(
                context));
    }

    public void testSetGetOnblur() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setOnblur("foo onblur");
        assertEquals("foo onblur", component.getOnblur());
    }

    public void testSetGetOnblur_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onblur");
        component.setValueBinding("onblur", vb);
        assertEquals("bar onblur", component.getOnblur());
        assertEquals("bar onblur", component.getValueBinding("onblur")
                .getValue(context));
    }

    public void testSetGetOnclick() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setOnclick("foo onclick");
        assertEquals("foo onclick", component.getOnclick());
    }

    public void testSetGetOnclick_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onclick");
        component.setValueBinding("onclick", vb);
        assertEquals("bar onclick", component.getOnclick());
        assertEquals("bar onclick", component.getValueBinding("onclick")
                .getValue(context));
    }

    public void testSetGetOndblclick() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setOndblclick("foo ondblclick");
        assertEquals("foo ondblclick", component.getOndblclick());
    }

    public void testSetGetOndblclick_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar ondblclick");
        component.setValueBinding("ondblclick", vb);
        assertEquals("bar ondblclick", component.getOndblclick());
        assertEquals("bar ondblclick", component.getValueBinding("ondblclick")
                .getValue(context));
    }

    public void testSetGetOnfocus() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setOnfocus("foo onfocus");
        assertEquals("foo onfocus", component.getOnfocus());
    }

    public void testSetGetOnfocus_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onfocus");
        component.setValueBinding("onfocus", vb);
        assertEquals("bar onfocus", component.getOnfocus());
        assertEquals("bar onfocus", component.getValueBinding("onfocus")
                .getValue(context));
    }

    public void testSetGetOnkeydown() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setOnkeydown("foo onkeydown");
        assertEquals("foo onkeydown", component.getOnkeydown());
    }

    public void testSetGetOnkeydown_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onkeydown");
        component.setValueBinding("onkeydown", vb);
        assertEquals("bar onkeydown", component.getOnkeydown());
        assertEquals("bar onkeydown", component.getValueBinding("onkeydown")
                .getValue(context));
    }

    public void testSetGetOnkeypress() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setOnkeypress("foo onkeypress");
        assertEquals("foo onkeypress", component.getOnkeypress());
    }

    public void testSetGetOnkeypress_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onkeypress");
        component.setValueBinding("onkeypress", vb);
        assertEquals("bar onkeypress", component.getOnkeypress());
        assertEquals("bar onkeypress", component.getValueBinding("onkeypress")
                .getValue(context));
    }

    public void testSetGetOnkeyup() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setOnkeyup("foo onkeyup");
        assertEquals("foo onkeyup", component.getOnkeyup());
    }

    public void testSetGetOnkeyup_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onkeyup");
        component.setValueBinding("onkeyup", vb);
        assertEquals("bar onkeyup", component.getOnkeyup());
        assertEquals("bar onkeyup", component.getValueBinding("onkeyup")
                .getValue(context));
    }

    public void testSetGetOnmousedown() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setOnmousedown("foo onmousedown");
        assertEquals("foo onmousedown", component.getOnmousedown());
    }

    public void testSetGetOnmousedown_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmousedown");
        component.setValueBinding("onmousedown", vb);
        assertEquals("bar onmousedown", component.getOnmousedown());
        assertEquals("bar onmousedown", component
                .getValueBinding("onmousedown").getValue(context));
    }

    public void testSetGetOnmousemove() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setOnmousemove("foo onmousemove");
        assertEquals("foo onmousemove", component.getOnmousemove());
    }

    public void testSetGetOnmousemove_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmousemove");
        component.setValueBinding("onmousemove", vb);
        assertEquals("bar onmousemove", component.getOnmousemove());
        assertEquals("bar onmousemove", component
                .getValueBinding("onmousemove").getValue(context));
    }

    public void testSetGetOnmouseout() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setOnmouseout("foo onmouseout");
        assertEquals("foo onmouseout", component.getOnmouseout());
    }

    public void testSetGetOnmouseout_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmouseout");
        component.setValueBinding("onmouseout", vb);
        assertEquals("bar onmouseout", component.getOnmouseout());
        assertEquals("bar onmouseout", component.getValueBinding("onmouseout")
                .getValue(context));
    }

    public void testSetGetOnmouseover() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setOnmouseover("foo onmouseover");
        assertEquals("foo onmouseover", component.getOnmouseover());
    }

    public void testSetGetOnmouseover_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmouseover");
        component.setValueBinding("onmouseover", vb);
        assertEquals("bar onmouseover", component.getOnmouseover());
        assertEquals("bar onmouseover", component
                .getValueBinding("onmouseover").getValue(context));
    }

    public void testSetGetOnmouseup() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setOnmouseup("foo onmouseup");
        assertEquals("foo onmouseup", component.getOnmouseup());
    }

    public void testSetGetOnmouseup_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmouseup");
        component.setValueBinding("onmouseup", vb);
        assertEquals("bar onmouseup", component.getOnmouseup());
        assertEquals("bar onmouseup", component.getValueBinding("onmouseup")
                .getValue(context));
    }

    public void testSetGetRel() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setRel("foo rel");
        assertEquals("foo rel", component.getRel());
    }

    public void testSetGetRel_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar rel");
        component.setValueBinding("rel", vb);
        assertEquals("bar rel", component.getRel());
        assertEquals("bar rel", component.getValueBinding("rel").getValue(
                context));
    }

    public void testSetGetRev() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setRev("foo rev");
        assertEquals("foo rev", component.getRev());
    }

    public void testSetGetRev_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar rev");
        component.setValueBinding("rev", vb);
        assertEquals("bar rev", component.getRev());
        assertEquals("bar rev", component.getValueBinding("rev").getValue(
                context));
    }

    public void testSetGetShape() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setShape("foo shape");
        assertEquals("foo shape", component.getShape());
    }

    public void testSetGetShape_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar shape");
        component.setValueBinding("shape", vb);
        assertEquals("bar shape", component.getShape());
        assertEquals("bar shape", component.getValueBinding("shape").getValue(
                context));
    }

    public void testSetGetStyle() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setStyle("foo style");
        assertEquals("foo style", component.getStyle());
    }

    public void testSetGetStyle_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar style");
        component.setValueBinding("style", vb);
        assertEquals("bar style", component.getStyle());
        assertEquals("bar style", component.getValueBinding("style").getValue(
                context));
    }

    public void testSetGetStyleClass() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setStyleClass("foo styleClass");
        assertEquals("foo styleClass", component.getStyleClass());
    }

    public void testSetGetStyleClass_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar styleClass");
        component.setValueBinding("styleClass", vb);
        assertEquals("bar styleClass", component.getStyleClass());
        assertEquals("bar styleClass", component.getValueBinding("styleClass")
                .getValue(context));
    }

    public void testSetGetTabindex() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setTabindex("foo tabindex");
        assertEquals("foo tabindex", component.getTabindex());
    }

    public void testSetGetTabindex_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar tabindex");
        component.setValueBinding("tabindex", vb);
        assertEquals("bar tabindex", component.getTabindex());
        assertEquals("bar tabindex", component.getValueBinding("tabindex")
                .getValue(context));
    }

    public void testSetGetTarget() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setTarget("foo target");
        assertEquals("foo target", component.getTarget());
    }

    public void testSetGetTarget_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar target");
        component.setValueBinding("target", vb);
        assertEquals("bar target", component.getTarget());
        assertEquals("bar target", component.getValueBinding("target")
                .getValue(context));
    }

    public void testSetGetTitle() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setTitle("foo title");
        assertEquals("foo title", component.getTitle());
    }

    public void testSetGetTitle_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar title");
        component.setValueBinding("title", vb);
        assertEquals("bar title", component.getTitle());
        assertEquals("bar title", component.getValueBinding("title").getValue(
                context));
    }

    public void testSetGetType() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        component.setType("foo type");
        assertEquals("foo type", component.getType());
    }

    public void testSetGetType_ValueBinding() throws Exception {
        HtmlCommandLink component = createHtmlCommandLink();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar type");
        component.setValueBinding("type", vb);
        assertEquals("bar type", component.getType());
        assertEquals("bar type", component.getValueBinding("type").getValue(
                context));
    }

    private HtmlCommandLink createHtmlCommandLink() {
        return (HtmlCommandLink) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlCommandLink();
    }

}
