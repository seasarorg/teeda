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
package org.seasar.teeda.core;

/**
 * @author shot
 */
public interface TODO {
    /*
     * TODO for 1.0
     *  - Make JSF init-code complete. 
     *  - StateManager 
     *  - Renderers(Last 1! CommandLink)
     *  - Test, test, test
     *    -> JSF Specification part(UIComponent things!)
     *    -> Teeda original part
     *  - Make TeedaTestCase better, such as....
     *    -> separate JSF-specified part and Teeda part.
     *       (JSFTestCase and TeedaTestCase?)
     *  - AbstractPhase.initializeChildren should be modified to use EditableValueHolder
     *  - Test LifecycleImpl and Phase
     *  - Use UIComponent's label attribute (if exist) for Conversion, Validation Error Message.
     *  - Support list-entries, map-entries to ManagedBean.
     *    (Need to modify ManagedBeanFactoryImpl, and ManagedBeanAssembler) 
     *  - Configure core-faces-config.xml for default setting.
     *  - MetaInfFacesConfigurator -> Support multiple jar file.
     *  - Add DI Support for Converter, Validator, UIComponent, Renderer
     *  - impl StateManager
     *  - impl taglib core
     *  - impl taglib html
     *  - [Seasar-user:3230]
     *  - [#7682] http://sourceforge.jp/tracker/index.php?func=detail&aid=7682&group_id=689&atid=6584
     *  - [#7975] [Seasar-user:3127] http://sourceforge.jp/tracker/index.php?func=detail&aid=7975&group_id=689&atid=6584
     *  - [#7974] [Seasar-user:3080] http://sourceforge.jp/tracker/index.php?func=detail&aid=7974&group_id=689&atid=6584
     *  - [#7840] [Seasar-user:3083] http://sourceforge.jp/tracker/index.php?func=detail&aid=7840&group_id=689&atid=6584
     *  - [Seasar-user:3253]
     *  - Continuum
     *  - To know navigation (from and outcome, and go to where), provide interceptor 
     *  
     * 
     * TODO for 1.1
     *  - 
     * 
     * DONE
     *  - Add copyright to all sources.
     *  - impl ResponseStateManager
     *  - impl RenderKitFactoryImpl
     *  - impl HtmlRenderKitImpl
     *  - use Find bugs
     *  - Impl NavigationHandler
     *  - Impl ViewHandler
     *  - Make Assemblers done.
     *  - change S2ScopeTranslator for Application scope.
     *  - use Clover or cobertura?
     * 
     * PEND
     *  - Selenium integration
     *  - make it easy to get elements from FacesConfig by key(such as component, converter,
     *    validator, renderkit, and managed-bean)
     *  - Client side solution like commons-validator or aspect interceptor or what
     *  - Portlet support(need for specification)
     * 
     * 
     * 
     * 
     */
}
