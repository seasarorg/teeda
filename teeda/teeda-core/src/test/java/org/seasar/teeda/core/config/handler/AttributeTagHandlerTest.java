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
package org.seasar.teeda.core.config.handler;

import java.util.List;
import java.util.Map;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.AttributeElement;
import org.seasar.teeda.core.config.element.AttributeHolder;
import org.seasar.teeda.core.config.element.ComponentElement;
import org.seasar.teeda.core.config.element.ConverterElement;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.element.ValidatorElement;
import org.seasar.teeda.core.config.element.impl.ComponentElementImpl;

/**
 * @author shot
 */
public class AttributeTagHandlerTest extends TagHandlerTestCase {

    public AttributeTagHandlerTest(String name) {
        super(name);
    }

    public void testAttributeTagHandler() throws Exception {
        // # Arrange #
        AttributeTagHandler handler = new AttributeTagHandler();
        TagHandlerContext context = new TagHandlerContext();
        AttributeHolder holder = new ComponentElementImpl();
        context.push(holder);

        // # Act #
        handler.start(context, new NullAttributes());
        handler.end(context, "body");

        // # Assert #
        assertNotNull(holder.getAttributeElements());
        assertEquals(1, holder.getAttributeElements().size());
        List list = holder.getAttributeElements();
        AttributeElement element = (AttributeElement) list.get(0);
        assertNotNull(element);
    }

    public void testAttributeTagHandlerByXMLParse() throws Exception {
        // # Arrange & Act #
        FacesConfig facesConfig = parse("testAttributeTagHandler.xml");

        // # Arrange #
        Map components = facesConfig.getComponentElements();
        ComponentElement component = (ComponentElement) components.get("type");
        AttributeElement attribute = getFirstAttribute(component);
        assertNotNull(attribute);
        assertEquals("hoge", attribute.getAttributeName());
        assertEquals(Hoge.class.getName(), attribute.getAttributeClass());

        Map converters = facesConfig.getConverterElementsById();
        ConverterElement converter = (ConverterElement) converters
                .get("converter-id");
        attribute = getFirstAttribute(converter);
        assertNotNull(attribute);
        assertEquals("foo", attribute.getAttributeName());
        assertEquals(Foo.class.getName(), attribute.getAttributeClass());

        Map validators = facesConfig.getValidatorElements();
        ValidatorElement validator = (ValidatorElement) validators
                .get("validator-id");
        attribute = getFirstAttribute(validator);
        assertNotNull(attribute);
        assertEquals("bar", attribute.getAttributeName());
        assertEquals(Bar.class.getName(), attribute.getAttributeClass());
    }

    private static AttributeElement getFirstAttribute(AttributeHolder holder) {
        List attributes = holder.getAttributeElements();
        assertNotNull(attributes);
        assertEquals(1, attributes.size());
        AttributeElement attribute = (AttributeElement) attributes.get(0);
        return attribute;
    }

    public static class Hoge {
    }

    public static class Foo {
    }

    public static class Bar {
    }
}
