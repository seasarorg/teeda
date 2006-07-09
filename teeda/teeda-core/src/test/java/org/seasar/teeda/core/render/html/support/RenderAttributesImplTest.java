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
package org.seasar.teeda.core.render.html.support;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

/**
 * @author manhole
 */
public class RenderAttributesImplTest extends TestCase {

    public void testCommandButton() throws Exception {
        // ## Arrange ##
        RenderAttributesImpl renderAttribute = new RenderAttributesImpl();
        renderAttribute.initialize();

        // ## Act ##
        final String[] names = renderAttribute.getAttributeNames(
                "javax.faces.Command", "javax.faces.Button");

        // ## Assert ##
        assertNotNull(names);
        List l = Arrays.asList(names);
        assertEquals(true, l.contains("accesskey"));
        assertEquals(false, l.contains("accept"));
    }

    public void testDataTable() throws Exception {
        // ## Arrange ##
        RenderAttributesImpl renderAttribute = new RenderAttributesImpl();
        renderAttribute.initialize();

        // ## Act ##
        final String[] names = renderAttribute.getAttributeNames(
                "javax.faces.Data", "javax.faces.Table");

        // ## Assert ##
        assertNotNull(names);
        List l = Arrays.asList(names);
        assertEquals(true, l.contains("bgcolor"));
        assertEquals(true, l.contains("cellspacing"));
        assertEquals(false, l.contains("columnClasses"));
        assertEquals(false, l.contains("disabledClass"));
    }

}
