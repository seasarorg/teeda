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
package org.seasar.teeda.extension.component.html;

import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.internal.scope.RedirectScope;

import org.seasar.teeda.core.util.NavigationHandlerUtil;
import org.seasar.teeda.extension.util.TransactionTokenUtil;

/**
 * @author higa
 *
 */
public class THtmlCommandButton extends HtmlCommandButton {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlCommandButton";

    public static final String DEFAULT_RENDERERTYPE = "org.seasar.teeda.extension.HtmlCommandButton";

    public void broadcast(FacesEvent event) throws AbortProcessingException {
        if (TransactionTokenUtil.isDoOnce(getId())) {
            FacesContext context = FacesContext.getCurrentInstance();
            if (TransactionTokenUtil.verify(context)) {
                super.broadcast(event);
            } else {
                String redirectingPath = RedirectScope
                        .getRedirectingPath(context);
                if (redirectingPath != null) {
                    NavigationHandlerUtil.assertNotAlreadyRedirect(context);
                    NavigationHandlerUtil.redirect(context, redirectingPath);
                }
                context.renderResponse();
            }
            return;
        }
        super.broadcast(event);
    }
}
