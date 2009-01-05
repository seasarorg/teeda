/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
