package org.seasar.teeda.core.config.element;

import java.util.List;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public interface PropertyElement extends JsfConfig, DefaultValueHolder, SuggestedValueHolder {

    public void setPropertyName(String attributeName);
    
    public void setPropertyClass(String attributeClass);
    
    public void addPropertyExtension(String propertyExtension);
    
    public String getPropertyName();
    
    public String getPropertyClass();

    public List getPropertyExtensions();

}
