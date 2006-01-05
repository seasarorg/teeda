package org.seasar.teeda.core.mock;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;

import org.seasar.framework.util.ArrayUtil;


public class MockLifecycle extends Lifecycle {

    private PhaseListener[] phaseListeners_ = new PhaseListener[0];
    public MockLifecycle(){
    }
    
    public void addPhaseListener(PhaseListener listener) {
        phaseListeners_ = (PhaseListener[]) ArrayUtil.add(phaseListeners_, listener);
    }

    public void execute(FacesContext context) throws FacesException {
    }

    public PhaseListener[] getPhaseListeners() {
        return phaseListeners_;
    }

    public void removePhaseListener(PhaseListener listener) {
    }

    public void render(FacesContext context) throws FacesException {
    }

}
