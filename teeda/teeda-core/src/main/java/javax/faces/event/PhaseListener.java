package javax.faces.event;

import java.io.Serializable;
import java.util.EventListener;

public interface PhaseListener extends EventListener, Serializable{

    public void afterPhase(PhaseEvent phaseevent);

    public void beforePhase(PhaseEvent phaseevent);

    public PhaseId getPhaseId();

}
