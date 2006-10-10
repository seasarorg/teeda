package org.seasar.teeda.extension.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;

public class TreeEventWrapper extends FacesEvent {

    private static final long serialVersionUID = 1L;

    private FacesEvent originalEvent;

    private String nodeId;

    public TreeEventWrapper(FacesEvent facesEvent, String nodeId,
            UIComponent component) {
        super(component);
        originalEvent = facesEvent;
        this.nodeId = nodeId;
    }

    public PhaseId getPhaseId() {
        return originalEvent.getPhaseId();
    }

    public void setPhaseId(PhaseId phaseId) {
        originalEvent.setPhaseId(phaseId);
    }

    public void queue() {
        originalEvent.queue();
    }

    public String toString() {
        return originalEvent.toString();
    }

    public boolean isAppropriateListener(FacesListener faceslistener) {
        return false;
    }

    public void processListener(FacesListener faceslistener) {
        throw new UnsupportedOperationException();
    }

    public FacesEvent getOriginalEvent() {
        return originalEvent;
    }

    public String getOriginalNodeId() {
        return nodeId;
    }
}
