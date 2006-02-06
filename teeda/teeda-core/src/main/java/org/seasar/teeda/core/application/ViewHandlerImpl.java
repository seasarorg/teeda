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
package org.seasar.teeda.core.application;

import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKitFactory;

import org.seasar.framework.util.StringUtil;

/**
 * @author shot
 */
public class ViewHandlerImpl extends ViewHandler {

    //TODO testing
    public ViewHandlerImpl() {
    }

    public Locale calculateLocale(FacesContext context) {
        if (context == null) {
            throw new NullPointerException("context");
        }
        Locale supportedLocale = getLocaleFromSupportedLocales(context);
        if(supportedLocale != null) {
            return supportedLocale;
        }
        Locale defaultLocale = getLocaleFromDefaultLocale(context);
        if(defaultLocale != null) {
            return defaultLocale;
        }
        return Locale.getDefault();
    }

    protected Locale getLocaleFromSupportedLocales(FacesContext context) {
        Application app = context.getApplication();
        for (Iterator locales = context.getExternalContext()
                .getRequestLocales(); locales.hasNext();) {
            Locale locale = (Locale) locales.next();
            for (Iterator supportedLocales = app.getSupportedLocales(); supportedLocales
                    .hasNext();) {
                Locale supportedLocale = (Locale) supportedLocales.next();
                if (isMatchLocale(locale, supportedLocale)) {
                    return supportedLocale;
                }
            }
        }
        return null;
    }
    
    protected Locale getLocaleFromDefaultLocale(FacesContext context) {
        Locale defaultLocale = context.getApplication().getDefaultLocale();
        for (Iterator locales = context.getExternalContext()
                .getRequestLocales(); locales.hasNext();) {
            Locale reqLocale = (Locale) locales.next();
            if (isMatchLocale(reqLocale, defaultLocale)) {
                return defaultLocale;
            }
        }
        return null;
    }
    
    protected boolean isMatchLocale(Locale reqLocale, Locale jsfLocale) {
        if (reqLocale.equals(jsfLocale)) {
            return true;
        }
        return reqLocale.getLanguage().equals(jsfLocale.getLanguage())
                && StringUtil.isEmpty(jsfLocale.getCountry());
    }

    public String calculateRenderKitId(FacesContext context) {
        if (context == null) {
            throw new NullPointerException("context");
        }
        String renderKitId = context.getApplication().getDefaultRenderKitId();
        if(renderKitId == null) {
            renderKitId = RenderKitFactory.HTML_BASIC_RENDER_KIT;
        }
        return renderKitId;
    }

    public UIViewRoot createView(FacesContext context, String viewId) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getActionURL(FacesContext context, String viewId) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getResourceURL(FacesContext context, String path) {
        // TODO Auto-generated method stub
        return null;
    }

    public void renderView(FacesContext context, UIViewRoot viewToRender)
            throws IOException, FacesException {
        // TODO Auto-generated method stub

    }

    public UIViewRoot restoreView(FacesContext context, String viewId) {
        if (context == null) {
            throw new NullPointerException("context");
        }
        Application app = context.getApplication();
        String renderKitId = calculateRenderKitId(context);
        StateManager stateManager = app.getStateManager(); 
        return stateManager.restoreView(context, viewId, renderKitId);
    }

    public void writeState(FacesContext context) throws IOException {
        // TODO Auto-generated method stub

    }

}
