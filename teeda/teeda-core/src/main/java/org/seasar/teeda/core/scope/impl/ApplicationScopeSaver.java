package org.seasar.teeda.core.scope.impl;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.scope.ScopeSaver;

public class ApplicationScopeSaver implements ScopeSaver {

	public void saveToScope(FacesContext context, String key, Object value) {
		Map applicationMap = context.getExternalContext().getApplicationMap();
		applicationMap.put(key, value);
	}

}
