package org.seasar.teeda.core.lifecycle;

import javax.faces.context.FacesContext;

public class ApplyRequestValuesPhase {

	public void executePhase(FacesContext context){
		context.getViewRoot().processDecodes(context);
	}
}
