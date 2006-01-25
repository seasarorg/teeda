package org.seasar.teeda.core.util;

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

}
