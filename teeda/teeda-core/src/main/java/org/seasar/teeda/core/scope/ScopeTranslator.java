package org.seasar.teeda.core.scope;


public interface ScopeTranslator{
	public Scope toScope(Object obj);
	
	public Object toExternalComponentScope(Scope scope);
	
	public void addScope(Scope scope, Object externalComponentScope);
}
