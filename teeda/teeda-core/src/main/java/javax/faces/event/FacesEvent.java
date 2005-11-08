package javax.faces.event;

import java.util.EventObject;

import javax.faces.component.UIComponent;

public abstract class FacesEvent extends EventObject {

	protected PhaseId phaseId_;

	public FacesEvent(UIComponent component) {
		super(component);
		if (component == null) {
			throw new IllegalArgumentException("FacesEvent");
		}
		phaseId_ = PhaseId.ANY_PHASE;
	}

	public UIComponent getComponent() {
		return (UIComponent) getSource();
	}

	public PhaseId getPhaseId() {
		return phaseId_;
	}

	public void setPhaseId(PhaseId phaseId) {
		phaseId_ = phaseId;
	}

	public abstract boolean isAppropriateListener(FacesListener listener);

	public abstract void processListener(FacesListener listener);

	public void queue() {
		((UIComponent) getSource()).queueEvent(this);
	}

}
