package org.seasar.teeda.core.config.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.element.FacetElement;


public class FacetElementImpl implements FacetElement {

    private String facetName_;
    private List facetExtensions_ = new ArrayList();
    
    public FacetElementImpl(){
    }

    public void setFacetName(String facetName) {
        facetName_ = facetName;
    }

    public void addFacetExtension(String facetExtension) {
        facetExtensions_.add(facetExtension);
    }

    public String getFacetName() {
        return facetName_;
    }

    public List getFacetExtensions() {
        return facetExtensions_;
    }

}
