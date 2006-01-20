package org.seasar.teeda.core.mock;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;

import org.seasar.framework.util.ArrayUtil;

public class MockLifecycleImpl extends MockLifecycle {

    private PhaseListener[] phaseListeners_ = new PhaseListener[0];

    public MockLifecycleImpl() {
    }

    public void addPhaseListener(PhaseListener listener) {
        phaseListeners_ = (PhaseListener[]) ArrayUtil.add(phaseListeners_,
                listener);
    }

    public void execute(FacesContext context) throws FacesException {
    }

    public PhaseListener[] getPhaseListeners() {
        return phaseListeners_;
    }

    public void removePhaseListener(PhaseListener listener) {
        phaseListeners_ = (PhaseListener[]) ArrayUtil.remove(phaseListeners_,
                listener);
    }

    public void render(FacesContext context) throws FacesException {
    }

    public void removeAllPhaseListener() {
        phaseListeners_ = new PhaseListener[0];
    }

}
