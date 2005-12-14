package org.seasar.teeda.core.config.element;

import java.util.List;


public interface AttributeHolder{

    public void addAttributeElement(AttributeElement attribute);
    
    public List getAttributeElements();
}
