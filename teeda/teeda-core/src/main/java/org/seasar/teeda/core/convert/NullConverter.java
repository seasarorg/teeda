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
package org.seasar.teeda.core.convert;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * @author higa
 *
 */
public class NullConverter implements Converter, StateHolder {

    private static final String[] DEFAULT_NULL_VALUES = new String[] { "", "on" };

    private String[] nullValues = DEFAULT_NULL_VALUES;

    private boolean transientValue;

    public String[] getNullValues() {
        return nullValues;
    }

    public void setNullValues(String[] nullValues) {
        this.nullValues = nullValues;
    }

    /**
     * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
     */
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) throws ConverterException {

        for (int i = 0; i < nullValues.length; ++i) {
            if (nullValues[i].equalsIgnoreCase(value)) {
                return null;
            }
        }
        return value;
    }

    /**
     * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     */
    public String getAsString(FacesContext context, UIComponent component,
            Object value) throws ConverterException {

        if (value == null) {
            return "";
        }
        return value.toString();
    }

    /**
     * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
     */
    public Object saveState(FacesContext context) {
        Object[] values = new Object[1];
        values[0] = nullValues;
        return values;
    }

    /**
     * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
     */
    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        nullValues = (String[]) values[0];

    }

    /**
     * @see javax.faces.component.StateHolder#isTransient()
     */
    public boolean isTransient() {
        return transientValue;
    }

    /**
     * @see javax.faces.component.StateHolder#setTransient(boolean)
     */
    public void setTransient(boolean transientValue) {
        this.transientValue = transientValue;
    }

}
