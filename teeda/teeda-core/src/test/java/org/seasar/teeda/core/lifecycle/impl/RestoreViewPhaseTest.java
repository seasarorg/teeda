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
import java.util.Locale;

import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.seasar.framework.mock.servlet.MockHttpServletRequest;
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

}
