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
package org.seasar.teeda.core.lifecycle.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.internal.WindowIdUtil;
import javax.servlet.http.Cookie;

import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.util.Mru;
import org.seasar.teeda.core.application.ViewHandlerImpl;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIViewRoot;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class RestoreViewPhaseTest extends TeedaTestCase {

    // TODO test
    public void testGetCurrentPhaseId() throws Exception {
        assertEquals(PhaseId.RESTORE_VIEW, new RestoreViewPhase()
                .getCurrentPhaseId());
    }

    public void testRestoredViewRootHasClientLocale() throws Exception {
        // ## Arrange ##
        final MockFacesContext context = getFacesContext();
        final RestoreViewPhase phase = new RestoreViewPhase();

        final MockHttpServletRequest request = context.getMockExternalContext()
                .getMockHttpServletRequest();
        // viewId = "/hello.html"
        request.setLocale(Locale.ITALY);

        final MockUIViewRoot mockUIViewRoot = new MockUIViewRoot();
        mockUIViewRoot.setLocale(Locale.CANADA);
        final Application application = context.getApplication();
        application.setViewHandler(new ViewHandlerImpl() {
            public UIViewRoot restoreView(FacesContext context, String viewId) {
                return mockUIViewRoot;
            }
        });
        application.setSupportedLocales(Arrays.asList(new Locale[] {
                Locale.ITALY, Locale.CANADA }));

        // ## Act ##
        phase.executePhase(context);

        // ## Assert ##
        assertSame(mockUIViewRoot, context.getViewRoot());
        assertEquals(Locale.ITALY, context.getViewRoot().getLocale());
    }

    public void testGetViewIdMruFromSession() throws Exception {
        final RestoreViewPhase phase = new RestoreViewPhase();
        phase.setViewIdMruSize(3);
        final Map sessionMap = new HashMap();
        Mru mru = phase.getViewIdMruFromSession(sessionMap);
        mru.put("aaa", "111");
        mru.put("bbb", "222");
        mru.put("ccc", "333");
        mru.get("aaa");
        Iterator i = mru.getKeyIterator();
        assertEquals("aaa", i.next());
        assertEquals("ccc", i.next());
        assertEquals("bbb", i.next());
        mru.put("ddd", "444");
        assertNull(mru.get("bbb"));
    }

    public void testSetupWindowId() throws Exception {
        final RestoreViewPhase phase = new RestoreViewPhase();
        phase.setViewIdMruSize(3);
        assertNull(phase.setupWindowId(getExternalContext()));

        getExternalContext().getRequestParameterMap().put(
                WindowIdUtil.NEWWINDOW, "true");
        assertNotNull(phase.setupWindowId(getExternalContext()));

        Cookie cookie = new Cookie(WindowIdUtil.TEEDA_WID, "hoge");
        getExternalContext().addRequestCookieMap(cookie);
        getExternalContext().getRequestParameterMap().put(
                WindowIdUtil.NEWWINDOW, "false");
        assertEquals("hoge", phase.setupWindowId(getExternalContext()));
    }

    public void testSaveViewIdToSession() throws Exception {
        final RestoreViewPhase phase = new RestoreViewPhase();
        Map sessionMap = new HashMap();
        phase.saveViewIdToSession(sessionMap, "123", "hoge.html");
        assertEquals("hoge.html", phase.getViewIdFromSession(sessionMap, "123"));
    }
}
