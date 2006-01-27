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
package org.seasar.teeda.core.util;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.seasar.framework.container.S2Container;

/**
 * @author higa
 *  
 */
public class BindingUtil {

	private BindingUtil() {
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
		HttpServletRequest request = container.getRequest();
		Object var = getValue(request, name);
		if (var != null) {
			return var;
		}
		if (container.hasComponentDef(name)) {
			return container.getComponent(name);
		}
		return null;
	}
	
	public static Object getValue(HttpServletRequest request, String name) {
		Object var = request.getAttribute(name);
		if (var != null) {
			return var;
		}
		var = request.getParameter(name);
		if (var != null && !"null".equals(var)) {
			return var;
		}
		var = request.getAttribute(name);
		if (var != null) {
			return var;
		}
		HttpSession session = request.getSession(false);
		if (session != null) {
			var = session.getAttribute(name);
			if (var != null) {
				return var;
			}
		}
		return null;
	}
	
    public static void setValue(FacesContext context, String name, Object newValue){
        
    }
    
    public static Object resolveBinding(String value) {
        FacesContext context = FacesContext.getCurrentInstance();
        Application app = context.getApplication();
        ValueBinding vb = app.createValueBinding(value);
        return vb.getValue(context);
    }
    
	public static Object getBindingValue(UIComponent component, String propertyName) {
		ValueBinding binding = component.getValueBinding(propertyName);
		if (binding != null) {
			FacesContext ctx = FacesContext.getCurrentInstance();
			return binding.getValue(ctx);
		}
		return null;
	}
	
	public static void setValueBinding(UIComponent component, String name, String value) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		Application app = ctx.getApplication();
		ValueBinding binding = app.createValueBinding(value);
		component.setValueBinding(name, binding);
	}
}