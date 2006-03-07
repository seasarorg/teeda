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
package org.seasar.teeda.core.context;

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.context.servlet.ServletFacesContextImpl;
import org.seasar.teeda.core.exception.FacesContextNotFoundRuntimeException;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class FacesContextFactoryImplTest extends TeedaTestCase {

    /**
     * Constructor for FacesContextFactoryImplTest.
     * 
     * @param name
     */
    public FacesContextFactoryImplTest(String name) {
        super(name);
    }

    public void testGetFacesContext_getFail() throws Exception {
        FacesContextFactoryImpl factory = new FacesContextFactoryImpl();
        try {
            factory.getFacesContext("", getRequest(), getResponse(),
                    getLifecycle());
            fail();
        } catch (FacesContextNotFoundRuntimeException e) {
            success();
        }
        try {
            factory.getFacesContext(getServletContext(), "", getResponse(),
                    getLifecycle());
            fail();
        } catch (FacesContextNotFoundRuntimeException e) {
            success();
        }
        try {
            factory.getFacesContext(getServletContext(), getRequest(), "",
                    getLifecycle());
            fail();
        } catch (FacesContextNotFoundRuntimeException e) {
            success();
        }
    }

    public void testGetFacesContext_getSuccess() throws Exception {
        FacesContextFactoryImpl factory = new FacesContextFactoryImpl();
        FacesContext context = factory.getFacesContext(getServletContext(),
                getRequest(), getResponse(), getLifecycle());

        assertNotNull(context);
        assertTrue(context instanceof ServletFacesContextImpl);
    }
}
