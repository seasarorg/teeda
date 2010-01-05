/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import java.util.Locale;

import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 * 
 */
public class FacesContextUtil {

    private FacesContextUtil() {
    }

    public static Locale getLocale() {
        return getLocale(FacesContext.getCurrentInstance());
    }

    public static Locale getLocale(FacesContext context) {
        return context.getViewRoot().getLocale();
    }

    public static ViewHandler getViewHandler() {
        return getViewHandler(FacesContext.getCurrentInstance());
    }

    public static ViewHandler getViewHandler(FacesContext context) {
        AssertionUtil.assertNotNull("FacesContext", context);
        return context.getApplication().getViewHandler();
    }

    public static StateManager getStateManager() {
        return getStateManager(FacesContext.getCurrentInstance());
    }

    public static StateManager getStateManager(FacesContext context) {
        AssertionUtil.assertNotNull("FacesContext", context);
        return context.getApplication().getStateManager();
    }
}
