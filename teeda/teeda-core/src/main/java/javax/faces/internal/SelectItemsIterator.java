/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.model.SelectItem;

/**
 * @author shot
 * @author manhole
 * 
 * This class might be changed without notice. Please do not use it
 * excluding the JSF specification part.
 */
public class SelectItemsIterator implements Iterator {

    private Iterator children = null;

    private Iterator items = null;

    private SelectItem nextValue;

    public SelectItemsIterator(UIComponent component) {
        this.children = component.getChildren().iterator();
    }

    public void remove() {
        throw new UnsupportedOperationException("remove");
    }

    public boolean hasNext() {
        if (nextValue != null) {
            return true;
        }
        nextValue = getNextSelectItem();
        if (nextValue != null) {
            return true;
        }
        return false;
    }

    protected SelectItem getNextSelectItem() {
        if (items != null) {
            if (items.hasNext()) {
                return (SelectItem) items.next();
            } else {
                items = null;
            }
        }
        if (!children.hasNext()) {
            return null;
        }
        UIComponent child = (UIComponent) children.next();
        if (child instanceof UISelectItem) {
            return createSelectItem((UISelectItem) child);
        } else if (child instanceof UISelectItems) {
            UISelectItems items = (UISelectItems) child;
            return getNextFromUISelectItems(items);
        } else {
            // throw new IllegalArgumentException();
            return getNextSelectItem();
        }
    }

    protected SelectItem getNextFromUISelectItems(UISelectItems items) {
        Object value = items.getValue();
        if (value instanceof SelectItem) {
            return (SelectItem) value;
        } else if (value instanceof SelectItem[]) {
            this.items = Arrays.asList((SelectItem[]) value).iterator();
            return getNextSelectItem();
        } else if (value instanceof Collection) {
            Collection c = (Collection) value;
            this.items = c.iterator();
            return getNextSelectItem();
        } else if (value instanceof Map) {
            this.items = new SelectItemsMapIterator((Map) value);
            return getNextSelectItem();
        } else {
            // throw new IllegalArgumentException();
            return getNextSelectItem();
        }
    }

    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        if (nextValue != null) {
            Object o = nextValue;
            nextValue = null;
            return o;
        }
        throw new NoSuchElementException();
    }

    protected SelectItem createSelectItem(UISelectItem ui) {
        SelectItem item = (SelectItem) ui.getValue();
        if (item != null) {
            return item;
        }
        return new SelectItem(ui.getItemValue(), ui.getItemLabel(), ui
                .getItemDescription(), ui.isItemDisabled());
    }

    protected static class SelectItemsMapIterator implements Iterator {

        private Map map;

        private Iterator keys;

        public SelectItemsMapIterator(Map map) {
            this.map = map;
            /*
             * use key iterator. see: API document of UISelectItems.
             */
            this.keys = map.keySet().iterator();
        }

        public boolean hasNext() {
            return keys.hasNext();
        }

        public Object next() {
            Object key = keys.next();
            Object value = map.get(key);
            return new SelectItem(value.toString(), key.toString());
        }

        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

}
