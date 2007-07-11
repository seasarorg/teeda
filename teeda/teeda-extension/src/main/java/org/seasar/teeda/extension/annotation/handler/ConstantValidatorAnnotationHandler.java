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
package org.seasar.teeda.extension.annotation.handler;

import java.lang.reflect.Field;
import java.util.Map;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.util.ConstantAnnotationUtil;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.FieldUtil;

/**
 * @author shot
 * @author higa
 */
public class ConstantValidatorAnnotationHandler extends
        AbstractValidatorAnnotationHandler {

    public void registerValidators(String componentName) {
        removeValidators(componentName);
        S2Container container = getContainer();
        NamingConvention namingConvention = (NamingConvention) container
                .getComponent(NamingConvention.class);
        ComponentDef componentDef = container.getComponentDef(componentName);
        Class componentClass = componentDef.getComponentClass();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(componentClass);
        processFields(container, componentClass, componentName,
                namingConvention, beanDesc);
        processSetterMethods(container, componentClass, componentName,
                namingConvention, beanDesc);
    }

    protected void processFields(S2Container container, Class componentClass,
            String componentName, NamingConvention namingConvention,
            BeanDesc beanDesc) {
        Field[] fields = componentClass.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            processField(container, componentClass, componentName,
                    namingConvention, beanDesc, fields[i]);
        }
    }

    protected void processField(S2Container container, Class componentClass,
            String componentName, NamingConvention namingConvention,
            BeanDesc beanDesc, Field field) {

        boolean isConstantAnnotation = ConstantAnnotationUtil
                .isConstantAnnotation(field);
        if (!isConstantAnnotation
                || !field.getName().endsWith(
                        namingConvention.getValidatorSuffix())) {
            return;
        }
        final String fieldString = field.getName();
        final int index = fieldString.lastIndexOf("_");
        final String fieldName = fieldString.substring(0, index);
        final String validatorName = fieldString.substring(index + 1);
        if (!beanDesc.hasPropertyDesc(fieldName)
                || !container.hasComponentDef(validatorName)) {
            return;
        }
        final String s = FieldUtil.getString(field, null);
        final Map properties = ConstantAnnotationUtil.convertExpressionToMap(s);
        registerValidator(componentName, fieldName, validatorName, properties);
    }

    protected void processSetterMethods(S2Container container,
            Class componentClass, String componentName,
            NamingConvention namingConvention, BeanDesc beanDesc) {
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (pd.hasWriteMethod()) {
                processSetterMethod(container, componentClass, componentName,
                        namingConvention, beanDesc, pd);
            }
        }
    }

    protected void processSetterMethod(S2Container container,
            Class componentClass, String componentName,
            NamingConvention namingConvention, BeanDesc beanDesc,
            PropertyDesc propertyDesc) {
    }
}