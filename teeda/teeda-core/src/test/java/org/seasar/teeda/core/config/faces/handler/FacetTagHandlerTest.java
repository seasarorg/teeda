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

import java.util.List;
import java.util.Map;

import org.seasar.teeda.core.config.faces.element.ComponentElement;
import org.seasar.teeda.core.config.faces.element.FacesConfig;
import org.seasar.teeda.core.config.faces.element.FacetElement;
import org.seasar.teeda.core.config.faces.element.FacetHolder;
import org.seasar.teeda.core.config.faces.element.impl.ComponentElementImpl;

/**
 * @author shot
 */
public class FacetTagHandlerTest extends TagHandlerTestCase {

    public void testFacetTagHandler() throws Exception {
        FacetHolder holder = new ComponentElementImpl();
        getContext().push(holder);
        FacetTagHandler handler = new FacetTagHandler();
        handler.start(getContext(), new NullAttributes());
        handler.end(getContext(), "a");
        List facets = holder.getFacetElements();
        assertNotNull(facets);
        FacetElement facet = (FacetElement) facets.get(0);
        assertNotNull(facet);
    }

    public void testFacetTagHandlerByXMLParse() throws Exception {
        FacesConfig facesConfig = parse("testFacetTagHandler.xml");
        Map components = facesConfig.getComponentElements();
        ComponentElement component = (ComponentElement) components.get("hoge");
        List facets = component.getFacetElements();
        assertNotNull(facets);
        FacetElement facet = (FacetElement) facets.get(0);
        assertNotNull(facet);
        assertEquals("facet", facet.getFacetName());
        assertEquals("aaa", facet.getFacetExtensions().get(0));
    }
}
