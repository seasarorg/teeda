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
package org.seasar.teeda.core.annotation;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import javax.faces.validator.Validator;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.util.AnnotationUtil;

/**
 * @author shot
 */
public class ConstantValidatorAnnotationHandler extends
        AbstractValidatorAnnotationHandler {

    //TODO more testing and do refactoring for AbstractValidatorAnnotationHandler
    protected static final String DELIMETER = ",";

    public void doRegisterValidator(Class targetClass, BeanDesc beanDesc) {
        String alias = getAlias(beanDesc);
        for (int i = 0; i < beanDesc.getPropertyDescSize(); i++) {
            PropertyDesc propDesc = beanDesc.getPropertyDesc(i);
            String validatorRule = getValidatorRule(propDesc);
            if (!beanDesc.hasField(validatorRule)) {
                continue;
            }
            if (!AnnotationUtil.isConstantAnnotation(beanDesc
                    .getField(validatorRule))) {
                continue;
            }
            String config = (String) beanDesc
                    .getFieldValue(validatorRule, null);
            String propertyName = propDesc.getPropertyName();
            String expression = getExpressionByAuto(targetClass, propertyName);
            if (alias != null) {
                expression = getExpression(alias, propertyName);
            }
            handleValidatorConfig(config, expression);
        }
    }

    protected String getValidatorRule(PropertyDesc propDesc) {
        String propertyName = propDesc.getPropertyName();
        String validatorRule = propertyName + AnnotationConstants.VALIDATOR;
        return validatorRule;
    }

    protected void handleValidatorConfig(String config, String expression) {
        List list = new LinkedList();
        for (;;) {
            int start = config.indexOf("{");
            int end = config.indexOf("}");
            if (start != -1 && end != -1) {
                String content = config.substring(start, end + 1);
                Validator validator = setUpValidator(content);
                list.add(validator);
            } else {
                break;
            }
            config = config.substring(end + 1);
            if (!checkIfHasNextConfig(config)) {
                break;
            }
        }
        Validator validator = chainValidators(list);
        getValidatorResource().addValidatorResource(expression, validator);
    }

    protected Validator setUpValidator(final String config) {
        String content = config.substring(1, config.length() - 1);
        String[] configs = content.split(DELIMETER);
        String validatorType = configs[0];
        Validator validator = createValidator(validatorType);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(validator.getClass());
        String[] params = getParams(configs);
        for (int i = 0; i < params.length; i++) {
            String[] pair = StringUtil.split(params[i], "=");
            if (pair.length == 2) {
                String propertyName = pair[0].trim();
                String value = pair[1].trim();
                PropertyDesc propDesc = beanDesc.getPropertyDesc(propertyName);
                propDesc.setValue(validator, value);
            }
        }
        return validator;
    }

    protected String[] getParams(String[] configs) {
        String[] params = new String[configs.length - 1];
        System.arraycopy(configs, 1, params, 0, configs.length - 1);
        return params;
    }

    protected String getExpressionByAuto(Class targetClass, String property) {
        String className = getShortClassName(targetClass);
        if (className.length() > 1) {
            className = className.substring(0, 1).toLowerCase()
                    + className.substring(1);
        } else {
            className = className.toLowerCase();
        }
        return getExpression(className, property);
    }

    protected String getExpression(String base, String property) {
        return "#{" + base + "." + property + "}";
    }

    protected String getAlias(BeanDesc beanDesc) {
        if (beanDesc.hasField(AnnotationConstants.ALIAS)) {
            Field field = beanDesc.getField(AnnotationConstants.ALIAS);
            if (AnnotationUtil.isConstantAnnotation(field)) {
                return (String) beanDesc.getFieldValue(
                        AnnotationConstants.ALIAS, null);
            }
        }
        return null;
    }

    private static boolean checkIfHasNextConfig(String config) {
        return (config != null) && (config.indexOf(",") < config.indexOf("{"));
    }

}
