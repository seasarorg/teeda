/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ProcessValidationsPhaseTest extends TeedaTestCase {

    public void testExecutePhase() throws Exception {
        // # Arrange
        FacesContext context = new MockFacesContextImpl();
        context.setViewRoot(new MockViewRoot());
        ProcessValidationsPhase phase = new ProcessValidationsPhase();

        // # Act
        phase.executePhase(context);
        
        // # Assert
        MockViewRoot root = (MockViewRoot) context.getViewRoot();
        assertTrue(root.isCalled());
    }

    public void testGetCurrentPhaseId() throws Exception {
        assertEquals(PhaseId.PROCESS_VALIDATIONS, new ProcessValidationsPhase()
                .getCurrentPhaseId());
    }

    private static class MockViewRoot extends UIViewRoot {

        private boolean isCalled_ = false;
        
        public void processValidators(FacesContext context) {
            isCalled_ = true;
        }
        
        public boolean isCalled() {
            return isCalled_;
        }
    }
}
