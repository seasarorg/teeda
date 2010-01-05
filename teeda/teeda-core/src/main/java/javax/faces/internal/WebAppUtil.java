/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package javax.faces.internal;

import java.util.List;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.validator.Validator;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

/**
 * @author shot
 *
 * This class might be changed without notice. Please do not use it
 * excluding the JSF specification part.
 */
public class WebAppUtil {

    private WebAppUtil() {
    }

    public static UIComponent createComponent(FacesContext context,
            String binding, String componentType) {
        UIComponent component = null;
        Application application = context.getApplication();
        if (binding != null) {
            ValueBinding vb = application.createValueBinding(binding);
            component = application.createComponent(vb, context, componentType);
            component.setValueBinding("binding", vb);
        } else {
            component = application.createComponent(componentType);
        }
        return component;
    }

    public static List getCreatedComponentIds(UIComponent component) {
        return (List) component.getAttributes().get(
                InternalConstants.JSP_CREATED_COMPONENT_IDS);
    }

    public static void setCreatedComponentIds(UIComponent component,
            List createdComponents) {
        component.getAttributes().put(
                InternalConstants.JSP_CREATED_COMPONENT_IDS, createdComponents);
    }

    public static void removeCreatedComponentIds(UIComponent component) {
        component.getAttributes().remove(
                InternalConstants.JSP_CREATED_COMPONENT_IDS);
    }

    public static List getCreatedFacetNames(UIComponent component) {
        return (List) component.getAttributes().get(
                InternalConstants.JSP_CREATED_FACET_NAMES);
    }

    public static void setCreatedFacetNames(UIComponent component,
            List createdComponents) {
        component.getAttributes().put(
                InternalConstants.JSP_CREATED_FACET_NAMES, createdComponents);
    }

    public static void removeCreatedFacetNames(UIComponent component) {
        component.getAttributes().remove(
                InternalConstants.JSP_CREATED_FACET_NAMES);
    }

    public static ResponseWriter buildResponseWriter(FacesContext facesContext,
            PageContext pageContext) {
        RenderKitFactory factory = (RenderKitFactory) FactoryFinder
                .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        RenderKit renderKit = factory.getRenderKit(facesContext, facesContext
                .getViewRoot().getRenderKitId());

        PageContextOutWriter writer = new PageContextOutWriter(pageContext);
        String encoding = pageContext.getRequest().getCharacterEncoding();
        FacesContext context = FacesContext.getCurrentInstance();
        String acceptContentTypes = getAcceptHeader(context);
        return renderKit.createResponseWriter(writer, acceptContentTypes,
                encoding);
    }

    public static String getAcceptHeader(FacesContext context) {
        return (String) context.getExternalContext().getRequestHeaderMap().get(
                "accept");
    }

    public static Object getValueFromCreatedValueBinding(String buindName) {
        FacesContext context = getFacesContext();
        ValueBinding vb = context.getApplication()
                .createValueBinding(buindName);
        return vb.getValue(context);
    }

    public static Converter createConverter(String converterId) {
        FacesContext context = getFacesContext();
        return context.getApplication().createConverter(converterId);
    }

    public static Converter createConverter(Class clazz) {
        FacesContext context = getFacesContext();
        return context.getApplication().createConverter(clazz);
    }

    public static Validator createValidator(String validatorId) {
        FacesContext context = getFacesContext();
        return context.getApplication().createValidator(validatorId);
    }

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public static Object getFactory(String factoryName) throws ServletException {
        try {
            return FactoryFinder.getFactory(factoryName);
        } catch (FacesException e) {
            Throwable rootCause = e.getCause();
            if (rootCause == null) {
                throw e;
            } else {
                throw new ServletException(e.getMessage(), rootCause);
            }
        }
    }

    public static String getLifecycleId(ServletConfig config) {
        String lifecycleId = config.getServletContext().getInitParameter(
                FacesServlet.LIFECYCLE_ID_ATTR);
        if (lifecycleId == null) {
            lifecycleId = LifecycleFactory.DEFAULT_LIFECYCLE;
        }
        return lifecycleId;
    }
}
