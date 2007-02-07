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

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.internal.scope.RedirectScope;

import org.seasar.teeda.core.exception.AlreadyRedirectingException;

public class NavigationHandlerUtil {

    private NavigationHandlerUtil() {
    }

    public static void handleNavigation(FacesContext context,
            String fromAction, String outCome) {
        NavigationHandler handler = context.getApplication()
                .getNavigationHandler();
        handler.handleNavigation(context, fromAction, outCome);
    }

    public static void handleNoNavigation(FacesContext context) {
        NavigationHandler handler = context.getApplication()
                .getNavigationHandler();
        handler.handleNavigation(context, null, null);
    }

    public static void redirect(FacesContext context, String path) {
        RedirectScope.setRedirectingPath(context, path);
        ExternalContext externalContext = context.getExternalContext();
        try {
            externalContext.redirect(externalContext.encodeActionURL(path));
        } catch (IOException e) {
            throw new FacesException(e.getMessage(), e);
        }
        context.responseComplete();
    }

    //For S2JSF redirect, we do not need this check.
    public static void assertNotAlreadyRedirect(FacesContext context) {
        if (RedirectScope.isRedirecting(context)) {
            throw new AlreadyRedirectingException();
        }
    }
}
