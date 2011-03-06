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
package javax.faces.component;

import java.util.Arrays;
import java.util.Iterator;

import javax.faces.el.ValueBinding;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.container.impl.S2ContainerImpl;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockValueBinding;

/**
 * @author manhole
 */
public class UISelectManyTest extends UIInputTest {

    public void testGetSelectedValues() throws Exception {
        // ## Arrange ##
        UISelectMany selectMany = createUISelectMany();
        selectMany.setValue(new Object[] { "a", "b", "c" });

        // ## Act ##
        Object[] actual = selectMany.getSelectedValues();

        // ## Assert ##
        assertEquals(3, actual.length);
        assertEquals("a", actual[0]);
        assertEquals("b", actual[1]);
        assertEquals("c", actual[2]);
        assertEquals("typesafe alias for getValue", selectMany.getValue(),
                selectMany.getSelectedValues());
    }

    public void testSetSelectedValues() throws Exception {
        // ## Arrange ##
        UISelectMany selectMany = createUISelectMany();

        // ## Act ##
        selectMany.setSelectedValues(new Object[] { "A", "B" });

        // ## Assert ##
        Object[] actual = (Object[]) selectMany.getValue();
        assertEquals(2, actual.length);
        assertEquals("A", actual[0]);
        assertEquals("B", actual[1]);
        assertEquals("typesafe alias for setValue", selectMany.getValue(),
                selectMany.getSelectedValues());
    }

    public void testSetSelectedValuesToValue_ValueBinding() throws Exception {
        // ## Arrange ##
        UISelectMany component = createUISelectMany();
        MockValueBinding vb = new MockValueBinding();
        Object[] value = new Object[] {};
        MockFacesContext context = getFacesContext();
        vb.setValue(context, value);

        // ## Act ##
        component.setValueBinding("selectedValues", vb);

        // ## Assert ##
        assertEquals(value, component.getSelectedValues());
        assertEquals(value, component.getValue());
        assertEquals(value, component.getValueBinding("selectedValues")
                .getValue(context));
        assertEquals(value, component.getValueBinding("value")
                .getValue(context));
    }

    public void testGetSelectedValuesToValue_ValueBinding() throws Exception {
        // ## Arrange ##
        UISelectMany component = createUISelectMany();
        Object[] value = new Object[] {};
        MockFacesContext context = getFacesContext();
        {
            MockValueBinding vb = new MockValueBinding();
            vb.setValue(context, value);
            component.setValueBinding("value", vb);
        }

        // ## Act ##
        ValueBinding vb = component.getValueBinding("selectedValues");

        // ## Assert ##
        assertEquals(value, vb.getValue(context));
        assertEquals(value, component.getSelectedValues());
        assertEquals(value, component.getValue());
    }

    public void testCompareValues_UISelectMany() throws Exception {
        // ## Arrange ##
        UISelectMany selectMany = createUISelectMany();

        // ## Act ##
        // ## Assert ##
        // String Array
        assertEquals(false, selectMany.compareValues(new String[] { "1", "2" },
                new String[] { "2", "1" }));
        assertEquals(false, selectMany.compareValues(new String[] { "1", "2",
                "34" }, new String[] { "2", "34", "1" }));
        assertEquals(true, selectMany.compareValues(new String[] { "1", "2",
                "34" }, new String[] { "2", "34" }));

        // List
        assertEquals(false, selectMany.compareValues(Arrays
                .asList(new String[] { "1", "2" }), Arrays.asList(new String[] {
                "1", "2" })));
        assertEquals(false, selectMany.compareValues(Arrays
                .asList(new String[] { "1", "2" }), Arrays.asList(new String[] {
                "2", "1" })));
        assertEquals(true, selectMany
                .compareValues(Arrays.asList(new String[] { "1", "2", "4" }),
                        Arrays.asList(new String[] { "1", "2", "3" })));

        // Array != List
        assertEquals(true, selectMany.compareValues(Arrays.asList(new String[] {
                "1", "2" }), new String[] { "1", "2" }));

        // Primitive Array
        assertEquals(false, selectMany.compareValues(new int[] { 1, 2 },
                new int[] { 2, 1 }));
        assertEquals(false, selectMany.compareValues(new int[] { 1, 2, 34 },
                new int[] { 2, 34, 1 }));
        assertEquals(true, selectMany.compareValues(new int[] { 1, 2, 34 },
                new int[] { 2, 34 }));

        assertEquals(false, selectMany.compareValues(new String[] {},
                new String[] {}));
        assertEquals(false, selectMany.compareValues(new String[] { "1" },
                new String[] { "1" }));
        assertEquals(false, selectMany.compareValues(new String[] { null },
                new String[] { null }));

        // primitive - wrapper
        assertEquals(false, selectMany.compareValues(new int[] { 1, 2 },
                new Integer[] { new Integer(2), new Integer(1) }));

        assertEquals(true, selectMany.compareValues(new int[] { 1 },
                new String[] { "1" }));

        assertEquals(true, selectMany.compareValues(new String[] { "1" }, "1"));
        assertEquals(true, selectMany.compareValues(new String[] { "1" }, "2"));
        assertEquals(true, selectMany.compareValues(new String[] { "2" }, "1"));
        assertEquals(true, selectMany.compareValues(new String[] { "" },
                new String[] { null }));
        assertEquals(true, selectMany.compareValues(new String[] { null },
                new String[] { "" }));
    }

    public void testValidateValue_Available() throws Exception {
        // ## Arrange ##
        S2Container container = new S2ContainerImpl();
        SingletonS2ContainerFactory.setContainer(container);
        SingletonS2ContainerFactory.init();
        UISelectMany component = arrangeForValidateTest();
        MockFacesContext context = getFacesContext();

        // ## Act ##
        component.validateValue(context, new String[] { "aV" });

        // ## Assert ##
        assertEquals(true, component.isValid());
        Iterator messages = context.getMessages();
        assertEquals(false, messages.hasNext());
    }

    public void testValidateValue_NotAvailable() throws Exception {
        // ## Arrange ##
        UISelectMany component = arrangeForValidateTest();
        MockFacesContext context = getFacesContext();

        // ## Act ##
        component.validateValue(context, new String[] { "aV", "c" });

        // ## Assert ##
        assertEquals(false, component.isValid());
        Iterator messages = context.getMessages();
        assertEquals(true, messages.hasNext());
        messages.next();
        assertEquals(false, messages.hasNext());
    }

    private UISelectMany arrangeForValidateTest() {
        UISelectMany component = createUISelectMany();
        component.setRendererType(null);
        component.setValid(true);
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("aV");
            selectItem.setItemLabel("aL");
            component.getChildren().add(selectItem);
        }
        {
            UISelectItem selectItem = new UISelectItem();
            selectItem.setItemValue("bV");
            selectItem.setItemLabel("bL");
            component.getChildren().add(selectItem);
        }
        return component;
    }

    private UISelectMany createUISelectMany() {
        return (UISelectMany) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new UISelectMany();
    }

}
