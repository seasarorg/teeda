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

import javax.faces.context.FacesContext;
import javax.faces.internal.FacesConfigOptions;

/**
 * @author shot
 * 
 */
public class JavaScriptPermissionUtil {

    public static final boolean JAVASCRIPT_DEFAULT_ALLOW = true;

    private JavaScriptPermissionUtil() {
    }

    public static boolean isJavaScriptPermitted(FacesContext context) {
        final String requestServletPath = context.getExternalContext()
                .getRequestPathInfo();
        final String[] javascriptNotAllowedPath = FacesConfigOptions
                .getJavascriptNotPermittedPath();
        boolean javaScriptAllowed = JAVASCRIPT_DEFAULT_ALLOW;
        if (javascriptNotAllowedPath == null) {
            return javaScriptAllowed;
        }
        for (int i = 0; i < javascriptNotAllowedPath.length; i++) {
            String notAllowedPath = adjustNotAllowedPath(javascriptNotAllowedPath[i]);
            if (requestServletPath != null
                    && requestServletPath.startsWith(notAllowedPath)
                    || (requestServletPath == null && notAllowedPath.equals("/"))) {
                javaScriptAllowed = false;
                break;
            }
        }
        return javaScriptAllowed;
    }

    private static String adjustNotAllowedPath(String path) {
        if (path == null) {
            return null;
        }
        String notAllowedPath = path.trim();
        if (!notAllowedPath.startsWith("/")) {
            notAllowedPath = "/" + notAllowedPath;
        }
        if (!notAllowedPath.endsWith("/")) {
            notAllowedPath = notAllowedPath + "/";
        }
        return notAllowedPath;
    }
}
