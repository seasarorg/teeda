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
package org.seasar.teeda.core.el.impl.commons;

import java.util.List;

import javax.faces.application.Application;
import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

import org.apache.commons.el.ArraySuffix;
import org.apache.commons.el.ComplexValue;
import org.apache.commons.el.Logger;
import org.apache.commons.el.PropertySuffix;
import org.apache.commons.el.ValueSuffix;
import org.seasar.teeda.core.el.ExpressionProcessor;
import org.seasar.teeda.core.el.Replacer;
import org.seasar.teeda.core.util.ApplicationUtil;
import org.seasar.teeda.core.util.PropertyResolverUtil;

/**
 * @author shot
 */
public class ComplexValueReplacer implements Replacer {

    private ExpressionProcessor processor_;

    public ComplexValueReplacer(ExpressionProcessor processor) {
        processor_ = processor;
    }

    public void replace(Object expression) {
        ComplexValue complexValue = (ComplexValue) expression;
        Application application = ApplicationUtil.getApplicationFromContext();
        List suffixes = complexValue.getSuffixes();
        for (int i = 0; i < suffixes.size(); i++) {
            ValueSuffix valueSuffix = (ValueSuffix) suffixes.get(i);
            if (isSuitablePropertySuffix(valueSuffix)) {
                PropertySuffix propertySuffix = (PropertySuffix) valueSuffix;
                suffixes.set(i, new JsfPropertySuffix(propertySuffix,
                        application, processor_));
            } else if (isSuitableArraySuffix(valueSuffix)) {
                ArraySuffix arraySuffix = (ArraySuffix) valueSuffix;
                suffixes.set(i, new JsfArraySuffix(arraySuffix, application,
                        processor_));
            } else {
                throw new IllegalStateException();
            }
        }
    }

    private static boolean isSuitablePropertySuffix(ValueSuffix valueSuffix) {
        return (valueSuffix instanceof PropertySuffix)
                && !(valueSuffix instanceof JsfPropertySuffix);
    }

    private static boolean isSuitableArraySuffix(ValueSuffix valueSuffix) {
        return (valueSuffix instanceof ArraySuffix)
                && !(valueSuffix instanceof JsfArraySuffix);
    }

    public static class JsfArraySuffix extends ArraySuffix {
        private Application application_;

        private ExpressionProcessor processor_;

        public JsfArraySuffix(ArraySuffix suffix, Application application,
                ExpressionProcessor processor) {
            super(suffix.getIndex());
            Object o = getIndex();
            processor_ = processor;
            application_ = application;
            processor_.processExpression(o, o.getClass());
        }

        public Object evaluate(Object base, VariableResolver resolver,
                FunctionMapper mapper, Logger logger) throws ELException {
            if (base == null) {
                return null;
            }
            Object indexValue = getIndex().evaluate(resolver, mapper, logger);
            if (indexValue == null) {
                return null;
            }
            Integer index = processor_.toIndex(base, indexValue);
            return PropertyResolverUtil.getValue(application_, base,
                    indexValue, index);
        }

    }

    public static class JsfPropertySuffix extends PropertySuffix {
        private Application application_;

        private ExpressionProcessor processor_;

        public JsfPropertySuffix(PropertySuffix propertySuffix,
                Application application, ExpressionProcessor processor) {
            super(propertySuffix.getName());
            application_ = application;
            processor_ = processor;
        }

        public Object evaluate(Object base, VariableResolver resolver,
                FunctionMapper mapper, Logger logger) throws ELException {
            if (base == null) {
                return null;
            }
            String indexValue = getName();
            if (indexValue == null) {
                return null;
            }
            Integer index = processor_.toIndex(base, indexValue);
            return PropertyResolverUtil.getValue(application_, base,
                    indexValue, index);
        }
    }
}
