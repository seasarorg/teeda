package org.seasar.teeda.core.config.element.impl;

import org.seasar.teeda.core.config.element.ReferencedBeanElement;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class ReferencedBeanElementImpl implements ReferencedBeanElement {

    private String referencedBeanName_;
    private String referencedClassName_;
    public ReferencedBeanElementImpl(){
    }
    
    public void setReferencedBeanName(String referencedBeanName) {
        referencedBeanName_ = referencedBeanName;
    }

    public void setReferencedBeanClass(String referencedBeanClass) {
        referencedClassName_ = referencedBeanClass;
    }

    public String getReferencedBeanName() {
        return referencedBeanName_;
    }

    public String getReferencedBeanClass() {
        return referencedClassName_;
    }

}
