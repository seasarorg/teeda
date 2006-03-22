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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.PropertyDef;
import org.seasar.framework.container.assembler.AutoBindingDefFactory;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.container.impl.PropertyDefImpl;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ArrayIterator;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.el.MethodBindingContext;
import org.seasar.teeda.core.el.ValueBindingContext;
import org.seasar.teeda.core.exception.ConverterInstantiateFailureException;
import org.seasar.teeda.core.exception.NoMethodBindingContextException;
import org.seasar.teeda.core.exception.NoValueBindingContextException;
import org.seasar.teeda.core.util.ApplicationUtil;
import org.seasar.teeda.core.util.ClassUtil;
import org.seasar.teeda.core.util.DIContainerUtil;
import org.seasar.teeda.core.util.IteratorUtil;

/**
 * @author shot
 */
public class ApplicationImpl extends Application implements
        ConfigurationSupport {

    private static final Logger logger_ = Logger
            .getLogger(ApplicationImpl.class);

    private ActionListener listener_ = null;

    private Locale locale_ = null;

    private String renderKitId_ = null;

    private String bundle_ = null;

    private NavigationHandler navigationHandler_ = null;

    private PropertyResolver propertyResolver_ = null;

    private VariableResolver variableResolver_ = null;

    private ViewHandler viewHandler_ = null;

    private StateManager manager_ = null;

    private Map componentTypeMap_ = Collections.synchronizedMap(new HashMap());

    private Map convertersMap_ = Collections.synchronizedMap(new HashMap());

    private Map converterConfigurationMap_ = Collections
            .synchronizedMap(new HashMap());

    private Map validatorsMap_ = Collections.synchronizedMap(new HashMap());

    private Set converterIdSet_ = new HashSet();

    private Set converterTypeSet_ = new HashSet();

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
        registerApplicationComponent(clazz);
        componentTypeMap_.put(componentType, clazz);
    }

    public UIComponent createComponent(String componentType)
            throws FacesException {
        if (StringUtil.isEmpty(componentType)) {
            throw new NullPointerException("componentType is null.");
        }
        UIComponent component = (UIComponent) getApplicationComponentByName(componentType);
        if (component != null) {
            return component;
        }
        Class componentClazz = (Class) componentTypeMap_.get(componentType);
        if (componentClazz == null) {
            throw new FacesException("Undeifined component type:"
                    + componentType);
        }
        component = (UIComponent) getApplicationComponent(componentClazz);
        return component;
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
        Object[] componentTypeKeys = DIContainerUtil
                .getComponentKeys(UIComponent.class);
        return new ArrayIterator(componentTypeKeys);
    }

    public void addConverter(String converterId, String converterClassName) {
        if (StringUtil.isEmpty(converterId)) {
            throw new NullPointerException("converterId is null");
        }
        if (StringUtil.isEmpty(converterClassName)) {
            throw new NullPointerException("converterClass is null");
        }
        Class converterClazz = ClassUtil.forName(converterClassName);
        ApplicationUtil.verifyClassType(Converter.class, converterClazz);
        if (!convertersMap_.containsKey(converterId)) {
            if (!DIContainerUtil.hasComponent(converterClazz)) {
                registerConverterComponent(converterClazz);
            }
            convertersMap_.put(converterId, converterClazz);
        }
        converterIdSet_.add(converterId);
    }

    public void addConverter(Class targetClass, String converterClassName) {
        if (targetClass == null) {
            throw new NullPointerException("targetClass is null");
        }
        if (StringUtil.isEmpty(converterClassName)) {
            throw new NullPointerException("converterClass is null");
        }
        Class converterClazz = ClassUtil.forName(converterClassName);
        ApplicationUtil.verifyClassType(Converter.class, converterClazz);
        if (!convertersMap_.containsKey(targetClass.getName())) {
            //Avoid duplicate registration because some converter already registered by converterId.
            if (!DIContainerUtil.hasComponent(converterClazz)) {
                registerConverterComponent(converterClazz);
            }
            convertersMap_.put(targetClass.getName(), converterClazz);
        }
        converterTypeSet_.add(targetClass);
    }

    public Converter createConverter(String converterId) {
        if (converterId == null) {
            throw new NullPointerException("converterId is null");
        }
        Converter converter = (Converter) getApplicationComponentByName(converterId);
        if (converter != null) {
            return converter;
        }
        Class converterClazz = (Class) convertersMap_.get(converterId);
        if (converterClazz == null) {
            throw new ConverterInstantiateFailureException(
                    new Object[] { converterId });
        }
        converter = (Converter) getApplicationComponent(converterClazz);
        return converter;
    }

    public Converter createConverter(Class targetClass) {
        if (targetClass == null) {
            throw new NullPointerException();
        }
        Converter c = doCreateConverter(targetClass);
        return c;
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
        Converter converter = (Converter) getApplicationComponentByName(targetClass
                .getName());
        if (converter != null) {
            return converter;
        }
        Class converterClazz = (Class) convertersMap_
                .get(targetClass.getName());
        if (converterClazz != null) {
            converter = (Converter) getApplicationComponent(converterClazz);
        }
        return converter;
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

    public void addConverterConfiguration(String converterClassName,
            ConverterConfiguration converterConfig) {
        if (StringUtil.isEmpty(converterClassName)) {
            throw new NullPointerException("converter target class name");
        }
        if (converterConfig == null) {
            throw new NullPointerException("converter config support");
        }
        Class converterClazz = ClassUtil.forName(converterClassName);
        List list = (List) converterConfigurationMap_.get(converterClazz);
        if (list == null) {
            list = new ArrayList();
        }
        list.add(converterConfig);
        converterConfigurationMap_.put(converterClazz, list);
    }

    public Iterator getConverterIds() {
        return converterIdSet_.iterator();
    }

    public Iterator getConverterTypes() {
        return converterTypeSet_.iterator();
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

    public void addValidator(String validatorId, String validatorClassName) {
        if (StringUtil.isEmpty(validatorId)) {
            throw new NullPointerException("Validator id is null.");
        }
        if (StringUtil.isEmpty(validatorClassName)) {
            throw new NullPointerException("Validator class is null.");
        }
        Class validatorClazz = ClassUtil.forName(validatorClassName);
        ApplicationUtil.verifyClassType(Validator.class, validatorClazz);
        registerApplicationComponent(validatorClazz);
        validatorsMap_.put(validatorId, validatorClazz);
    }

    public Validator createValidator(String validatorId) throws FacesException {
        if (validatorId == null) {
            throw new NullPointerException();
        }
        Validator validator = (Validator) getApplicationComponentByName(validatorId);
        if (validator != null) {
            return validator;
        }
        Class validatorClazz = (Class) validatorsMap_.get(validatorId);
        if (validatorClazz == null) {
            throw new FacesException("Undefined validator class for "
                    + validatorId);
        }
        validator = (Validator) getApplicationComponent(validatorClazz);
        return validator;
    }

    public Iterator getValidatorIds() {
        Object[] validatorKeys = DIContainerUtil
                .getComponentKeys(Validator.class);
        return new ArrayIterator(validatorKeys);
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

    protected void registerApplicationComponent(Class targetClazz,
            String targetId) {
        ComponentDef componentDef = createComponentDef(targetClazz, targetId);
        registerApplicationComponent(componentDef);
    }

    protected void registerApplicationComponent(Class targetClazz) {
        registerApplicationComponent(targetClazz, null);
    }

    protected void registerApplicationComponent(ComponentDef componentDef) {
        if (logger_.isDebugEnabled()) {
            logger_
                    .debug("component name = "
                            + componentDef.getComponentName());
            logger_.debug("component class = "
                    + componentDef.getComponentClass());
        }
        DIContainerUtil.register(componentDef);
    }

    //TODO need support multiple namespaces.
    protected Object getApplicationComponentByName(String componentName) {
        Object o = getApplicationComponent(componentName);
        if (o == null) {
            if(logger_.isDebugEnabled()) {
                logger_.debug("Component(component name = " + componentName + ") not found.");
            }
            o = getApplicationComponent(JsfConstants.TEEDA_NAMESPACE
                    + JsfConstants.NS_SEP + componentName);
        }
        return o;
    }

    protected Object getApplicationComponent(Object componentKey) {
        return DIContainerUtil.getComponentNoException(componentKey);
    }

    protected void registerConverterComponent(Class converterClazz,
            String converterId) {
        ComponentDef componentDef = createComponentDef(converterClazz,
                converterId);
        List converterConfigs = (List) converterConfigurationMap_
                .get(converterClazz);
        for (Iterator itr = IteratorUtil.getIterator(converterConfigs); itr
                .hasNext();) {
            ConverterConfiguration config = (ConverterConfiguration) itr.next();
            if (config != null) {
                String propertyName = config.getPropertyName();
                PropertyDef propertyDef = new PropertyDefImpl(propertyName);
                propertyDef.setValue(config.getDefaultValue());
                componentDef.addPropertyDef(propertyDef);
            }
        }
        registerApplicationComponent(componentDef);
    }

    protected void registerConverterComponent(Class converterClazz) {
        registerConverterComponent(converterClazz, null);
    }

    private ComponentDef createComponentDef(Class targetClazz, String targetName) {
        ComponentDef componentDef = new ComponentDefImpl(targetClazz,
                targetName);
        componentDef.setInstanceDef(InstanceDefFactory.PROTOTYPE);
        componentDef.setAutoBindingDef(AutoBindingDefFactory.NONE);
        return componentDef;
    }
}
