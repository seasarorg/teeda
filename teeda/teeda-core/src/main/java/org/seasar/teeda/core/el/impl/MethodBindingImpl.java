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
package org.seasar.teeda.core.el.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.internal.AssertionUtil;

import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.ValueBindingBase;
import org.seasar.teeda.core.exception.ExtendEvaluationException;
import org.seasar.teeda.core.exception.ExtendMethodNotFoundExceptin;
import org.seasar.teeda.core.exception.MethodNotAccessibleException;
import org.seasar.teeda.core.util.DIContainerUtil;

/**
 * @author shot
 */
public class MethodBindingImpl extends MethodBinding implements StateHolder {

    private ValueBindingBase vb_;

    private Class[] classes_;

    private ELParser parser_;

    private boolean transientValue_ = false;

    public MethodBindingImpl(ValueBindingBase vb, Class[] classes,
            ELParser parser) {
        classes_ = classes;
        vb_ = vb;
        parser_ = parser;
    }

    public MethodBindingImpl() {
        classes_ = null;
        vb_ = null;
        parser_ = null;
    }

    public Object invoke(FacesContext context, Object[] params)
            throws EvaluationException, MethodNotFoundException {
        AssertionUtil.assertNotNull("context is null.", context);
        Object[] obj = getBaseAndProperty(context);
        Object base = obj[0];
        Object property = obj[1];
        try {
            Method m = base.getClass().getMethod(property.toString(), classes_);
            return m.invoke(base, params);
        } catch (SecurityException e) {
            throw new MethodNotAccessibleException(e,
                    base.getClass().getName(), getExpressionString());
        } catch (NoSuchMethodException e) {
            throw new ExtendMethodNotFoundExceptin(e,
                    base.getClass().getName(), getExpressionString());
        } catch (IndexOutOfBoundsException e) {
            throw new PropertyNotFoundException("Expression:"
                    + getExpressionString(), e);
        } catch (InvocationTargetException e) {
            throw new ExtendEvaluationException(e.getCause(), base.getClass()
                    .getName(), getExpressionString());
        } catch (Exception e) {
            throw new ExtendEvaluationException(e, base.getClass().getName(),
                    getExpressionString());
        }
    }

    public Class getType(FacesContext context) throws MethodNotFoundException {
        AssertionUtil.assertNotNull("context is null.", context);
        Object[] obj = getBaseAndProperty(context);
        Object base = obj[0];
        Object property = obj[1];

        Class returnType;
        try {
            returnType = base.getClass().getMethod(property.toString(),
                    classes_).getReturnType();
            if (returnType.getName().equals("void")) {
                return Void.class;
            }
            return returnType;
        } catch (SecurityException e) {
            throw new MethodNotAccessibleException(e,
                    base.getClass().getName(), getExpressionString());
        } catch (NoSuchMethodException e) {
            throw new ExtendMethodNotFoundExceptin(e,
                    base.getClass().getName(), getExpressionString());
        } catch (IndexOutOfBoundsException e) {
            throw new PropertyNotFoundException("Expression:"
                    + getExpressionString(), e);
        }
    }

    public String getExpressionString() {
        return vb_.getExpressionString();
    }

    public boolean isTransient() {
        return transientValue_;
    }

    public void setTransient(boolean transientValue) {
        transientValue_ = transientValue;
    }

    public Object saveState(FacesContext context) {
        return new Object[] { vb_.saveState(context), classes_, };
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] obj = (Object[]) state;
        vb_ = new ValueBindingImpl();
        vb_.restoreState(context, obj[0]);
        classes_ = (Class[]) obj[1];
        parser_ = (ELParser) DIContainerUtil
                .getComponentNoException(ELParser.class);
    }

    private Object[] getBaseAndProperty(FacesContext context)
            throws ReferenceSyntaxException {
        Object expression = vb_.getExpression();
        Object o = parser_.getExpressionProcessor().resolveBase(context,
                expression);
        if (!(o instanceof Object[])) {
            throw new ReferenceSyntaxException();
        }
        Object[] obj = (Object[]) o;
        return obj;
    }
}
