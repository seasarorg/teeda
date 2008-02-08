/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import org.seasar.teeda.core.config.faces.element.ConverterElement;
import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.config.faces.element.FactoryElement;
import org.seasar.teeda.core.config.faces.element.impl.ConverterElementImpl;

public class SimpleStringTagHandlerTest extends TagHandlerTestCase {

    public void testEndTagHandlerContextString() throws Exception {
        ConverterElement tag = new ConverterElementImpl();
        getContext().push(tag);
        SimpleStringTagHandler handler = new SimpleStringTagHandler(
                "converter-id");
        handler.end(getContext(), "id");
        ConverterElement result = (ConverterElement) getContext().pop();
        assertEquals("id", result.getConverterId());
    }

    public void testSimpleStringTagHandlerByXMLParse() throws Exception {
        FacesConfig facesConfig = parse("testSimpleStringTagHandler.xml");
        assertEquals(1, facesConfig.getFactoryElements().size());
        FactoryElement factory = (FactoryElement) facesConfig
                .getFactoryElements().get(0);
        List list = factory.getApplicationFactories();
        String target = (String) list.get(0);
        assertEquals("org.seasar.teeda.core.mock.MockApplicationFactory",
                target);
    }

}
