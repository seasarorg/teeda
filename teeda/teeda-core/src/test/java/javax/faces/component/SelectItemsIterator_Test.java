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

import javax.faces.model.SelectItem;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.mock.MockUIComponentBase;

/**
 * @author shot
 */
public class SelectItemsIterator_Test extends TestCase {

    public void testHasNext() {
        MockUIComponentBase parent = new MockUIComponentBase();
        parent.setId("hoge");
        MockUIComponent child = new MockUIComponent();
        child.setId("child");
        parent.getChildren().add(child);
        SelectItemsIterator_ itr = new SelectItemsIterator_(parent);
        assertTrue(itr.hasNext());
    }

    public void testNext1() {
        String str = "aaa";
        MockUIComponentBase parent = new MockUIComponentBase();
        parent.setId("hoge");
        UISelectItem child = new UISelectItem();
        child.setId("child");
        child.setItemLabel("label");
        child.setItemValue(str);
        parent.getChildren().add(child);
        SelectItemsIterator_ itr = new SelectItemsIterator_(parent);
        Object o = itr.next();
        assertTrue(o instanceof SelectItem);
        assertEquals("label", ((SelectItem) o).getLabel());
        assertEquals("aaa", ((SelectItem) o).getValue());
    }

    public void testNext2() {
        MockUIComponentBase parent = new MockUIComponentBase();
        parent.setId("hoge");
        UISelectItems items = new UISelectItems();
        SelectItem child = new SelectItem();
        items.setValue(child);
        child.setLabel("label");
        parent.getChildren().add(items);
        SelectItemsIterator_ itr = new SelectItemsIterator_(parent);
        Object o = itr.next();
        assertTrue(o instanceof SelectItem);
        assertEquals("label", ((SelectItem) o).getLabel());
    }

}
