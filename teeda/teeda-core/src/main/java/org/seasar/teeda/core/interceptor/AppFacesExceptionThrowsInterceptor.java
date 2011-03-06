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
package org.seasar.teeda.core.interceptor;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.interceptors.ThrowsInterceptor;
import org.seasar.teeda.core.exception.AppFacesException;

/**
 * @author shot
 *
 */
public class AppFacesExceptionThrowsInterceptor extends ThrowsInterceptor {

    private static final long serialVersionUID = 1L;

    public String handleThrowable(AppFacesException ex,
            MethodInvocation invocation) throws Throwable {
        FacesContext context = FacesContext.getCurrentInstance();
        final FacesMessage facesMessage = ex.getFacesMessage();
        context.addMessage(null, facesMessage);
        return null;
    }

}
