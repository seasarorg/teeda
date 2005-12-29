package org.seasar.teeda.core.scope;



public interface ScopeManager {

	public Scope getScope(String scopeKey);
	
	public void addScope(Scope scope, Object outerComponentScope) throws ScopeAlreadyRegisteredException;
	
	public boolean isDefaultScope(Scope scope);
	
	public void setScopeTranslator(ScopeTranslator translator);
	
	public ScopeTranslator getScopeTranslator();
	
}
