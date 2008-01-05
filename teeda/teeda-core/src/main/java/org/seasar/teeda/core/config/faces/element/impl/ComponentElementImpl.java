/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.config.faces.element.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.core.config.faces.element.AttributeElement;
import org.seasar.teeda.core.config.faces.element.ComponentElement;
import org.seasar.teeda.core.config.faces.element.FacetElement;
import org.seasar.teeda.core.config.faces.element.PropertyElement;

public class ComponentElementImpl implements ComponentElement {

    private String componentType_;

    private String componentClass_;

    private List attributes_ = new ArrayList();

    private List properties_ = new ArrayList();

    private List facets_ = new ArrayList();

    public ComponentElementImpl() {
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
