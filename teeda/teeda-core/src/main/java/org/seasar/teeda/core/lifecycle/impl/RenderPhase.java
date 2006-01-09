package org.seasar.teeda.core.lifecycle.impl;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.event.PhaseId;

import org.seasar.teeda.core.lifecycle.AbstractPhase;

public class RenderPhase extends AbstractPhase{
	
	public void executePhase(FacesContext context){
		Application application = context.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		try {
			viewHandler.renderView(context, context.getViewRoot());
		} catch (IOException e) {
			throw new FacesException(e.getMessage(), e);
		} catch (EvaluationException ex) {
			Throwable cause = ex.getCause();
			if (cause instanceof RuntimeException) {
				throw (RuntimeException) cause;
			} else if (cause instanceof Error) {
				throw (Error) cause;
			} else {
				throw ex;
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.seasar.teeda.core.lifecycle.AbstractPhase#getCurrentPhaseId()
	 */
	protected PhaseId getCurrentPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}
