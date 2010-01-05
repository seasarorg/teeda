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
package org.seasar.teeda.core.util;

import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.AssertionUtil;

public class UIParameterUtil {

    private UIParameterUtil() {
    }

    public static void saveParametersToRequest(UIComponent component,
            FacesContext context) {
        List children = component.getChildren();
        for (int i = 0; i < children.size(); ++i) {
            Object o = (UIComponent) children.get(i);
            if (o instanceof UIParameter) {
                UIParameter param = (UIParameter) o;
                context.getExternalContext().getRequestMap().put(
                        param.getName(), param.getValue());
            }
        }
    }

    public static void saveParametersToInstance(UIComponent component,
            Object obj) {
        AssertionUtil.assertNotNull("component", component);
        AssertionUtil.assertNotNull("obj", obj);
        for (Iterator itr = component.getChildren().iterator(); itr.hasNext();) {
            Object o = itr.next();
            if (o instanceof UIParameter) {
                UIParameter param = (UIParameter) o;
                String name = param.getName();
                Object value = param.getValue();
                BeanDesc beanDesc = BeanDescFactory.getBeanDesc(obj.getClass());
                if (beanDesc.hasPropertyDesc(name)) {
                    PropertyDesc propertyDesc = beanDesc.getPropertyDesc(name);
                    if (propertyDesc.isWritable()) {
                        propertyDesc.setValue(obj, value);
                    }
                }
            }
        }
    }

}
