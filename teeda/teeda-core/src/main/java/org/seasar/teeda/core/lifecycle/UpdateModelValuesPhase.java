package org.seasar.teeda.core.lifecycle;

import javax.faces.context.FacesContext;

public class UpdateModelValuesPhase {
	public void executePhase(FacesContext context){
		context.getViewRoot().processUpdates(context);
	}

}
