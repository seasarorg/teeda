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
package org.seasar.teeda.core.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.el.MethodBindingContext;
import org.seasar.teeda.core.el.ValueBindingContext;
import org.seasar.teeda.core.exception.InstantiateConverterFailureException;
import org.seasar.teeda.core.exception.NoMethodBindingContextException;
import org.seasar.teeda.core.exception.NoValueBindingContextException;
import org.seasar.teeda.core.util.ApplicationUtil;
import org.seasar.teeda.core.util.ClassUtil;
import org.seasar.teeda.core.util.IteratorUtil;
import org.seasar.teeda.core.util.PropertyUtil;

/**
 * @author shot
 */
public class ApplicationImpl extends Application implements
        ConfigurationSupport {

    private ActionListener listener_ = null;

    private Locale locale_ = null;

    private String renderKitId_ = null;

    private String bundle_ = null;

    private NavigationHandler navigationHandler_ = null;

    private PropertyResolver propertyResolver_ = null;

    private VariableResolver variableResolver_ = null;

    private ViewHandler viewHandler_ = null;

    private StateManager manager_ = null;

    private Map componentClassMap_ = Collections.synchronizedMap(new HashMap());

    private Map converterIdMap_ = Collections.synchronizedMap(new HashMap());

    private Map converterClassMap_ = Collections.synchronizedMap(new HashMap());

    private Map converterConfigurationMap_ = Collections
            .synchronizedMap(new HashMap());

    private Map validatorMap_ = Collections.synchronizedMap(new HashMap());

    private Map validatorFromDIMap_ = Collections
            .synchronizedMap(new HashMap());

    private Collection supportedLocales_ = Collections.EMPTY_SET;

    private ValueBindingContext vbContext_ = null;

    private MethodBindingContext mbContext_ = null;

    public ApplicationImpl() {

    }

    public ActionListener getActionListener() {
        return listener_;
    }

    public void setActionListener(ActionListener listener) {
        if (listener == null) {
            throw new NullPointerException("ActionListener is null.");
        }
        listener_ = listener;
    }

    public Locale getDefaultLocale() {
        return locale_;
    }

    public void setDefaultLocale(Locale locale) {
        if (locale == null) {
            throw new NullPointerException("Locale is null.");
        }
        locale_ = locale;
    }

    public String getDefaultRenderKitId() {
        return renderKitId_;
    }

    public void setDefaultRenderKitId(String renderKitId) {
        renderKitId_ = renderKitId;
    }

    public String getMessageBundle() {
        return bundle_;
    }

    public void setMessageBundle(String bundle) {
        if (bundle == null) {
            throw new NullPointerException("MessageBundle is null.");
        }
        bundle_ = bundle;
    }

    public NavigationHandler getNavigationHandler() {
        return navigationHandler_;
    }

    public void setNavigationHandler(NavigationHandler handler) {
        if (handler == null) {
            throw new NullPointerException("NavigationHandler is null.");
        }
        navigationHandler_ = handler;
    }

    public PropertyResolver getPropertyResolver() {
        return propertyResolver_;
    }

    public void setPropertyResolver(PropertyResolver resolver) {
        if (resolver == null) {
            throw new NullPointerException("PropertyResolver is null.");
        }
        propertyResolver_ = resolver;
    }

    public VariableResolver getVariableResolver() {
        return variableResolver_;
    }

    public void setVariableResolver(VariableResolver resolver) {
        if (resolver == null) {
            throw new NullPointerException("VariableResolver is null.");
        }
        variableResolver_ = resolver;
    }

    public ViewHandler getViewHandler() {
        return viewHandler_;
    }

    public void setViewHandler(ViewHandler handler) {
        if (handler == null) {
            throw new NullPointerException("ViewHandler is null.");
        }
        viewHandler_ = handler;
    }

    public StateManager getStateManager() {
        return manager_;
    }

    public void setStateManager(StateManager manager) {
        manager_ = manager;
    }

    public void addComponent(String componentType, String componentClassName) {
        if (StringUtil.isEmpty(componentType)) {
            throw new NullPointerException("componentType is null.");
        }
        if (StringUtil.isEmpty(componentClassName)) {
            throw new NullPointerException("componentClassName is null.");
        }
        Class clazz = ClassUtil.forName(componentClassName);
        ApplicationUtil.verifyClassType(UIComponent.class, clazz);
        componentClassMap_.put(componentType, clazz);
    }

    public UIComponent createComponent(String componentType)
            throws FacesException {
        if (StringUtil.isEmpty(componentType)) {
            throw new NullPointerException("componentType is null.");
        }
        Class componentClass = (Class) componentClassMap_.get(componentType);
        if (componentClass == null) {
            throw new FacesException("Undeifined component type:"
                    + componentType);
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

    public Iterator getComponentTypes() {
        return componentClassMap_.keySet().iterator();
    }

    public void addConverter(String converterId, String converterClassName) {
        if (StringUtil.isEmpty(converterId)) {
            throw new NullPointerException("converterId is null");
        }
        if (StringUtil.isEmpty(converterClassName)) {
            throw new NullPointerException("converterClass is null");
        }
        Class clazz = ClassUtil.forName(converterClassName);
        ApplicationUtil.verifyClassType(Converter.class, clazz);
        converterIdMap_.put(converterId, clazz);
    }

    public void addConverter(Class targetClass, String converterClassName) {
        if (targetClass == null) {
            throw new NullPointerException("targetClass is null");
        }
        if (StringUtil.isEmpty(converterClassName)) {
            throw new NullPointerException("converterClass is null");
        }
        Class clazz = ClassUtil.forName(converterClassName);
        ApplicationUtil.verifyClassType(Converter.class, clazz);
        converterClassMap_.put(targetClass, clazz);
    }

    public Converter createConverter(String converterId) {
        if (converterId == null) {
            throw new NullPointerException("converterId is null");
        }
        Class clazz = (Class) converterIdMap_.get(converterId);
        try {
            Converter converter = (Converter) createConverter0(clazz);
            return converter;
        } catch (Exception e) {
            Object[] args = { e, clazz };
            throw new InstantiateConverterFailureException(args, e);
        }
    }

    public Converter createConverter(Class targetClass) {
        if (targetClass == null) {
            throw new NullPointerException();
        }
        Converter c = doCreateConverter(targetClass);
        return c;
    }

    public void addConverterConfiguration(String converterClassName,
            ConverterConfiguration converterConfig) {
        if (StringUtil.isEmpty(converterClassName)) {
            throw new NullPointerException("converter target class name");
        }
        if (converterConfig == null) {
            throw new NullPointerException("converter config support");
        }
        Class clazz = ClassUtil.forName(converterClassName);
        List list = (List) converterConfigurationMap_.get(clazz);
        if (list == null) {
            list = new ArrayList();
        }
        list.add(converterConfig);
        converterConfigurationMap_.put(clazz, list);
    }

    private Converter createConverter0(Class clazz) {
        Converter converter = null;
        try {
            converter = (Converter) ClassUtil.newInstance(clazz);
        } catch (Exception e) {
            Object[] args = { e, clazz };
            throw new InstantiateConverterFailureException(args, e);
        }
        List list = (List) converterConfigurationMap_.get(clazz);
        for (Iterator itr = IteratorUtil.getIterator(list); itr.hasNext();) {
            ConverterConfiguration config = (ConverterConfiguration) itr.next();
            if (config != null) {
                String propertyName = config.getPropertyName();
                PropertyUtil.setValue(converter, propertyName, config
                        .getDefaultValue());
            }
        }
        return converter;
    }

    private Converter doCreateConverter(Class targetClass) {
        Converter c = createConverterByClass(targetClass);
        if (c != null) {
            return c;
        }
        c = createConverterByInterface(targetClass);
        if (c != null) {
            return c;
        }
        c = createConverterBySuperClass(targetClass);
        if (c != null) {
            return c;
        }
        c = createConverterForPrimitive(targetClass);
        if (c != null) {
            return c;
        }
        return null;
    }

    protected Converter createConverterByClass(Class targetClass) {
        Class converterClass = (Class) converterClassMap_.get(targetClass);
        if (converterClass != null) {
            return (Converter) createConverter0(converterClass);
        }
        return null;
    }

    protected Converter createConverterByInterface(Class targetClass) {
        Class[] interfaces = targetClass.getInterfaces();
        if (interfaces != null) {
            for (int i = 0; i < interfaces.length; i++) {
                Converter converter = doCreateConverter(interfaces[i]);
                if (converter != null) {
                    return converter;
                }
            }
        }
        return null;
    }

    protected Converter createConverterBySuperClass(Class targetClass) {
        Class superClass = targetClass.getSuperclass();
        if (superClass != null) {
            return doCreateConverter(superClass);
        }
        return null;
    }

    protected Converter createConverterForPrimitive(Class targetClass) {
        Class primitiveClass = ClassUtil.getWrapperClass(targetClass);
        if (primitiveClass != null) {
            return doCreateConverter(primitiveClass);
        }
        return null;
    }

    public Iterator getConverterIds() {
        return converterIdMap_.keySet().iterator();
    }

    public Iterator getConverterTypes() {
        return converterClassMap_.keySet().iterator();
    }

    public Iterator getSupportedLocales() {
        return supportedLocales_.iterator();
    }

    public void setSupportedLocales(Collection supportedLocales) {
        if (supportedLocales == null) {
            throw new NullPointerException();
        }
        supportedLocales_ = supportedLocales;
    }

    public void addValidator(String validatorId, Validator validator) {
        if (StringUtil.isEmpty(validatorId)) {
            throw new NullPointerException("Validator id is null.");
        }
        validatorFromDIMap_.put(validatorId, validator);
    }
    
    public void addValidator(String validatorId, String validatorClassName) {
        if (StringUtil.isEmpty(validatorId)) {
            throw new NullPointerException("Validator id is null.");
        }
        if (StringUtil.isEmpty(validatorClassName)) {
            throw new NullPointerException("Validator class is null.");
        }
        Class clazz = ClassUtil.forName(validatorClassName);
        ApplicationUtil.verifyClassType(Validator.class, clazz);
        validatorMap_.put(validatorId, clazz);
    }

    public Validator createValidator(String validatorId) throws FacesException {
        if (validatorId == null) {
            throw new NullPointerException();
        }
        if(validatorFromDIMap_.get(validatorId) != null) {
            return (Validator) validatorFromDIMap_.get(validatorId);
        }
        Class validatorClass = (Class) validatorMap_.get(validatorId);
        if (validatorClass == null) {
            throw new FacesException("Undefined validator class:"
                    + validatorClass);
        }
        return (Validator) ClassUtil.newInstance(validatorClass);
    }

    public Iterator getValidatorIds() {
        return IteratorUtil.getCompositeIterator(validatorMap_, validatorFromDIMap_);
    }

    public MethodBinding createMethodBinding(String ref, Class[] params)
            throws ReferenceSyntaxException {
        if (ref == null) {
            throw new NullPointerException();
        }
        if (mbContext_ == null) {
            throw new NoMethodBindingContextException(ref, params);
        }
        return mbContext_.createMethodBinding(this, ref, params);
    }

    public ValueBinding createValueBinding(String ref)
            throws ReferenceSyntaxException {
        if (ref == null) {
            throw new NullPointerException();
        }
        if (vbContext_ == null) {
            throw new NoValueBindingContextException(ref);
        }
        return vbContext_.createValueBinding(this, ref);
    }

    public void setValueBindingContext(ValueBindingContext vbContext) {
        vbContext_ = vbContext;
    }

    public void setMethodBindingContext(MethodBindingContext mbContext) {
        mbContext_ = mbContext;
    }

    public ValueBindingContext getValueBindingContext() {
        return vbContext_;
    }

    public MethodBindingContext getMethodBindingContext() {
        return mbContext_;
    }

}
