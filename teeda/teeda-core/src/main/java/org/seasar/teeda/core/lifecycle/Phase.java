package org.seasar.teeda.core.lifecycle;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

public interface Phase {

	public void prePhase(FacesContext context, PhaseId phaseId);
	
	public void doPhase(FacesContext context);
	
	public void postPhase(FacesContext context, PhaseId phaseId);
}
