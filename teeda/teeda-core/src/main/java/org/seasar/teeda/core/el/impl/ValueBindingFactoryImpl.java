/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.ValueBindingFactory;

/**
 * @author shot
 */
public class ValueBindingFactoryImpl implements ValueBindingFactory {

    private Map cache = Collections.synchronizedMap(new HashMap());

    private ELParser parser;

    public ValueBindingFactoryImpl() {
        this.parser = ELParserFactory.createELParser();
    }

    public void setELParser(ELParser parser) {
        this.parser = parser;
    }

    public ELParser getELParser() {
        return parser;
    }

    public ValueBinding createValueBinding(Application application,
            String expression) {
        if (StringUtil.isEmpty(expression)) {
            throw new EmptyRuntimeException("Expression string");
        }
        if (application == null) {
            throw new EmptyRuntimeException("Application");
        }
        ValueBinding vb = getValueBindingFromCache(application, expression);
        if (vb != null) {
            return vb;
        }
        vb = new ValueBindingImpl(application, expression, getELParser());
        cache.put(expression, vb);
        return vb;
    }

    public void clearCache() {
        cache.clear();
    }

    protected ValueBinding getRestoredValueBinding(StateHolder holder,
            String expression) {
        FacesContext context = FacesContext.getCurrentInstance();
        holder
                .restoreState(context,
                        new Object[] { expression, getELParser() });
        return (ValueBinding) holder;
    }

    protected ValueBinding getValueBindingFromCache(Application application,
            String expression) {
        ValueBinding vb = (ValueBinding) cache.get(expression);
        if (vb != null && vb instanceof StateHolder) {
            vb = getRestoredValueBinding((StateHolder) vb, expression);
        }
        return vb;
    }
}
