package org.seasar.teeda.core.lifecycle;

import javax.faces.context.FacesContext;

public interface Phase {

	public void prePhase(FacesContext context);
	
	public void doPhase(FacesContext context);
	
	public void postPhase(FacesContext context);
}

