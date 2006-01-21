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
package javax.faces.component;

import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.mock.NullUIComponent;
import org.seasar.teeda.core.unit.AssertUtil;

/**
 * @author manhole
 */
public class UIDataTest extends UIComponentBaseTest {

    public final void testSetGetFirst() {
        UIData data = createUIData();
        data.setFirst(9);
        assertEquals(9, data.getFirst());
    }

    public final void testSetGetFirst_ValueBinding() {
        UIData data = createUIData();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Integer(10));
        data.setValueBinding("first", vb);
        assertEquals(10, data.getFirst());
    }

    public final void testSetGetFirst_Negative() {
        UIData data = createUIData();
        try {
            data.setFirst(-1);
            fail();
        } catch (IllegalArgumentException iae) {
            AssertUtil.assertExceptionMessageExist(iae);
        }
    }

    // FIXME this test should be success?
    public final void fixme_testSetGetFirst_ValueBinding_Negative() {
        UIData data = createUIData();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Integer(-10));
        try {
            data.setValueBinding("first", vb);
            System.out.println(data.getFirst());
            fail();
        } catch (IllegalArgumentException iae) {
            AssertUtil.assertExceptionMessageExist(iae);
        }
    }

    public final void testSetGetFooter() {
        UIData data = createUIData();
        UIComponent component = new NullUIComponent();
        data.setFooter(component);
        assertEquals(component, data.getFooter());
        assertEquals(component, data.getFacet("footer"));
    }

    public final void testSetGetFooter_ValueBindingNotWork() {
        UIData data = createUIData();
        MockValueBinding vb = new MockValueBinding();
        UIComponent component = new NullUIComponent();
        vb.setValue(getFacesContext(), component);
        data.setValueBinding("footer", vb);
        assertEquals(null, data.getFooter());
    }

    public final void testSetGetFooterByFacet() {
        UIData data = createUIData();
        UIComponent component = new NullUIComponent();
        data.getFacets().put("footer", component);
        assertEquals(component, data.getFooter());
        assertEquals(component, data.getFacet("footer"));
    }

    public final void testSetFooter_NullArg() {
        UIData data = createUIData();
        try {
            data.setFooter(null);
            fail();
        } catch (NullPointerException npe) {
            AssertUtil.assertExceptionMessageExist(npe);
        }
    }

    public final void testSetGetHeader() {
        UIData data = createUIData();
        UIComponent component = new NullUIComponent();
        data.setHeader(component);
        assertEquals(component, data.getHeader());
        assertEquals(component, data.getFacet("header"));
    }

    public final void testSetGetHeader_ValueBindingNotWork() {
        UIData data = createUIData();
        MockValueBinding vb = new MockValueBinding();
        UIComponent component = new NullUIComponent();
        vb.setValue(getFacesContext(), component);
        data.setValueBinding("header", vb);
        assertEquals(null, data.getHeader());
    }

    public final void testSetGetHeaderByFacet() {
        UIData data = createUIData();
        UIComponent component = new NullUIComponent();
        data.getFacets().put("header", component);
        assertEquals(component, data.getHeader());
        assertEquals(component, data.getFacet("header"));
    }

    public final void testSetHeader_NullArg() {
        UIData data = createUIData();
        try {
            data.setHeader(null);
            fail();
        } catch (NullPointerException npe) {
            AssertUtil.assertExceptionMessageExist(npe);
        }
    }

    // TODO test
    public void testIsRowAvailable() throws Exception {
        // ## Arrange ##
        UIData data = createUIData();

        // ## Act ##
        data.isRowAvailable();

        // ## Assert ##

    }

    // TODO test
    public void testGetRowCount() throws Exception {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##

    }

    // TODO test
    public void testGetRowData() throws Exception {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##

    }

    // TODO test
    public void testGetRowIndex() throws Exception {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##

    }

    // TODO test
    public void testSetRowIndex() throws Exception {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##

    }

    // TODO test
    public void testGetRows() throws Exception {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##

    }

    // TODO test
    public void testSetRows() throws Exception {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##

    }

    // TODO test
    public void testGetVar() throws Exception {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##

    }

    // TODO test
    public void testSetVar() throws Exception {
        // ## Arrange ##

        // ## Act ##

        // ## Assert ##

    }

    // TODO test saveState
    // TODO test restoreState
    // TODO test getValue
    // TODO test setValue
    // TODO test setValueBinding
    // TODO test getClientId
    // TODO test queueEvent
    // TODO test broadcast
    // TODO test encodeBegin
    // TODO test processDecodes
    // TODO test processValidators
    // TODO test processUpdates

    private UIData createUIData() {
        return (UIData) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIData();
    }

}
