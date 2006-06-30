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

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.application.NavigationHandlerImpl;
import org.seasar.teeda.extension.html.PagePersistence;

/**
 * @author higa
 * 
 */
public class HtmlNavigationHandler extends NavigationHandlerImpl {

    private PagePersistence pagePersistence;

    public void setPagePersistence(PagePersistence pagePersistence) {
        this.pagePersistence = pagePersistence;
    }

    protected void redirect(FacesContext context, ExternalContext externalContext, String redirectPath, String newViewId) {
        pagePersistence.save(context, newViewId);
        super.redirect(context, externalContext, redirectPath, newViewId);
    }

}
