/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.internal.scope.RedirectScope;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.JsfConstants;

/**
 * @author shinsuke
 * 
 */
public class PortletErrorPageManagerImpl extends ServletErrorPageManagerImpl {

    /*
     * (non-Javadoc)
     * 
     * @see org.seasar.teeda.core.util.ServletErrorPageManagerImpl#handleException(java.lang.Throwable,
     *      javax.faces.context.FacesContext,
     *      javax.faces.context.ExternalContext)
     */
    public boolean handleException(Throwable exception, FacesContext context,
            ExternalContext extContext) throws IOException {
        if (PortletUtil.isPortlet(context)) {
            String location = getLocation(exception.getClass());
            if (location == null) {
                return false;
            }
            storeErrorInfoToAttribute(extContext, exception);
            RedirectScope.setRedirectingPath(context, location);
            return true;
        } else {
            return super.handleException(exception, context, extContext);
        }
    }

    protected void storeErrorInfoToAttribute(ExternalContext extContext,
            Throwable exception) {
        AssertionUtil.assertNotNull("extContext", extContext);
        AssertionUtil.assertNotNull("exception", exception);
        extContext.getSessionMap().put(JsfConstants.ERROR_EXCEPTION, exception);
    }

}
