package org.seasar.teeda.core.config.element;

import java.util.List;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public interface ManagedBeanElement extends JsfConfig, ListEntriesHolder, MapEntriesHolder{

    public void setManagedBeanName(String managedBeanName);

    public void setManagedBeanClass(String managedBeanClass);

    public void setManagedBeanScope(String managedBeanScope);
    
    public void addManagedPropertyElement(ManagedPropertyElement managedProperty);
    
    public String getManagedBeanName();

    public String getManagedBeanClass();

    public String getManagedBeanScope();
    
    public List getManagedProperties();
}
