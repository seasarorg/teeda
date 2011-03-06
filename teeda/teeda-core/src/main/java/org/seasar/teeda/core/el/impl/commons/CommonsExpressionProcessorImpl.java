/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ReferenceSyntaxException;
import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

import org.apache.commons.el.ArraySuffix;
import org.apache.commons.el.BinaryOperatorExpression;
import org.apache.commons.el.ComplexValue;
import org.apache.commons.el.ConditionalExpression;
import org.apache.commons.el.Expression;
import org.apache.commons.el.ExpressionString;
import org.apache.commons.el.FunctionInvocation;
import org.apache.commons.el.Literal;
import org.apache.commons.el.NamedValue;
import org.apache.commons.el.PropertySuffix;
import org.apache.commons.el.UnaryOperatorExpression;
import org.apache.commons.el.ValueSuffix;
import org.seasar.teeda.core.el.ExpressionProcessor;
import org.seasar.teeda.core.el.Replacer;

/**
 * @author shot
 */
public class CommonsExpressionProcessorImpl implements ExpressionProcessor {

    private static final long serialVersionUID = 1L;

    private static final Map replacers_ = new HashMap();

    protected static FunctionMapper mapper_ = new FunctionMapper() {
        public Method resolveFunction(String prefix, String localName) {
            throw new ReferenceSyntaxException();
        }
    };

    public CommonsExpressionProcessorImpl() {
        init();
    }

    public void init() {
        replacers_.put(Object.class, new ObjectReplacer(this));
        replacers_.put(BinaryOperatorExpression.class,
                new BinaryOperatorExpressionReplacer(this));
        replacers_.put(UnaryOperatorExpression.class,
                new UnaryOperatorExpressionReplacer(this));
        replacers_.put(FunctionInvocation.class,
                new IgnorerableExpressionReplacer(this));
        replacers_.put(Literal.class, new IgnorerableExpressionReplacer(this));
        replacers_.put(NamedValue.class,
                new IgnorerableExpressionReplacer(this));
        replacers_.put(ExpressionString.class, new ExpressionStringReplacer(
                this));
        replacers_.put(ComplexValue.class, new ComplexValueReplacer(this));
    }

    public void processExpression(Object expression, Class type) {
        Replacer replacer = (Replacer) replacers_.get(type);
        if (replacer != null) {
            replacer.replace(expression);
        }
    }

    public Object evaluate(FacesContext context, Object expression)
            throws EvaluationException {
        try {
            VariableResolver resolver = new ELVariableResolver(context);
            if (expression instanceof Expression) {
                return ((Expression) expression).evaluate(resolver, mapper_,
                        CommonsElLogger.getLogger());
            } else if (expression instanceof ExpressionString) {
                return ((ExpressionString) expression).evaluate(resolver,
                        mapper_, CommonsElLogger.getLogger());
            }
        } catch (ELException e) {
            throw new EvaluationException(e);
        }
        return null;
    }

    public Integer toIndex(Object base, Object index)
            throws ReferenceSyntaxException {
        Integer indexValue = null;
        if (base instanceof List || base.getClass().isArray()) {
            indexValue = CoercionsUtil.coerceToInteger(index);
            if (indexValue == null) {
                throw new ReferenceSyntaxException();
            }
        } else if (base instanceof UIComponent) {
            indexValue = CoercionsUtil.coerceToInteger(index);
        }
        return indexValue;
    }

    public Object resolveBase(FacesContext context, Object expression)
            throws ReferenceSyntaxException, PropertyNotFoundException {
        while (expression instanceof ConditionalExpression) {
            ConditionalExpression conditionalExpression = ((ConditionalExpression) expression);
            Object value = evaluate(context, conditionalExpression
                    .getCondition());
            boolean condition = CoercionsUtil.coerceToPrimitiveBoolean(value);
            expression = (condition) ? conditionalExpression.getTrueBranch()
                    : conditionalExpression.getFalseBranch();
        }
        if (expression instanceof NamedValue) {
            return ((NamedValue) expression).getName();
        }
        if (!(expression instanceof ComplexValue)) {
            return null;
        }
        ComplexValue complexValue = (ComplexValue) expression;
        Object base = evaluate(context, complexValue.getPrefix());
        VariableResolver resolver = new ELVariableResolver(context);
        if (base == null) {
            throw new PropertyNotFoundException("Base is null: "
                    + complexValue.getPrefix().getExpressionString());
        }

        List suffixes = complexValue.getSuffixes();
        int max = suffixes.size() - 1;
        for (int i = 0; i < max; i++) {
            ValueSuffix suffix = (ValueSuffix) suffixes.get(i);
            try {
                base = suffix.evaluate(base, resolver, mapper_, CommonsElLogger
                        .getLogger());
            } catch (ELException e) {
                throw new EvaluationException(e);
            }
            if (base == null) {
                throw new PropertyNotFoundException("Base is null: "
                        + suffix.getExpressionString());
            }
        }
        ArraySuffix arraySuffix = (ArraySuffix) suffixes.get(max);
        Expression arraySuffixIndex = arraySuffix.getIndex();
        Object index;
        if (arraySuffixIndex != null) {
            index = evaluate(context, arraySuffixIndex);
            if (index == null) {
                throw new PropertyNotFoundException("Index is null: "
                        + arraySuffixIndex.getExpressionString());
            }
        } else {
            index = ((PropertySuffix) arraySuffix).getName();
        }
        return new Object[] { base, index };
    }

    public Object getCoercedObject(Object newValue, Class type)
            throws EvaluationException {
        return CoercionsUtil.coerce(newValue, type);
    }
}
