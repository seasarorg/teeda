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
package org.seasar.teeda.core.application.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.application.ComponentLookupStrategy;
import org.seasar.teeda.core.util.DIContainerUtil;

/**
 * @author shot
 */
public class DefaultComponentLookupStrategy implements ComponentLookupStrategy {

    private List namespaces = new ArrayList();

    public DefaultComponentLookupStrategy() {
        namespaces.add(JsfConstants.TEEDA_NAMESPACE);
    }
    public Object getComponentByName(String componentName) {
        if (StringUtil.isEmpty(componentName)) {
            throw new IllegalArgumentException();
        }
        Object component = DIContainerUtil
                .getComponentNoException(componentName);
        if (component != null) {
            return component;
        }
        for(Iterator itr = namespaces.iterator();itr.hasNext();) {
            String namespace = (String) itr.next();
            component = getComponentByDefaultNamespace(namespace, componentName);
            if(component != null) {
                return component;
            }
        }
        return null;
    }

    public Object getComponentByClass(Class componentClazz) {
        if (componentClazz == null) {
            throw new IllegalArgumentException();
        }
        return DIContainerUtil.getComponentNoException(componentClazz);
    }

    public void addNamespace(String namespace) {
        if(!StringUtil.isEmpty(namespace)) {
            this.namespaces.add(namespace);
        }
    }

    private Object getComponentByDefaultNamespace(String namespace, String componentName) {
        return DIContainerUtil.getComponentNoException(namespace
                + JsfConstants.NS_SEP + componentName);
    }
}
