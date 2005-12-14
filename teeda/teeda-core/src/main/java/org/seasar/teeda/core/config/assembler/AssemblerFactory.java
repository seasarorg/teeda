package org.seasar.teeda.core.config.assembler;

import java.util.List;
import java.util.Map;

import org.seasar.teeda.core.config.element.FacesConfig;


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
        getProvider().assembleManagedBeans(facesConfig);
    }
    
    public static void assmbleNavigationRules(FacesConfig facesConfig){
        getProvider().assembleNavigationRules(facesConfig);
    }
    
    public static void assembleRenderKits(FacesConfig facesConfig){
        getProvider().assembleRenderKits(facesConfig);
    }
    
    public static void assembleLifecycle(FacesConfig facesConfig){
        getProvider().assembleLifecycle(facesConfig);
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
        
        public ComponentsAssembler assembleComponent(FacesConfig facesConfig);
        
        public ConvertersAssembler assembleConverter(FacesConfig facesConfig);
        
        public ValidatorsAssembler assembleValidator(FacesConfig facesConfig);
        
        public ManagedBeanAssembler assembleManagedBeans(FacesConfig facesConfig);

        public NavigationRulesAssembler assembleNavigationRules(FacesConfig facesConfig);

        public RenderKitsAssembler assembleRenderKits(FacesConfig facesConfig);

        public LifecycleAssembler assembleLifecycle(FacesConfig facesConfig);

    }

    public static class DefaultProvider implements Provider{
                
        public DefaultProvider(){
        }
        
        public FactoriesAssembler assembleFactories(FacesConfig facesConfig) {
            List factories = facesConfig.getFactoryElements();
            return new SimpleFactoriesAssembler(factories);
        }

        public ApplicationAssembler assembleApplication(FacesConfig facesConfig) {
            List applications = facesConfig.getApplicationElements();
            return new SimpleApplicationAssembler(applications);
        }

        public ComponentsAssembler assembleComponent(FacesConfig facesConfig) {
            Map components = facesConfig.getComponentElements();
            return new SimpleComponentsAssembler(components);
        }

        public ConvertersAssembler assembleConverter(FacesConfig facesConfig){
            Map convertersByClass = facesConfig.getConverterElementsByClass();
            Map convertersById = facesConfig.getConverterElementsById();
            return new SimpleConvertersAssembler(convertersByClass, convertersById);
        }
        
        public ValidatorsAssembler assembleValidator(FacesConfig facesConfig){
            Map validators = facesConfig.getValidatorElements();
            return new SimpleValidatorsAssembler(validators);
        }
        
        public ManagedBeanAssembler assembleManagedBeans(FacesConfig facesConfig) {
        }

        public NavigationRulesAssembler assembleNavigationRules(FacesConfig facesConfig) {
        }

        public RenderKitsAssembler assembleRenderKits(FacesConfig facesConfig) {
            Map renderKits = facesConfig.getRenderKitElements();
            return new SimpleRenderKitsAssembler(renderKits);
        }

        public LifecycleAssembler assembleLifecycle(FacesConfig facesConfig) {
            List lifecycles = facesConfig.getLifecycleElements();
            return new SimpleLifecycleAssembler(lifecycles);
        }
    }
}