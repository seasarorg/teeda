package org.seasar.teeda.core.managedbean.impl;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.seasar.teeda.core.managedbean.IllegalManagedBeanScopeException;
import org.seasar.teeda.core.managedbean.ManagedBeanScopeSaver;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.scope.ScopeAlreadyRegisteredException;
import org.seasar.teeda.core.scope.ScopeSaver;
import org.seasar.teeda.core.scope.impl.ApplicationScopeSaver;
import org.seasar.teeda.core.scope.impl.NoneScopeSaver;
import org.seasar.teeda.core.scope.impl.RequestScopeSaver;
import org.seasar.teeda.core.scope.impl.SessionScopeSaver;

public class ManagedBeanScopeSaverImpl implements ManagedBeanScopeSaver{

	private static Map scopeSavers_ = new HashMap();

	static {
		scopeSavers_.put(Scope.APPLICATION, new ApplicationScopeSaver());
		scopeSavers_.put(Scope.REQUEST, new RequestScopeSaver());
		scopeSavers_.put(Scope.SESSION, new SessionScopeSaver());
		scopeSavers_.put(Scope.NONE, new NoneScopeSaver());
	}

	public ManagedBeanScopeSaverImpl(){
	}
	
	public void saveToScope(FacesContext context, Scope scope, String key, Object value){
		if(scope == null){
			throw new IllegalArgumentException();
		}
		ScopeSaver saver = (ScopeSaver) scopeSavers_.get(scope);
		if (saver != null) {
			saver.saveToScope(context, key, value);
		}else{
			throw new IllegalManagedBeanScopeException(key, value);
		}
	}
	
	public void addScope(Scope scope, ScopeSaver saver) throws ScopeAlreadyRegisteredException {
		if(scopeSavers_.containsKey(scope)){
			throw new ScopeAlreadyRegisteredException(new Object[]{scope.getScopeKey()});
		}
		scopeSavers_.put(scope, saver);
	}

}
