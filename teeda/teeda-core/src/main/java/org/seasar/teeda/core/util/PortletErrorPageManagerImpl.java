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

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.portlet.FacesPortlet;

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
            // TODO redirect might not work correctly.. need to review page handling
            extContext.getRequestMap().put(FacesPortlet.VIEW_ID, location);
            return true;
        } else {
            return super.handleException(exception, context, extContext);
        }
    }

    protected void storeErrorInfoToAttribute(ExternalContext extContext,
            Throwable exception) {
        AssertionUtil.assertNotNull("extContext", extContext);
        AssertionUtil.assertNotNull("exception", exception);
        extContext.getRequestMap().put(JsfConstants.ERROR_EXCEPTION, exception);
        extContext.getRequestMap().put(JsfConstants.ERROR_EXCEPTION_TYPE,
                exception.getClass());
        extContext.getRequestMap().put(JsfConstants.ERROR_MESSAGE,
                exception.getMessage());

    }

}
