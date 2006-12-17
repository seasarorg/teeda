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

import java.util.Map;

import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import junitx.framework.ArrayAssert;
import junitx.framework.ObjectAssert;

import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.impl.ValueBindingImpl;
import org.seasar.teeda.core.el.impl.commons.CommonsELParser;
import org.seasar.teeda.core.el.impl.commons.CommonsExpressionProcessorImpl;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponentBase;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;
import org.seasar.teeda.core.mock.MockVariableResolver;

/**
 * @author manhole
 */
public class UIDataTeedaTest extends UIComponentBaseTeedaTest {

    public void testGetCliendId() throws Exception {
        // ## Arrange ##
        UIData component = createUIData();
        component.setId("foo");

        // ## Act & Assert ##
        assertEquals("foo:0", component.getClientId(getFacesContext()));
    }

    public void testGetClientId_WithParentNamingContainer() {
        UIData component = createUIData();
        component.setId("a");

        MockUIComponentBase parent = new MockUIComponentBaseWithNamingContainer();
        parent.setClientId("b");
        parent.getChildren().add(component);

        String clientId = component.getClientId(getFacesContext());
        assertNotNull(clientId);
        assertEquals("b:a:0", clientId);
    }

    public void testGetClientId_WithParentNotNamingContainer() {
        UIData component = createUIData();
        component.setId("a");

        MockUIComponentBase parent = new MockUIComponentBase();
        ObjectAssert.assertNotInstanceOf(NamingContainer.class, parent);
        parent.setClientId("b");
        parent.getChildren().add(component);

        String clientId = component.getClientId(getFacesContext());
        assertNotNull(clientId);
        assertEquals("a:0", clientId);
    }

    public void testSetGetRowIndexAndSaveRestoreState() throws Exception {
        // ## Arrange ##
        UIData data = createUIData();
        data.setVar("foo");
        data.setValue(new String[] { "a", "b", "c", "d" });
        UIColumn col = new UIColumn();
        MockFacesContext facesContext = getFacesContext();
        ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        ValueBinding vb = new ValueBindingImpl(facesContext.getApplication(),
                "#{foo}", parser);
        HtmlInputText editableValueHolder = new HtmlInputText();
        // inputText.setId("input");
        editableValueHolder.setValueBinding("value", vb);
        assertEquals(null, editableValueHolder.getValue());

        col.getChildren().add(editableValueHolder);
        data.getChildren().add(col);

        // ## Act & Assert ##
        data.setRowIndex(123);
        assertEquals(123, data.getRowIndex());
        assertEquals(false, data.isRowAvailable());

        data.setRowIndex(1);
        assertEquals(1, data.getRowIndex());
        assertEquals(true, data.isRowAvailable());

        Map req = facesContext.getExternalContext().getRequestMap();

        assertEquals("b", req.get("foo"));
        MockVariableResolver vr = getVariableResolver();
        assertEquals("b", vr.resolveVariable(facesContext, "foo"));
        assertEquals("b", editableValueHolder.getValue());
        //editableValueHolder.setValue("b2");
        //assertEquals("b2", editableValueHolder.getValue());
        assertEquals("b", req.get("foo"));
        assertEquals("b", getVariableResolver().resolveVariable(facesContext,
                "foo"));

        data.setRowIndex(2);
        assertEquals(2, data.getRowIndex());
        assertEquals(true, data.isRowAvailable());
        assertEquals("c", req.get("foo"));
        assertEquals("c", vr.resolveVariable(facesContext, "foo"));
        assertEquals("c", editableValueHolder.getValue());

        data.setRowIndex(1);
        assertEquals(true, data.isRowAvailable());
        assertEquals("restore state of EditableValueHolder's", "b",
                editableValueHolder.getValue());
        assertEquals("b", req.get("foo"));
        assertEquals("b", vr.resolveVariable(facesContext, "foo"));

        data.setRowIndex(-1);
        assertEquals(false, data.isRowAvailable());
        assertEquals(null, req.get("foo"));
        assertEquals(null, vr.resolveVariable(facesContext, "foo"));
        assertEquals(null, editableValueHolder.getValue());
    }

    public void testEncodeBegin_resetSavedState() throws Exception {
        // ## Arrange ##
        UIData data = createUIData();
        data.setVar("foo");
        data.setValue(new String[] { "a", "original" });
        UIColumn col = new UIColumn();
        MockFacesContext facesContext = getFacesContext();
        ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(new CommonsExpressionProcessorImpl());
        ValueBinding vb = new ValueBindingImpl(facesContext.getApplication(),
                "#{foo}", parser);
        HtmlInputText editableValueHolder = new HtmlInputText();
        // inputText.setId("input");
        editableValueHolder.setValueBinding("value", vb);
        assertEquals(null, editableValueHolder.getValue());

        col.getChildren().add(editableValueHolder);
        data.getChildren().add(col);

        data.setRowIndex(1);
        EditableValueHolder holder = (EditableValueHolder) ((UIColumn) data
                .getChildren().get(0)).getChildren().get(0);
        assertEquals("original", holder.getValue());
        holder.setValue("changed");

        data.setRowIndex(0);
        data.setRowIndex(1);
        assertEquals("changed", holder.getValue());

        // ## Act ##
        data.setRowIndex(0);
        data.encodeBegin(facesContext);

        // ## Assert ##
        assertEquals("a", holder.getValue());
        data.setRowIndex(1);
        assertEquals("original", holder.getValue());
    }

    public void testSaveAndRestoreState() throws Exception {
        super.testSaveAndRestoreState();

        // ## Arrange ##
        UIData data1 = createUIData();
        data1.setFirst(10);
        data1.setValue(new String[] { "A", "B", "C" });

        // ## Act ##
        FacesContext context = getFacesContext();
        final Object state = data1.saveState(context);

        UIData data2 = createUIData();
        data2.restoreState(context, serializeAndDeserialize(state));

        // ## Assert ##
        assertEquals(data1.getFirst(), data2.getFirst());
        assertEquals(data1.getRowCount(), data2.getRowCount());
        assertEquals(data1.getRowData(), data2.getRowData());
        assertEquals(data1.getRowIndex(), data2.getRowIndex());
        assertEquals(data1.getRows(), data2.getRows());
        ArrayAssert.assertEquals((String[]) data1.getValue(), (String[]) data2
                .getValue());
        assertEquals(data1.getVar(), data2.getVar());
        assertEquals(data1.isRowAvailable(), data2.isRowAvailable());
    }

    private UIData createUIData() {
        return (UIData) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UIData();
    }

}
