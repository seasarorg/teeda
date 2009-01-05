/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package javax.faces.component;

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.mock.NullUIComponent;
import org.seasar.teeda.core.unit.ExceptionAssert;

/**
 * @author shot
 * @author manhole
 */
public class UIColumnTest extends UIComponentBaseTest {

    public final void testSetGetFooter() {
        UIColumn column = createUIColumn();
        UIComponent component = new NullUIComponent();
        column.setFooter(component);
        assertEquals(component, column.getFooter());
        assertEquals(component, column.getFacet("footer"));
    }

    public final void testSetGetFooter_ValueBindingNotWork() {
        UIColumn column = createUIColumn();
        MockValueBinding vb = new MockValueBinding();
        UIComponent component = new NullUIComponent();
        FacesContext context = getFacesContext();
        vb.setValue(context, component);
        column.setValueBinding("footer", vb);
        assertEquals(null, column.getFooter());
    }

    public final void testSetGetFooterByFacet() {
        UIColumn column = createUIColumn();
        UIComponent component = new NullUIComponent();
        column.getFacets().put("footer", component);
        assertEquals(component, column.getFooter());
        assertEquals(component, column.getFacet("footer"));
    }

    public final void testSetFooter_NullArg() {
        UIColumn column = createUIColumn();
        try {
            column.setFooter(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testSetGetHeader() {
        UIColumn column = createUIColumn();
        UIComponent component = new NullUIComponent();
        column.setHeader(component);
        assertEquals(component, column.getHeader());
        assertEquals(component, column.getFacet("header"));
    }

    public final void testSetGetHeader_ValueBindingNotWork() {
        UIColumn column = createUIColumn();
        MockValueBinding vb = new MockValueBinding();
        UIComponent component = new NullUIComponent();
        FacesContext context = getFacesContext();
        vb.setValue(context, component);
        column.setValueBinding("header", vb);
        assertEquals(null, column.getHeader());
    }

    public final void testSetGetHeaderByFacet() {
        UIColumn column = createUIColumn();
        UIComponent component = new NullUIComponent();
        column.getFacets().put("header", component);
        assertEquals(component, column.getHeader());
        assertEquals(component, column.getFacet("header"));
    }

    public final void testSetHeader_NullArg() {
        UIColumn column = createUIColumn();
        try {
            column.setHeader(null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    private UIColumn createUIColumn() {
        return (UIColumn) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIColumn();
    }

}
