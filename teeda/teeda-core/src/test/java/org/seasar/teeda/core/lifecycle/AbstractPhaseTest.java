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
package org.seasar.teeda.core.lifecycle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;

import junitx.framework.ObjectAssert;

import org.seasar.teeda.core.mock.MockLifecycle;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class AbstractPhaseTest extends TeedaTestCase {

    private PhaseListener[] listeners_;

    protected void setUp() throws Exception {
        listeners_ = getLifecycle().clearAllPhaseListener();
    }

    protected void tearDown() throws Exception {
        getLifecycle().setupDefaultPhaseListener(listeners_);
    }

    public void testPrePhase() throws Exception {
        // # Arrange #
        List list = new ArrayList();
        MockNotifyPhaseListener listener = new MockNotifyBeforePhaseListener(
                list);
        listener.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
        getLifecycle().addPhaseListener(listener);
        TargetPhase phase = new TargetPhase();
        phase.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);

        // # Act #
        phase.prePhase(getFacesContext());

        // # Assert #
        assertEquals(1, list.size());
        PhaseEvent event = (PhaseEvent) list.get(0);
        assertEquals(PhaseId.APPLY_REQUEST_VALUES, event.getPhaseId());
        assertEquals(getLifecycle(), event.getSource());
    }

    public void testPostPhase() {
        // # Arrange #
        PhaseListener listener1 = new MockAnyPhaseListener();
        getLifecycle().addPhaseListener(listener1);

        PhaseListener listener2 = new MockProcessValidationsPhaseListener();
        getLifecycle().addPhaseListener(listener2);

        LinkedList orderStack = new LinkedList();
        MockNotifiedPhase phase = new MockNotifiedPhase(orderStack);
        phase.setPhaseId(PhaseId.PROCESS_VALIDATIONS);

        // # Act #
        phase.postPhase(getFacesContext());

        // # Assert #
        assertEquals(2, orderStack.size());
        PhaseListener l1 = (PhaseListener) orderStack.get(0);
        assertEquals(PhaseId.PROCESS_VALIDATIONS, l1.getPhaseId());
        ObjectAssert.assertSame(listener2, l1);
        PhaseListener l2 = (PhaseListener) orderStack.get(1);
        assertEquals(PhaseId.ANY_PHASE, l2.getPhaseId());
        ObjectAssert.assertSame(listener1, l2);
    }

    public void testIsTargetListener_succeed() {
        // # Arrange #
        TargetPhase p = new TargetPhase();
        p.setPhaseId(PhaseId.ANY_PHASE);
        PhaseListener listener = new MockAnyPhaseListener();

        // # Act and Assert #
        assertTrue(p.isTargetListener(listener, PhaseId.APPLY_REQUEST_VALUES));

        // # Arrange #
        p.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
        listener = new MockApplyRequestValuesPhaseListener();

        // # Act and Assert #
        assertTrue(p.isTargetListener(listener, PhaseId.APPLY_REQUEST_VALUES));

        // # Arrange #
        p.setPhaseId(PhaseId.INVOKE_APPLICATION);
        listener = new MockInvokeApplicationPhaseListener();

        // # Act and Assert #
        assertTrue(p.isTargetListener(listener, PhaseId.INVOKE_APPLICATION));

        // # Arrange #
        p.setPhaseId(PhaseId.PROCESS_VALIDATIONS);
        listener = new MockProcessValidationsPhaseListener();

        // # Act and Assert #
        assertTrue(p.isTargetListener(listener, PhaseId.PROCESS_VALIDATIONS));

        // # Arrange #
        p.setPhaseId(PhaseId.RENDER_RESPONSE);
        listener = new MockRenderResponsePhaseListener();

        // # Act and Assert #
        assertTrue(p.isTargetListener(listener, PhaseId.RENDER_RESPONSE));

        // # Arrange #
        p.setPhaseId(PhaseId.RESTORE_VIEW);
        listener = new MockRestoreViewPhaseListener();

        // # Act and Assert #
        assertTrue(p.isTargetListener(listener, PhaseId.RESTORE_VIEW));

        // # Arrange #
        p.setPhaseId(PhaseId.UPDATE_MODEL_VALUES);
        listener = new MockUpdateModelValuesPhaseListener();

        // # Act and Assert #
        assertTrue(p.isTargetListener(listener, PhaseId.UPDATE_MODEL_VALUES));
    }

    public void testIsTargetListener_fail() throws Exception {
        // # Arrange #
        TargetPhase p = new TargetPhase();
        p.setPhaseId(PhaseId.PROCESS_VALIDATIONS);
        PhaseListener listener = new MockApplyRequestValuesPhaseListener();

        // # Act and Assert #
        assertFalse(p.isTargetListener(listener, PhaseId.PROCESS_VALIDATIONS));
    }

    public void testGetLifecycle() {
        TargetPhase p = new TargetPhase();
        Lifecycle lifecycle = p.getLifecycle();
        assertNotNull(lifecycle);
        assertTrue(lifecycle instanceof MockLifecycle);
    }

    private static class TargetPhase extends AbstractPhase {

        private PhaseId phaseId_ = PhaseId.ANY_PHASE;

        protected void executePhase(FacesContext context) {
        }

        protected PhaseId getCurrentPhaseId() {
            return phaseId_;
        }

        protected void setPhaseId(PhaseId phaseId) {
            phaseId_ = phaseId;
        }
    }

    private static class MockPhaseListener implements PhaseListener {
        private static final long serialVersionUID = 1L;

        private PhaseId phaseId_;

        public MockPhaseListener(PhaseId phaseId) {
            phaseId_ = phaseId;
        }

        public void afterPhase(PhaseEvent event) {
        }

        public void beforePhase(PhaseEvent event) {
        }

        public PhaseId getPhaseId() {
            return phaseId_;
        }

    }

    private static class MockAnyPhaseListener extends MockPhaseListener {
        private static final long serialVersionUID = 1L;

        public MockAnyPhaseListener() {
            super(PhaseId.ANY_PHASE);
        }
    }

    private static class MockApplyRequestValuesPhaseListener extends
            MockPhaseListener {
        private static final long serialVersionUID = 1L;

        public MockApplyRequestValuesPhaseListener() {
            super(PhaseId.APPLY_REQUEST_VALUES);
        }
    }

    private static class MockInvokeApplicationPhaseListener extends
            MockPhaseListener {
        private static final long serialVersionUID = 1L;

        public MockInvokeApplicationPhaseListener() {
            super(PhaseId.INVOKE_APPLICATION);
        }
    }

    private static class MockProcessValidationsPhaseListener extends
            MockPhaseListener {
        private static final long serialVersionUID = 1L;

        public MockProcessValidationsPhaseListener() {
            super(PhaseId.PROCESS_VALIDATIONS);
        }
    }

    private static class MockRenderResponsePhaseListener extends
            MockPhaseListener {
        private static final long serialVersionUID = 1L;

        public MockRenderResponsePhaseListener() {
            super(PhaseId.RENDER_RESPONSE);
        }
    }

    private static class MockRestoreViewPhaseListener extends MockPhaseListener {
        private static final long serialVersionUID = 1L;

        public MockRestoreViewPhaseListener() {
            super(PhaseId.RESTORE_VIEW);
        }
    }

    private static class MockUpdateModelValuesPhaseListener extends
            MockPhaseListener {
        private static final long serialVersionUID = 1L;

        public MockUpdateModelValuesPhaseListener() {
            super(PhaseId.UPDATE_MODEL_VALUES);
        }
    }

    private static class MockNotifyPhaseListener implements PhaseListener {
        public static final int NOTIFY_BEFORE_PHASE = 0;

        public static final int NOTIFY_AFTER_PHASE = 1;

        public static final int NOTIFY_BOTH_PHASE = 2;

        private static final long serialVersionUID = 1L;

        private List notify_;

        private PhaseId phaseId_;

        private int whenNotify_ = -1;

        public MockNotifyPhaseListener(List notify) {
            this(notify, NOTIFY_BOTH_PHASE);
        }

        public MockNotifyPhaseListener(List notify, int whenNotify) {
            notify_ = notify;
            whenNotify_ = whenNotify;
        }

        public void afterPhase(PhaseEvent event) {
            if (whenNotify_ == NOTIFY_AFTER_PHASE
                    || whenNotify_ == NOTIFY_BOTH_PHASE) {
                notify_.add(event);
            }
        }

        public void beforePhase(PhaseEvent event) {
            if (whenNotify_ == NOTIFY_BEFORE_PHASE
                    || whenNotify_ == NOTIFY_BOTH_PHASE) {
                notify_.add(event);
            }
        }

        public void setPhaseId(PhaseId phaseId) {
            phaseId_ = phaseId;
        }

        public PhaseId getPhaseId() {
            return phaseId_;
        }
    }

    private static class MockNotifyBeforePhaseListener extends
            MockNotifyPhaseListener {
        private static final long serialVersionUID = 1L;

        public MockNotifyBeforePhaseListener(List notify) {
            super(notify, NOTIFY_BEFORE_PHASE);
        }
    }

    private static class MockNotifiedPhase extends TargetPhase {

        private LinkedList list_;

        public MockNotifiedPhase(LinkedList list) {
            list_ = list;
        }

        protected boolean isTargetListener(PhaseListener listener,
                PhaseId phaseId) {
            list_.add(listener);
            return super.isTargetListener(listener, phaseId);
        }

    }

}
