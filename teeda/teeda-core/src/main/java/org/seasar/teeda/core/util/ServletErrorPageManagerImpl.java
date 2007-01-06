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
package org.seasar.teeda.core.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author higa
 *
 */
public class ServletErrorPageManagerImpl implements ErrorPageManager {

    private Map locations = new HashMap();

    public void addErrorPage(Class exceptionType, String location) {
        locations.put(exceptionType, location);
    }

    public boolean handleException(Throwable exception,
            ExternalContext extContext) throws IOException {
        String location = getLocation(exception.getClass());
        if (location == null) {
            return false;
        }
        ServletRequest request = ServletExternalContextUtil
                .getRequest(extContext);
        if (request.getAttribute(JsfConstants.ERROR_EXCEPTION) != null) {
            setErrorPageAttributesToServletError(request);
            setResponseStatus(extContext);
            return true;
        }
        ServletExternalContextUtil
                .storeErrorInfoToAttribute(request, exception);
        extContext.dispatch(location);
        return true;
    }

    protected String getLocation(Class exceptionType) {
        Class clazz = exceptionType;
        String location = (String) locations.get(clazz);
        while (location == null && !clazz.equals(Throwable.class)) {
            clazz = clazz.getSuperclass();
            location = (String) locations.get(clazz);
        }
        return location;
    }

    protected void setErrorPageAttributesToServletError(ServletRequest request) {
        request.setAttribute(JsfConstants.SERVLET_ERROR_EXCEPTION, request
                .getAttribute(JsfConstants.ERROR_EXCEPTION));
        request.setAttribute(JsfConstants.SERVLET_ERROR_EXCEPTION_TYPE, request
                .getAttribute(JsfConstants.ERROR_EXCEPTION_TYPE));
        request.setAttribute(JsfConstants.SERVLET_ERROR_EXCEPTION_MESSAGE,
                request.getAttribute(JsfConstants.ERROR_MESSAGE));
    }

    protected void setResponseStatus(final ExternalContext extContext) {
        HttpServletResponse response = ServletExternalContextUtil
                .getResponse(extContext);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
