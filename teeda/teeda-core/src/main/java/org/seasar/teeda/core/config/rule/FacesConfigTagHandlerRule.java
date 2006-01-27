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
package org.seasar.teeda.core.config.rule;

import org.seasar.teeda.core.config.handler.ApplicationTagHandler;
import org.seasar.teeda.core.config.handler.AttributeTagHandler;
import org.seasar.teeda.core.config.handler.ComponentTagHandler;
import org.seasar.teeda.core.config.handler.ConverterTagHandler;
import org.seasar.teeda.core.config.handler.DefaultValueTagHandler;
import org.seasar.teeda.core.config.handler.FacesConfigTagHandler;
import org.seasar.teeda.core.config.handler.FacetTagHandler;
import org.seasar.teeda.core.config.handler.FactoryTagHandler;
import org.seasar.teeda.core.config.handler.LifecycleTagHandler;
import org.seasar.teeda.core.config.handler.ListEntriesTagHandler;
import org.seasar.teeda.core.config.handler.LocaleConfigTagHandler;
import org.seasar.teeda.core.config.handler.ManagedBeanTagHandler;
import org.seasar.teeda.core.config.handler.ManagedPropertyTagHandler;
import org.seasar.teeda.core.config.handler.MapEntriesTagHandler;
import org.seasar.teeda.core.config.handler.MapEntryTagHandler;
import org.seasar.teeda.core.config.handler.NavigationCaseTagHandler;
import org.seasar.teeda.core.config.handler.NavigationRuleTagHandler;
import org.seasar.teeda.core.config.handler.NullValueTagHandler;
import org.seasar.teeda.core.config.handler.PropertyTagHandler;
import org.seasar.teeda.core.config.handler.ReferencedBeanTagHandler;
import org.seasar.teeda.core.config.handler.RenderKitTagHandler;
import org.seasar.teeda.core.config.handler.RendererTagHandler;
import org.seasar.teeda.core.config.handler.SuggestedValueTagHandler;
import org.seasar.teeda.core.config.handler.ValidatorTagHandler;

/**
 * @author Shinpei Ohtani(aka shot)
 * 
 * Rule for faces-config tag handling. 
 */
public class FacesConfigTagHandlerRule extends JsfBaseTagHandlerRule {

    public FacesConfigTagHandlerRule() {
        addTagHandler("/faces-config", new FacesConfigTagHandler());

        // <factory>
        addTagHandler("factory", new FactoryTagHandler());
        addTagHandler("application-factory");
        addTagHandler("faces-context-factory");
        addTagHandler("lifecycle-factory");
        addTagHandler("render-kit-factory");

        // <application>
        addTagHandler("application", new ApplicationTagHandler());
        addTagHandler("action-listener");
        addTagHandler("default-render-kit-id");
        addTagHandler("message-bundle");
        addTagHandler("navigation-handler");
        addTagHandler("view-handler");
        addTagHandler("state-manager");
        addTagHandler("property-resolver");
        addTagHandler("variable-resolver");
        // <locale-config>
        addTagHandler("locale-config", new LocaleConfigTagHandler());
        addTagHandler("default-locale");
        addTagHandler("supported-locale");

        // <lifecycle>
        addTagHandler("lifecycle", new LifecycleTagHandler());
        addTagHandler("phase-listener");

        // <converter>
        // ignore description, display-name
        addTagHandler("converter", new ConverterTagHandler());
        addTagHandler("converter-id");
        addTagHandler("converter-for-class");
        addTagHandler("converter-class");

        // <validator>
        // ignore description, display-name
        addTagHandler("validator", new ValidatorTagHandler());
        addTagHandler("validator-id");
        addTagHandler("validator-class");

        addTagHandler("attribute", new AttributeTagHandler());
        addTagHandler("attribute-name");
        addTagHandler("attribute-class");
        addTagHandler("attribute-extension");

        addTagHandler("property", new PropertyTagHandler());
        addTagHandler("property-name");
        addTagHandler("property-class");
        addTagHandler("property-extension");

        // <managed-bean>
        // ignore description, display-name
        addTagHandler("managed-bean", new ManagedBeanTagHandler());
        addTagHandler("managed-bean-name");
        addTagHandler("managed-bean-class");
        addTagHandler("managed-bean-scope");

        addTagHandler("managed-property", new ManagedPropertyTagHandler());
        addTagHandler("property-name");
        addTagHandler("property-class");

        addTagHandler("map-entries", new MapEntriesTagHandler());
        addTagHandler("map-entry", new MapEntryTagHandler());
        addTagHandler("key-class");
        addTagHandler("value-class");

        addTagHandler("list-entries", new ListEntriesTagHandler());

        addTagHandler("value");
        addTagHandler("default-value", new DefaultValueTagHandler());
        addTagHandler("suggested-value", new SuggestedValueTagHandler());
        addTagHandler("null-value", new NullValueTagHandler());

        addTagHandler("component", new ComponentTagHandler());
        addTagHandler("component-type");
        addTagHandler("component-class");
        addTagHandler("component-extension");

        addTagHandler("facet", new FacetTagHandler());
        addTagHandler("facet-name");
        addTagHandler("facet-extension");

        addTagHandler("navigation-rule", new NavigationRuleTagHandler());
        addTagHandler("from-view-id");
        
        addTagHandler("navigation-case", new NavigationCaseTagHandler());
        addTagHandler("from-action");
        addTagHandler("from-outcome");
        addTagHandler("to-view-id");
        addTagHandler("redirect");
        
        addTagHandler("referenced-bean", new ReferencedBeanTagHandler());
        addTagHandler("referenced-bean-name");
        addTagHandler("referenced-bean-class");
        
        addTagHandler("render-kit", new RenderKitTagHandler());
        addTagHandler("render-kit-id");
        addTagHandler("render-kit-class");

        addTagHandler("renderer", new RendererTagHandler());
        addTagHandler("component-family");
        addTagHandler("renderer-type");
        addTagHandler("renderer-class");

    }
}