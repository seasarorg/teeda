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

import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;

import org.seasar.teeda.core.config.assembler.impl.DefaultApplicationAssembler;
import org.seasar.teeda.core.config.assembler.impl.DefaultComponentAssembler;
import org.seasar.teeda.core.config.assembler.impl.DefaultConverterAssembler;
import org.seasar.teeda.core.config.assembler.impl.DefaultFactoriesAssembler;
import org.seasar.teeda.core.config.assembler.impl.DefaultLifecycleAssembler;
import org.seasar.teeda.core.config.assembler.impl.DefaultManagedBeanAssembler;
import org.seasar.teeda.core.config.assembler.impl.DefaultNavigationRuleAssembler;
import org.seasar.teeda.core.config.assembler.impl.DefaultRenderKitAssembler;
import org.seasar.teeda.core.config.assembler.impl.DefaultValidatorAssembler;
import org.seasar.teeda.core.config.element.FacesConfig;

/**
 * @author shot
 * TODO need to refine.
 */
public class AssemblerFactory {

    private static Provider provider_ = new DefaultProvider();

    public static void assembleFactories(FacesConfig facesConfig) {
        getProvider().assembleFactories(facesConfig).assemble();
    }
    
    public static void assembleApplication(FacesConfig facesConfig){
        getProvider().assembleApplication(facesConfig).assemble();
        getProvider().assembleComponent(facesConfig).assemble();
        getProvider().assembleConverter(facesConfig).assemble();
        getProvider().assembleValidator(facesConfig).assemble();
    }
    
    public static void assembleManagedBeans(FacesConfig facesConfig){
        getProvider().assembleManagedBeans(facesConfig).assemble();
    }
    
    public static void assmbleNavigationRules(FacesConfig facesConfig, ExternalContext externalContext){
        NavigationRuleAssembler assembler = getProvider().assembleNavigationRules(facesConfig);
        assembler.setExternalContext(externalContext);
        assembler.assemble();
    }
    
    public static void assembleRenderKits(FacesConfig facesConfig){
        getProvider().assembleRenderKits(facesConfig);
    }
    
    public static void assembleLifecycle(FacesConfig facesConfig, ExternalContext externalContext){
        LifecycleAssembler assembler = getProvider().assembleLifecycle(facesConfig);
        assembler.setExternalContext(externalContext);
        assembler.assemble();
    }
    
    public static void setProvider(Provider provider){
        provider_ = provider;
    }
    
    public static Provider getProvider(){
        return provider_;
    }
    
    public interface Provider {
        
        public FactoriesAssembler assembleFactories(FacesConfig facesConfig);

        public ApplicationAssembler assembleApplication(FacesConfig facesConfig);
        
        public ComponentAssembler assembleComponent(FacesConfig facesConfig);
        
        public ConverterAssembler assembleConverter(FacesConfig facesConfig);
        
        public ValidatorAssembler assembleValidator(FacesConfig facesConfig);
        
        public ManagedBeanAssembler assembleManagedBeans(FacesConfig facesConfig);

        public NavigationRuleAssembler assembleNavigationRules(FacesConfig facesConfig);

        public RenderKitAssembler assembleRenderKits(FacesConfig facesConfig);

        public LifecycleAssembler assembleLifecycle(FacesConfig facesConfig);

    }

    public static class DefaultProvider implements Provider{
                
        public DefaultProvider(){
        }
        
        public FactoriesAssembler assembleFactories(FacesConfig facesConfig) {
            List factories = facesConfig.getFactoryElements();
            return new DefaultFactoriesAssembler(factories);
        }

        public ApplicationAssembler assembleApplication(FacesConfig facesConfig) {
            List applications = facesConfig.getApplicationElements();
            return new DefaultApplicationAssembler(applications);
        }

        public ComponentAssembler assembleComponent(FacesConfig facesConfig) {
            Map components = facesConfig.getComponentElements();
            return new DefaultComponentAssembler(components);
        }

        public ConverterAssembler assembleConverter(FacesConfig facesConfig){
            Map convertersByClass = facesConfig.getConverterElementsByClass();
            Map convertersById = facesConfig.getConverterElementsById();
            return new DefaultConverterAssembler(convertersByClass, convertersById);
        }
        
        public ValidatorAssembler assembleValidator(FacesConfig facesConfig){
            Map validators = facesConfig.getValidatorElements();
            return new DefaultValidatorAssembler(validators);
        }
        
        public ManagedBeanAssembler assembleManagedBeans(FacesConfig facesConfig) {
            Map managedBeans = facesConfig.getManagedBeanElements();
            return new DefaultManagedBeanAssembler(managedBeans);
        }

        public NavigationRuleAssembler assembleNavigationRules(FacesConfig facesConfig) {
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
}