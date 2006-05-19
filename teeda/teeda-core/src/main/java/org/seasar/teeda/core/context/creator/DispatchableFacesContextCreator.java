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
package org.seasar.teeda.core.context.creator;

import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;

import org.seasar.teeda.core.context.creator.servlet.ServletEnvironmentUtil;
import org.seasar.teeda.core.context.creator.servlet.ServletFacesContextCreator;

/**
 * @author shot
 * 
 */
public class DispatchableFacesContextCreator implements FacesContextCreator {
    
    //TODO dispatch to applicable creator
    public FacesContext create(Object context, Object request, Object response,
            Lifecycle lifecycle) {
        if(ServletEnvironmentUtil.isServletEnvironment(context, request, response)) {
            return new ServletFacesContextCreator().create(context, request, response, lifecycle);
        }
        //TODO add Portlet environment case
        return null;
    }

}
