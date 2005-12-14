package org.seasar.teeda.core.managedbean;


public interface ManagedBeanFactory {

    public Object getManagedBean(String name);
    
    public void setManagedBean(String name, Class type, Scope scope);
    
    public void setManagedBean(String name, Class type, Scope scope, String initMethodName, String destroyMethodName);
}
