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
package org.seasar.teeda.extension.annotation.handler;

import java.lang.reflect.Field;
import java.util.Map;

import javax.faces.application.FacesMessage;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.beans.util.BeanUtil;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.util.ConstantAnnotationUtil;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.FieldUtil;
import org.seasar.teeda.extension.ExtensionConstants;

/**
 * @author shot
 */
public class ConstantFacesMessageAnnotationHandler extends
        AbstractFacesMessageAnnotationHandler {

    public void registerFacesMessages(String componentName) {
        final S2Container container = getContainer();
        NamingConvention namingConvention = (NamingConvention) container
                .getComponent(NamingConvention.class);
        ComponentDef componentDef = container.getComponentDef(componentName);
        Class componentClass = componentDef.getComponentClass();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(componentClass);
        processFields(container, componentClass, componentName,
                namingConvention, beanDesc);
    }

    protected void processFields(S2Container container, Class componentClass,
            String componentName, NamingConvention namingConvention,
            BeanDesc beanDesc) {
        final Field[] fields = componentClass.getDeclaredFields();
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
        final String fieldString = field.getName();
        if (!isConstantAnnotation
                || !fieldString
                        .endsWith(ExtensionConstants.FACES_MESSAGE_AGGREGATION_SUFFIX)) {
            return;
        }
        String fieldName = fieldString.substring(0, fieldString.length()
                - ExtensionConstants.FACES_MESSAGE_AGGREGATION_SUFFIX.length());
        if (!beanDesc.hasPropertyDesc(fieldName)) {
            return;
        }
        String s = (String) FieldUtil.get(field, null);
        Map m = ConstantAnnotationUtil.convertExpressionToMap(s);
        String id = (String) m.remove("id");
        FacesMessage message = createFacesMessage(id);
        if (id == null) {
            BeanUtil.copyProperties(m, message);
        }
        registerFacesMessage(componentName, fieldName, message);
    }

}
