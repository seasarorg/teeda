package org.seasar.teeda.core.config.element;


/**
 * @author shot
 */
public interface ComponentElement 
    extends JsfConfig, FacetHolder, AttributeHolder, PropertyHolder {

    public void setComponentType(String componentType);

    public void setComponentClass(String componentClass);
    
    public String getComponentType();
    
    public String getComponentClass();
    
}
