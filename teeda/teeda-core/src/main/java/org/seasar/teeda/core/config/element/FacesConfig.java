package org.seasar.teeda.core.config.element;

import java.util.List;
import java.util.Map;

/**
 * @author Shinpei Ohtani(aka shot)
 * 
 */
public interface FacesConfig extends JsfConfig{

    public void addApplicationElement(ApplicationElement application);

    public void addFactoryElement(FactoryElement factory);
    
    public void addComponentElement(ComponentElement component);
    
    public void addConverterElement(ConverterElement converter);
    
    public void addManagedBeanElement(ManagedBeanElement managedBean);

    public void addNavigationRuleElement(NavigationRuleElement navigationRule);
    
    public void addRenderKitElement(RenderKitElement renderKit);
    
    public void addLifecycleElement(LifecycleElement lifecycle);
    
    public void addValidatorElement(ValidatorElement validator);
    
    public void addReferencedBeanElement(ReferencedBeanElement referencedBean);
    
    public List getApplicationElements();
    
    public List getFactoryElements();
    
    public List getLifecycleElements();

    public Map getComponentElements();

    public Map getConverterElementsById();
    
    public Map getConverterElementsByClass();
    
    public Map getManagedBeanElements();
    
    public List getNavigationRuleElements();
    
    public Map getRenderKitElements();
        
    public Map getValidatorElements();
    
    public List getReferencedBeanElements();
}
