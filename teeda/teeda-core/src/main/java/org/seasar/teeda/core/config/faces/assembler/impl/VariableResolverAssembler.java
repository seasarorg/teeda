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
package org.seasar.teeda.core.config.faces.assembler.impl;

import javax.faces.application.Application;
import javax.faces.el.VariableResolver;

import org.seasar.teeda.core.config.faces.assembler.ApplicationChildAssembler;

/**
 * @author shot
 */
public class VariableResolverAssembler extends ApplicationChildAssembler {

    public VariableResolverAssembler(String resolverName, Application application){
        super(resolverName, application);
    }
    
    public void assemble() {
        VariableResolver previous = getApplication().getVariableResolver();
        VariableResolver resolver = 
            (VariableResolver)createMarshalInstance(VariableResolver.class, previous);
        getApplication().setVariableResolver(resolver);
    }

}
