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
package org.seasar.teeda.core.lifecycle;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.core.util.PostbackUtil;

/**
 * @author shot
 */
public class LifecycleImplTest extends TeedaTestCase {

    //TODO more testing
    private LifecycleImpl lifecycle;

    public void setUpExecute_restoreAndResponseCompleteWithoutPostback()
            throws Exception {
        lifecycle = new LifecycleImpl();
        lifecycle
                .setRestoreViewPhase(new MockRestoreViewPhaseWithoutPostback());
    }

    public void testExecute_restoreAndResponseCompleteWithoutPostback()
            throws Exception {
        lifecycle.execute(getFacesContext());
        assertNull(getFacesContext().getExternalContext().getRequestMap().get(
                "postback"));
    }

    public void setUpExecute_restoreAndResponseCompleteWithPostback()
            throws Exception {
        lifecycle = new LifecycleImpl();
        lifecycle.setRestoreViewPhase(new MockRestoreViewPhase());
    }

    public void testExecute_restoreAndResponseCompleteWithPostback()
            throws Exception {
        lifecycle.execute(getFacesContext());
        assertEquals(Boolean.TRUE, getFacesContext().getExternalContext()
                .getRequestMap().get("postback"));
    }

    public static class MockRestoreViewPhaseWithoutPostback implements Phase {

        public void execute(FacesContext context) throws FacesException {
            context.responseComplete();
        }

    }

    public static class MockRestoreViewPhase implements Phase {

        public void execute(FacesContext context) throws FacesException {
            context.responseComplete();
            PostbackUtil.setPostback(context.getExternalContext()
                    .getRequestMap(), true);
        }
    }
}