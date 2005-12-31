/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.exception.IllegalClassTypeException;

/**
 * @author Shinpei Ohtani
 */
public class ApplicationUtil {

    private ApplicationUtil(){
    }
    
    public static Application getApplicationFromFactory(){
        ApplicationFactory appFactory = FactoryFinderUtil.getApplicationFactory();
        return appFactory.getApplication();
    }
    
    public static Application getApplicationFromContext(){
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication();
    }
    
    public static void verifyClassType(Class expected, Class actual){
        if(!ClassUtil.isAssignableFrom(expected, actual)){
            throw new IllegalClassTypeException(expected, actual);
        }

    }
}
