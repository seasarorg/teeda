package org.seasar.teeda.core.config.element;

import java.util.List;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public interface AttributeElement extends JsfConfig, DefaultValueHolder, SuggestedValueHolder {

    public void setAttributeName(String attributeName);
    
    public void setAttributeClass(String attributeClass);

    public void addAttributeExtension(String attributeExtension);
    
    public String getAttributeName();
    
    public String getAttributeClass();
    
    public List getAttributeExtensions();
}
