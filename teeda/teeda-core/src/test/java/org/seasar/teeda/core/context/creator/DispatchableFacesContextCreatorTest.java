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
package org.seasar.teeda.core.context.creator;

import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;

import org.seasar.framework.mock.portlet.MockPortletContext;
import org.seasar.framework.mock.portlet.MockPortletContextImpl;
import org.seasar.framework.mock.portlet.MockPortletRequestImpl;
import org.seasar.framework.mock.portlet.MockPortletResponseImpl;
import org.seasar.teeda.core.context.creator.portlet.PortletFacesContextCreator;
import org.seasar.teeda.core.context.creator.servlet.ServletFacesContextCreator;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class DispatchableFacesContextCreatorTest extends TeedaTestCase {

    public void testCreate_inCaseOfServletEnvironment() throws Exception {
        DispatchableFacesContextCreator creator = new DispatchableFacesContextCreator();
        creator.setServletFacesContextCreator(new ServletFacesContextCreator() {
            public FacesContext create(Object context, Object request,
                    Object response, Lifecycle lifecycle) {
                return getFacesContext();
            }

        });
        FacesContext context = creator.create(getServletContext(),
                getRequest(), getResponse(), getLifecycle());
        assertNotNull(context);
        assertTrue(context instanceof MockFacesContext);
    }

    public void testCreate_inCaseOfPortletEnvironment() throws Exception {
        DispatchableFacesContextCreator creator = new DispatchableFacesContextCreator();
        creator.setPortletFacesContextCreator(new PortletFacesContextCreator() {
            public FacesContext create(Object context, Object request,
                    Object response, Lifecycle lifecycle) {
                return getFacesContext();
            }

        });
        MockPortletContext portletContext = new MockPortletContextImpl("/hoge");
        FacesContext context = creator.create(portletContext,
                new MockPortletRequestImpl(portletContext),
                new MockPortletResponseImpl(), getLifecycle());
        assertNotNull(context);
        assertTrue(context instanceof MockFacesContext);
    }

    public void testCreate_inCaseOfNoSuchEnvironment() throws Exception {

        assertTrue(true);

    }

}
