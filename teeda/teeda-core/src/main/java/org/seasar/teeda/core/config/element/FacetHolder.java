package org.seasar.teeda.core.config.element;

import java.util.List;


public interface FacetHolder{

    public void addFacetElement(FacetElement facet);
 
    public List getFacetElements();
}
