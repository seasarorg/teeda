package org.seasar.teeda.core.lifecycle;

import javax.faces.context.FacesContext;

public class InvokeApplicationPhase {
	public void executePhase(FacesContext context){
		context.getViewRoot().processApplication(context);
	}

}
