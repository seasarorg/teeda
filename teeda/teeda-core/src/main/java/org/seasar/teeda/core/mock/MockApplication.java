package org.seasar.teeda.core.mock;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;

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


public class MockApplication extends Application {

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
    public MockApplication(){
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
        return (locales_ != null) ? locales_.iterator() : Collections.EMPTY_LIST.iterator();
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
        return propertyResolver_;
    }

    public void setPropertyResolver(PropertyResolver resolver) {
        propertyResolver_ = resolver;
    }

    public VariableResolver getVariableResolver() {
        return variableResolver_;
    }

    public void setVariableResolver(VariableResolver resolver) {
        variableResolver_ = resolver;
    }

    public ViewHandler getViewHandler() {
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

    /**
     *
     */

    public void addComponent(String componentType, String componentClassName) {
    }

    /**
     *
     */

    public UIComponent createComponent(String componentType)
            throws FacesException {
        return null;
    }

    /**
     *
     */

    public UIComponent createComponent(ValueBinding componentBinding,
            FacesContext context, String componentType) throws FacesException {
        return null;
    }

    /**
     *
     */

    public Iterator getComponentTypes() {
        return null;
    }

    /**
     *
     */

    public void addConverter(String converterId, String converterClass) {
    }

    /**
     *
     */

    public void addConverter(Class targetClass, String converterClass) {
    }

    /**
     *
     */

    public Converter createConverter(String converterId) {
        return null;
    }

    /**
     *
     */

    public Converter createConverter(Class targetClass) {
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

    public void addValidator(String validatorId, String validatorClass) {
    }

    /**
     *
     */

    public Validator createValidator(String validatorId) throws FacesException {
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
