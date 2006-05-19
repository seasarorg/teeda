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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seasar.framework.container.ComponentDef;
import org.seasar.teeda.core.config.faces.assembler.ManagedBeanAssembler;
import org.seasar.teeda.core.config.faces.element.ApplicationElement;
import org.seasar.teeda.core.config.faces.element.ComponentElement;
import org.seasar.teeda.core.config.faces.element.ConverterElement;
import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.config.faces.element.FactoryElement;
import org.seasar.teeda.core.config.faces.element.LifecycleElement;
import org.seasar.teeda.core.config.faces.element.ManagedBeanElement;
import org.seasar.teeda.core.config.faces.element.NavigationRuleElement;
import org.seasar.teeda.core.config.faces.element.ReferencedBeanElement;
import org.seasar.teeda.core.config.faces.element.RenderKitElement;
import org.seasar.teeda.core.config.faces.element.ValidatorElement;
import org.seasar.teeda.core.config.faces.element.impl.ManagedBeanElementImpl;
import org.seasar.teeda.core.resource.ValidatorResource;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.annotation.ValidatorAnnotationHandler;
import org.seasar.teeda.extension.annotation.assembler.AnnotationEnhanceManagedBeanAssembler;

/**
 * @author shot
 */
public class AnnotationEnhanceAssembleProviderTest extends TeedaTestCase {

    public void testAssembleManagedBeans() throws Exception {
        AnnotationEnhanceAssembleProvider provider = new AnnotationEnhanceAssembleProvider();
        ValidatorAnnotationHandler handler = new MockValidatorAnnotationHandler();
        provider.setValidatorAnnotationHandler(handler);
        ManagedBeanAssembler assembler = provider
                .assembleManagedBeans(new MockFacesConfig());
        assertNotNull(assembler);
        assertNotNull(provider.getValidatorAnnotationHandler());
        assertNotNull(((AnnotationEnhanceManagedBeanAssembler) assembler)
                .getAnnotationHandler());
        assertTrue(((AnnotationEnhanceManagedBeanAssembler) assembler)
                .getAnnotationHandler() instanceof MockValidatorAnnotationHandler);
    }

    private static class MockFacesConfig implements FacesConfig {

        public void addApplicationElement(ApplicationElement application) {
            // TODO Auto-generated method stub

        }

        public void addFactoryElement(FactoryElement factory) {
            // TODO Auto-generated method stub

        }

        public void addComponentElement(ComponentElement component) {
            // TODO Auto-generated method stub

        }

        public void addConverterElement(ConverterElement converter) {
            // TODO Auto-generated method stub

        }

        public void addManagedBeanElement(ManagedBeanElement managedBean) {
            // TODO Auto-generated method stub

        }

        public void addNavigationRuleElement(
                NavigationRuleElement navigationRule) {
            // TODO Auto-generated method stub

        }

        public void addRenderKitElement(RenderKitElement renderKit) {
            // TODO Auto-generated method stub

        }

        public void addLifecycleElement(LifecycleElement lifecycle) {
            // TODO Auto-generated method stub

        }

        public void addValidatorElement(ValidatorElement validator) {
            // TODO Auto-generated method stub

        }

        public void addReferencedBeanElement(
                ReferencedBeanElement referencedBean) {
            // TODO Auto-generated method stub

        }

        public List getApplicationElements() {
            // TODO Auto-generated method stub
            return null;
        }

        public List getFactoryElements() {
            // TODO Auto-generated method stub
            return null;
        }

        public List getLifecycleElements() {
            // TODO Auto-generated method stub
            return null;
        }

        public Map getComponentElements() {
            // TODO Auto-generated method stub
            return null;
        }

        public Map getConverterElementsById() {
            // TODO Auto-generated method stub
            return null;
        }

        public Map getConverterElementsByClass() {
            // TODO Auto-generated method stub
            return null;
        }

        public Map getManagedBeanElements() {
            ManagedBeanElement mb = new ManagedBeanElementImpl();
            mb.setManagedBeanName("aaa");
            mb.setManagedBeanClass(Hoge.class.getName());
            mb.setManagedBeanScope("request");
            Map map = new HashMap();
            map.put(mb.getManagedBeanName(), mb);
            return map;
        }

        public List getNavigationRuleElements() {
            // TODO Auto-generated method stub
            return null;
        }

        public Map getRenderKitElements() {
            // TODO Auto-generated method stub
            return null;
        }

        public Map getValidatorElements() {
            // TODO Auto-generated method stub
            return null;
        }

        public List getReferencedBeanElements() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    private static class MockValidatorAnnotationHandler implements
            ValidatorAnnotationHandler {

        public void registerValidator(ComponentDef componentDef) {
            // TODO Auto-generated method stub

        }

        public void setValidatorResource(ValidatorResource resources) {
            // TODO Auto-generated method stub

        }

        public ValidatorResource getValidatorResource() {
            // TODO Auto-generated method stub
            return null;
        }

        public void addIgnoreSuffix(String suffix) {
            // TODO Auto-generated method stub

        }

    }

    private static class Hoge {
    }
}
