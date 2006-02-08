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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

/**
 * @author manhole
 */
public class SelectItemGroupsIterator extends SelectItemsIterator {

    public SelectItemGroupsIterator(UIComponent component) {
        super(component);
    }

    protected SelectItem getNextFromUISelectItems(UISelectItems items) {
        Object value = items.getValue();
        if (value instanceof SelectItem) {
            SelectItem selectItem = (SelectItem) value;
            SelectItemGroup group = new SelectItemGroup();
            group.setSelectItems(new SelectItem[] { selectItem });
            return group;
        } else if (value instanceof SelectItem[]) {
            SelectItem[] selectItems = (SelectItem[]) value;
            SelectItemGroup group = new SelectItemGroup();
            group.setSelectItems(selectItems);
            return group;
        } else if (value instanceof Collection) {
            Collection c = (Collection) value;
            List l = new ArrayList(c);
            SelectItem[] selectItems = new SelectItem[l.size()];
            l.toArray(selectItems);
            SelectItemGroup group = new SelectItemGroup();
            group.setSelectItems(selectItems);
            return group;
        } else if (value instanceof Map) {
            List l = new ArrayList();
            for (Iterator it = new SelectItemsMapIterator((Map) value); it
                    .hasNext();) {
                l.add(it.next());
            }
            SelectItem[] selectItems = new SelectItem[l.size()];
            l.toArray(selectItems);
            SelectItemGroup group = new SelectItemGroup();
            group.setSelectItems(selectItems);
            return group;
        } else {
            // throw new IllegalArgumentException();
            return getNextSelectItem();
        }
    }

}
