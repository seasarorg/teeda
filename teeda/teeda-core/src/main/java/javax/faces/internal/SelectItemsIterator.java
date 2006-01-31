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
import java.util.Map.Entry;

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

    private Object nextItem_;

    public SelectItemsIterator(UIComponent component) {
        children_ = component.getChildren().iterator();
    }

    public void remove() {
        throw new UnsupportedOperationException("remove");
    }

    public boolean hasNext() {
        if (nextItem_ != null) {
            return true;
        }
        nextItem_ = readAhead();
        if (nextItem_ != null) {
            return true;
        }
        return false;
    }

    private Object readAhead() {
        if (items_ != null) {
            if (items_.hasNext()) {
                return items_.next();
            } else {
                items_ = null;
            }
        }
        if (children_.hasNext()) {
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
                    items_ = Arrays.asList((Object[]) value).iterator();
                    return readAhead();
                } else if (value instanceof Collection) {
                    Collection c = (Collection) value;
                    items_ = c.iterator();
                    return readAhead();
                } else if (value instanceof Map) {
                    items_ = new SelectItemsMapIterator((Map) value);
                    return readAhead();
                } else {
                    // throw new IllegalArgumentException();
                }
            } else {
                // throw new IllegalArgumentException();
            }
        }
        return null;
    }

    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        if (nextItem_ != null) {
            Object o = nextItem_;
            nextItem_ = null;
            return o;
        }
        throw new NoSuchElementException();
    }

    private SelectItem createSelectItem(UISelectItem ui) {
        // TODO why doing getValue?
        // SelectItem item = (SelectItem) ui.getValue();
        // if (item == null) {
        // item = new SelectItem(ui.getItemValue(), ui.getItemLabel(), ui
        // .getItemDescription(), ui.isItemDisabled());
        // }
        // return item;
        return new SelectItem(ui.getItemValue(), ui.getItemLabel(), ui
                .getItemDescription(), ui.isItemDisabled());
    }

    private static class SelectItemsMapIterator implements Iterator {

        private Iterator entries_;

        public SelectItemsMapIterator(Map m) {
            entries_ = m.entrySet().iterator();
        }

        public boolean hasNext() {
            return entries_.hasNext();
        }

        public Object next() {
            Map.Entry entry = (Entry) entries_.next();
            return new SelectItem(entry.getValue().toString(), entry.getKey()
                    .toString());
        }

        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

}
