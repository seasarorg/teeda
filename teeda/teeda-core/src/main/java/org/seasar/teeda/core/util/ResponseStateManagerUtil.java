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

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.internal.FactoryFinderUtil;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.ResponseStateManager;

/**
 * @author shot
 */
public class ResponseStateManagerUtil {

    private ResponseStateManagerUtil() {
    }

    public static ResponseStateManager getResponseStateManager(
            FacesContext context) {
        UIViewRoot viewRoot = context.getViewRoot();
        String renderKitId = viewRoot.getRenderKitId();
        return getResponseStateManager(context, renderKitId);
    }

    public static ResponseStateManager getResponseStateManager(
            FacesContext context, String renderKitId) {
        RenderKitFactory factory = FactoryFinderUtil.getRenderKitFactory();
        RenderKit renderKit = factory.getRenderKit(context, renderKitId);
        return renderKit.getResponseStateManager();
    }
}
