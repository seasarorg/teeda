package org.seasar.teeda.core.scope;

import javax.faces.context.FacesContext;

public interface ScopeSaver {

	public void saveToScope(FacesContext context, String key, Object value);
}
