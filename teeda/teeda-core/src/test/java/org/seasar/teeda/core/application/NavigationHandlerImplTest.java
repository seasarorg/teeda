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

    public void testHandleNavigation_exactMatch() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        root.setViewId("aaa");
        context.setViewRoot(root);
        NavigationContext navContext = createNavigationContext("aaa", "from",
                "outcome", "bbb", false);
        NavigationContextFactory.addNavigationContext(getExternalContext(),
                navContext);

        // ## Act ##
        NavigationHandlerImpl handler = new NavigationHandlerImpl();
        handler.handleNavigation(context, "from", "outcome");

        // ## Assert ##
        assertEquals("bbb", context.getViewRoot().getViewId());
    }

    public void testHandleNavigation_wildCardMatch() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        root.setViewId("aaabbb");
        context.setViewRoot(root);
        NavigationContext navContext = createNavigationContext("aaa*", "from",
                "outcome", "bbb", false);
        NavigationContextFactory.addNavigationContext(getExternalContext(),
                navContext);

        // ## Act ##
        NavigationHandlerImpl handler = new NavigationHandlerImpl();
        handler.handleNavigation(context, "from", "outcome");

        // ## Assert ##
        assertEquals("bbb", context.getViewRoot().getViewId());
    }

    public void testHandleNavigation_defaultMatch() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        root.setViewId("*");
        context.setViewRoot(root);
        NavigationContext navContext = createNavigationContext("*", "from",
                "outcome", "bbb", false);
        NavigationContextFactory.addNavigationContext(getExternalContext(),
                navContext);

        // ## Act ##
        NavigationHandlerImpl handler = new NavigationHandlerImpl();
        handler.handleNavigation(context, "from", "outcome");

        // ## Assert ##
        assertEquals("bbb", context.getViewRoot().getViewId());
    }

    public void testHandleNavigation_redirect() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        root.setViewId("id");
        context.setViewRoot(root);
        NavigationContext navContext = createNavigationContext("id", "from",
                "outcome", "bbb", true);
        NavigationContextFactory.addNavigationContext(getExternalContext(),
                navContext);

        // ## Act ##
        NavigationHandlerImpl handler = new NavigationHandlerImpl();
        handler.handleNavigation(context, "from", "outcome");

        // ## Assert ##
        assertEquals("id", context.getViewRoot().getViewId());
    }

    public void testGetNavigationCaseContext_fromActionAndOutComeNotNull()
            throws Exception {
        // ## Arrange ##
        NavigationContext navContext = createNavigationContext("id", "action",
                "outcome", "to", false);
        NavigationContextFactory.addNavigationContext(getExternalContext(),
                navContext);
        NavigationHandlerImpl handler = new NavigationHandlerImpl();

        // ## Act ##
        NavigationCaseContext caseContext = handler.getNavigationCaseContext(
                getFacesContext(), "action", "outcome", "id");

        // ## Arrange ##
        assertNotNull(caseContext);
        assertEquals("action", caseContext.getFromAction());
        assertEquals("outcome", caseContext.getFromOutcome());
        assertEquals("to", caseContext.getToViewId());
        assertFalse(caseContext.isRedirect());
    }

    public void testGetNavigationCaseContext_outcomeIsNull() throws Exception {
        // ## Arrange ##
        NavigationContext navContext = createNavigationContext("id", "action",
                null, "to", false);
        NavigationContextFactory.addNavigationContext(getExternalContext(),
                navContext);
        NavigationHandlerImpl handler = new NavigationHandlerImpl();

        // ## Act ##
        NavigationCaseContext caseContext = handler.getNavigationCaseContext(
                getFacesContext(), "action", "outcome", "id");

        // ## Arrange ##
        assertNotNull(caseContext);
        assertEquals("action", caseContext.getFromAction());
        assertNull(caseContext.getFromOutcome());
        assertEquals("to", caseContext.getToViewId());
        assertFalse(caseContext.isRedirect());
    }

    public void testNavigationCaseContext_fromActionIsNull() throws Exception {
        // ## Arrange ##
        NavigationContext navContext = createNavigationContext("id", null,
                "outcome", "to", false);
        NavigationContextFactory.addNavigationContext(getExternalContext(),
                navContext);
        NavigationHandlerImpl handler = new NavigationHandlerImpl();

        // ## Act ##
        NavigationCaseContext caseContext = handler.getNavigationCaseContext(
                getFacesContext(), "action", "outcome", "id");

        // ## Arrange ##
        assertNotNull(caseContext);
        assertNull(caseContext.getFromAction());
        assertEquals("outcome", caseContext.getFromOutcome());
        assertEquals("to", caseContext.getToViewId());
        assertFalse(caseContext.isRedirect());
    }

    public void testNavigationCaseContext_fromActionAndOutComeNull()
            throws Exception {
        // ## Arrange ##
        NavigationContext navContext = createNavigationContext("id", null,
                null, "to", false);
        NavigationContextFactory.addNavigationContext(getExternalContext(),
                navContext);
        NavigationHandlerImpl handler = new NavigationHandlerImpl();

        // ## Act ##
        NavigationCaseContext caseContext = handler.getNavigationCaseContext(
                getFacesContext(), "action", "outcome", "id");

        // ## Arrange ##
        assertNotNull(caseContext);
        assertNull(caseContext.getFromAction());
        assertNull(caseContext.getFromOutcome());
        assertEquals("to", caseContext.getToViewId());
        assertFalse(caseContext.isRedirect());
    }

    public void testHandleNavigationMoreReallistic_likeNormalLogin() {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        root.setViewId("/index.jsp");
        context.setViewRoot(root);
        NavigationContext navContext = createNavigationContext("/index.jsp",
                null, "login", "/welcome.jsp", false);
        NavigationContextFactory.addNavigationContext(getExternalContext(),
                navContext);

        // ## Act ##
        NavigationHandlerImpl handler = new NavigationHandlerImpl();
        handler.handleNavigation(context, "/index.jsp", "login");

        // ## Assert ##
        assertEquals("/welcome.jsp", context.getViewRoot().getViewId());

    }

    public void testHandleNavigationMoreReallistic_likeNormalLogout() {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        UIViewRoot root = new UIViewRoot();
        root.setViewId("/index.jsp");
        context.setViewRoot(root);
        NavigationContext navContext = createNavigationContext("*", null,
                "logout", "/logout.jsp", false);
        NavigationContextFactory.addNavigationContext(getExternalContext(),
                navContext);

        // ## Act ##
        NavigationHandlerImpl handler = new NavigationHandlerImpl();
        handler.handleNavigation(context, "/index.jsp", "logout");

        // ## Assert ##
        assertEquals("/logout.jsp", context.getViewRoot().getViewId());

    }

    private NavigationContext createNavigationContext(String fromViewId,
            String fromAction, String outcome, String toViewId,
            boolean isRedirect) {
        NavigationContext navContext = new NavigationContext();
        navContext.setFromViewId(fromViewId);
        NavigationCaseContext caseContext = new NavigationCaseContext(
                fromAction, outcome, toViewId, isRedirect);
        navContext.addNavigationCaseContext(caseContext);
        return navContext;
    }
}
