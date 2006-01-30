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

import java.util.Iterator;

import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.model.SelectItem;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockUIComponentBase;

/**
 * @author shot
 */
public class SelectItemsIteratorTest extends TestCase {

    // TODO
    public void todo_testHasNext() {
        MockUIComponentBase parent = new MockUIComponentBase();
        parent.setId("hoge");
        MockUIComponent child = new MockUIComponent();
        child.setId("child");
        parent.getChildren().add(child);

        SelectItemsIterator itr = new SelectItemsIterator(parent);

        assertTrue(itr.hasNext());
        Object value = itr.next();
        System.out.println(value);
    }

    public void testNext1() {
        MockUIComponentBase parent = new MockUIComponentBase();
        parent.setId("hoge");
        UISelectItem child = new UISelectItem();
        child.setId("child");
        child.setItemLabel("label");
        child.setItemValue("aaa");
        parent.getChildren().add(child);

        SelectItemsIterator itr = new SelectItemsIterator(parent);
        assertEquals(true, itr.hasNext());
        Object o = itr.next();
        assertTrue(o instanceof SelectItem);
        SelectItem selectItem = (SelectItem) o;
        assertEquals("label", selectItem.getLabel());
        assertEquals("aaa", selectItem.getValue());
        assertEquals(false, itr.hasNext());
    }

    public void testNext2() {
        MockUIComponentBase parent = new MockUIComponentBase();
        parent.setId("hoge");
        UISelectItems items = new UISelectItems();
        SelectItem child = new SelectItem();
        items.setValue(child);
        child.setLabel("label");
        parent.getChildren().add(items);
        SelectItemsIterator itr = new SelectItemsIterator(parent);
        Object o = itr.next();
        assertTrue(o instanceof SelectItem);
        assertEquals("label", ((SelectItem) o).getLabel());
    }

    public void testItemCounts() throws Exception {
        // ## Arrange ##
        MockUIComponentBase component = new MockUIComponentBase();
        {
            UISelectItem uiSelectItem = new UISelectItem();
            uiSelectItem.setItemValue("a1");
            uiSelectItem.setItemLabel("b1");
            uiSelectItem.setItemDescription("c1");
            component.getChildren().add(uiSelectItem);
        }
        {
            UISelectItem uiSelectItem = new UISelectItem();
            uiSelectItem.setItemValue("a2");
            uiSelectItem.setItemLabel("b2");
            uiSelectItem.setItemDescription("c2");
            component.getChildren().add(uiSelectItem);
        }

        // ## Act ##
        Iterator it = new SelectItemsIterator(component);

        // ## Assert ##
        assertEquals(true, it.hasNext());
        {
            SelectItem selectItem = (SelectItem) it.next();
            assertEquals("a1", selectItem.getValue());
        }
        assertEquals(true, it.hasNext());
        {
            SelectItem selectItem = (SelectItem) it.next();
            assertEquals("a2", selectItem.getValue());
        }
        assertEquals(false, it.hasNext());
    }

}
