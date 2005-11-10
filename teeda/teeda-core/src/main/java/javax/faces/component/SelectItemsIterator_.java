/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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
import java.util.List;
import java.util.NoSuchElementException;

import javax.faces.model.SelectItem;

/**
 * @author Shinpei Ohtani
 */
class SelectItemsIterator_ implements Iterator {

	private UIComponent parent_ = null;
	
	private Iterator children_ = null;
	
	private Iterator items_ = null;
	
	public SelectItemsIterator_(UIComponent parent){
		parent_ = parent;
		children_ = parent_.getChildren().iterator();
	}
	
	public void remove() {
		throw new UnsupportedOperationException();
	}

	public boolean hasNext() {
		return (items_ != null) ? items_.hasNext() : children_.hasNext();
	}

	public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        if (items_ != null) {
            return items_.next();
        }
        UIComponent child = (UIComponent)children_.next();
        if (child instanceof UISelectItem) {
            return createSelectItem(child);
        } else if (child instanceof UISelectItems) {
            UISelectItems items = (UISelectItems) child;
            Object value = items.getValue();
            if (value instanceof SelectItem) {
                return value;
            } else if (value instanceof SelectItem[]) {
                items_ = Arrays.asList((Object[])value).iterator();
                return next();
            } else if (value instanceof List) {
            	List list = (List)value;
                items_ = list.iterator();
                return next();
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
	}

    private SelectItem createSelectItem(UIComponent component){
        UISelectItem ui = (UISelectItem) component;
        SelectItem item = (SelectItem) ui.getValue();
        if (item == null) {
            item = new SelectItem(ui.getItemValue(),
                                  ui.getItemLabel(),
                                  ui.getItemDescription(),
                                  ui.isItemDisabled());
        }
        return item;
    }
}
