package org.seasar.teeda.extension.event;

import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;

import org.seasar.teeda.core.mock.MockFacesEvent;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class TreeEventWrapperTest extends TeedaTestCase {

    public void testGetPhaseId() throws Exception {
        MockUIComponent component = new MockUIComponent();
        component.setId("a");
        FacesEvent original = new MockFacesEvent(component) {

            private static final long serialVersionUID = 1L;

            public PhaseId getPhaseId() {
                return PhaseId.INVOKE_APPLICATION;
            }

        };
        TreeEventWrapper e = new TreeEventWrapper(original, "0", component);
        assertEquals(PhaseId.INVOKE_APPLICATION, e.getPhaseId());
    }

    public void testIsAppropriateListener_allwaysReturnFalse() throws Exception {
        MockUIComponent component = new MockUIComponent();
        component.setId("a");
        FacesEvent original = new MockFacesEvent(component) {

            private static final long serialVersionUID = 1L;

            public boolean isAppropriateListener(FacesListener listener) {
                return true;
            }


        };
        TreeEventWrapper e = new TreeEventWrapper(original, "0", component);
        assertFalse(e.isAppropriateListener(new FacesListener(){}));
    }
    
    public void testProcessListener_throwException() throws Exception {
        MockUIComponent component = new MockUIComponent();
        component.setId("a");
        FacesEvent original = new MockFacesEvent(component) {

            private static final long serialVersionUID = 1L;

        };
        TreeEventWrapper e = new TreeEventWrapper(original, "0", component);
        try {
            e.processListener(new FacesListener(){});
            fail();
        }catch(UnsupportedOperationException expected) {
            success();
        }
    }
}
