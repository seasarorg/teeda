package org.seasar.teeda.core.config.faces.assembler;

import javax.faces.context.ExternalContext;

import junit.framework.TestCase;

import org.seasar.teeda.core.config.faces.assembler.impl.DefaultAssembleProvider;
import org.seasar.teeda.core.config.faces.element.FacesConfig;

public class AssemblerAssemblerTest extends TestCase {

    public void testPluggable() throws Exception {
        AssemblerAssembler assembler = new AssemblerAssembler();
        assertTrue(assembler.getProvider() instanceof DefaultAssembleProvider);
        assembler.setAssembleProvider(new DummyAssembleProvider());
        assertTrue(assembler.getProvider() instanceof DummyAssembleProvider);
    }

    private static class DummyAssembleProvider implements AssembleProvider {

        public FactoryAssembler assembleFactories(FacesConfig facesConfig) {
            return null;
        }

        public ApplicationAssembler assembleApplication(FacesConfig facesConfig) {
            return null;
        }

        public ComponentAssembler assembleComponent(FacesConfig facesConfig) {
            return null;
        }

        public ConverterAssembler assembleConverter(FacesConfig facesConfig) {
            return null;
        }

        public ValidatorAssembler assembleValidator(FacesConfig facesConfig) {
            return null;
        }

        public ManagedBeanAssembler assembleManagedBeans(FacesConfig facesConfig) {
            return null;
        }

        public NavigationRuleAssembler assembleNavigationRules(
                FacesConfig facesConfig) {
            return null;
        }

        public RenderKitAssembler assembleRenderKits(FacesConfig facesConfig) {
            return null;
        }

        public LifecycleAssembler assembleLifecycle(FacesConfig facesConfig) {
            return null;
        }

        public void setExternalContext(ExternalContext externalContext) {
        }

        public ExternalContext getExternalContext() {
            return null;
        }

    }
}
