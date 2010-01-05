/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.application.navigation;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

/**
 * @author shot
 */
public class NavigationResourceTest extends TestCase {

    public void tearDown() {
        NavigationResource.removeAll();
    }

    public void testAddNavigationContext_navigationContextIsNull()
            throws Exception {
        try {
            NavigationResource.addNavigationContext(null);
            fail();
        } catch (IllegalArgumentException expected) {
            assertTrue(true);
        }
    }

    public void testAddNavigationContext_getNavigationContext()
            throws Exception {
        // # Arrange
        NavigationContext navContext = new NavigationContext();
        navContext.setFromViewId("fromId");
        NavigationCaseContext caseContext = new NavigationCaseContext();
        caseContext.setToViewId("toId");
        caseContext.setFromAction("action");
        caseContext.setFromOutcome("outcome");
        caseContext.setRedirect(true);
        navContext.addNavigationCaseContext(caseContext);

        // # Act
        NavigationResource.addNavigationContext(navContext);

        // # Assert
        Map map = NavigationResource.getNavigationContexts();
        List list = (List) map.get("fromId");
        NavigationContext targetContext = (NavigationContext) list.get(0);
        assertNotNull(targetContext);
        assertEquals("fromId", targetContext.getFromViewId());
        assertNotNull(targetContext.getNavigationCase("action", "outcome"));
        NavigationCaseContext targetCase = targetContext.getNavigationCase(
                "action", "outcome");
        assertEquals("action", targetCase.getFromAction());
        assertEquals("outcome", targetCase.getFromOutcome());
        assertEquals("toId", targetCase.getToViewId());
    }

    public void testAddNavigationContext_getWildCardNavigationContext()
            throws Exception {
        // # Arrange
        NavigationContext navContext = new NavigationContext();
        navContext.setFromViewId("aa*");
        NavigationCaseContext caseContext = new NavigationCaseContext();
        caseContext.setToViewId("toId");
        caseContext.setFromAction("action");
        caseContext.setFromOutcome("outcome");
        caseContext.setRedirect(true);
        navContext.addNavigationCaseContext(caseContext);

        // # Act
        NavigationResource.addNavigationContext(navContext);

        // # Assert
        Map map = NavigationResource.getWildCardMatchNavigationContexts();
        List list = (List) map.get("aa*");
        NavigationContext targetContext = (NavigationContext) list.get(0);
        assertNotNull(targetContext);
        assertEquals("aa*", targetContext.getFromViewId());
        assertNotNull(targetContext.getNavigationCase("action", "outcome"));
        NavigationCaseContext targetCase = targetContext.getNavigationCase(
                "action", "outcome");
        assertEquals("action", targetCase.getFromAction());
        assertEquals("outcome", targetCase.getFromOutcome());
        assertEquals("toId", targetCase.getToViewId());
    }

    public void testRemoveNavigationContext() throws Exception {
        // # Arrange
        NavigationContext navContext = new NavigationContext();
        navContext.setFromViewId("fromId");
        NavigationCaseContext caseContext = new NavigationCaseContext();
        caseContext.setToViewId("toId");
        caseContext.setFromAction("action");
        caseContext.setFromOutcome("outcome");
        caseContext.setRedirect(true);
        navContext.addNavigationCaseContext(caseContext);

        // # Act
        NavigationResource.addNavigationContext(navContext);
        NavigationResource.removeNavigationContext("fromId");

        // # Assert
        Map map = NavigationResource.getNavigationContexts();
        assertNull(map.get("fromId"));
    }
}
