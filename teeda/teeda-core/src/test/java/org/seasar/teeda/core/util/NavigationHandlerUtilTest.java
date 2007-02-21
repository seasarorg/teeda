/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.util;

import org.seasar.teeda.core.exception.AlreadyRedirectingException;
import org.seasar.teeda.core.mock.MockNavigationHandler;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class NavigationHandlerUtilTest extends TeedaTestCase {

    public void testHandleNavigation() {
        NavigationHandlerUtil.handleNavigation(getFacesContext(), "from", "to");
        MockNavigationHandler handler = (MockNavigationHandler) getApplication()
                .getNavigationHandler();
        assertEquals("from", handler.getFromAction());
        assertEquals("to", handler.getOutCome());
    }

    public void testAssertNotAlreadyRedirect() {
        NavigationHandlerUtil.assertNotAlreadyRedirect(getFacesContext());
        try {
            NavigationHandlerUtil.assertNotAlreadyRedirect(getFacesContext());
            fail();
        } catch (AlreadyRedirectingException ex) {
            System.out.println(ex.getMessage());
        }
        getFacesContext().getViewRoot().setViewId("/view/hoge/bbb.html");
        NavigationHandlerUtil.assertNotAlreadyRedirect(getFacesContext());
    }
}
