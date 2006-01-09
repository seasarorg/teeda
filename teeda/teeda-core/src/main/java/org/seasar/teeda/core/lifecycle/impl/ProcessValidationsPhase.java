package org.seasar.teeda.core.lifecycle.impl;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.seasar.teeda.core.lifecycle.AbstractPhase;

public class ProcessValidationsPhase extends AbstractPhase{
	
	public void executePhase(FacesContext context){
		context.getViewRoot().processValidators(context);
	}

	protected PhaseId getCurrentPhaseId() {
		return PhaseId.PROCESS_VALIDATIONS;
	}

}
