package org.seasar.teeda.core.config.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.element.AttributeElement;
import org.seasar.teeda.core.config.element.ComponentElement;
import org.seasar.teeda.core.config.element.FacetElement;
import org.seasar.teeda.core.config.element.PropertyElement;


public class ComponentElementImpl implements ComponentElement {

    private String componentType_;
    private String componentClass_;
    private List attributes_ = new ArrayList();
    private List properties_ = new ArrayList();
    private List facets_ = new ArrayList();
    
    public ComponentElementImpl(){
    }
    
    public void setComponentType(String componentType) {
        componentType_ = componentType;
    }

    public void setComponentClass(String componentClass) {
        componentClass_ = componentClass;
    }

    public String getComponentType() {
        return componentType_;
    }

    public String getComponentClass() {
        return componentClass_;
    }

    public void addAttributeElement(AttributeElement attribute) {
        attributes_.add(attribute);
    }

    public void addPropertyElement(PropertyElement property) {
        properties_.add(property);
    }

    public void addFacetElement(FacetElement facet) {
        facets_.add(facet);
    }

    public List getPropertyElements() {
        return properties_;
    }

    public List getFacetElements() {
        return facets_;
    }

    public List getAttributeElements() {
        return attributes_;
    }

}
