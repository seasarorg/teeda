package org.seasar.teeda.extension.event;

import javax.faces.event.FacesEvent;
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

}
