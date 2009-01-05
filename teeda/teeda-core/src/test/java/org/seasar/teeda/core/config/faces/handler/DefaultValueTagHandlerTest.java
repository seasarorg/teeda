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
package org.seasar.teeda.core.config.faces.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.faces.element.AttributeElement;
import org.seasar.teeda.core.config.faces.element.PropertyElement;
import org.seasar.teeda.core.config.faces.element.impl.AttributeElementImpl;
import org.seasar.teeda.core.config.faces.element.impl.PropertyElementImpl;

/**
 * @author shot
 */
public class DefaultValueTagHandlerTest extends TagHandlerTestCase {

    /**
     * Constructor for DefaultValueTagHandlerTest.
     * 
     * @param name
     */
    public DefaultValueTagHandlerTest(String name) {
        super(name);
    }

    public void testDefaultValueTagHandler_withPropertyElement()
            throws Exception {
        // # Arrange #
        PropertyElement element = new PropertyElementImpl();
        getContext().push(element);

        // # Act #
        DefaultValueTagHandler handler = new DefaultValueTagHandler();
        handler.end(getContext(), "a");

        // # Assert #
        assertEquals("a", element.getDefaultValue());
    }

    public void testDefaultValueTagHandler_withAttributeElement()
            throws Exception {
        // # Arrange #
        TagHandlerContext context = new TagHandlerContext();
        AttributeElement element = new AttributeElementImpl();
        context.push(element);

        // # Act #
        DefaultValueTagHandler handler = new DefaultValueTagHandler();
        handler.end(context, "a");

        // # Assert #
        assertEquals("a", element.getDefaultValue());
    }

}
