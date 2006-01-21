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
public class HtmlOutputTextTest extends UIOutputTest {

    public void testSetGetEscape() throws Exception {
        HtmlOutputText htmlOutputText = createHtmlOutputText();
        // default is true
        assertEquals(true, htmlOutputText.isEscape());
        htmlOutputText.setEscape(false);
        assertEquals(false, htmlOutputText.isEscape());
    }

    public void testSetGetEscape_ValueBinding() throws Exception {
        HtmlOutputText htmlOutputText = createHtmlOutputText();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), Boolean.FALSE);
        htmlOutputText.setValueBinding("escape", vb);
        assertEquals(false, htmlOutputText.isEscape());
    }

    public void testSetGetStyle() throws Exception {
        HtmlOutputText htmlOutputText = createHtmlOutputText();
        assertEquals(null, htmlOutputText.getStyle());
        htmlOutputText.setStyle("foo style");
        assertEquals("foo style", htmlOutputText.getStyle());
    }

    public void testSetGetStyle_ValueBinding() throws Exception {
        HtmlOutputText htmlOutputText = createHtmlOutputText();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar style");
        htmlOutputText.setValueBinding("style", vb);
        assertEquals("bar style", htmlOutputText.getStyle());
    }

    public void testSetGetStyleClass() throws Exception {
        HtmlOutputText htmlOutputText = createHtmlOutputText();
        assertEquals(null, htmlOutputText.getStyleClass());
        htmlOutputText.setStyleClass("foo class");
        assertEquals("foo class", htmlOutputText.getStyleClass());
    }

    public void testSetGetStyleClass_ValueBinding() throws Exception {
        HtmlOutputText htmlOutputText = createHtmlOutputText();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar class");
        htmlOutputText.setValueBinding("styleClass", vb);
        assertEquals("bar class", htmlOutputText.getStyleClass());
    }

    public void testSetGetTitle() throws Exception {
        HtmlOutputText htmlOutputText = createHtmlOutputText();
        assertEquals(null, htmlOutputText.getTitle());
        htmlOutputText.setTitle("foo title");
        assertEquals("foo title", htmlOutputText.getTitle());
    }

    public void testSetGetTitle_ValueBinding() throws Exception {
        HtmlOutputText htmlOutputText = createHtmlOutputText();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar title");
        htmlOutputText.setValueBinding("title", vb);
        assertEquals("bar title", htmlOutputText.getTitle());
    }


    private HtmlOutputText createHtmlOutputText() {
        return (HtmlOutputText) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlOutputText();
    }

}
