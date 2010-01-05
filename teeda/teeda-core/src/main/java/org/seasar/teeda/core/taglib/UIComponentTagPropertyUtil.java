/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.taglib;

import javax.faces.component.UIComponent;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.teeda.core.util.BindingUtil;

public class UIComponentTagPropertyUtil {

    protected UIComponentTagPropertyUtil() {
    }

    public static void setComponentProperty(UIComponent component,
            String propertyName, String value) {
        if (value == null) {
            return;
        }
        if (BindingUtil.isValueReference(value)) {
            BindingUtil.setValueBinding(component, propertyName, value);
        } else {
            setBeanProperty(component, propertyName, value);
        }
    }

    private static void setBeanProperty(UIComponent component,
            String propertyName, String value) {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(component.getClass());
        if (beanDesc.hasPropertyDesc(propertyName)) {
            PropertyDesc pd = beanDesc.getPropertyDesc(propertyName);
            if (pd.isWritable()) {
                pd.setValue(component, value);
            }
        } else {
            component.getAttributes().put(propertyName, value);
        }
    }

}
