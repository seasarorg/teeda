package org.seasar.teeda.core.lifecycle.impl;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.seasar.teeda.core.lifecycle.AbstractPhase;

public class InvokeApplicationPhase extends AbstractPhase{
	public void executePhase(FacesContext context){
		context.getViewRoot().processApplication(context);
	}

	/* (non-Javadoc)
	 * @see org.seasar.teeda.core.lifecycle.AbstractPhase#getCurrentPhaseId()
	 */
	protected PhaseId getCurrentPhaseId() {
		return PhaseId.INVOKE_APPLICATION;
	}

}
