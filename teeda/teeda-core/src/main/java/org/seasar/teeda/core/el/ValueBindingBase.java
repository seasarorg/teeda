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
package org.seasar.teeda.core.el;

import java.util.Arrays;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.managedbean.ManagedBeanFactory;
import org.seasar.teeda.core.util.DIContainerUtil;

/**
 * 
 * @author shot
 *
 */
public abstract class ValueBindingBase extends ValueBinding implements
        StateHolder {

    static {
        Arrays.sort(JsfConstants.JSF_IMPLICIT_OBJECTS);
    };

    protected abstract void setValueInScope(FacesContext context, String name,
            Object newValue);

    public abstract Object getExpression();

    protected final ManagedBeanFactory getManagedBeanFactory() {
        return (ManagedBeanFactory) DIContainerUtil
                .getComponent(ManagedBeanFactory.class);
    }

    public static boolean isImplicitObject(String expressionString) {
        return (Arrays.binarySearch(JsfConstants.JSF_IMPLICIT_OBJECTS,
                expressionString) >= 0);
    }

}
