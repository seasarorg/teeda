package javax.faces.lifecycle;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;

public abstract class Lifecycle {

    public abstract void addPhaseListener(PhaseListener listener);

    public abstract void execute(FacesContext context)
            throws FacesException;

    public abstract PhaseListener[] getPhaseListeners();

    public abstract void removePhaseListener(PhaseListener listener);

    public abstract void render(FacesContext context)
            throws FacesException;
}
