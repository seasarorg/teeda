package org.seasar.teeda.core.config.element.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.seasar.framework.exception.EmptyRuntimeException;
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

/**
 * @author Shinpei Ohtani(aka shot)
 */
public class FacesConfigWrapperImpl implements FacesConfig {

    private List applications_ = new LinkedList();
    private List factories_ = new LinkedList();
    private List lifecycles_ = new LinkedList();
    private List referencedBeans_ = new LinkedList();
    private Map components_ = new HashMap();
    private Map convertersByIds_ = new HashMap();
    private Map convertersByClasses_ = new HashMap();
    private Map managedBeans_ = new HashMap();
    private Map navigationRules_ = new HashMap();
    private Map renderKits_ = new HashMap();
    private Map validators_ = new HashMap();

    public FacesConfigWrapperImpl(List facesConfig){
        if(facesConfig == null || facesConfig.size() == 0){
            throw new EmptyRuntimeException("FacesConfigWrapperImpl");
        }
        deployAllFacesConfig(facesConfig);
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
        navigationRules_.put(navigationRule.getFromViewId(), navigationRule);
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

    public Map getNavigationRuleElements() {
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

    private void deployAllFacesConfig(List facesConfig){
        FacesConfig config = null;
        for(Iterator itr = facesConfig.iterator();itr.hasNext();){
            Object o = itr.next();
            if(o instanceof FacesConfig){
                config = (FacesConfig)o;
                deployFacesConfig(config);
            }
        }
    }
    
    private void deployFacesConfig(FacesConfig facesConfig){
        factories_.addAll(facesConfig.getFactoryElements());
        applications_.addAll(facesConfig.getApplicationElements());
        lifecycles_.addAll(facesConfig.getLifecycleElements());
        referencedBeans_.addAll(facesConfig.getReferencedBeanElements());
        components_.putAll(facesConfig.getComponentElements());
        convertersByIds_.putAll(facesConfig.getConverterElementsById());
        convertersByClasses_.putAll(facesConfig.getConverterElementsByClass());
        managedBeans_.putAll(facesConfig.getManagedBeanElements());
        navigationRules_.putAll(facesConfig.getNavigationRuleElements());
        renderKits_.putAll(facesConfig.getRenderKitElements());
        validators_.putAll(facesConfig.getValidatorElements());
    }
}
