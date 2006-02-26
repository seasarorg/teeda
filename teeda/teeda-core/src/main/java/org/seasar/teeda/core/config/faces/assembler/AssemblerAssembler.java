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
package org.seasar.teeda.core.config.faces.assembler;

import org.seasar.teeda.core.config.faces.element.FacesConfig;

/**
 * @author shot
 */
public class AssemblerAssembler {

    private AssembleProvider provider_;

    public void assembleFactories(FacesConfig facesConfig) {
        getProvider().assembleFactories(facesConfig).assemble();
    }
    
    public void assembleApplication(FacesConfig facesConfig){
        getProvider().assembleApplication(facesConfig).assemble();
        getProvider().assembleComponent(facesConfig).assemble();
        getProvider().assembleConverter(facesConfig).assemble();
        getProvider().assembleValidator(facesConfig).assemble();
    }
    
    public void assembleManagedBeans(FacesConfig facesConfig){
        getProvider().assembleManagedBeans(facesConfig).assemble();
    }
    
    public void assmbleNavigationRules(FacesConfig facesConfig){
        getProvider().assembleNavigationRules(facesConfig).assemble();
    }
    
    public void assembleRenderKits(FacesConfig facesConfig){
        getProvider().assembleRenderKits(facesConfig).assemble();
    }
    
    public void assembleLifecycle(FacesConfig facesConfig){
        getProvider().assembleLifecycle(facesConfig).assemble();
    }
    
    public void setAssembleProvider(AssembleProvider provider){
        provider_ = provider;
    }
    
    public AssembleProvider getProvider(){
        return provider_;
    }
        
}