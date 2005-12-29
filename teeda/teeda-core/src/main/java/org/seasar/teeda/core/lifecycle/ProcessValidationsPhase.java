package org.seasar.teeda.core.lifecycle;

import javax.faces.context.FacesContext;

public class ProcessValidationsPhase {
	public void executePhase(FacesContext context){
		context.getViewRoot().processValidators(context);
	}

}
