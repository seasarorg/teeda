/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.mock;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.el.PropertyResolver;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.ValueBinding;
import javax.faces.el.VariableResolver;
import javax.faces.event.ActionListener;
import javax.faces.validator.Validator;

import org.seasar.teeda.core.util.ClassUtil;

/**
 * @author shot
 */
public class MockApplicationImpl extends Application {

    private String bundle_;

    private ActionListener actionListener_;

    private Locale defaultLocale_;

    private Collection locales_;

    private VariableResolver variableResolver_;

    private PropertyResolver propertyResolver_;

    private NavigationHandler navigationHandler_;

    private ViewHandler viewHandler_;

    private StateManager stateManager_;

    private String defaultRenderKitId_;

    private Map componentClassMap_ = new HashMap();

    private Map convertersById_ = new HashMap();

    private Map convertersByClass_ = new HashMap();

    private Map validators_ = new HashMap();

    public MockApplicationImpl() {
    }

    public ActionListener getActionListener() {
        return actionListener_;
    }

    public void setActionListener(ActionListener actionListener) {
        actionListener_ = actionListener;
    }

    public Locale getDefaultLocale() {
        return defaultLocale_;
    }

    public void setDefaultLocale(Locale locale) {
        defaultLocale_ = locale;
    }

    public String getDefaultRenderKitId() {
        return defaultRenderKitId_;
    }

    public void setDefaultRenderKitId(String renderKitId) {
        defaultRenderKitId_ = renderKitId;
    }

    public String getMessageBundle() {
        return bundle_;
    }

    public void setMessageBundle(String bundle) {
        bundle_ = bundle;
    }

    public Iterator getSupportedLocales() {
        return (locales_ != null) ? locales_.iterator()
                : Collections.EMPTY_LIST.iterator();
    }

    public void setSupportedLocales(Collection locales) {
        locales_ = locales;
    }

    public NavigationHandler getNavigationHandler() {
        return navigationHandler_;
    }

    public void setNavigationHandler(NavigationHandler handler) {
        navigationHandler_ = handler;
    }

    public PropertyResolver getPropertyResolver() {
        if (propertyResolver_ == null) {
            propertyResolver_ = new MockPropertyResolver();
        }
        return propertyResolver_;
    }

    public void setPropertyResolver(PropertyResolver resolver) {
        propertyResolver_ = resolver;
    }

    public VariableResolver getVariableResolver() {
        if (variableResolver_ == null) {
            variableResolver_ = new MockVariableResolver();
        }
        return variableResolver_;
    }

    public void setVariableResolver(VariableResolver resolver) {
        variableResolver_ = resolver;
    }

    public ViewHandler getViewHandler() {
        if (viewHandler_ == null) {
            viewHandler_ = new MockViewHandler();
        }
        return viewHandler_;
    }

    public void setViewHandler(ViewHandler handler) {
        viewHandler_ = handler;
    }

    public StateManager getStateManager() {
        return stateManager_;
    }

    public void setStateManager(StateManager manager) {
        stateManager_ = manager;
    }

    public void addComponent(String componentType, String componentClassName) {
        Class clazz = ClassUtil.forName(componentClassName);
        componentClassMap_.put(componentType, clazz);
    }

    public UIComponent createComponent(String componentType)
            throws FacesException {
        Class componentClass = (Class) componentClassMap_.get(componentType);
        if (componentClass == null) {
            throw new FacesException();
        }
        return (UIComponent) ClassUtil.newInstance(componentClass);
    }

    public UIComponent createComponent(ValueBinding vb, FacesContext context,
            String componentType) throws FacesException {
        Object obj = vb.getValue(context);
        if (obj instanceof UIComponent) {
            return (UIComponent) obj;
        } else {
            UIComponent component = createComponent(componentType);
            vb.setValue(context, component);
            return component;
        }
    }

    public void addConverter(String converterId, String converterClass) {
        if (converterId == null || converterClass == null) {
            throw new IllegalArgumentException();
        }
        convertersById_.put(converterId, converterClass);
    }

    public void addConverter(Class targetClass, String converterClass) {
        if (targetClass == null || converterClass == null) {
            throw new IllegalArgumentException();
        }
        convertersByClass_.put(targetClass, converterClass);
    }

    public Converter createConverter(String converterId) {
        Converter converter = null;
        if (convertersById_.containsKey(converterId)) {
            String className = (String) convertersById_.get(converterId);
            converter = (Converter) ClassUtil.newInstance(className);
        }
        return converter;
    }

    public Converter createConverter(Class targetClass) {
        Converter converter = null;
        if (convertersByClass_.containsKey(targetClass)) {
            String className = (String) convertersByClass_.get(targetClass);
            converter = (Converter) ClassUtil.newInstance(className);
        }
        return converter;
    }

    public void addValidator(String validatorId, String validatorClass) {
        validators_.put(validatorId, validatorClass);
    }

    public Validator createValidator(String validatorId) throws FacesException {
        String validatorClass = (String) validators_.get(validatorId);
        Validator v = null;
        if (validatorClass != null) {
            v = (Validator) ClassUtil.newInstance(validatorClass);
        }
        return v;
    }

    public Iterator getComponentTypes() {
        return null;
    }

    /**
     * 
     */

    public Iterator getConverterIds() {
        return null;
    }

    /**
     * 
     */

    public Iterator getConverterTypes() {
        return null;
    }

    /**
     * 
     */

    public MethodBinding createMethodBinding(String ref, Class[] params)
            throws ReferenceSyntaxException {
        return null;
    }

    /**
     * 
     */

    public Iterator getValidatorIds() {
        return null;
    }

    /**
     * 
     */

    public ValueBinding createValueBinding(String ref)
            throws ReferenceSyntaxException {
        return null;
    }

}
