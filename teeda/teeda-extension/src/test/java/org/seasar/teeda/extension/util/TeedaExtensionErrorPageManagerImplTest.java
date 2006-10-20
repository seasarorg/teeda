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

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.mock.MockExternalContext;
import org.seasar.teeda.core.mock.MockExternalContextImpl;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.extension.html.impl.SessionPagePersistence;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author shot
 */
public class TeedaExtensionErrorPageManagerImplTest extends
        TeedaExtensionTestCase {

    public void testHandleException() throws Exception {
        // # Arrange #
        TeedaExtensionErrorPageManagerImpl manager = new TeedaExtensionErrorPageManagerImpl();
        FooException e = new FooException();
        e.setMessage("aaa");
        manager.addErrorPage(e.getClass(), "a.jsp");
        MockFacesContext context = getFacesContext();
        final boolean[] calls = new boolean[] { false };
        MockExternalContext ext = new MockExternalContextImpl() {

            public void redirect(String requestURI) throws IOException {
                calls[0] = true;
                System.out.println(requestURI);
            }

        };
        context.setExternalContext(ext);
        SessionPagePersistence spp = new SessionPagePersistence() {

            public void removeSubApplicationPages(FacesContext context) {
                //no op
            }

            public void save(FacesContext context, String viewId) {
                //no op
            }

        };
        register(spp);

        // # Act & Assert #
        assertTrue(manager.handleException(e, context.getExternalContext()));
        HttpServletRequest req = (HttpServletRequest) ext.getRequest();
        assertEquals(e, req.getAttribute(JsfConstants.ERROR_EXCEPTION));
        assertEquals(e.getClass(), req
                .getAttribute(JsfConstants.ERROR_EXCEPTION_TYPE));
        assertEquals("aaa", req.getAttribute(JsfConstants.ERROR_MESSAGE));

        assertTrue(calls[0]);
    }

    private static class HogeException extends Exception {

        private static final long serialVersionUID = 1L;
    }

    private static class FooException extends HogeException {

        private static final long serialVersionUID = 1L;

        private String message_;

        public void setMessage(String message) {
            message_ = message;
        }

        public String getMessage() {
            return message_;
        }
    }

}