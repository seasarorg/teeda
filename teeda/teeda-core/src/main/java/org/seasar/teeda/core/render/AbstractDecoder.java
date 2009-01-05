/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.render;

import java.util.Map;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 */
public abstract class AbstractDecoder implements Decoder {

    public void decode(FacesContext context, UIComponent component) {
        AssertionUtil.assertNotNull("context is null.", context);
        AssertionUtil.assertNotNull("component is null.", component);
        ValueHolderWrapper wrapper = createValueHolderWrapper(component);
        Map paramMap = getRequestParameterMap(context);
        String clientId = getClientId(component, context);
        if (paramMap.containsKey(clientId)) {
            Object value = paramMap.get(clientId);
            wrapper.setValue(value);
        }
    }

    public void decodeMany(FacesContext context, UIComponent component) {
        AssertionUtil.assertNotNull("context is null.", context);
        AssertionUtil.assertNotNull("component is null.", component);
        ValueHolderWrapper wrapper = createValueHolderWrapper(component);
        Map paramValuesMap = getRequestParameterValuesMap(context);
        String clientId = getClientId(component, context);
        String[] value = null;
        if (paramValuesMap.containsKey(clientId)) {
            value = (String[]) paramValuesMap.get(clientId);
        }
        if (value != null) {
            wrapper.setValue(value);
        } else {
            wrapper.setValue(new String[0]);
        }
    }

    protected abstract ValueHolderWrapper createValueHolderWrapper(
            UIComponent component);

    protected Map getRequestParameterMap(FacesContext context) {
        return context.getExternalContext().getRequestParameterMap();
    }

    protected Map getRequestParameterValuesMap(FacesContext context) {
        return context.getExternalContext().getRequestParameterValuesMap();
    }

    protected String getClientId(UIComponent component, FacesContext context) {
        return component.getClientId(context);
    }

    protected static class ValueHolderWrapper {

        private ValueHolder holder;

        public ValueHolderWrapper(ValueHolder holder) {
            this.holder = holder;
        }

        public void setValue(Object value) {
            if (holder instanceof EditableValueHolder) {
                ((EditableValueHolder) holder).setSubmittedValue(value);
            } else {
                holder.setValue(value);
            }
        }

        public Object getValue() {
            if (holder instanceof EditableValueHolder) {
                return ((EditableValueHolder) holder).getSubmittedValue();
            } else {
                return holder.getValue();
            }
        }
    }

}
