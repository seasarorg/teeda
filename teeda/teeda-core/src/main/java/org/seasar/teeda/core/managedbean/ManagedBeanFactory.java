package org.seasar.teeda.core.managedbean;

import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.scope.ScopeManager;


public interface ManagedBeanFactory {

    public Object getManagedBean(String name);
    
    public Scope getManagedBeanScope(String name);
    
    public void setManagedBean(String name, Class type, Scope scope);
    
    public void setManagedBean(String name, Class type, Scope scope, String initMethodName, String destroyMethodName);
    
    public void setScopeManager(ScopeManager ScopeManager);
    
    public ScopeManager getScopeManager();
    
	public void setManagedBeanScopeSaver(ManagedBeanScopeSaver managedBeanScopeSaver);
	
	public ManagedBeanScopeSaver getManagedBeanScopeSaver();

}
