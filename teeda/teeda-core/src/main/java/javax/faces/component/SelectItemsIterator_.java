package javax.faces.component;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.faces.model.SelectItem;


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
            UISelectItem ui = (UISelectItem) child;
            SelectItem item = (SelectItem) ui.getValue();
            if (item == null) {
                item = new SelectItem(ui.getItemValue(),
                                      ui.getItemLabel(),
                                      ui.getItemDescription(),
                                      ui.isItemDisabled());
            }
            return item;
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

}
