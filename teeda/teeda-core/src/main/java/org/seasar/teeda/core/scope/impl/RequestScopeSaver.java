package org.seasar.teeda.core.scope.impl;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.scope.ScopeSaver;

public class RequestScopeSaver implements ScopeSaver {

	public void saveToScope(FacesContext context, String key, Object value) {
		Map requestMap = context.getExternalContext().getRequestMap();
		requestMap.put(key, value);
	}

}
