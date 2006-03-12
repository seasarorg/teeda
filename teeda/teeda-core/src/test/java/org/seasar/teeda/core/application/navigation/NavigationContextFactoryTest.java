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
package org.seasar.teeda.core.application.navigation;

import java.util.List;
import java.util.Map;

import org.seasar.teeda.core.mock.MockExternalContextImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class NavigationContextFactoryTest extends TeedaTestCase {

    public void testAddNavigationContext_externalContextIsNull()
            throws Exception {
        try {
            NavigationContextFactory.addNavigationContext(null,
                    new NavigationContext());
            fail();
        } catch (IllegalArgumentException expected) {
            assertTrue(true);
        }
    }

    public void testAddNavigationContext_navigationContextIsNull()
            throws Exception {
        try {
            NavigationContextFactory.addNavigationContext(
                    new MockExternalContextImpl(), null);
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
        NavigationContextFactory.addNavigationContext(getExternalContext(),
                navContext);

        // # Assert
        Map map = NavigationContextFactory
                .getNavigationContexts(getFacesContext());
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
        NavigationContextFactory.addNavigationContext(getExternalContext(),
                navContext);

        // # Assert
        Map map = NavigationContextFactory
                .getWildCardMatchNavigationContexts(getFacesContext());
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

}
