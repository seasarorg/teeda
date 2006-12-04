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
package org.seasar.teeda.extension.util;

import java.util.Set;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 * 
 */
public class VirtualResourceTest extends TeedaTestCase {

    public void testAddAndGetJSResources() throws Exception {
        final MockFacesContext context = getFacesContext();
        VirtualResource.addJSResource(context, "/aaa/bbb/ccc.js");
        final Set resources = VirtualResource.getJSResources(context);
        assertNotNull(resources);
        assertEquals("/aaa/bbb/ccc.js", resources.iterator().next());
    }
    
    //TODO testing
}