package org.seasar.teeda.core.config.element;

/**
 * @author Shinpei Ohtani(aka shot)
 */
public interface ReferencedBeanElement extends JsfConfig {
    
    public void setReferencedBeanName(String referencedBeanName);
    
    public void setReferencedBeanClass(String referencedBeanClass);
    
    public String getReferencedBeanName();

    public String getReferencedBeanClass();
    
}
