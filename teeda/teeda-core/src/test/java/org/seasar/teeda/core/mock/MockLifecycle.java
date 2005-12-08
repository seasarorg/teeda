package org.seasar.teeda.core.mock;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;


public class MockLifecycle extends Lifecycle {

    /**
     *
     */

    public void addPhaseListener(PhaseListener listener) {
    }

    /**
     *
     */

    public void execute(FacesContext context) throws FacesException {
    }

    /**
     *
     */

    public PhaseListener[] getPhaseListeners() {
        return null;
    }

    /**
     *
     */

    public void removePhaseListener(PhaseListener listener) {
    }

    /**
     *
     */

    public void render(FacesContext context) throws FacesException {
    }

}
