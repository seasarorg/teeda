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
package org.seasar.teeda.extension.interceptor;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.InstanceDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.deployer.InstanceRequestDef;
import org.seasar.framework.container.deployer.InstanceSessionDef;
import org.seasar.framework.convention.NamingConvention;

/**
 * @author shot
 */
public class ActionSupportInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 1L;

    private S2Container container;

    private NamingConvention namingConvention;

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object ret = invocation.proceed();
        if (namingConvention == null) {
            namingConvention = (NamingConvention) container
                    .getComponent(NamingConvention.class);
        }
        final ComponentDef componentDef = getComponentDef(invocation);
        final Class actionClass = componentDef.getConcreteClass();
        String name = deleteEnhancedSuffix(actionClass.getName());
        if (!hasActionSuffix(name)) {
            return ret;
        }
        String actionComponentName = namingConvention
                .fromClassNameToComponentName(name);
        if (!container.hasComponentDef(actionComponentName)) {
            return ret;
        }
        Object action = container.getComponent(actionComponentName);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(actionClass);
        for (int i = 0; i < beanDesc.getPropertyDescSize(); i++) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            Class propertyType = pd.getPropertyType();
            String propertyClassName = deleteEnhancedSuffix(propertyType
                    .getName());
            if (propertyClassName.endsWith(namingConvention.getPageSuffix())) {
                String pageComponentName = namingConvention
                        .fromClassNameToComponentName(propertyClassName);
                Object page = (pd.isReadable()) ? pd.getValue(action) : null;
                InstanceDef instanceDef = componentDef.getInstanceDef();
                savePage(instanceDef, pageComponentName, page);
            }
        }
        return ret;
    }

    protected void savePage(InstanceDef instanceDef, String pageComponentName,
            Object page) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        if (instanceDef instanceof InstanceRequestDef) {
            Map requestMap = externalContext.getRequestMap();
            requestMap.put(pageComponentName, page);
        } else if (instanceDef instanceof InstanceSessionDef) {
            Map sessionMap = externalContext.getSessionMap();
            sessionMap.put(pageComponentName, page);
        }
    }

    private boolean hasActionSuffix(String name) {
        return name.endsWith(namingConvention.getActionSuffix());
    }

    private static String deleteEnhancedSuffix(String name) {
        int enhanceIndex = name.indexOf("$");
        if (enhanceIndex > 0) {
            name = name.substring(0, enhanceIndex);
        }
        return name;
    }

    public S2Container getContainer() {
        return container;
    }

    public void setContainer(S2Container container) {
        this.container = container.getRoot();
    }

    public NamingConvention getNamingConvention() {
        return namingConvention;
    }

    public void setNamingConvention(NamingConvention namingConvention) {
        this.namingConvention = namingConvention;
    }

}
