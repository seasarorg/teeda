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

import org.seasar.teeda.core.util.ClassUtil;

/**
 * @author shot
 */
public abstract class ApplicationChildAssembler implements JsfAssembler {

    private Application application_;
    private String targetName_;
    public ApplicationChildAssembler(String targetName, Application application){
        if(targetName == null || targetName.equals("")){
            throw new IllegalArgumentException("targetName");
        }
        if(application == null){
            throw new IllegalArgumentException("application");
        }
        application_ = application;
        targetName_ = targetName;
    }

    public ApplicationChildAssembler(Application application){
        this(null, application);
    }

    protected Object createMarshalInstance(Class clazz, Object previous){
        return ClassUtil.createMarshalInstance(targetName_, clazz, previous);
    }
    
    protected final Application getApplication(){
        return application_;
    }

    protected final String getTargetName(){
        return targetName_;
    }
}
