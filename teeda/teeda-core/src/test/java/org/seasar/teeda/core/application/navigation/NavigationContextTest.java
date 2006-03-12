package org.seasar.teeda.core.application.navigation;

import junit.framework.TestCase;

public class NavigationContextTest extends TestCase {

    public void testAddNavigationCase_setNull() throws Exception {
        NavigationContext context = new NavigationContext();
        context.addNavigationCaseContext(null);
        
        assertNull(context.getFromViewId());
        assertTrue(context.getNavigationCases().size() == 0);
   }
    
    public void testGetFromViewId() throws Exception {
        //TODO testing
    }
}
