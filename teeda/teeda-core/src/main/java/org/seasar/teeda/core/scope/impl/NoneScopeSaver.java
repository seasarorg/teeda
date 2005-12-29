package org.seasar.teeda.core.scope.impl;

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.scope.ScopeSaver;

public class NoneScopeSaver implements ScopeSaver {

	public void saveToScope(FacesContext context, String key, Object value) {
		//do nothing.
	}

}
