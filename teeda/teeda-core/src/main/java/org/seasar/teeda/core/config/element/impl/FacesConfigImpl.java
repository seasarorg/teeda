package org.seasar.teeda.core.config.element.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.seasar.teeda.core.config.element.ApplicationElement;
import org.seasar.teeda.core.config.element.ComponentElement;
import org.seasar.teeda.core.config.element.ConverterElement;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.element.FactoryElement;
import org.seasar.teeda.core.config.element.LifecycleElement;
import org.seasar.teeda.core.config.element.ManagedBeanElement;
import org.seasar.teeda.core.config.element.NavigationRuleElement;
import org.seasar.teeda.core.config.element.ReferencedBeanElement;
import org.seasar.teeda.core.config.element.RenderKitElement;
import org.seasar.teeda.core.config.element.ValidatorElement;

public class FacesConfigImpl implements FacesConfig {

    private List applications_ = new ArrayList();
    private List factories_ = new ArrayList();
    private List lifecycles_ = new ArrayList();
    private List referencedBeans_ = new ArrayList();
    private List navigationRules_ = new ArrayList();
    private Map components_ = new HashMap();
    private Map convertersByIds_ = new HashMap();
    private Map convertersByClasses_ = new HashMap();
    private Map managedBeans_ = new HashMap();
    private Map renderKits_ = new HashMap();
    private Map validators_ = new HashMap();
    
    public FacesConfigImpl(){
        
    }

    public void addApplicationElement(ApplicationElement application) {
        applications_.add(application);
    }

    public void addFactoryElement(FactoryElement factory) {
        factories_.add(factory);
    }

    public void addComponentElement(ComponentElement component) {
        components_.put(component.getComponentType(), component);
    }

    public void addConverterElement(ConverterElement converter) {
        if(converter.getConverterId() != null){
            convertersByIds_.put(converter.getConverterId(), converter);
        }else if(converter.getConverterForClass() != null){
            convertersByClasses_.put(converter.getConverterForClass(), converter);
        }
    }

    public void addManagedBeanElement(ManagedBeanElement managedBean) {
        managedBeans_.put(managedBean.getManagedBeanName(), managedBean);
    }

    public void addNavigationRuleElement(NavigationRuleElement navigationRule) {
        navigationRules_.add(navigationRule);
    }

    public void addRenderKitElement(RenderKitElement renderKit) {
        renderKits_.put(renderKit.getRenderKitId(), renderKit);
    }

    public void addLifecycleElement(LifecycleElement lifecycle) {
        lifecycles_.add(lifecycle);
    }

    public void addValidatorElement(ValidatorElement validator) {
        validators_.put(validator.getValidatorId(), validator);
    }

    public void addReferencedBeanElement(ReferencedBeanElement referencedBean) {
        referencedBeans_.add(referencedBean);
    }

    public List getApplicationElements() {
        return applications_;
    }

    public List getFactoryElements() {
        return factories_;
    }

    public List getLifecycleElements() {
        return lifecycles_;
    }

    public Map getComponentElements() {
        return components_;
    }

    public Map getManagedBeanElements() {
        return managedBeans_;
    }

    public List getNavigationRuleElements() {
        return navigationRules_;
    }

    public Map getRenderKitElements() {
        return renderKits_;
    }

    public Map getValidatorElements() {
        return validators_;
    }

    public Map getConverterElementsById() {
        return convertersByIds_;
    }

    public Map getConverterElementsByClass() {
        return convertersByClasses_;
    }

    public List getReferencedBeanElements() {
        return referencedBeans_;
    }

}