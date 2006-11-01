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

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesMessageResource;
import javax.faces.internal.FacesMessageUtil;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.teeda.core.util.BindingUtil;

/**
 * @author shot
 */
public abstract class AbstractFacesMessageAnnotationHandler implements
        FacesMessageAnnotationHandler {

    //TODO refactor needed because of too many args.
    public void registerFacesMessages(String componentName) {
        final S2Container container = getContainer();
        NamingConvention namingConvention = (NamingConvention) container
                .getComponent(NamingConvention.class);
        ComponentDef componentDef = container.getComponentDef(componentName);
        Class componentClass = componentDef.getComponentClass();
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(componentClass);
        processFields(container, componentClass, componentName,
                namingConvention, beanDesc);
    }

    protected void registerFacesMessage(String componentName,
            String propertyName, FacesMessage message) {
        String expression = BindingUtil.getExpression(componentName,
                propertyName);
        FacesMessageResource.addFacesMessage(expression, message);
    }

    protected abstract void processFields(S2Container container,
            Class componentClass, String componentName,
            NamingConvention namingConvention, BeanDesc beanDesc);

    protected FacesMessage createFacesMessage(String id) {
        FacesContext context = FacesContext.getCurrentInstance();
        return FacesMessageUtil.getMessage(context, id, new Object[] {});
    }

    public void removeAll() {
        FacesMessageResource.removeAll();
    }

    protected S2Container getContainer() {
        return SingletonS2ContainerFactory.getContainer();
    }

}