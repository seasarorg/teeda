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
package org.seasar.teeda.core;

import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ServletErrorPageManagerImplTest extends TeedaTestCase {

    public void testGetLocation_bySameException() throws Exception {
        // # Arrange #
        ServletErrorPageManagerImpl manager = new ServletErrorPageManagerImpl();
        manager.addErrorPage(FooException.class, "a.jsp");
        
        // # Act & Assert #
        assertEquals("a.jsp", manager
                .getLocation(FooException.class));
    }

    public void testGetLocation_bySuperClass() throws Exception {
        // # Arrange #
        ServletErrorPageManagerImpl manager = new ServletErrorPageManagerImpl();
        manager.addErrorPage(HogeException.class, "b.jsp");
        
        // # Act & Assert #
        assertEquals("b.jsp", manager.getLocation(FooException.class));
    }

    public void testGetLocation_notFound() throws Exception {
        // # Arrange #
        ServletErrorPageManagerImpl manager = new ServletErrorPageManagerImpl();
        
        // # Act & Assert #
        assertNull(manager.getLocation(FooException.class));
    }

    public void testHandleException_locationNull() throws Exception {
        // # Arrange #
        ServletErrorPageManagerImpl manager = new ServletErrorPageManagerImpl();
        
        // # Act & Assert #
        assertFalse(manager.handleException(new Exception(), getExternalContext()));
    }
    
    public void testHandleException() throws Exception {
        // # Arrange #
        ServletErrorPageManagerImpl manager = new ServletErrorPageManagerImpl();
        
        // # Act #
        manager.addErrorPage(FooException.class, "a.jsp");
        
        // # Assert #
        assertTrue(manager.handleException(new FooException(), getExternalContext()));
    }
    
    private static class HogeException extends Exception {
    }

    private static class FooException extends HogeException {
    }
}

/*

    public boolean handleException(Throwable exception,
            ExternalContext extContext) throws IOException {
        String location = getLocation(exception.getClass());
        if (location == null) {
            return false;
        }
        ServletRequest request = ServletExternalContextUtil
                .getRequest(extContext);
        if (request.getAttribute(JsfConstants.ERROR_EXCEPTION) != null) {
            setErrorPageAttributesToServletError(request);
            HttpServletResponse response = ServletExternalContextUtil
                    .getResponse(extContext);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return true;
        }
        request.setAttribute(JsfConstants.ERROR_EXCEPTION, exception);
        request.setAttribute(JsfConstants.ERROR_EXCEPTION_TYPE, exception
                .getClass());
        request
                .setAttribute(JsfConstants.ERROR_MESSAGE, exception
                        .getMessage());
        extContext.dispatch(location);
        return true;
    }



*/

