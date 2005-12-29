package org.seasar.teeda.core.scope.impl;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.scope.ScopeSaver;

public class SessionScopeSaver implements ScopeSaver {

	public void saveToScope(FacesContext context, String key, Object value) {
		Map sessionMap = context.getExternalContext().getSessionMap();
		sessionMap.put(key, value);
	}

}
