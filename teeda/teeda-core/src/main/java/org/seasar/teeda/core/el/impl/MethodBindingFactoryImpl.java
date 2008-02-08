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

import javax.faces.application.Application;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.MethodBindingFactory;
import org.seasar.teeda.core.el.ValueBindingBase;
import org.seasar.teeda.core.el.ValueBindingFactory;
import org.seasar.teeda.core.exception.IllegalClassTypeException;

/**
 * @author shot
 */
public class MethodBindingFactoryImpl implements MethodBindingFactory {

    private ValueBindingFactory valueBindingContextFactory;

    public void setValueBindingContext(
            ValueBindingFactory valueBindingContextFactory) {
        this.valueBindingContextFactory = valueBindingContextFactory;
    }

    public MethodBinding createMethodBinding(Application application,
            String ref, Class[] params) {
        ValueBinding vb = valueBindingContextFactory.createValueBinding(
                application, ref);
        ELParser parser = valueBindingContextFactory.getELParser();
        if (!(vb instanceof ValueBindingBase)) {
            throw new IllegalClassTypeException(ValueBindingBase.class,
                    ValueBinding.class);
        }
        MethodBinding mb = new MethodBindingImpl((ValueBindingBase) vb, params,
                parser);
        return mb;
    }

}
