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
public class HtmlOutputFormatTest extends UIOutputTest {

    public void testDefaultRendererType() throws Exception {
        HtmlOutputFormat htmlOutputFormat = createHtmlOutputFormat();
        assertEquals("javax.faces.Format", htmlOutputFormat.getRendererType());
    }

    public void testSetGetEscape() throws Exception {
        HtmlOutputFormat htmlOutputFormat = createHtmlOutputFormat();
        // default is true
        assertEquals(true, htmlOutputFormat.isEscape());
        htmlOutputFormat.setEscape(false);
        assertEquals(false, htmlOutputFormat.isEscape());
    }

    public void testSetGetEscape_ValueBinding() throws Exception {
        HtmlOutputFormat htmlOutputFormat = createHtmlOutputFormat();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), Boolean.FALSE);
        htmlOutputFormat.setValueBinding("escape", vb);
        assertEquals(false, htmlOutputFormat.isEscape());
    }

    public void testSetGetStyle() throws Exception {
        HtmlOutputFormat component = createHtmlOutputFormat();
        component.setStyle("foo style");
        assertEquals("foo style", component.getStyle());
    }

    public void testSetGetStyle_ValueBinding() throws Exception {
        HtmlOutputFormat component = createHtmlOutputFormat();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar style");
        component.setValueBinding("style", vb);
        assertEquals("bar style", component.getStyle());
    }

    public void testSetGetStyleClass() throws Exception {
        HtmlOutputFormat component = createHtmlOutputFormat();
        component.setStyleClass("foo styleClass");
        assertEquals("foo styleClass", component.getStyleClass());
    }

    public void testSetGetStyleClass_ValueBinding() throws Exception {
        HtmlOutputFormat component = createHtmlOutputFormat();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar styleClass");
        component.setValueBinding("styleClass", vb);
        assertEquals("bar styleClass", component.getStyleClass());
    }

    public void testSetGetTitle() throws Exception {
        HtmlOutputFormat component = createHtmlOutputFormat();
        component.setTitle("foo title");
        assertEquals("foo title", component.getTitle());
    }

    public void testSetGetTitle_ValueBinding() throws Exception {
        HtmlOutputFormat component = createHtmlOutputFormat();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "bar title");
        component.setValueBinding("title", vb);
        assertEquals("bar title", component.getTitle());
    }

    private HtmlOutputFormat createHtmlOutputFormat() {
        return (HtmlOutputFormat) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlOutputFormat();
    }
}
