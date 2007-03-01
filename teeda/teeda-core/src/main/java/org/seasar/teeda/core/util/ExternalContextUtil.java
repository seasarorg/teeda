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

import javax.faces.application.ViewHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.portlet.FacesPortlet;

/**
 * @author higa
 * @author manhole
 */
public class ExternalContextUtil {

    private ExternalContextUtil() {
    }

    public static String getViewId(ExternalContext externalContext) {
        // PortletSupport
        if (PortletUtil.isPortlet(FacesContext.getCurrentInstance())) {
            final PortletRequest request = (PortletRequest) externalContext
                    .getRequest();
            String viewId = (String) request.getAttribute(FacesPortlet.VIEW_ID);
            if (viewId == null) {
                viewId = request.getParameter(FacesPortlet.VIEW_ID);
            }
            return viewId;
        } else {
            String viewId = externalContext.getRequestPathInfo();
            if (viewId == null) {
                viewId = externalContext.getRequestServletPath();
                final int dot = viewId.lastIndexOf('.');
                if (dot >= 0) {
                    final String suffix = getSuffix(externalContext);
                    viewId = viewId.substring(0, dot) + suffix;
                }
            }
            return viewId;
        }
    }

    private static String getSuffix(ExternalContext externalContext) {
        final String defaultSuffix = externalContext
                .getInitParameter(ViewHandler.DEFAULT_SUFFIX_PARAM_NAME);
        if (defaultSuffix != null) {
            return defaultSuffix;
        }
        return ViewHandler.DEFAULT_SUFFIX;
    }

    public static String encodeActionURL(FacesContext context, String url) {
        AssertionUtil.assertNotNull("FacesContext", context);
        return context.getExternalContext().encodeActionURL(url);
    }
}
