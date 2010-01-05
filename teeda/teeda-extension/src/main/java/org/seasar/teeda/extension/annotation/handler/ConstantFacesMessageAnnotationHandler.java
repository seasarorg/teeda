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
package org.seasar.teeda.extension.annotation.handler;

import java.lang.reflect.Field;
import java.util.Map;

import javax.faces.application.FacesMessage;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.beans.util.BeanUtil;
import org.seasar.framework.container.util.ConstantAnnotationUtil;
import org.seasar.framework.util.FieldUtil;
import org.seasar.teeda.extension.ExtensionConstants;

/**
 * @author shot
 */
public class ConstantFacesMessageAnnotationHandler extends
        AbstractFacesMessageAnnotationHandler {

    protected void processFields(Class componentClass, String componentName) {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(componentClass);
        final Field[] fields = componentClass.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            processField(componentName, beanDesc, fields[i]);
        }
    }

    protected void processField(String componentName, BeanDesc beanDesc,
            Field field) {
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
        //Gridのコンポーネントは、Pageに無いexpression(#{hogePage.aaaGrid})になるので、チェックをはずす
        /*
         if (!beanDesc.hasPropertyDesc(fieldName)) {
         return;
         }
         */
        final String s = (String) FieldUtil.get(field, null);
        final Map m = ConstantAnnotationUtil.convertExpressionToMap(s);
        String id = (m != null) ? (String) m.remove("id")
                : "org.seasar.teeda.extension.DEFAULT_MESSAGE_AGGREGATION";
        FacesMessage message = createFacesMessage(id);
        if (id == null) {
            BeanUtil.copyProperties(m, message);
        }
        registerFacesMessage(componentName, fieldName, message);
    }

}
