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
package javax.faces.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.component.UISelectMany;
import javax.faces.model.SelectItem;

import junit.framework.TestCase;
import junitx.framework.ObjectAssert;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.ExceptionAssert;

/**
 * @author shot
 * @author manhole
 */
public class SelectItemsIteratorTest extends TestCase {

    public void testUISelectItem() throws Exception {
        // ## Arrange ##
        UISelectMany component = new UISelectMany();
        {
            UISelectItem uiSelectItem = new UISelectItem();
            uiSelectItem.setItemValue("a");
            uiSelectItem.setItemLabel("b");
            uiSelectItem.setItemDescription("c");
            component.getChildren().add(uiSelectItem);
        }
        {
            UISelectItem uiSelectItem = new UISelectItem();
            uiSelectItem.setItemValue("d");
            uiSelectItem.setItemLabel("e");
            uiSelectItem.setItemDescription("f");
            component.getChildren().add(uiSelectItem);
        }

        // ## Act ##
        SelectItemsIterator it = new SelectItemsIterator(component);

        // ## Assert ##
        assertEquals(true, it.hasNext());
        {
            SelectItem item = (SelectItem) it.next();
            assertEquals("a", item.getValue());
            assertEquals("b", item.getLabel());
            assertEquals("c", item.getDescription());
        }
        assertEquals(true, it.hasNext());
        {
            SelectItem item = (SelectItem) it.next();
            assertEquals("d", item.getValue());
            assertEquals("e", item.getLabel());
            assertEquals("f", item.getDescription());
        }
        assertEquals(false, it.hasNext());
    }

    public void testEmptyUISelectItems() throws Exception {
        // ## Arrange ##
        UISelectMany component = new UISelectMany();
        component.getChildren().add(new UISelectItems());

        // ## Act ##
        SelectItemsIterator it = new SelectItemsIterator(component);

        // ## Assert ##
        assertEquals(false, it.hasNext());
    }

    public void testUISelectItems_SelectItem() throws Exception {
        // ## Arrange ##
        UISelectMany component = new UISelectMany();
        {
            SelectItem selectItem = new SelectItem();
            selectItem.setValue("val");
            selectItem.setLabel("lab");
            selectItem.setDescription("desc");
            UISelectItems ui = new UISelectItems();
            ui.setValue(selectItem);
            component.getChildren().add(ui);
        }

        // ## Act ##
        SelectItemsIterator it = new SelectItemsIterator(component);

        // ## Assert ##
        assertEquals(true, it.hasNext());
        SelectItem item = (SelectItem) it.next();
        assertEquals("val", item.getValue());
        assertEquals("lab", item.getLabel());
        assertEquals("desc", item.getDescription());
        assertEquals(false, it.hasNext());
    }

    public void testUISelectItems_SelectItemArray() throws Exception {
        // ## Arrange ##
        UISelectMany component = new UISelectMany();
        {
            UISelectItems ui = new UISelectItems();
            SelectItem item1 = new SelectItem("v1", "l1", "d1");
            SelectItem item2 = new SelectItem("v2", "l2", "d2");
            ui.setValue(new SelectItem[] { item1, item2 });
            component.getChildren().add(ui);
        }

        // ## Act ##
        SelectItemsIterator it = new SelectItemsIterator(component);

        // ## Assert ##
        assertEquals(true, it.hasNext());
        {
            SelectItem item = (SelectItem) it.next();
            assertEquals("v1", item.getValue());
            assertEquals("l1", item.getLabel());
            assertEquals("d1", item.getDescription());
        }
        assertEquals(true, it.hasNext());
        {
            SelectItem item = (SelectItem) it.next();
            assertEquals("v2", item.getValue());
            assertEquals("l2", item.getLabel());
            assertEquals("d2", item.getDescription());
        }
        assertEquals(false, it.hasNext());
    }

    public void testUISelectItems_SelectItemCollection() throws Exception {
        // ## Arrange ##
        UISelectMany component = new UISelectMany();
        {
            UISelectItems uiSelectItems = new UISelectItems();
            List l = new ArrayList();
            l.add(new SelectItem("v1", "l1", "d1"));
            l.add(new SelectItem("v2", "l2", "d2"));
            l.add(new SelectItem("v3", "l3", "d3"));
            uiSelectItems.setValue((Collection) l);
            component.getChildren().add(uiSelectItems);
        }

        // ## Act ##
        SelectItemsIterator it = new SelectItemsIterator(component);

        // ## Assert ##
        assertEquals(true, it.hasNext());
        {
            SelectItem item = (SelectItem) it.next();
            assertEquals("v1", item.getValue());
            assertEquals("l1", item.getLabel());
            assertEquals("d1", item.getDescription());
        }
        assertEquals(true, it.hasNext());
        {
            SelectItem item = (SelectItem) it.next();
            assertEquals("v2", item.getValue());
            assertEquals("l2", item.getLabel());
            assertEquals("d2", item.getDescription());
        }
        assertEquals(true, it.hasNext());
        {
            SelectItem item = (SelectItem) it.next();
            assertEquals("v3", item.getValue());
            assertEquals("l3", item.getLabel());
            assertEquals("d3", item.getDescription());
        }
        assertEquals(false, it.hasNext());
    }

    public void testUISelectItems_SelectItemMap() throws Exception {
        // ## Arrange ##
        UISelectMany component = new UISelectMany();
        {
            Map m = new LinkedHashMap();
            m.put("l1", "v1");
            m.put("l2", "v2");
            UISelectItems ui = new UISelectItems();
            ui.setValue(m);
            component.getChildren().add(ui);
        }

        // ## Act ##
        SelectItemsIterator it = new SelectItemsIterator(component);

        // ## Assert ##
        assertEquals(true, it.hasNext());
        {
            SelectItem item = (SelectItem) it.next();
            assertEquals("v1", item.getValue());
            assertEquals("l1", item.getLabel());
            assertEquals(null, item.getDescription());
        }
        assertEquals(true, it.hasNext());
        {
            SelectItem item = (SelectItem) it.next();
            assertEquals("v2", item.getValue());
            assertEquals("l2", item.getLabel());
            assertEquals(null, item.getDescription());
        }
        assertEquals(false, it.hasNext());
    }

    public void testChildrenAreNotUISelectItemOrUISelectItems()
            throws Exception {
        // ## Arrange ##
        UISelectMany component = new UISelectMany();
        UIComponent invalidChild = new MockUIComponent();
        ObjectAssert.assertNotInstanceOf(UISelectItem.class, invalidChild);
        ObjectAssert.assertNotInstanceOf(UISelectItems.class, invalidChild);
        component.getChildren().add(invalidChild);

        // ## Act ##
        SelectItemsIterator it = new SelectItemsIterator(component);

        // ## Assert ##
        assertEquals("only valid children are UISelectItem or UISelectItems",
                false, it.hasNext());
    }

    public void testRemove() throws Exception {
        // ## Arrange ##
        SelectItemsIterator it = new SelectItemsIterator(new MockUIComponent());

        // ## Act ##
        // ## Assert ##
        try {
            it.remove();
        } catch (UnsupportedOperationException uoe) {
            ExceptionAssert.assertMessageExist(uoe);
        }
    }

    public void testNextThrowsNoSuchElementException() throws Exception {
        // ## Arrange ##
        UISelectMany component = new UISelectMany();
        {
            UISelectItem uiSelectItem = new UISelectItem();
            uiSelectItem.setItemValue("a");
            uiSelectItem.setItemLabel("b");
            component.getChildren().add(uiSelectItem);
        }
        {
            UISelectItem uiSelectItem = new UISelectItem();
            uiSelectItem.setItemValue("a");
            uiSelectItem.setItemLabel("b");
            component.getChildren().add(uiSelectItem);
        }

        // ## Act ##
        SelectItemsIterator it = new SelectItemsIterator(component);
        it.next();
        it.next();

        // ## Assert ##
        try {
            it.next();
            fail();
        } catch (NoSuchElementException nsee) {
        }
    }

}
