package javax.faces.component;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

public class UISelectMany extends UIInput {

	public static final String COMPONENT_FAMILY = "javax.faces.SelectMany";

	public static final String COMPONENT_TYPE = "javax.faces.SelectMany";

	public static final String INVALID_MESSAGE_ID = "javax.faces.component.UISelectMany.INVALID";

	private static final String DEFAULT_RENDER_TYPE = "javax.faces.Listbox";

	public UISelectMany() {
		super();
		setRendererType(DEFAULT_RENDER_TYPE);
	}

	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public Object[] getSelectedValues() {
		return (Object[]) getValue();
	}

	public void setSelectedValues(Object[] selectedValues) {
		setValue(selectedValues);
	}

	public ValueBinding getValueBinding(String name) {
		if ("selectedValues".equals(name)) {
			return super.getValueBinding("value");
		} else {
			return super.getValueBinding(name);
		}
	}

	public void setValueBinding(String name, ValueBinding binding) {
		if ("selectedValues".equals(name)) {
			super.setValueBinding("value", binding);
		} else {
			super.setValueBinding(name, binding);
		}
	}

	protected boolean compareValues(Object previous, Object value) {
		if (previous == null && value != null) {
			return true;
		} else if (previous != null && value == null) {
			return true;
		} else if (previous == null && value == null) {
			return true;
		}

		boolean valueChanged = false;
		Object oldarray[] = null;
		Object newarray[] = null;

		if (!ComponentUtils_.isObjectArray(previous)) {
			previous = toObjectArray(previous);
		}

		if (!ComponentUtils_.isObjectArray(value)) {
			value = toObjectArray(value);
		}

		if (!ComponentUtils_.isObjectArray(previous)
				|| !ComponentUtils_.isObjectArray(value)) {
			return false;
		}
		oldarray = (Object[]) previous;
		newarray = (Object[]) value;
		if (oldarray.length != newarray.length) {
			return true;
		}

		for (int oldCounts = 0, newCounts = 0, i = 0; i < oldarray.length; ++i) {
			oldCounts = countElementOccurrence(oldarray[i], oldarray);
			newCounts = countElementOccurrence(oldarray[i], newarray);
			if (oldCounts != newCounts) {
				valueChanged = true;
				break;
			}
		}
		return valueChanged;
	}

	protected void validateValue(FacesContext context, Object value) {
		super.validateValue(context, value);
		if (!isValid() || (value == null)) {
			return;
		}
		boolean isList = (value instanceof List);
		int length = (isList) ? ((List) value).size() : Array.getLength(value);
		for (int i = 0; i < length; i++) {
			Iterator items = new SelectItemsIterator_(this);
			Object indexValue = 
				(isList) ? ((List)value).get(i) : Array.get(value, i);
			if (!ComponentUtils_.valueMatches(indexValue, items)) {
				Object[] args = {getId()};
				FacesMessageUtils_.addErrorMessage(context, this, INVALID_MESSAGE_ID, args);
	            setValid(false);
				break;
			}
		}
	}

	private int countElementOccurrence(Object element, Object[] array) {
		int count = 0;
		for (int i = 0; i < array.length; ++i) {
			Object arrayElement = array[i];
			if (arrayElement != null && element != null) {
				if (arrayElement.equals(element)) {
					count++;
				}
			}
		}
		return count;
	}

	private Object[] toObjectArray(Object obj) {
		ComponentUtils_.assertNotNull("primitiveArray", obj);
		if (ComponentUtils_.isObjectArray(obj)) {
			return (Object[]) obj;
		}else if(obj instanceof List) {
			return ((List) obj).toArray();
		}else if(!obj.getClass().isArray()) {
			return null;
		}
		int length = Array.getLength(obj);
		Object[] array = new Object[length];
		for (int i = 0; i < length; i++) {
			array[i] = Array.get(obj, i);
		}
		return array;
	}

}
