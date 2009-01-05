/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.mock;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.teeda.core.JsfConstants;

/**
 * @author shot
 * @author manhole
 */
public class MockVariableResolver extends VariableResolver {

    private Map values = new HashMap();

    public MockVariableResolver() {
    }

    public void putValue(String key, Object value) {
        values.put(key, value);
    }

    public Object resolveVariable(FacesContext context, String name)
            throws EvaluationException {
        ExternalContext externalContext = context.getExternalContext();
        {
            Object value = values.get(name);
            if (value != null) {
                return value;
            }
        }
        Map applicationMap = externalContext.getApplicationMap();
        Map sessionMap = externalContext.getSessionMap();
        Map requestMap = externalContext.getRequestMap();
        if (JsfConstants.REQUEST_SCOPE.equals(name)) {
            return requestMap;
        }
        if (JsfConstants.SESSION_SCOPE.equals(name)) {
            return sessionMap;
        }
        if (JsfConstants.APPLICATION_SCOPE.equals(name)) {
            return applicationMap;
        }
        {
            Object value = requestMap.get(name);
            if (value != null) {
                return value;
            }
        }
        {
            Map requestParameterMap = externalContext.getRequestParameterMap();
            Object value = requestParameterMap.get(name);
            if (value != null) {
                return value;
            }
        }
        {
            Object value = sessionMap.get(name);
            if (value != null) {
                return value;
            }
        }
        {
            Object value = applicationMap.get(name);
            if (value != null) {
                return value;
            }
        }
        if (SingletonS2ContainerFactory.hasContainer()) {
            final S2Container container = SingletonS2ContainerFactory
                    .getContainer();
            if (container.hasComponentDef(name)) {
                final Object value = container.getComponent(name);
                if (value != null) {
                    return value;
                }
            }
        }
        return null;
    }

}
