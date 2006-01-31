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

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.internal.FacesMessageUtils;
import javax.faces.internal.SelectItemsIterator;

/**
 * @author shot
 */
public class UISelectMany extends UIInput {

    public static final String COMPONENT_FAMILY = "javax.faces.SelectMany";

    public static final String COMPONENT_TYPE = "javax.faces.SelectMany";

    public static final String INVALID_MESSAGE_ID = "javax.faces.component.UISelectMany.INVALID";

    private static final String DEFAULT_RENDER_TYPE = "javax.faces.Listbox";

    public UISelectMany() {
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
        if (previous == null && value == null) {
            return false;
        }
        if (previous == null || value == null) {
            return true;
        }
        if (isNotArray(previous) && isNotArray(value)) {
            return !previous.equals(value);
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

    private boolean isNotArray(Object previous) {
        return !previous.getClass().isArray();
    }

    protected void validateValue(FacesContext context, Object value) {
        super.validateValue(context, value);
        if (!isValid() || (value == null)) {
            return;
        }
        boolean isList = (value instanceof List);
        int length = (isList) ? ((List) value).size() : Array.getLength(value);
        for (int i = 0; i < length; i++) {
            Iterator items = new SelectItemsIterator(this);
            Object indexValue = (isList) ? ((List) value).get(i) : Array.get(
                    value, i);
            if (!ComponentUtils_.valueMatches(indexValue, items)) {
                Object[] args = { getId() };
                FacesMessageUtils.addErrorMessage(context, this,
                        INVALID_MESSAGE_ID, args);
                setValid(false);
                break;
            }
        }
    }

    private int countElementOccurrence(Object element, Object[] array) {
        int count = 0;
        for (int i = 0; i < array.length; ++i) {
            Object arrayElement = array[i];
            if (arrayElement == null && element == null) {
                count++;
            } else if (arrayElement != null && element != null
                    && arrayElement.equals(element)) {
                count++;
            }
        }
        return count;
    }

    private Object[] toObjectArray(Object obj) {
        ComponentUtils_.assertNotNull("primitiveArray", obj);
        if (ComponentUtils_.isObjectArray(obj)) {
            return (Object[]) obj;
        } else if (obj instanceof List) {
            return ((List) obj).toArray();
        } else if (isNotArray(obj)) {
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
