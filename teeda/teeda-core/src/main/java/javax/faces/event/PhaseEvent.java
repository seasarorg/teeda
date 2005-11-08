package javax.faces.event;

import java.util.EventObject;

import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;

/**
 * TODO TEST
 */
public class PhaseEvent extends EventObject {

	private FacesContext facesContext_;
	private PhaseId phaseId_;
	
	public PhaseEvent(FacesContext context, PhaseId phaseId, Lifecycle lifecycle){
		super(lifecycle);

		assertNotNull(context);
		assertNotNull(phaseId);
		assertNotNull(lifecycle);
		
		facesContext_ = context;
		phaseId_ = phaseId;
	}
	
	public FacesContext getFacesContext() {
		return facesContext_;
	}
	
	public PhaseId getPhaseId() {
		return phaseId_;
	}
	
	private static void assertNotNull(Object obj){
		if(obj == null){
			throw new NullPointerException();
		}
	}
}
