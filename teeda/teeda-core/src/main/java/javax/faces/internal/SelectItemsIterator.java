/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import javax.faces.context.FacesContext;
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

    protected SelectItem createSelectItem(final UISelectItem ui) {
        final Object value = ui.getValue();
        if (value != null) {
            return convertValueAsSelectItem(value, ui);
        } else {
            final Object itemValue = ui.getItemValue();
            final String label = ui.getItemLabel();
            final String description = ui.getItemDescription();
            final boolean disabled = ui.isItemDisabled();
            return createSelectItem(itemValue, label, description, disabled);
        }
    }

    private SelectItem createSelectItem(final Object value, String label,
            final String description, final boolean disabled) {
        if (label == null && value != null) {
            label = value.toString();
        }
        SelectItem selectItem = new SelectItem();
        if (value != null) {
            selectItem.setValue(value);
        }
        if (label != null) {
            selectItem.setLabel(label);
        }
        selectItem.setDescription(description);
        selectItem.setDisabled(disabled);
        return selectItem;
    }

    private SelectItem convertValueAsSelectItem(final Object value,
            final UIComponent component) {
        if (value instanceof SelectItem) {
            return (SelectItem) value;
        } else {
            final FacesContext context = FacesContext.getCurrentInstance();
            final String clientId = component.getClientId(context);
            final StringBuffer sb = new StringBuffer(100);
            sb.append("component [");
            sb.append(clientId);
            sb.append("] is not SelectItem. but was [");
            sb.append(value.getClass().getName());
            sb.append("]");
            throw new IllegalStateException(new String(sb));
        }
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
