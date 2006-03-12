package org.seasar.teeda.core.application.navigation;

import junit.framework.TestCase;

public class NavigationCaseContextTest extends TestCase {

    public void testNavigationCaseContext() throws Exception {
        NavigationCaseContext caseContext = new NavigationCaseContext(
                "fromAction", "outcome", "toViewId", false);
        assertEquals("fromAction", caseContext.getFromAction());
        assertEquals("outcome", caseContext.getFromOutcome());
        assertEquals("toViewId", caseContext.getToViewId());
        assertEquals(false, caseContext.isRedirect());
        System.out.println(caseContext);
    }
}
