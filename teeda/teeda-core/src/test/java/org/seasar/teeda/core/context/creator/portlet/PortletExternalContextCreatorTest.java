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
package org.seasar.teeda.core.context.creator.portlet;

import javax.faces.context.ExternalContext;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import junit.framework.TestCase;

import org.seasar.teeda.core.context.portlet.PortletExternalContextImpl;
import org.seasar.teeda.core.mock.MockPortletContextImpl;
import org.seasar.teeda.core.mock.MockPortletRequestImpl;
import org.seasar.teeda.core.mock.MockPortletResponseImpl;

/**
 * @author shot
 */
public class PortletExternalContextCreatorTest extends TestCase {

    public void testCreate() throws Exception {
        PortletExternalContextCreator creator = new PortletExternalContextCreator();
        assertNull(creator.create("a", "b", "c"));

        PortletContext context = new MockPortletContextImpl();
        PortletRequest request = new MockPortletRequestImpl();
        PortletResponse response = new MockPortletResponseImpl();
        ExternalContext externalContext = creator.create(context, request,
                response);
        assertNotNull(externalContext);
        assertTrue(externalContext instanceof PortletExternalContextImpl);
    }
}
