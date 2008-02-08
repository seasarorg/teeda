/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.seasar.teeda.core.mock.MockUIViewRoot;
import org.seasar.teeda.core.mock.MockViewHandlerImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class RenderResponsePhaseTest extends TeedaTestCase {

    public void testGetCurrentPhaseId() throws Exception {
        assertEquals(PhaseId.RENDER_RESPONSE, new RenderResponsePhase()
                .getCurrentPhaseId());
    }

    public void testExecutePhase_renderViewSuccess() throws Exception {
        // # Assert #
        ViewHandler orgHandler = getApplication().getViewHandler();
        try {
            MockArgsStoreViewHandler handler = new MockArgsStoreViewHandler();
            getApplication().setViewHandler(handler);
            MockUIViewRoot root = new MockUIViewRoot();
            root.setId("aaa");
            getFacesContext().setViewRoot(root);
            RenderResponsePhase phase = new RenderResponsePhase();

            // # Act #
            phase.executePhase(getFacesContext());

            // # Assert #
            assertEquals(getFacesContext(), handler.getContext());
            assertEquals(root.getId(), handler.getViewToRender().getId());
        } finally {
            getApplication().setViewHandler(orgHandler);
        }
    }

    public void testExecutePhase_renderViewThrowIOException() throws Exception {
        // # Assert #
        ViewHandler orgHandler = getApplication().getViewHandler();
        try {
            MockThrowIOExceptionViewHandler handler = new MockThrowIOExceptionViewHandler();
            getApplication().setViewHandler(handler);
            RenderResponsePhase phase = new RenderResponsePhase();

            // # Act #
            phase.executePhase(getFacesContext());
            fail();
        } catch (FacesException expected) {
            // # Assert #
            assertTrue(expected.getCause() instanceof IOException);
            IOException e = (IOException) expected.getCause();
            assertEquals("hoge", e.getMessage());
            success();
        } finally {
            getApplication().setViewHandler(orgHandler);
        }
    }

    private static class MockArgsStoreViewHandler extends MockViewHandlerImpl {

        private FacesContext context_;

        private UIViewRoot viewToRender_;

        public void renderView(FacesContext context, UIViewRoot viewToRender)
                throws IOException, FacesException {
            context_ = context;
            viewToRender_ = viewToRender;
        }

        public FacesContext getContext() {
            return context_;
        }

        public UIViewRoot getViewToRender() {
            return viewToRender_;
        }

    }

    private static class MockThrowIOExceptionViewHandler extends
            MockViewHandlerImpl {
        public void renderView(FacesContext context, UIViewRoot viewToRender)
                throws IOException, FacesException {
            throw new IOException("hoge");
        }
    }
}
