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

import javax.faces.validator.Validator;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.util.ConstantAnnotationUtil;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.FieldUtil;
import org.seasar.framework.util.StringUtil;

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
        String suffix = namingConvention.getValidatorSuffix();
        ComponentDef componentDef = container.getComponentDef(componentName);
        Class componentClass = componentDef.getComponentClass();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(componentClass);
        Field[] fields = componentClass.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            Field field = fields[i];
            boolean isConstantAnnotation = ConstantAnnotationUtil
                    .isConstantAnnotation(field);
            if (!isConstantAnnotation || !field.getName().endsWith(suffix)) {
                continue;
            }
            String[] names = StringUtil.split(field.getName(), "_");
            if (names.length != 2 || !beanDesc.hasPropertyDesc(names[0])
                    || !container.hasComponentDef(names[1])) {
                continue;
            }
            Validator validator = (Validator) container.getComponent(names[1]);
            String s = (String) FieldUtil.get(field, null);
            Map m = ConstantAnnotationUtil.convertExpressionToMap(s);
            copyProperties(validator, m);
            registerValidator(componentName, names[0], validator);
        }
    }
}