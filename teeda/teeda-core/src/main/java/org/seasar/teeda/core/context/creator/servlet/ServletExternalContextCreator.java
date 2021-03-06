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
package org.seasar.teeda.core.context.creator.servlet;

import javax.faces.context.ExternalContext;
import javax.faces.internal.EncodeUrlCustomizer;
import javax.faces.internal.WindowIdEncodeUrlCustomizer;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.seasar.teeda.core.context.creator.ExternalContextCreator;
import org.seasar.teeda.core.context.servlet.ServletExternalContextImpl;
import org.seasar.teeda.core.util.DIContainerUtil;

/**
 * @author shot
 *
 */
public class ServletExternalContextCreator implements ExternalContextCreator {

    public ExternalContext create(Object context, Object request,
            Object response) {
        if (!ServletEnvironmentUtil.isServletEnvironment(context, request,
                response)) {
            return null;
        }
        return createExternalContext((ServletContext) context,
                (ServletRequest) request, (ServletResponse) response);
    }

    protected ExternalContext createExternalContext(ServletContext context,
            ServletRequest request, ServletResponse response) {
        ServletExternalContextImpl externalContext = new ServletExternalContextImpl(
                context, request, response);
        EncodeUrlCustomizer customizer = (EncodeUrlCustomizer) DIContainerUtil
                .getComponentNoException(EncodeUrlCustomizer.class);
        if (customizer == null) {
            customizer = new WindowIdEncodeUrlCustomizer();
        }
        externalContext.setEncodeUrlCustomizer(customizer);
        return externalContext;
    }

}
