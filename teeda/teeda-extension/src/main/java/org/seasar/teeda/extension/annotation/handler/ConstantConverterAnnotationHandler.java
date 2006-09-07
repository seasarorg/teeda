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

import javax.faces.convert.Converter;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.beans.util.BeanUtil;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.util.ConstantAnnotationUtil;
import org.seasar.framework.util.FieldUtil;
import org.seasar.framework.util.StringUtil;

/**
 * @author shot
 */
public class ConstantConverterAnnotationHandler extends
        AbstractConverterAnnotationHandler {

    private static final String CONVERTER_SUFFIX = "Converter";

    //TODO namingConvention needs to support converter(like validator)?
    public void registerConverters(String componentName) {
        S2Container container = getContainer();
        ComponentDef componentDef = container.getComponentDef(componentName);
        Class clazz = componentDef.getComponentClass();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz);
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            Field field = fields[i];
            boolean isConstantAnnotation = ConstantAnnotationUtil
                    .isConstantAnnotation(field);
            if (!isConstantAnnotation
                    || !field.getName().endsWith(CONVERTER_SUFFIX)) {
                continue;
            }
            String[] names = StringUtil.split(field.getName(), "_");
            if (names.length != 2 || !beanDesc.hasPropertyDesc(names[0])
                    || !container.hasComponentDef(names[1])) {
                continue;
            }
            Converter converter = (Converter) container.getComponent(names[1]);
            String s = (String) FieldUtil.get(field, null);
            Map m = ConstantAnnotationUtil.convertExpressionToMap(s);
            BeanUtil.copyProperties(m, converter);
            registerConverter(componentName, names[0], converter);
        }
    }
}