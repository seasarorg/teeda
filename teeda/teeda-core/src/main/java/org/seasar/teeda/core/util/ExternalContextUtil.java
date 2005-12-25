/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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

/**
 * @author higa
 *  
 */
public class ExternalContextUtil {

    private ExternalContextUtil() {
    }

    public static String getViewId(ExternalContext externalContext) {
        String viewId = externalContext.getRequestPathInfo();
        if (viewId == null) {
            viewId = externalContext.getRequestServletPath();
            String defaultSuffix = externalContext
                    .getInitParameter(ViewHandler.DEFAULT_SUFFIX_PARAM_NAME);
            String suffix = defaultSuffix != null ? defaultSuffix
                    : ViewHandler.DEFAULT_SUFFIX;
            int dot = viewId.lastIndexOf('.');
            if (dot >= 0) {
                viewId = viewId.substring(0, dot) + suffix;
            }
        }
        return viewId;
    }
}