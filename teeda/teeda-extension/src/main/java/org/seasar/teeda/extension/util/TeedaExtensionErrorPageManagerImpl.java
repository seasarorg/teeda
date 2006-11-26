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
package org.seasar.teeda.extension.util;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.core.util.ServletErrorPageManagerImpl;
import org.seasar.teeda.core.util.ServletExternalContextUtil;
import org.seasar.teeda.extension.html.PagePersistence;

/**
 * @author shot
 */
public class TeedaExtensionErrorPageManagerImpl extends
        ServletErrorPageManagerImpl {

    //TODO injection PagePersistence.
    //TODO PortletSupport
    public boolean handleException(Throwable exception,
            ExternalContext extContext) throws IOException {
        AssertionUtil.assertNotNull("exception", exception);
        final String message = exception.getMessage();
        final FacesContext context = FacesContext.getCurrentInstance();
        final FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                message, message);
        context.addMessage(null, fm);
        String location = getLocation(exception.getClass());
        if (location == null) {
            return false;
        }
        ServletRequest request = ServletExternalContextUtil
                .getRequest(extContext);
        if (request.getAttribute(JsfConstants.ERROR_EXCEPTION) != null) {
            setErrorPageAttributesToServletError(request);
            HttpServletResponse response = ServletExternalContextUtil
                    .getResponse(extContext);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return true;
        }
        ServletExternalContextUtil.storeErrorInfoToAttribute(request, exception);
        PagePersistence pagePersistence = (PagePersistence) DIContainerUtil
                .getComponentNoException(PagePersistence.class);
        pagePersistence.removeSubApplicationPages(context);
        pagePersistence.save(context, location);
        try {
            final String actionURL;
            if (location != null && location.startsWith("/")) {
                actionURL = extContext.getRequestContextPath() + location;
            } else {
                actionURL = location;
            }
            extContext.redirect(extContext.encodeActionURL(actionURL));
        } catch (IOException e) {
            throw new FacesException(e.getMessage(), e);
        }
        return true;
    }

}
