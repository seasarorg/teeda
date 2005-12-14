package org.seasar.teeda.core.config.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.element.ListEntriesElement;
import org.seasar.teeda.core.config.element.ManagedBeanElement;
import org.seasar.teeda.core.config.element.ManagedPropertyElement;
import org.seasar.teeda.core.config.element.MapEntriesElement;

public class ManagedBeanElementImpl implements ManagedBeanElement{

    private String managedBeanName_;
    private String managedBeanClass_;
    private String managedBeanScope_;
    private List managedProperties_ = new ArrayList();
    private ListEntriesElement listEntries_;
    private MapEntriesElement mapEntries_;
    public ManagedBeanElementImpl(){
    }
    
    public void setManagedBeanName(String managedBeanName) {
        managedBeanName_ = managedBeanName;
    }

    public void setManagedBeanClass(String managedBeanClass) {
        managedBeanClass_ = managedBeanClass;
    }

    public void setManagedBeanScope(String managedBeanScope) {
        managedBeanScope_ = managedBeanScope;
    }

    public String getManagedBeanName() {
        return managedBeanName_;
    }

    public String getManagedBeanClass() {
        return managedBeanClass_;
    }

    public String getManagedBeanScope() {
        return managedBeanScope_;
    }

    public void addManagedPropertyElement(ManagedPropertyElement managedProperty) {
        managedProperties_.add(managedProperty);
    }

    public void setListEntries(ListEntriesElement listEntries) {
        listEntries_ = listEntries;
    }

    public ListEntriesElement getListEntries() {
        return listEntries_;
    }

    public void setMapEntries(MapEntriesElement mapEntries) {
        mapEntries_ = mapEntries;
    }

    public MapEntriesElement getMapEntries() {
        return mapEntries_;
    }

    public List getManagedProperties() {
        return managedProperties_;
    }
    
}
