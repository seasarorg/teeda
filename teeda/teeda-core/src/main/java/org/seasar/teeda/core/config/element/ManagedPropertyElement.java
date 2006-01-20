package org.seasar.teeda.core.config.element;

/**
 * @author shot
 * 
 */
public interface ManagedPropertyElement 
    extends JsfConfig, NullValueHolder, ListEntriesHolder, MapEntriesHolder {

    public void setPropertyName(String propertyName);
    
    public void setPropertyClass(String propertyClass);
    
    public void setValue(String value);
                
    public String getPropertyName();
    
    public String getPropertyClas();
    
    public String getValue();
            
}
