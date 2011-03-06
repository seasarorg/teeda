/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.config.faces.element;

import java.util.List;
import java.util.Map;

/**
 * @author shot
 */
public interface FacesConfig extends JsfConfigElement {

    public void addApplicationElement(ApplicationElement application);

    public void addFactoryElement(FactoryElement factory);

    public void addComponentElement(ComponentElement component);

    public void addConverterElement(ConverterElement converter);

    public void addManagedBeanElement(ManagedBeanElement managedBean);

    public void addNavigationRuleElement(NavigationRuleElement navigationRule);

    public void addRenderKitElement(RenderKitElement renderKit);

    public void addLifecycleElement(LifecycleElement lifecycle);

    public void addValidatorElement(ValidatorElement validator);

    public void addReferencedBeanElement(ReferencedBeanElement referencedBean);

    public List getApplicationElements();

    public List getFactoryElements();

    public List getLifecycleElements();

    public Map getComponentElements();

    public Map getConverterElementsById();

    public Map getConverterElementsByClass();

    public Map getManagedBeanElements();

    public List getNavigationRuleElements();

    public Map getRenderKitElements();

    public Map getValidatorElements();

    public List getReferencedBeanElements();
}
