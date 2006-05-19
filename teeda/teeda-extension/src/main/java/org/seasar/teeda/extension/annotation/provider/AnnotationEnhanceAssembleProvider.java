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
package org.seasar.teeda.extension.annotation.provider;

import javax.faces.context.ExternalContext;

import org.seasar.teeda.core.config.faces.assembler.ManagedBeanAssembler;
import org.seasar.teeda.core.config.faces.assembler.impl.DefaultAssembleProvider;
import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.extension.annotation.ValidatorAnnotationHandler;
import org.seasar.teeda.extension.annotation.assembler.AnnotationEnhanceManagedBeanAssembler;

/**
 * @author shot
 */
public class AnnotationEnhanceAssembleProvider extends DefaultAssembleProvider {

    private ValidatorAnnotationHandler annotationHandler;

    public AnnotationEnhanceAssembleProvider() {
        ExternalContext extContext = (ExternalContext) DIContainerUtil
                .getComponentNoException(ExternalContext.class);
        setExternalContext(extContext);
    }

    public ManagedBeanAssembler assembleManagedBeans(FacesConfig facesConfig) {
        AnnotationEnhanceManagedBeanAssembler assembler = new AnnotationEnhanceManagedBeanAssembler(
                facesConfig.getManagedBeanElements());
        assembler.setAnnotationHandler(annotationHandler);
        return assembler;
    }

    public void setValidatorAnnotationHandler(
            ValidatorAnnotationHandler annotationHandler) {
        this.annotationHandler = annotationHandler;
    }
    
    public ValidatorAnnotationHandler getValidatorAnnotationHandler() {
        return annotationHandler;
    }
}
