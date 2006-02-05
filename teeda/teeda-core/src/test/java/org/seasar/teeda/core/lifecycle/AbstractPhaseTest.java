package org.seasar.teeda.core.lifecycle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.seasar.teeda.core.unit.TeedaTestCase;

public class AbstractPhaseTest extends TeedaTestCase {

    //TODO testing
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
        MockBeforePhaseNotifyListener listener = new MockBeforePhaseNotifyListener(
                list);
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
    }
    
    public void testInitializeChildren() {
    }

    public void testIsTargetListener() {
    }

    public void testGetLifecycle() {
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

    private static class MockBeforePhaseNotifyListener implements PhaseListener {

        private List notify_;

        public MockBeforePhaseNotifyListener(List notify) {
            notify_ = notify;
        }

        public void afterPhase(PhaseEvent event) {
        }

        public void beforePhase(PhaseEvent event) {
            notify_.add(event);
        }

        public PhaseId getPhaseId() {
            return PhaseId.APPLY_REQUEST_VALUES;
        }

    }
}
