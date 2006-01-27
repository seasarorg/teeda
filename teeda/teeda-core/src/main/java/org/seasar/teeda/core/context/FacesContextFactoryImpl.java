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
package org.seasar.teeda.core.context;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.seasar.teeda.core.context.servlet.ServletExternalContextImpl;
import org.seasar.teeda.core.context.servlet.ServletFacesContextImpl;
import org.seasar.teeda.core.exception.FacesContextNotFoundRuntimeException;

/**
 * @author shot
 */
public class FacesContextFactoryImpl extends FacesContextFactory {

    public FacesContextFactoryImpl() {
    }

    public FacesContext getFacesContext(Object context, Object request,
            Object response, Lifecycle lifecycle) throws FacesException {
        if (context == null || request == null || response == null
                || lifecycle == null) {
            throw new NullPointerException(
                    "Any of parameters are null context = " + context
                            + ", request = " + request + ", response = "
                            + response + ", lifecycle = " + lifecycle);
        }
        if (isServletEnvironment(context, request, response)) {
            ExternalContext externalContext = new ServletExternalContextImpl(
                    (ServletContext) context, (ServletRequest) request,
                    (ServletResponse) response);
            return new ServletFacesContextImpl(externalContext);
        }
        throw new FacesContextNotFoundRuntimeException();
    }

    private static boolean isServletEnvironment(Object context, Object request,
            Object response) {
        return (context instanceof ServletContext)
                && (request instanceof ServletRequest)
                && (response instanceof ServletResponse);
    }
}
