/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author manhole
 */
public class HtmlOutputTextTest extends UIOutputTest {

    public void testSetGetEscape() throws Exception {
        HtmlOutputText component = createHtmlOutputText();
        // default is true
        assertEquals(true, component.isEscape());
        component.setEscape(false);
        assertEquals(false, component.isEscape());
    }

    public void testSetGetEscape_ValueBinding() throws Exception {
        HtmlOutputText component = createHtmlOutputText();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, Boolean.FALSE);
        component.setValueBinding("escape", vb);
        assertEquals(false, component.isEscape());
        assertEquals(Boolean.FALSE, component.getValueBinding("escape")
                .getValue(context));
    }

    public void testSetGetStyle() throws Exception {
        HtmlOutputText component = createHtmlOutputText();
        assertEquals(null, component.getStyle());
        component.setStyle("foo style");
        assertEquals("foo style", component.getStyle());
    }

    public void testSetGetStyle_ValueBinding() throws Exception {
        HtmlOutputText component = createHtmlOutputText();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar style");
        component.setValueBinding("style", vb);
        assertEquals("bar style", component.getStyle());
        assertEquals("bar style", component.getValueBinding("style").getValue(
                context));
    }

    public void testSetGetStyleClass() throws Exception {
        HtmlOutputText component = createHtmlOutputText();
        assertEquals(null, component.getStyleClass());
        component.setStyleClass("foo class");
        assertEquals("foo class", component.getStyleClass());
    }

    public void testSetGetStyleClass_ValueBinding() throws Exception {
        HtmlOutputText component = createHtmlOutputText();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar class");
        component.setValueBinding("styleClass", vb);
        assertEquals("bar class", component.getStyleClass());
        assertEquals("bar class", component.getValueBinding("styleClass")
                .getValue(context));
    }

    public void testSetGetTitle() throws Exception {
        HtmlOutputText component = createHtmlOutputText();
        assertEquals(null, component.getTitle());
        component.setTitle("foo title");
        assertEquals("foo title", component.getTitle());
    }

    public void testSetGetTitle_ValueBinding() throws Exception {
        HtmlOutputText component = createHtmlOutputText();
        MockValueBinding vb = new MockValueBinding();
        FacesContext context = getFacesContext();
        vb.setValue(context, "bar title");
        component.setValueBinding("title", vb);
        assertEquals("bar title", component.getTitle());
        assertEquals("bar title", component.getValueBinding("title").getValue(
                context));
    }

    private HtmlOutputText createHtmlOutputText() {
        return (HtmlOutputText) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new HtmlOutputText();
    }

}
