/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.util;

import java.util.Map;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.exception.NoEditableValueHolderRuntimeException;

/**
 * @author higa
 * @author manhole
 */
public class DecodeUtil {

    private DecodeUtil() {
    }

    public static void decode(FacesContext context, UIComponent component) {
        AssertionUtil.assertNotNull("context is null.", context);
        AssertionUtil.assertNotNull("component is null.", component);
        if (!(component instanceof EditableValueHolder)) {
            throw new NoEditableValueHolderRuntimeException(component
                    .getClass());
        }
        EditableValueHolder evh = (EditableValueHolder) component;
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        String clientId = component.getClientId(context);
        if (paramMap.containsKey(clientId)) {
            Object submittedValue = paramMap.get(clientId);
            evh.setSubmittedValue(submittedValue);
        }
    }

    public static void decodeMany(FacesContext context, UIComponent component) {
        AssertionUtil.assertNotNull("context is null.", context);
        AssertionUtil.assertNotNull("component is null.", component);
        if (!(component instanceof EditableValueHolder)) {
            throw new NoEditableValueHolderRuntimeException(component
                    .getClass());
        }
        EditableValueHolder evh = (EditableValueHolder) component;
        Map paramValuesMap = context.getExternalContext()
                .getRequestParameterValuesMap();
        String clientId = component.getClientId(context);
        String[] value = null;
        if (paramValuesMap.containsKey(clientId)) {
            value = (String[]) paramValuesMap.get(clientId);
        }
        if (value != null) {
            evh.setSubmittedValue(value);
        } else {
            evh.setSubmittedValue(new String[0]);
        }
    }

}
