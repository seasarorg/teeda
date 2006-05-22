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
package org.seasar.teeda.extension.html.impl;

import java.io.IOException;

import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.teeda.core.application.ViewHandlerImpl;
import org.seasar.teeda.core.util.ExternalContextUtil;
import org.seasar.teeda.extension.html.ViewRenderer;

/**
 * @author higa
 *
 */
public class HtmlViewHandler extends ViewHandlerImpl {

    private ViewRenderer viewRenderer;

    public void setViewRenderer(ViewRenderer viewRenderer) {
        this.viewRenderer = viewRenderer;
    }

    public void renderView(FacesContext context, UIViewRoot viewRoot)
            throws IOException {

        ExternalContext externalContext = context.getExternalContext();
        String path = ExternalContextUtil.getViewId(externalContext);
        if (path.equals(viewRoot.getViewId())) {
            HttpServletRequest request = (HttpServletRequest) externalContext
                    .getRequest();
            HttpServletResponse response = (HttpServletResponse) externalContext
                    .getResponse();
            viewRenderer.renderView(path, request, response);
        } else {
            externalContext.dispatch(viewRoot.getViewId());
        }
    }
}