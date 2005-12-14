package org.seasar.teeda.core.config.element;

import java.util.List;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public interface FacetElement extends JsfConfig{
    
    public void setFacetName(String facetName);
    
    public void addFacetExtension(String facetExtension);
    
    public String getFacetName();
        
    public List getFacetExtensions();
}
