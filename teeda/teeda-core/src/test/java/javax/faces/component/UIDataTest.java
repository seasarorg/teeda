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

import java.util.Map;

import javax.faces.component.UIData.FacesEventWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import junitx.framework.ObjectAssert;

import org.seasar.teeda.core.mock.MockDataModel;
import org.seasar.teeda.core.mock.MockFacesEvent;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.mock.MockVariableResolver;
import org.seasar.teeda.core.mock.NullFacesEvent;
import org.seasar.teeda.core.mock.NullUIComponent;
import org.seasar.teeda.core.unit.ExceptionAssert;

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
            ExceptionAssert.assertMessageExist(iae);
        }
    }

    // XXX this test should be success?
    public final void fixme_testSetGetFirst_ValueBinding_Negative() {
        UIData data = createUIData();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), new Integer(-10));
        try {
            data.setValueBinding("first", vb);
            System.out.println(data.getFirst());
            fail();
        } catch (IllegalArgumentException iae) {
            ExceptionAssert.assertMessageExist(iae);
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
            ExceptionAssert.assertMessageExist(npe);
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
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public void testIsRowAvailable() throws Exception {
        // ## Arrange ##
        UIData data = createUIData();
        MockDataModel mockDataModel = new MockDataModel();
        data.setValue(mockDataModel);
        mockDataModel.setRowAvailable(true);

        // ## Act & Assert ##
        assertEquals(true, data.isRowAvailable());

        mockDataModel.setRowAvailable(false);
        assertEquals(false, data.isRowAvailable());
    }

    public void testGetRowCount() throws Exception {
        // ## Arrange ##
        UIData data = createUIData();
        MockDataModel mockDataModel = new MockDataModel();
        mockDataModel.setRowCount(101);
        data.setValue(mockDataModel);

        // ## Act & Assert ##
        assertEquals(101, data.getRowCount());
    }

    public void testGetRowData() throws Exception {
        // ## Arrange ##
        UIData data = createUIData();
        MockDataModel mockDataModel = new MockDataModel();
        mockDataModel.setRowData("abcd");
        data.setValue(mockDataModel);

        // ## Act & Assert ##
        assertEquals("abcd", data.getRowData());
    }

    public void testSetGetRowIndex() throws Exception {
        // ## Arrange ##
        UIData data = createUIData();
        MockDataModel dataModel = new MockDataModel();
        data.setValue(dataModel);

        // ## Act & Assert ##
        data.setRowIndex(19);
        assertEquals(19, data.getRowIndex());

        data.setRowIndex(1);
        assertEquals(1, data.getRowIndex());
    }

    public void testSetGetRowIndex_MinusOne() throws Exception {
        // ## Arrange ##
        UIData data = createUIData();
        MockDataModel mockDataModel = new MockDataModel();
        data.setValue(mockDataModel);
        data.setVar("varName");
        Map requestMap = getFacesContext().getExternalContext().getRequestMap();
        requestMap.put("varName", "fooo");

        // ## Act ##
        data.setRowIndex(-1);

        // ## Assert ##
        assertEquals(null, requestMap.get("varName"));
    }

    public void testSetGetRowIndex_UnderMinusOne() throws Exception {
        // ## Arrange ##
        UIData data = createUIData();
        MockDataModel mockDataModel = new MockDataModel();
        data.setValue(mockDataModel);

        // ## Act ##
        // ## Assert ##
        try {
            data.setRowIndex(-2);
        } catch (IllegalArgumentException iae) {
            ExceptionAssert.assertMessageExist(iae);
        }
    }

    public void testSetGetRows() throws Exception {
        UIData data = createUIData();
        assertEquals(0, data.getRows());
        data.setRows(2);
        assertEquals(2, data.getRows());
        data.setRows(0);
        assertEquals(0, data.getRows());
    }

    public void testSetGetRows_Negative() throws Exception {
        UIData data = createUIData();
        try {
            data.setRows(-1);
            fail();
        } catch (IllegalArgumentException iae) {
            ExceptionAssert.assertMessageExist(iae);
        }
    }

    public final void testSetGetVar() {
        UIData data = createUIData();
        data.setVar("aaa");
        assertEquals("aaa", data.getVar());
    }

    public final void testSetGetValue() {
        UIData data = createUIData();
        data.setValue("abc");
        assertEquals("abc", data.getValue());
    }

    public final void testSetGetValue_ValueBinding() {
        UIData data = createUIData();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "some value");
        data.setValueBinding("value", vb);
        assertEquals("some value", data.getValue());
    }

    public final void testSetValueBinding_IllegalArgId() {
        UIData data = createUIData();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "a");
        try {
            data.setValueBinding("id", vb);
            fail();
        } catch (IllegalArgumentException iae) {
            ExceptionAssert.assertMessageExist(iae);
        }
    }

    public final void testSetValueBinding_IllegalArgVar() {
        UIData data = createUIData();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "a");
        try {
            data.setValueBinding("var", vb);
            fail();
        } catch (IllegalArgumentException iae) {
            ExceptionAssert.assertMessageExist(iae);
        }
    }

    public final void testSetValueBinding_IllegalArgRowIndex() {
        UIData data = createUIData();
        MockValueBinding vb = new MockValueBinding();
        vb.setValue(getFacesContext(), "a");
        try {
            data.setValueBinding("rowIndex", vb);
            fail();
        } catch (IllegalArgumentException iae) {
            ExceptionAssert.assertMessageExist(iae);
        }
    }

    public void testQueueEvent_WithParent() throws Exception {
        // ## Arrange ##
        UIData data = createUIData();
        MockUIComponent parent = new MockUIComponent();

        data.setParent(parent);
        NullFacesEvent event = new NullFacesEvent();

        // ## Act ##
        data.queueEvent(event);

        // ## Assert ##
        FacesEvent queued = parent.getQueueEvent();
        ObjectAssert.assertInstanceOf(UIData.FacesEventWrapper.class, queued);
        UIData.FacesEventWrapper wrapper = (FacesEventWrapper) queued;
        FacesEvent originalEvent = wrapper.getFacesEvent();
        assertSame(event, originalEvent);
    }

    public void testBroadcast_WrappedEvent() throws Exception {
        // ## Arrange ##
        UIData data = createUIData();
        final boolean[] calls = new boolean[1];

        // ## Act ##
        UIComponent source = new MockUIComponent() {
            public void broadcast(FacesEvent event)
                    throws AbortProcessingException {
                calls[0] = true;
            }
        };
        FacesEvent originalEvent = new MockFacesEvent(source);
        data.broadcast(new UIData.FacesEventWrapper(originalEvent, 4,
                new NullUIComponent()));

        // ## Assert ##
        assertEquals(true, calls[0]);
    }

    public void testProcessDecodes() throws Exception {
        // ## Arrange ##
        final int[] calls = { 0, 0, 0 };
        final UIData data = createUIData();
        data.setValue(new String[] { "A", "B", "C" });
        data.getFacets().put("f1", new MockUIComponent() {
            public void processDecodes(FacesContext context) {
                calls[0]++;
            }
        });
        {
            UIColumn column = new UIColumn();
            column.getFacets().put("col1_f1", new MockUIComponent() {
                public void processDecodes(FacesContext context) {
                    calls[1]++;
                }
            });
            column.getChildren().add(new MockUIComponent() {
                public void processDecodes(FacesContext context) {
                    assertEquals(calls[2], data.getRowIndex());
                    calls[2]++;
                }
            });
            data.getChildren().add(column);
        }
        data.setRendererType(null);
        assertEquals(0, data.getRowIndex());

        // ## Act ##
        data.processDecodes(getFacesContext());

        // ## Assert ##
        assertEquals(1, calls[0]);
        assertEquals(1, calls[1]);
        assertEquals(3, calls[2]);
        assertEquals(-1, data.getRowIndex());
    }

    public void testProcessDecodes_RenderFalse() throws Exception {
        // ## Arrange ##
        final int[] calls = { 0, 0, 0 };
        final UIData data = createUIData();
        data.setRendered(false);
        data.setValue(new String[] { "A", "B", "C" });
        data.getFacets().put("f1", new MockUIComponent() {
            public void processDecodes(FacesContext context) {
                calls[0]++;
            }
        });
        {
            UIColumn column = new UIColumn();
            column.getFacets().put("col1_f1", new MockUIComponent() {
                public void processDecodes(FacesContext context) {
                    calls[1]++;
                }
            });
            column.getChildren().add(new MockUIComponent() {
                public void processDecodes(FacesContext context) {
                    calls[2]++;
                }
            });
            data.getChildren().add(column);
        }
        data.setRendererType(null);
        assertEquals(0, data.getRowIndex());

        // ## Act ##
        data.processDecodes(getFacesContext());

        // ## Assert ##
        assertEquals(0, calls[0]);
        assertEquals(0, calls[1]);
        assertEquals(0, calls[2]);
        assertEquals(0, data.getRowIndex());
    }

    public void testProcessValidators() throws Exception {
        // ## Arrange ##
        final int[] calls = { 0, 0, 0 };
        final UIData data = createUIData();
        data.setValue(new String[] { "A", "B", "C" });
        data.getFacets().put("f1", new MockUIComponent() {
            public void processValidators(FacesContext context) {
                calls[0]++;
            }
        });
        {
            UIColumn column = new UIColumn();
            column.getFacets().put("col1_f1", new MockUIComponent() {
                public void processValidators(FacesContext context) {
                    calls[1]++;
                }
            });
            column.getChildren().add(new MockUIComponent() {
                public void processValidators(FacesContext context) {
                    assertEquals(calls[2], data.getRowIndex());
                    calls[2]++;
                }
            });
            data.getChildren().add(column);
        }
        assertEquals(0, data.getRowIndex());

        // ## Act ##
        data.processValidators(getFacesContext());

        // ## Assert ##
        assertEquals(1, calls[0]);
        assertEquals(1, calls[1]);
        assertEquals(3, calls[2]);
        assertEquals(-1, data.getRowIndex());
    }

    public void testProcessDecodes_CallFacetsAndChildren() throws Exception {
        // override superclass behavior
    }

    public void testProcessValidators_CallFacetsAndChildren() throws Exception {
        // override superclass behavior
    }

    public void testProcessUpdates_CallFacetsAndChildren() throws Exception {
        // override superclass behavior
    }

    public void testProcessValidators_RenderFalse() throws Exception {
        // ## Arrange ##
        final int[] calls = { 0, 0, 0 };
        final UIData data = createUIData();
        data.setRendered(false);
        data.setValue(new String[] { "A", "B", "C" });
        data.getFacets().put("f1", new MockUIComponent() {
            public void processValidators(FacesContext context) {
                calls[0]++;
            }
        });
        {
            UIColumn column = new UIColumn();
            column.getFacets().put("col1_f1", new MockUIComponent() {
                public void processValidators(FacesContext context) {
                    calls[1]++;
                }
            });
            column.getChildren().add(new MockUIComponent() {
                public void processValidators(FacesContext context) {
                    calls[2]++;
                }
            });
            data.getChildren().add(column);
        }
        assertEquals(0, data.getRowIndex());

        // ## Act ##
        data.processValidators(getFacesContext());

        // ## Assert ##
        assertEquals(0, calls[0]);
        assertEquals(0, calls[1]);
        assertEquals(0, calls[2]);
        assertEquals(0, data.getRowIndex());
    }

    public void testProcessUpdates() throws Exception {
        // ## Arrange ##
        final int[] calls = { 0, 0, 0 };
        final UIData data = createUIData();
        data.setValue(new String[] { "A", "B", "C" });
        data.getFacets().put("f1", new MockUIComponent() {
            public void processUpdates(FacesContext context) {
                calls[0]++;
            }
        });
        {
            UIColumn column = new UIColumn();
            column.getFacets().put("col1_f1", new MockUIComponent() {
                public void processUpdates(FacesContext context) {
                    calls[1]++;
                }
            });
            column.getChildren().add(new MockUIComponent() {
                public void processUpdates(FacesContext context) {
                    assertEquals(calls[2], data.getRowIndex());
                    calls[2]++;
                }
            });
            data.getChildren().add(column);
        }
        assertEquals(0, data.getRowIndex());

        // ## Act ##
        data.processUpdates(getFacesContext());

        // ## Assert ##
        assertEquals(1, calls[0]);
        assertEquals(1, calls[1]);
        assertEquals(3, calls[2]);
        assertEquals(-1, data.getRowIndex());
    }

    public void testProcessUpdates_RenderFalse() throws Exception {
        // ## Arrange ##
        final int[] callCount = { 0, 0, 0 };
        final UIData data = createUIData();
        data.setRendered(false);
        data.setValue(new String[] { "A", "B", "C" });
        data.getFacets().put("f1", new MockUIComponent() {
            public void processUpdates(FacesContext context) {
                callCount[0]++;
            }
        });
        {
            UIColumn column = new UIColumn();
            column.getFacets().put("col1_f1", new MockUIComponent() {
                public void processUpdates(FacesContext context) {
                    callCount[1]++;
                }
            });
            column.getChildren().add(new MockUIComponent() {
                public void processUpdates(FacesContext context) {
                    callCount[2]++;
                }
            });
            data.getChildren().add(column);
        }

        // ## Act ##
        data.processUpdates(getFacesContext());

        // ## Assert ##
        assertEquals(0, callCount[0]);
        assertEquals(0, callCount[1]);
        assertEquals(0, callCount[2]);
        assertEquals(0, data.getRowIndex());
    }

    private UIData createUIData() {
        return (UIData) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIData();
    }

    protected void setUp() throws Exception {
        super.setUp();
        MockVariableResolver variableResolver = new MockVariableResolver();
        getFacesContext().getApplication()
                .setVariableResolver(variableResolver);
    }

}
