/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import java.util.List;
import java.util.Map;

import org.seasar.teeda.core.config.faces.assembler.ApplicationAssembler;
import org.seasar.teeda.core.config.faces.assembler.AssembleProvider;
import org.seasar.teeda.core.config.faces.assembler.ComponentAssembler;
import org.seasar.teeda.core.config.faces.assembler.ConverterAssembler;
import org.seasar.teeda.core.config.faces.assembler.FactoryAssembler;
import org.seasar.teeda.core.config.faces.assembler.LifecycleAssembler;
import org.seasar.teeda.core.config.faces.assembler.ManagedBeanAssembler;
import org.seasar.teeda.core.config.faces.assembler.NavigationRuleAssembler;
import org.seasar.teeda.core.config.faces.assembler.RenderKitAssembler;
import org.seasar.teeda.core.config.faces.assembler.ValidatorAssembler;
import org.seasar.teeda.core.config.faces.element.FacesConfig;

/**
 * @author shot
 */
public class DefaultAssembleProvider implements AssembleProvider {

    public FactoryAssembler assembleFactories(FacesConfig facesConfig) {
        List factories = facesConfig.getFactoryElements();
        return new DefaultFactoryAssembler(factories);
    }

    public ApplicationAssembler assembleApplication(FacesConfig facesConfig) {
        List applications = facesConfig.getApplicationElements();
        return new DefaultApplicationAssembler(applications);
    }

    public ComponentAssembler assembleComponent(FacesConfig facesConfig) {
        Map components = facesConfig.getComponentElements();
        return new DefaultComponentAssembler(components);
    }

    public ConverterAssembler assembleConverter(FacesConfig facesConfig) {
        Map convertersByClass = facesConfig.getConverterElementsByClass();
        Map convertersById = facesConfig.getConverterElementsById();
        return new DefaultConverterAssembler(convertersByClass, convertersById);
    }

    public ValidatorAssembler assembleValidator(FacesConfig facesConfig) {
        Map validators = facesConfig.getValidatorElements();
        return new DefaultValidatorAssembler(validators);
    }

    public ManagedBeanAssembler assembleManagedBeans(FacesConfig facesConfig) {
        Map managedBeans = facesConfig.getManagedBeanElements();
        return new DefaultManagedBeanAssembler(managedBeans);
    }

    public NavigationRuleAssembler assembleNavigationRules(
            FacesConfig facesConfig) {
        List navigationRules = facesConfig.getNavigationRuleElements();
        return new DefaultNavigationRuleAssembler(navigationRules);
    }

    public RenderKitAssembler assembleRenderKits(FacesConfig facesConfig) {
        Map renderKits = facesConfig.getRenderKitElements();
        return new DefaultRenderKitAssembler(renderKits);
    }

    public LifecycleAssembler assembleLifecycle(FacesConfig facesConfig) {
        List lifecycles = facesConfig.getLifecycleElements();
        return new DefaultLifecycleAssembler(lifecycles);
    }
}
