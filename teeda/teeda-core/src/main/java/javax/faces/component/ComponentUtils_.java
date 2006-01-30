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

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Locale;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;
import javax.faces.event.PhaseId;
import javax.faces.internal.ArrayIterator;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.commons.beanutils.BeanUtils;

/**
 * @author shot
 * 
 * TODO TEST
 */
public class ComponentUtils_ {

    private static final int LOCALE_LENGTH_SHORT = 2;

    private static final int LOCALE_LENGTH_LONG = 5;

    private ComponentUtils_() {
    }

    public static void assertNotNull(Object obj) {
        assertNotNull(null, obj);
    }

    public static void assertNotNull(String message, Object obj) {
        if (obj == null) {
            throw new NullPointerException(message);
        }
    }

    public static void assertIntegerNotNegative(int num) {
        assertIntegerNotNegative(num, null);
    }

    public static void assertIntegerNotNegative(int num, String message) {
        if (num < 0) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void copyProperties(Object to, Object from) {
        try {
            BeanUtils.copyProperties(to, from);

            // should be more understandable exception
        } catch (IllegalAccessException e) {
            throw new FacesException(e);
        } catch (InvocationTargetException e) {
            throw new FacesException(e);
        }
    }

    public static void processAppropriatePhaseAction(FacesContext context,
            UIComponent component, PhaseId phase) {
        if (phase == PhaseId.APPLY_REQUEST_VALUES) {
            component.processDecodes(context);
        } else if (phase == PhaseId.PROCESS_VALIDATIONS) {
            component.processValidators(context);
        } else if (phase == PhaseId.UPDATE_MODEL_VALUES) {
            component.processUpdates(context);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static Object getValueBindingValue(UIComponent component,
            String bindingName) {
        ValueBinding vb = component.getValueBinding(bindingName);
        return (vb != null) ? vb.getValue(component.getFacesContext()) : null;
    }

    public static Class getValueBindingType(UIComponent component,
            String bindingName) {
        ValueBinding vb = component.getValueBinding(bindingName);
        return (vb != null) ? vb.getType(component.getFacesContext()) : null;
    }

    public static Boolean convertToBoolean(boolean value) {
        return (value) ? Boolean.TRUE : Boolean.FALSE;
    }

    public static Converter createConverter(FacesContext context, Class type) {
        return context.getApplication().createConverter(type);
    }

    public static boolean isPerformNoConversion(Class type) {
        return (type == null || type == String.class || type == Object.class);
    }

    public static boolean convertToPrimitiveBoolean(Object obj) {
        if (obj instanceof Boolean) {
            return (obj != null) ? ((Boolean) obj).booleanValue() : false;
        } else {
            return false;
        }
    }

    public static Locale calculateLocale(FacesContext context) {
        ViewHandler viewHandler = context.getApplication().getViewHandler();
        return viewHandler.calculateLocale(context);
    }

    public static Locale getLocale(FacesContext context) {
        if (context == null) {
            throw new NullPointerException();
        }
        return context.getViewRoot().getLocale();
    }

    public static boolean isLocaleShort(String locale) {
        assertNotNull(locale);
        if (locale.length() == LOCALE_LENGTH_SHORT) {
            if (locale.indexOf("-") == -1 || locale.indexOf("_") == -1) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLocaleLong(String locale) {
        assertNotNull(locale);
        if (locale.length() == LOCALE_LENGTH_LONG) {
            return true;
        }
        return false;
    }

    public static boolean valueMatches(Object value, Iterator selectItems) {
        while (selectItems.hasNext()) {
            SelectItem item = (SelectItem) selectItems.next();
            if (item instanceof SelectItemGroup) {
                SelectItem[] subitems = ((SelectItemGroup) item)
                        .getSelectItems();
                if ((subitems != null) && (subitems.length > 0)) {
                    if (valueMatches(value, new ArrayIterator(subitems))) {
                        return true;
                    }
                }
            } else {
                Object itemValue = item.getValue();
                if (value == null) {
                    if (itemValue == null) {
                        return true;
                    }
                } else if (itemValue == null) {
                    if (value == null) {
                        return true;
                    }
                } else {
                    if (value.equals(itemValue)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isObjectArray(Object obj) {
        return (obj instanceof Object[]);
    }
}
