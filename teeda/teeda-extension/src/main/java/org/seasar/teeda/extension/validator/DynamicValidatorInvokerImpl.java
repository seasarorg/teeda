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
package org.seasar.teeda.extension.validator;

import java.lang.reflect.Method;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.internal.DynamicValidatorInvoker;
import javax.faces.validator.Validator;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.MethodUtil;
import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.core.util.DIContainerUtil;

/**
 * @author shot
 */
public class DynamicValidatorInvokerImpl implements DynamicValidatorInvoker {

    private NamingConvention namingConvention;

    private static final Object[] EMPTY_ARGS = new Object[0];

    public Validator invoke(FacesContext context, UIComponent component,
            Object newValue) {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("component", component);
        final String id = component.getId();
        final ValueBinding vb = component.getValueBinding("value");
        if (id == null || vb == null) {
            return null;
        }
        final String expressionString = vb.getExpressionString();
        final int index = expressionString.indexOf(".");
        if (BindingUtil.isValueReference(expressionString) && index > 0) {
            final String componentName = expressionString.substring(2, index);
            final Class clazz = namingConvention
                    .fromComponentNameToClass(componentName);
            if (clazz == null) {
                return null;
            }
            final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz);
            final String validatorProperty = id + "Validator";
            if (!beanDesc.hasPropertyDesc(validatorProperty)) {
                return null;
            }
            PropertyDesc pd = beanDesc.getPropertyDesc(validatorProperty);
            if (Validator.class.isAssignableFrom(pd.getPropertyType())) {
                if (!DIContainerUtil.hasComponent(clazz)) {
                    return null;
                }
                Object page = DIContainerUtil.getComponentNoException(clazz);
                if (page == null) {
                    return null;
                }
                if (pd.isReadable() && pd.hasReadMethod()) {
                    Method readMethod = pd.getReadMethod();
                    return (Validator) MethodUtil.invoke(readMethod, page,
                            EMPTY_ARGS);
                }
            }
        }
        return null;
    }

    public void setNamingConvention(NamingConvention namingConvention) {
        this.namingConvention = namingConvention;
    }
}
