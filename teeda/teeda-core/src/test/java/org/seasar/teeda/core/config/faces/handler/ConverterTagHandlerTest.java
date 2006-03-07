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
package org.seasar.teeda.core.config.faces.handler;

import java.util.List;
import java.util.Map;

import org.seasar.teeda.core.config.faces.element.AttributeElement;
import org.seasar.teeda.core.config.faces.element.ConverterElement;
import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.config.faces.element.PropertyElement;
import org.seasar.teeda.core.config.faces.element.impl.ConverterElementImpl;
import org.seasar.teeda.core.config.faces.element.impl.FacesConfigImpl;
import org.seasar.teeda.core.mock.MockConverter;

/**
 * @author shot
 */
public class ConverterTagHandlerTest extends TagHandlerTestCase {

    /**
     * Constructor for ComponentTagHandlerTest.
     * 
     * @param name
     */
    public ConverterTagHandlerTest(String name) {
        super(name);
    }

    public void testConverterTagHandler_withConverterId() throws Exception {
        // # Arrange #
        FacesConfig facesConfig = new FacesConfigImpl();
        getContext().push(facesConfig);

        // # Act #
        ConverterTagHandler handler = new ConverterTagHandler() {
            protected ConverterElement createConverterElement() {
                ConverterElementImpl c = new ConverterElementImpl();
                c.setConverterId("aaa");
                return c;
            }
        };
        handler.start(getContext(), new NullAttributes());
        handler.end(getContext(), "a");

        // # Assert #
        Map converters = facesConfig.getConverterElementsById();
        assertNotNull(converters);
        assertEquals(1, converters.entrySet().size());
        ConverterElement converter = (ConverterElement) converters.get("aaa");
        assertEquals("aaa", converter.getConverterId());
    }

    public void testConverterTagHandler_withConverterForClass()
            throws Exception {
        // # Arrange #
        FacesConfig facesConfig = new FacesConfigImpl();
        getContext().push(facesConfig);

        // # Act #
        ConverterTagHandler handler = new ConverterTagHandler() {
            protected ConverterElement createConverterElement() {
                ConverterElementImpl c = new ConverterElementImpl();
                c.setConverterForClass("aaa");
                return c;
            }
        };
        handler.start(getContext(), new NullAttributes());
        handler.end(getContext(), "a");

        // # Assert #
        Map converters = facesConfig.getConverterElementsByClass();
        assertNotNull(converters);
        assertEquals(1, converters.entrySet().size());
        ConverterElement converter = (ConverterElement) converters.get("aaa");
        assertEquals("aaa", converter.getConverterForClass());
    }

    public void testConverterTagHandlerByXMLParse() throws Exception {
        // # Arrange & Act#
        FacesConfig facesConfig = parse("testConverterTagHandler.xml");

        // # Assert #
        Map map = facesConfig.getConverterElementsById();
        assertEquals(1, map.values().size());
        ConverterElement converter = (ConverterElement) map
                .get("javax.faces.mock");

        assertEquals("javax.faces.mock", converter.getConverterId());
        assertNull(converter.getConverterForClass());
        assertEquals(MockConverter.class.getName(), converter
                .getConverterClass());

        List attributes = converter.getAttributeElements();
        assertNotNull(attributes);
        assertEquals(1, attributes.size());
        AttributeElement attribute = (AttributeElement) attributes.get(0);
        assertEquals("attr", attribute.getAttributeName());
        assertEquals("A", attribute.getAttributeClass());

        map = facesConfig.getConverterElementsByClass();
        converter = (ConverterElement) map.get("java.lang.Double");
        List properties = converter.getPropertyElements();
        assertNotNull(properties);
        assertEquals(1, properties.size());
        PropertyElement property = (PropertyElement) properties.get(0);
        assertEquals("b", property.getPropertyName());
        assertEquals("B", property.getPropertyClass());
        assertNull(property.getDefaultValue());
    }

}
