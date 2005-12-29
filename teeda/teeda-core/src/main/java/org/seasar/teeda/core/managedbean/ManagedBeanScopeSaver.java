package org.seasar.teeda.core.managedbean;

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.scope.ScopeSaver;


public interface ManagedBeanScopeSaver {

	public void saveToScope(FacesContext context, Scope scope, String key, Object value);
	
	public void addScope(Scope scope, ScopeSaver saver);
}
