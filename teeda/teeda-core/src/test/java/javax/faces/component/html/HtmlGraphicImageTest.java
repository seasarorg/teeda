/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
import javax.faces.component.UIGraphicTest;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author manhole
 */
public class HtmlGraphicImageTest extends UIGraphicTest {

    public void testSetGetAlt() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setAlt("foo alt");
        assertEquals("foo alt", component.getAlt());
    }

    public void testSetGetAlt_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar alt");
        component.setValueBinding("alt", vb);
        assertEquals("bar alt", component.getAlt());
        assertEquals("bar alt", component.getValueBinding("alt").getValue(
                context));
    }

    public void testSetGetDir() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setDir("foo dir");
        assertEquals("foo dir", component.getDir());
    }

    public void testSetGetDir_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar dir");
        component.setValueBinding("dir", vb);
        assertEquals("bar dir", component.getDir());
        assertEquals("bar dir", component.getValueBinding("dir").getValue(
                context));
    }

    public void testSetGetHeight() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setHeight("foo height");
        assertEquals("foo height", component.getHeight());
    }

    public void testSetGetHeight_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar height");
        component.setValueBinding("height", vb);
        assertEquals("bar height", component.getHeight());
        assertEquals("bar height", component.getValueBinding("height")
                .getValue(context));
    }

    public void testSetGetIsmap() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setIsmap(true);
        assertEquals(true, component.isIsmap());
    }

    public void testSetGetIsmap_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, Boolean.TRUE);
        component.setValueBinding("ismap", vb);
        assertEquals(true, component.isIsmap());
        assertEquals(Boolean.TRUE, component.getValueBinding("ismap").getValue(
                context));
    }

    public void testSetGetLang() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setLang("foo lang");
        assertEquals("foo lang", component.getLang());
    }

    public void testSetGetLang_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar lang");
        component.setValueBinding("lang", vb);
        assertEquals("bar lang", component.getLang());
        assertEquals("bar lang", component.getValueBinding("lang").getValue(
                context));
    }

    public void testSetGetLongdesc() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setLongdesc("foo longdesc");
        assertEquals("foo longdesc", component.getLongdesc());
    }

    public void testSetGetLongdesc_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar longdesc");
        component.setValueBinding("longdesc", vb);
        assertEquals("bar longdesc", component.getLongdesc());
        assertEquals("bar longdesc", component.getValueBinding("longdesc")
                .getValue(context));
    }

    public void testSetGetOnclick() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOnclick("foo onclick");
        assertEquals("foo onclick", component.getOnclick());
    }

    public void testSetGetOnclick_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onclick");
        component.setValueBinding("onclick", vb);
        assertEquals("bar onclick", component.getOnclick());
        assertEquals("bar onclick", component.getValueBinding("onclick")
                .getValue(context));
    }

    public void testSetGetOndblclick() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOndblclick("foo ondblclick");
        assertEquals("foo ondblclick", component.getOndblclick());
    }

    public void testSetGetOndblclick_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar ondblclick");
        component.setValueBinding("ondblclick", vb);
        assertEquals("bar ondblclick", component.getOndblclick());
        assertEquals("bar ondblclick", component.getValueBinding("ondblclick")
                .getValue(context));
    }

    public void testSetGetOnkeydown() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOnkeydown("foo onkeydown");
        assertEquals("foo onkeydown", component.getOnkeydown());
    }

    public void testSetGetOnkeydown_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onkeydown");
        component.setValueBinding("onkeydown", vb);
        assertEquals("bar onkeydown", component.getOnkeydown());
        assertEquals("bar onkeydown", component.getValueBinding("onkeydown")
                .getValue(context));
    }

    public void testSetGetOnkeypress() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOnkeypress("foo onkeypress");
        assertEquals("foo onkeypress", component.getOnkeypress());
    }

    public void testSetGetOnkeypress_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onkeypress");
        component.setValueBinding("onkeypress", vb);
        assertEquals("bar onkeypress", component.getOnkeypress());
        assertEquals("bar onkeypress", component.getValueBinding("onkeypress")
                .getValue(context));
    }

    public void testSetGetOnkeyup() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOnkeyup("foo onkeyup");
        assertEquals("foo onkeyup", component.getOnkeyup());
    }

    public void testSetGetOnkeyup_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onkeyup");
        component.setValueBinding("onkeyup", vb);
        assertEquals("bar onkeyup", component.getOnkeyup());
        assertEquals("bar onkeyup", component.getValueBinding("onkeyup")
                .getValue(context));
    }

    public void testSetGetOnmousedown() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOnmousedown("foo onmousedown");
        assertEquals("foo onmousedown", component.getOnmousedown());
    }

    public void testSetGetOnmousedown_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmousedown");
        component.setValueBinding("onmousedown", vb);
        assertEquals("bar onmousedown", component.getOnmousedown());
        assertEquals("bar onmousedown", component
                .getValueBinding("onmousedown").getValue(context));
    }

    public void testSetGetOnmousemove() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOnmousemove("foo onmousemove");
        assertEquals("foo onmousemove", component.getOnmousemove());
    }

    public void testSetGetOnmousemove_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmousemove");
        component.setValueBinding("onmousemove", vb);
        assertEquals("bar onmousemove", component.getOnmousemove());
        assertEquals("bar onmousemove", component
                .getValueBinding("onmousemove").getValue(context));
    }

    public void testSetGetOnmouseout() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOnmouseout("foo onmouseout");
        assertEquals("foo onmouseout", component.getOnmouseout());
    }

    public void testSetGetOnmouseout_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmouseout");
        component.setValueBinding("onmouseout", vb);
        assertEquals("bar onmouseout", component.getOnmouseout());
        assertEquals("bar onmouseout", component.getValueBinding("onmouseout")
                .getValue(context));
    }

    public void testSetGetOnmouseover() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOnmouseover("foo onmouseover");
        assertEquals("foo onmouseover", component.getOnmouseover());
    }

    public void testSetGetOnmouseover_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmouseover");
        component.setValueBinding("onmouseover", vb);
        assertEquals("bar onmouseover", component.getOnmouseover());
        assertEquals("bar onmouseover", component
                .getValueBinding("onmouseover").getValue(context));
    }

    public void testSetGetOnmouseup() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOnmouseup("foo onmouseup");
        assertEquals("foo onmouseup", component.getOnmouseup());
    }

    public void testSetGetOnmouseup_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar onmouseup");
        component.setValueBinding("onmouseup", vb);
        assertEquals("bar onmouseup", component.getOnmouseup());
        assertEquals("bar onmouseup", component.getValueBinding("onmouseup")
                .getValue(context));
    }

    public void testSetGetStyle() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setStyle("foo style");
        assertEquals("foo style", component.getStyle());
    }

    public void testSetGetStyle_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar style");
        component.setValueBinding("style", vb);
        assertEquals("bar style", component.getStyle());
        assertEquals("bar style", component.getValueBinding("style").getValue(
                context));
    }

    public void testSetGetStyleClass() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setStyleClass("foo styleClass");
        assertEquals("foo styleClass", component.getStyleClass());
    }

    public void testSetGetStyleClass_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar styleClass");
        component.setValueBinding("styleClass", vb);
        assertEquals("bar styleClass", component.getStyleClass());
        assertEquals("bar styleClass", component.getValueBinding("styleClass")
                .getValue(context));
    }

    public void testSetGetTitle() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setTitle("foo title");
        assertEquals("foo title", component.getTitle());
    }

    public void testSetGetTitle_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar title");
        component.setValueBinding("title", vb);
        assertEquals("bar title", component.getTitle());
        assertEquals("bar title", component.getValueBinding("title").getValue(
                context));
    }

    public void testSetGetUsemap() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setUsemap("foo usemap");
        assertEquals("foo usemap", component.getUsemap());
    }

    public void testSetGetUsemap_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar usemap");
        component.setValueBinding("usemap", vb);
        assertEquals("bar usemap", component.getUsemap());
        assertEquals("bar usemap", component.getValueBinding("usemap")
                .getValue(context));
    }

    public void testSetGetWidth() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setWidth("foo width");
        assertEquals("foo width", component.getWidth());
    }

    public void testSetGetWidth_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar width");
        component.setValueBinding("width", vb);
        assertEquals("bar width", component.getWidth());
        assertEquals("bar width", component.getValueBinding("width").getValue(
                context));
    }

    private HtmlGraphicImage createHtmlGraphicImage() {
        return (HtmlGraphicImage) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlGraphicImage();
    }

}
