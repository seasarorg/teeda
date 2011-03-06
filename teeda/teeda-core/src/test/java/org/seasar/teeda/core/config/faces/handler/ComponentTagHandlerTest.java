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
package org.seasar.teeda.core.config.faces.handler;

import java.util.List;
import java.util.Map;

import org.seasar.teeda.core.config.faces.element.AttributeElement;
import org.seasar.teeda.core.config.faces.element.ComponentElement;
import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.config.faces.element.FacetElement;
import org.seasar.teeda.core.config.faces.element.PropertyElement;
import org.seasar.teeda.core.config.faces.element.impl.FacesConfigImpl;
import org.seasar.teeda.core.mock.MockUIComponent;

/**
 * @author shot
 */
public class ComponentTagHandlerTest extends TagHandlerTestCase {

    /**
     * Constructor for ComponentTagHandlerTest.
     * 
     * @param name
     */
    public ComponentTagHandlerTest(String name) {
        super(name);
    }

    public void testComponentTagHandler() throws Exception {
        // # Arrange #
        //        TagHandlerContext context = new TagHandlerContext();
        FacesConfig facesConfig = new FacesConfigImpl();
        getContext().push(facesConfig);

        // # Act #
        ComponentTagHandler handler = new ComponentTagHandler();
        handler.start(getContext(), new NullAttributes());
        handler.end(getContext(), "a");

        // # Assert #
        Map components = facesConfig.getComponentElements();
        assertNotNull(components);
        assertEquals(1, components.entrySet().size());
        ComponentElement component = (ComponentElement) components.values()
                .iterator().next();
        assertNotNull(component);
    }

    public void testComponentTagHandlerByXMLParse() throws Exception {
        // # Arrange & Act#
        FacesConfig facesConfig = parse("testComponentTagHandler.xml");

        // # Assert #
        Map map = facesConfig.getComponentElements();
        ComponentElement component = (ComponentElement) map.get("hoge");
        assertEquals("hoge", component.getComponentType());
        assertEquals(MockUIComponent.class.getName(), component
                .getComponentClass());

        List facets = component.getFacetElements();
        assertNotNull(facets);
        assertEquals(1, facets.size());
        FacetElement facet = (FacetElement) facets.get(0);
        assertEquals("facet", facet.getFacetName());

        List attributes = component.getAttributeElements();
        assertNotNull(attributes);
        assertEquals(1, attributes.size());
        AttributeElement attribute = (AttributeElement) attributes.get(0);
        assertEquals("attr", attribute.getAttributeName());
        assertEquals("org.seasar.teeda.core.config.handler.Bean1", attribute
                .getAttributeClass());

        List properties = component.getPropertyElements();
        assertNotNull(properties);
        assertEquals(1, properties.size());
        PropertyElement property = (PropertyElement) properties.get(0);
        assertEquals("id", property.getPropertyName());
        assertEquals("String", property.getPropertyClass());
        assertEquals("hoge", property.getDefaultValue());
    }

}
