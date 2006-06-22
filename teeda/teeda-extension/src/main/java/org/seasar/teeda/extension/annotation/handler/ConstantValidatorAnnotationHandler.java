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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.validator.Validator;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.framework.util.FieldUtil;
import org.seasar.framework.util.OgnlUtil;
import org.seasar.framework.util.StringUtil;

/**
 * @author shot
 * @author higa
 */
public class ConstantValidatorAnnotationHandler extends
        AbstractValidatorAnnotationHandler {

    private static final String VALIDATOR_SUFFIX = "_VALIDATOR";

    private static final String VALIDATOR = "validator";

    public void registerValidator(String componentName, Class clazz) {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz);
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            if (!beanDesc.hasField(pd.getPropertyName() + VALIDATOR_SUFFIX)) {
                continue;
            }
            Field field = beanDesc.getField(pd.getPropertyName()
                    + VALIDATOR_SUFFIX);
            String s = (String) FieldUtil.get(field, null);
            if (StringUtil.isEmpty(s)) {
                continue;
            }
            Object o = OgnlUtil.getValue(OgnlUtil.parseExpression(s),
                    SingletonS2ContainerFactory.getContainer());
            if (o instanceof List) {
                List l = (List) o;
                for (int j = 0; j < l.size(); ++j) {
                    Map m = (Map) l.get(j);
                    registerValidator(componentName, pd.getPropertyName(), m);
                }
            } else if (o instanceof Map) {
                Map m = (Map) o;
                registerValidator(componentName, pd.getPropertyName(), m);
            } else {
                throw new IllegalStateException(s);
            }
        }
    }

    protected void registerValidator(String componentName, String propertyName,
            Map m) {
        String validatorName = (String) m.remove(VALIDATOR);
        if (validatorName == null) {
            throw new EmptyRuntimeException("validatorName");
        }
        Validator validator = (Validator) SingletonS2ContainerFactory
                .getContainer().getComponent(validatorName);
        copyProperties(validator, m);
        registerValidator(componentName, propertyName, validator);
    }

    protected void copyProperties(Validator validator, Map m) {
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(validator.getClass());
        for (Iterator i = m.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            if (!beanDesc.hasPropertyDesc(key)) {
                continue;
            }
            PropertyDesc pd = beanDesc.getPropertyDesc(key);
            if (pd.hasWriteMethod()) {
                pd.setValue(validator, m.get(key));
            }
        }
    }
}