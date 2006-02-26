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
package org.seasar.teeda.core.application;

import javax.faces.component.UIViewRoot;

import org.seasar.teeda.core.application.navigation.NavigationCaseContext;
import org.seasar.teeda.core.application.navigation.NavigationContext;
import org.seasar.teeda.core.application.navigation.NavigationContextFactory;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class NavigationHandlerImplTest extends TeedaTestCase {

    /**
     * Constructor for NavigationHandlerImplTest.
     * @param name
     */
    public NavigationHandlerImplTest(String name) {
        super(name);
    }

    public void testHandleNavigation1() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        root.setViewId("aaa");
        context.setViewRoot(root);
        NavigationContext navContext = createNavigationContext("aaa", "from", "outcome", "bbb", false);
        NavigationContextFactory.addNavigationContext(getExternalContext(), navContext);
        
        // ## Act ##
        NavigationHandlerImpl handler = new NavigationHandlerImpl();
        handler.handleNavigation(getFacesContext(), "from", "outcome");
        
        // ## Assert ##
        assertEquals("bbb", getFacesContext().getViewRoot().getViewId());
    }
    
    public void testHandleNavigation2() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        root.setViewId("aaa*");
        context.setViewRoot(root);
        NavigationContext navContext = createNavigationContext("aaa*", "from", "outcome", "bbb", false);
        NavigationContextFactory.addNavigationContext(getExternalContext(), navContext);
        
        // ## Act ##
        NavigationHandlerImpl handler = new NavigationHandlerImpl();
        handler.handleNavigation(getFacesContext(), "from", "outcome");
        
        // ## Assert ##
        assertEquals("bbb", getFacesContext().getViewRoot().getViewId());
    }

    public void testHandleNavigation3() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        root.setViewId("*");
        context.setViewRoot(root);
        NavigationContext navContext = createNavigationContext("*", "from", "outcome", "bbb", false);
        NavigationContextFactory.addNavigationContext(getExternalContext(), navContext);
        
        // ## Act ##
        NavigationHandlerImpl handler = new NavigationHandlerImpl();
        handler.handleNavigation(getFacesContext(), "from", "outcome");
        
        // ## Assert ##
        assertEquals("bbb", getFacesContext().getViewRoot().getViewId());
    }

    public void testGetNavigationCaseContext1() throws Exception {
        // ## Arrange ##
        NavigationContext navContext = createNavigationContext("id", "action", "outcome", "to", false);
        NavigationHandlerImpl handler = new NavigationHandlerImpl();
        
        // ## Act ##
        NavigationCaseContext caseContext = handler.getNavigationCaseContext(navContext, "action", "outcome");
        
        // ## Arrange ##
        assertNotNull(caseContext);
        assertEquals("action", caseContext.getFromAction());
        assertEquals("outcome", caseContext.getFromOutcome());
        assertEquals("to", caseContext.getToViewId());
        assertFalse(caseContext.isRedirect());
    }
    
    public void testGetNavigationCaseContext2() throws Exception {
        // ## Arrange ##
        NavigationContext navContext = createNavigationContext("id", "action", "outcome", "to", false);
        NavigationHandlerImpl handler = new NavigationHandlerImpl();
        
        // ## Act ##
        NavigationCaseContext caseContext = handler.getNavigationCaseContext(navContext, "action", null);
        
        // ## Arrange ##
        assertNotNull(caseContext);
        assertEquals("action", caseContext.getFromAction());
        assertEquals("outcome", caseContext.getFromOutcome());
        assertEquals("to", caseContext.getToViewId());
        assertFalse(caseContext.isRedirect());
    }

    public void testetNavigationCaseContext3() throws Exception {
        // ## Arrange ##
        NavigationContext navContext = createNavigationContext("id", "action", "outcome", "to", false);
        NavigationHandlerImpl handler = new NavigationHandlerImpl();
        
        // ## Act ##
        NavigationCaseContext caseContext = handler.getNavigationCaseContext(navContext, null, "outcome");
        
        // ## Arrange ##
        assertNotNull(caseContext);
        assertEquals("action", caseContext.getFromAction());
        assertEquals("outcome", caseContext.getFromOutcome());
        assertEquals("to", caseContext.getToViewId());
        assertFalse(caseContext.isRedirect());
    }

    public void testetNavigationCaseContext4() throws Exception {
        // ## Arrange ##
        NavigationContext navContext = createNavigationContext("id", "action", "outcome", "to", false);
        NavigationHandlerImpl handler = new NavigationHandlerImpl();
        
        // ## Act ##
        NavigationCaseContext caseContext = handler.getNavigationCaseContext(navContext, null, null);
        
        // ## Arrange ##
        assertNotNull(caseContext);
        assertEquals("action", caseContext.getFromAction());
        assertEquals("outcome", caseContext.getFromOutcome());
        assertEquals("to", caseContext.getToViewId());
        assertFalse(caseContext.isRedirect());
    }

    private NavigationContext createNavigationContext(String fromViewId, String fromAction, String outcome, String toViewId, boolean isRedirect){
        NavigationContext navContext = new NavigationContext();
        navContext.setFromViewId(fromViewId);
        NavigationCaseContext caseContext = new NavigationCaseContext(fromAction, outcome, toViewId, isRedirect);
        navContext.addNavigationCaseContext(caseContext);
        return navContext;
    }
}
