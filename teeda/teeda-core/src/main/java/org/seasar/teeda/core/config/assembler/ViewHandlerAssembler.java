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
package org.seasar.teeda.core.config.assembler;

import javax.faces.application.Application;
import javax.faces.application.ViewHandler;

import org.seasar.teeda.core.util.ClassUtil;

/**
 * @author Shinpei Ohtani
 */
public class ViewHandlerAssembler implements JsfAssembler {

    private String handlerName_;
    
    private Application application_;
    
    public ViewHandlerAssembler(String handlerName, Application application){
        handlerName_ = handlerName;
        application_ = application;
    }
    
    public void assemble() {
        ViewHandler previous = application_.getViewHandler();
        ViewHandler handler = 
            (ViewHandler)ClassUtil.createMarshalInstance(handlerName_, ViewHandler.class, previous);
        application_.setViewHandler(handler);
    }

}
