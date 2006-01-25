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
import javax.faces.component.UIGraphicTest;

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
        vb.setValue(getFacesContext(), "bar alt");
        component.setValueBinding("alt", vb);
        assertEquals("bar alt", component.getAlt());
    }

    public void testSetGetDir() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setDir("foo dir");
        assertEquals("foo dir", component.getDir());
    }

    public void testSetGetDir_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar dir");
        component.setValueBinding("dir", vb);
        assertEquals("bar dir", component.getDir());
    }

    public void testSetGetHeight() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setHeight("foo height");
        assertEquals("foo height", component.getHeight());
    }

    public void testSetGetHeight_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar height");
        component.setValueBinding("height", vb);
        assertEquals("bar height", component.getHeight());
    }

    public void testSetGetIsmap() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setIsmap(true);
        assertEquals(true, component.isIsmap());
    }

    public void testSetGetIsmap_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), Boolean.TRUE);
        component.setValueBinding("ismap", vb);
        assertEquals(true, component.isIsmap());
    }

    public void testSetGetLang() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setLang("foo lang");
        assertEquals("foo lang", component.getLang());
    }

    public void testSetGetLang_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar lang");
        component.setValueBinding("lang", vb);
        assertEquals("bar lang", component.getLang());
    }

    public void testSetGetLongdesc() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setLongdesc("foo longdesc");
        assertEquals("foo longdesc", component.getLongdesc());
    }

    public void testSetGetLongdesc_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar longdesc");
        component.setValueBinding("longdesc", vb);
        assertEquals("bar longdesc", component.getLongdesc());
    }

    public void testSetGetOnclick() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOnclick("foo onclick");
        assertEquals("foo onclick", component.getOnclick());
    }

    public void testSetGetOnclick_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onclick");
        component.setValueBinding("onclick", vb);
        assertEquals("bar onclick", component.getOnclick());
    }

    public void testSetGetOndblclick() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOndblclick("foo ondblclick");
        assertEquals("foo ondblclick", component.getOndblclick());
    }

    public void testSetGetOndblclick_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar ondblclick");
        component.setValueBinding("ondblclick", vb);
        assertEquals("bar ondblclick", component.getOndblclick());
    }

    public void testSetGetOnkeydown() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOnkeydown("foo onkeydown");
        assertEquals("foo onkeydown", component.getOnkeydown());
    }

    public void testSetGetOnkeydown_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onkeydown");
        component.setValueBinding("onkeydown", vb);
        assertEquals("bar onkeydown", component.getOnkeydown());
    }

    public void testSetGetOnkeypress() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOnkeypress("foo onkeypress");
        assertEquals("foo onkeypress", component.getOnkeypress());
    }

    public void testSetGetOnkeypress_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onkeypress");
        component.setValueBinding("onkeypress", vb);
        assertEquals("bar onkeypress", component.getOnkeypress());
    }

    public void testSetGetOnkeyup() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOnkeyup("foo onkeyup");
        assertEquals("foo onkeyup", component.getOnkeyup());
    }

    public void testSetGetOnkeyup_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onkeyup");
        component.setValueBinding("onkeyup", vb);
        assertEquals("bar onkeyup", component.getOnkeyup());
    }

    public void testSetGetOnmousedown() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOnmousedown("foo onmousedown");
        assertEquals("foo onmousedown", component.getOnmousedown());
    }

    public void testSetGetOnmousedown_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmousedown");
        component.setValueBinding("onmousedown", vb);
        assertEquals("bar onmousedown", component.getOnmousedown());
    }

    public void testSetGetOnmousemove() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOnmousemove("foo onmousemove");
        assertEquals("foo onmousemove", component.getOnmousemove());
    }

    public void testSetGetOnmousemove_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmousemove");
        component.setValueBinding("onmousemove", vb);
        assertEquals("bar onmousemove", component.getOnmousemove());
    }

    public void testSetGetOnmouseout() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOnmouseout("foo onmouseout");
        assertEquals("foo onmouseout", component.getOnmouseout());
    }

    public void testSetGetOnmouseout_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmouseout");
        component.setValueBinding("onmouseout", vb);
        assertEquals("bar onmouseout", component.getOnmouseout());
    }

    public void testSetGetOnmouseover() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOnmouseover("foo onmouseover");
        assertEquals("foo onmouseover", component.getOnmouseover());
    }

    public void testSetGetOnmouseover_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmouseover");
        component.setValueBinding("onmouseover", vb);
        assertEquals("bar onmouseover", component.getOnmouseover());
    }

    public void testSetGetOnmouseup() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setOnmouseup("foo onmouseup");
        assertEquals("foo onmouseup", component.getOnmouseup());
    }

    public void testSetGetOnmouseup_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar onmouseup");
        component.setValueBinding("onmouseup", vb);
        assertEquals("bar onmouseup", component.getOnmouseup());
    }

    public void testSetGetStyle() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setStyle("foo style");
        assertEquals("foo style", component.getStyle());
    }

    public void testSetGetStyle_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar style");
        component.setValueBinding("style", vb);
        assertEquals("bar style", component.getStyle());
    }

    public void testSetGetStyleClass() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setStyleClass("foo styleClass");
        assertEquals("foo styleClass", component.getStyleClass());
    }

    public void testSetGetStyleClass_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar styleClass");
        component.setValueBinding("styleClass", vb);
        assertEquals("bar styleClass", component.getStyleClass());
    }

    public void testSetGetTitle() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setTitle("foo title");
        assertEquals("foo title", component.getTitle());
    }

    public void testSetGetTitle_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar title");
        component.setValueBinding("title", vb);
        assertEquals("bar title", component.getTitle());
    }

    public void testSetGetUsemap() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setUsemap("foo usemap");
        assertEquals("foo usemap", component.getUsemap());
    }

    public void testSetGetUsemap_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar usemap");
        component.setValueBinding("usemap", vb);
        assertEquals("bar usemap", component.getUsemap());
    }

    public void testSetGetWidth() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        component.setWidth("foo width");
        assertEquals("foo width", component.getWidth());
    }

    public void testSetGetWidth_ValueBinding() throws Exception {
        HtmlGraphicImage component = createHtmlGraphicImage();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar width");
        component.setValueBinding("width", vb);
        assertEquals("bar width", component.getWidth());
    }

    private HtmlGraphicImage createHtmlGraphicImage() {
        return (HtmlGraphicImage) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlGraphicImage();
    }

}
