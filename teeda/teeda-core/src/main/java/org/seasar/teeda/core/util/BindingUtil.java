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
package org.seasar.teeda.core.util;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.seasar.framework.container.ExternalContext;
import org.seasar.framework.container.S2Container;

/**
 * @author higa
 * @author shot
 */
public class BindingUtil {

    private BindingUtil() {
    }

    public static String getExpression(String componentName) {
        return "#{" + componentName + "}";
    }

    public static String getExpression(String componentName, String propertyName) {
        return "#{" + componentName + "." + propertyName + "}";
    }

    public static boolean isValueReference(String value) {
        if (value == null) {
            return false;
        }
        int start = value.indexOf("#{");
        if (start < 0) {
            return false;
        }
        int end = value.lastIndexOf('}');
        return (end >= 0 && start < end);
    }

    public static Object getValue(S2Container container, String name) {
        Object var = getValue(container.getExternalContext(), name);
        if (var != null) {
            return var;
        }
        if (container.hasComponentDef(name)) {
            return container.getComponent(name);
        }
        return null;
    }

    public static Object getValue(ExternalContext externalContext, String name) {
        Object var = externalContext.getRequestMap().get(name);
        if (var != null) {
            return var;
        }
        var = externalContext.getRequestParameterMap().get(name);
        if (var != null && !"null".equals(var)) {
            return var;
        }
        var = externalContext.getSessionMap().get(name);
        if (var != null) {
            return var;
        }
        return null;
    }

    public static Object resolveBinding(String value) {
        FacesContext context = FacesContext.getCurrentInstance();
        Application app = context.getApplication();
        ValueBinding vb = app.createValueBinding(value);
        return vb.getValue(context);
    }

    public static String resolveBindingNoException(String value) {
        Object ret = null;
        try {
            ret = resolveBinding(value);
        } catch (Exception ignore) {
            return null;
        }
        return (ret != null) ? ret.toString() : null;
    }

    public static Object getBindingValue(UIComponent component,
            String propertyName) {
        ValueBinding binding = component.getValueBinding(propertyName);
        if (binding != null) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            return binding.getValue(ctx);
        }
        return null;
    }

    public static void setValueBinding(UIComponent component, String name,
            String value) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Application app = ctx.getApplication();
        ValueBinding binding = app.createValueBinding(value);
        component.setValueBinding(name, binding);
    }
}
