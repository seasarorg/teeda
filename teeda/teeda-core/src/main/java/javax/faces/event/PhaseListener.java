package javax.faces.event;

import java.io.Serializable;
import java.util.EventListener;

public interface PhaseListener extends EventListener, Serializable{

    public void afterPhase(PhaseEvent event);

    public void beforePhase(PhaseEvent event);

    public PhaseId getPhaseId();

}
