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
package org.seasar.teeda.extension.el.impl;

import javax.faces.application.Application;
import javax.faces.el.ValueBinding;

import org.seasar.framework.container.Expression;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.ognl.OgnlExpression;
import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.el.impl.ValueBindingContextImpl;
import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.extension.el.OgnlAware;

public class OgnlValueBindingContextImpl extends ValueBindingContextImpl
        implements OgnlAware {

    private S2Container container;

    public ValueBinding createValueBinding(Application application,
            String expression) {
        if (StringUtil.isEmpty(expression)) {
            throw new EmptyRuntimeException("Expression string");
        }
        if (!isOgnlValueReference(expression)) {
            return super.createValueBinding(application, expression);
        } else {
            String source = expression.substring(3, expression.length() - 1);
            Object obj = createExpression(source)
                    .evaluate(getContainer(), null);
            return super.createValueBinding(application, BindingUtil
                    .getExpression(obj.toString()));
        }
    }

    public Expression createExpression(String source) {
        return new OgnlExpression(source);
    }

    private static boolean isOgnlValueReference(String expression) {
        return expression.startsWith(OgnlAware.TEEDA_OGNL_PREFIX)
                && expression.endsWith(OgnlAware.TEEDA_OGNL_SUFFIX);
    }

    public void setContainer(S2Container container) {
        this.container = container;
    }

    public S2Container getContainer() {
        return container;
    }

}
