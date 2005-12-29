package org.seasar.teeda.core.scope.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.scope.ScopeAlreadyRegisteredException;
import org.seasar.teeda.core.scope.ScopeManager;
import org.seasar.teeda.core.scope.ScopeTranslator;

public class ScopeManagerImpl implements ScopeManager {

	private ScopeTranslator translator_;
	private static Map SCOPES = new HashMap();
	private static final Map DEFAULT_SCOPES;

	static{
		SCOPES.put(Scope.APPLICATION.getScopeKey(), Scope.APPLICATION);
		SCOPES.put(Scope.SESSION.getScopeKey(), Scope.SESSION);
		SCOPES.put(Scope.REQUEST.getScopeKey(), Scope.REQUEST);
		SCOPES.put(Scope.NONE.getScopeKey(), Scope.NONE);
		Map map = new HashMap();
		map.put(Scope.APPLICATION.getScopeKey(), Scope.APPLICATION);
		map.put(Scope.SESSION.getScopeKey(), Scope.SESSION);
		map.put(Scope.REQUEST.getScopeKey(), Scope.REQUEST);
		map.put(Scope.NONE.getScopeKey(), Scope.NONE);
		DEFAULT_SCOPES = Collections.unmodifiableMap(map);
	}
	public ScopeManagerImpl(){
	}
	
	public Scope getScope(String scopeKey) {
		if(scopeKey == null){
			throw new IllegalArgumentException();
		}
		return (Scope)SCOPES.get(scopeKey);
	}

	public void addScope(Scope scope, Object outerComponentScope) throws ScopeAlreadyRegisteredException {
		String scopeKey = scope.getScopeKey();
		if(SCOPES.containsKey(scopeKey)){
			throw new ScopeAlreadyRegisteredException(new Object[]{scopeKey});
		}
		SCOPES.put(scope.getScopeKey(), scope);
		if(translator_ != null){
			translator_.addScope(scope, outerComponentScope);
		}
	}

	public boolean isDefaultScope(Scope scope) {
		return DEFAULT_SCOPES.containsValue(scope);
	}
	
	public void setScopeTranslator(ScopeTranslator translator){
		translator_ = translator;
	}
	
	public ScopeTranslator getScopeTranslator(){
		return translator_;
	}
	
}

