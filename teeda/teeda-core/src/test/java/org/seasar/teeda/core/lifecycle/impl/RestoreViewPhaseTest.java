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

import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.util.LruHashMap;
import org.seasar.teeda.core.application.ViewHandlerImpl;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIViewRoot;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.core.util.PostbackUtil;

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
        phase.setViewIdLruSize(3);
        final Map sessionMap = new HashMap();
        LruHashMap lru = phase.getViewIdLruFromSession(sessionMap);
        lru.put("aaa", "111");
        lru.put("bbb", "222");
        lru.put("ccc", "333");
        lru.get("aaa");
        Iterator i = lru.keySet().iterator();
        assertEquals("bbb", i.next());
        assertEquals("ccc", i.next());
        assertEquals("aaa", i.next());
        lru.put("ddd", "444");
        assertNull(lru.get("bbb"));
    }

    public void testSaveViewIdToSession() throws Exception {
        final RestoreViewPhase phase = new RestoreViewPhase();
        Map sessionMap = new HashMap();
        phase.saveViewIdToSession(sessionMap, "123", "hoge.html");
        assertEquals("hoge.html", phase.getViewIdFromSession(sessionMap, "123"));
    }

    /*
     * TEEDA-117
     * viewIdが同じ & POST
     */
    public void testPostbackTrue() throws Exception {
        // ## Arrange ##
        final RestoreViewPhase phase = new RestoreViewPhase() {
            protected String getViewIdFromSession(Map sessionMap,
                    String windowId) {
                return "/fooViewId";
            }
        };
        final MockFacesContext context = getFacesContext();
        final MockHttpServletRequest request = getRequest();
        request.setPathInfo("/fooViewId");
        request.setMethod("POST");

        // ## Act ##
        phase.executePhase(context);

        // ## Assert ##
        assertEquals(true, PostbackUtil.isPostback(context.getExternalContext()
                .getRequestMap()));
    }

    /*
     * TEEDA-117
     * viewIdが異なる & POST
     */
    public void testPostbackFalse1() throws Exception {
        // ## Arrange ##
        final RestoreViewPhase phase = new RestoreViewPhase() {
            protected String getViewIdFromSession(Map sessionMap,
                    String windowId) {
                return "/otherViewId";
            }
        };
        final MockFacesContext context = getFacesContext();
        final MockHttpServletRequest request = getRequest();
        request.setPathInfo("/fooViewId");
        request.setMethod("POST");

        // ## Act ##
        phase.executePhase(context);

        // ## Assert ##
        assertEquals(false, PostbackUtil.isPostback(context
                .getExternalContext().getRequestMap()));
    }

    /*
     * TEEDA-117
     * viewIdが同じ & GET
     */
    public void testPostbackFalse2() throws Exception {
        // ## Arrange ##
        final RestoreViewPhase phase = new RestoreViewPhase() {
            protected String getViewIdFromSession(Map sessionMap,
                    String windowId) {
                return "/fooViewId";
            }
        };
        final MockFacesContext context = getFacesContext();
        final MockHttpServletRequest request = getRequest();
        request.setPathInfo("/fooViewId");
        request.setMethod("GET");

        // ## Act ##
        phase.executePhase(context);

        // ## Assert ##
        assertEquals(false, PostbackUtil.isPostback(context
                .getExternalContext().getRequestMap()));
    }

}
