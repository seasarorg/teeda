/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;

import org.seasar.framework.log.Logger;

/**
 * @author higa
 * @author shot
 */
public class ServletErrorPageManagerImpl implements ErrorPageManager {

    private static final Logger logger = Logger
            .getLogger(ServletErrorPageManagerImpl.class);

    protected Map locations = new HashMap();

    public void addErrorPage(Class exceptionType, String location) {
        locations.put(exceptionType, location);
    }

    public boolean handleException(Throwable exception, FacesContext context,
            ExternalContext extContext) throws IOException {
        if (logger.isDebugEnabled()) {
            logger.log("ETDA0001", new Object[0], exception);
        }
        String location = getLocation(exception.getClass());
        if (location == null) {
            return false;
        }
        ServletRequest request = ServletExternalContextUtil
                .getRequest(extContext);
        ServletExternalContextUtil
                .storeErrorInfoToAttribute(request, exception);
        extContext.dispatch(location);
        return true;
    }

    protected String getLocation(Class exceptionType) {
        Class clazz = exceptionType;
        String location = (String) locations.get(clazz);
        while (location == null && !clazz.equals(Throwable.class)) {
            clazz = clazz.getSuperclass();
            location = (String) locations.get(clazz);
        }
        return location;
    }
}
