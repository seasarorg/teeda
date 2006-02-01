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
 * This class might be changed without a previous notice. Please do not use it
 * excluding the JSF specification part.
 */
public class SelectItemsIterator implements Iterator {

    private Iterator children_ = null;

    private Iterator items_ = null;

    private Object nextValue_;

    public SelectItemsIterator(UIComponent component) {
        children_ = component.getChildren().iterator();
    }

    public void remove() {
        throw new UnsupportedOperationException("remove");
    }

    public boolean hasNext() {
        if (nextValue_ != null) {
            return true;
        }
        nextValue_ = getNext();
        if (nextValue_ != null) {
            return true;
        }
        return false;
    }

    private Object getNext() {
        if (items_ != null) {
            if (items_.hasNext()) {
                return items_.next();
            } else {
                items_ = null;
            }
        }
        if (!children_.hasNext()) {
            return null;
        }
        UIComponent child = (UIComponent) children_.next();
        if (child instanceof UISelectItem) {
            Object o = createSelectItem((UISelectItem) child);
            return o;
        } else if (child instanceof UISelectItems) {
            UISelectItems items = (UISelectItems) child;
            Object value = items.getValue();
            if (value instanceof SelectItem) {
                return value;
            } else if (value instanceof SelectItem[]) {
                items_ = Arrays.asList((SelectItem[]) value).iterator();
                return getNext();
            } else if (value instanceof Collection) {
                Collection c = (Collection) value;
                items_ = c.iterator();
                return getNext();
            } else if (value instanceof Map) {
                items_ = new SelectItemsMapIterator((Map) value);
                return getNext();
            } else {
                // throw new IllegalArgumentException();
                return getNext();
            }
        } else {
            // throw new IllegalArgumentException();
            return getNext();
        }
    }

    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        if (nextValue_ != null) {
            Object o = nextValue_;
            nextValue_ = null;
            return o;
        }
        throw new NoSuchElementException();
    }

    private SelectItem createSelectItem(UISelectItem ui) {
        SelectItem item = (SelectItem) ui.getValue();
        if (item != null) {
            return item;
        }
        return new SelectItem(ui.getItemValue(), ui.getItemLabel(), ui
                .getItemDescription(), ui.isItemDisabled());
    }

    private static class SelectItemsMapIterator implements Iterator {

        private Map map_;

        private Iterator keys_;

        public SelectItemsMapIterator(Map map) {
            map_ = map;
            /*
             * use key iterator.
             * see: API document of UISelectItems.
             */
            keys_ = map.keySet().iterator();
        }

        public boolean hasNext() {
            return keys_.hasNext();
        }

        public Object next() {
            Object key = keys_.next();
            Object value = map_.get(key);
            return new SelectItem(value.toString(), key.toString());
        }

        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

}
