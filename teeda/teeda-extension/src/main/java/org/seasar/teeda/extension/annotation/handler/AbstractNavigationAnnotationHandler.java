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
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.teeda.core.application.navigation.NavigationCaseContext;
import org.seasar.teeda.core.application.navigation.NavigationContext;
import org.seasar.teeda.core.application.navigation.NavigationResource;


/**
 * @author higa
 */
public abstract class AbstractNavigationAnnotationHandler implements
        NavigationAnnotationHandler {

    private static final String PAGE = "PAGE";

    public void registerNavigationsByAction(String actionName, Class clazz) {
        S2Container container = SingletonS2ContainerFactory.getContainer();
        NamingConvention namingConvention = (NamingConvention) container.getComponent(NamingConvention.class);
        String viewId = namingConvention.fromActionNameToPath(actionName);
        registerNavigations(viewId, clazz, container, namingConvention);
    }

    public void registerNavigationsByPage(String pageName, Class clazz) {
        S2Container container = SingletonS2ContainerFactory.getContainer();
        NamingConvention namingConvention = (NamingConvention) container.getComponent(NamingConvention.class);
        String viewId = namingConvention.fromPageNameToPath(pageName);
        registerNavigations(viewId, clazz, container, namingConvention);
    }

    protected void registerNavigations(String viewId, Class clazz, S2Container container, NamingConvention namingConvention) {
        NavigationResource.removeNavigationContext(viewId);
        NavigationContext nc = new NavigationContext();
        nc.setFromViewId(viewId);
        List nccList = getNavigationCaseContexts(clazz, container, namingConvention);
        if (nccList.size() == 0) {
            return;
        }
        for (int i = 0; i < nccList.size(); ++i) {
            NavigationCaseContext ncc = (NavigationCaseContext) nccList.get(i);
            nc.addNavigationCaseContext(ncc);
        }
        NavigationResource.addNavigationContext(nc);
    }
    
    protected NavigationCaseContext createNavigationCaseContext(String outcome, String toViewId, boolean redirect) {
        NavigationCaseContext ncc = new NavigationCaseContext();
        ncc.setFromOutcome(outcome);
        ncc.setToViewId(toViewId);
        ncc.setRedirect(redirect);
        return ncc;
    }
    
    protected abstract NavigationCaseContext createNavigationCaseContext(BeanDesc beanDesc, Field field, S2Container container, NamingConvention namingConvention);
    
    protected List getNavigationCaseContexts(Class clazz, S2Container container, NamingConvention namingConvention) {
        List nccs = new ArrayList();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(clazz);
        for (int i = 0; i < beanDesc.getFieldSize(); ++i) {
            Field field = beanDesc.getField(i);
            if (!isTarget(field)) {
                continue;
            }
            NavigationCaseContext ncc = createNavigationCaseContext(beanDesc, field, container, namingConvention);
            if (ncc != null) {
                nccs.add(ncc);
            }
        }
        return nccs;
    }

    protected boolean isTarget(Field field) {
        int m = field.getModifiers();
        return Modifier.isFinal(m) && Modifier.isStatic(m) && field.getType().equals(String.class) && field.getName().endsWith(PAGE);
    }
    
    public void removeAll() {
        NavigationResource.removeAll();
    }
}