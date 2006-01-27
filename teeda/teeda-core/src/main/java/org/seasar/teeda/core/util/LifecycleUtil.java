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
package org.seasar.teeda.core.util;

import javax.faces.context.ExternalContext;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.webapp.FacesServlet;

public class LifecycleUtil {

    private LifecycleUtil() {
    }

    public static Lifecycle getLifecycle(ExternalContext externalContext) {
        LifecycleFactory lifecycleFactory = FactoryFinderUtil.getLifecycleFactory();
        Lifecycle lifecycle = 
            lifecycleFactory.getLifecycle(getLifecycleId(externalContext));
        return lifecycle;
    }

    public static String getLifecycleId(ExternalContext externalContext) {
        String lifecycleId = externalContext.getInitParameter(FacesServlet.LIFECYCLE_ID_ATTR);
        if(lifecycleId != null){
            return lifecycleId;
        }
        return LifecycleFactory.DEFAULT_LIFECYCLE;
    }
}
