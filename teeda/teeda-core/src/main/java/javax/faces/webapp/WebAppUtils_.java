package javax.faces.webapp;

import java.util.List;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.validator.Validator;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

class WebAppUtils_ {
    
    private WebAppUtils_() {
    }

    public static UIComponent createComponent(FacesContext context, String binding, String componentType){
        UIComponent component = null;
        Application application = context.getApplication();
        if(binding != null){
            ValueBinding vb = application.createValueBinding(binding);
            component = application.createComponent(vb, context,componentType);
            component.setValueBinding("binding", vb);
        }else{
            component = application.createComponent(componentType);
        }
        return component;
    }
            
    public static ValueBinding createValueBindingByApplication(UIComponent component, FacesContext context, String ref){
        return context.getApplication().createValueBinding(ref);
    }
    
    public static List getCreatedComponentIds(UIComponent component){
        return (List)component.getAttributes().get(WebAppConstants_.JSP_CREATED_COMPONENT_IDS);
    }

    public static void setCreatedComponentIds(UIComponent component, List createdComponents){
        component.getAttributes().put(WebAppConstants_.JSP_CREATED_COMPONENT_IDS, createdComponents);
    }
    
    public static void removeCreatedComponentIds(UIComponent component){
        component.getAttributes().remove(WebAppConstants_.JSP_CREATED_COMPONENT_IDS);
    }
    
    public static List getCreatedFacetNames(UIComponent component){
        return (List)component.getAttributes().get(WebAppConstants_.JSP_CREATED_FACET_NAMES);
    }

    public static void setCreatedFacetNames(UIComponent component, List createdComponents){
        component.getAttributes().put(WebAppConstants_.JSP_CREATED_FACET_NAMES, createdComponents);
    }
    
    public static void removeCreatedFacetNames(UIComponent component){
        component.getAttributes().remove(WebAppConstants_.JSP_CREATED_FACET_NAMES);
    }

    public static ResponseWriter buildResponseWriter(FacesContext facesContext,
            PageContext pageContext) {
        RenderKitFactory factory = (RenderKitFactory)FactoryFinder
                .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        RenderKit renderKit = factory.getRenderKit(facesContext, facesContext
                .getViewRoot().getRenderKitId());
        
        PageContextOutWriter_ writer = new PageContextOutWriter_(pageContext);
        String contentType = pageContext.getRequest().getContentType();
        String encoding = pageContext.getRequest().getCharacterEncoding();
        return renderKit.createResponseWriter(writer, contentType, encoding);
    }
    
    public static Object getValueFromCreatedValueBinding(String buindName){
        FacesContext context = getFacesContext();
        ValueBinding vb = context.getApplication().createValueBinding(buindName);
        return vb.getValue(context);
    }
    
    public static Converter createConverter(String converterId){
        FacesContext context = getFacesContext();
        return context.getApplication().createConverter(converterId);
    }
    
    public static Validator createValidator(String validatorId){
        FacesContext context = getFacesContext();
        return context.getApplication().createValidator(validatorId);
    }
    
    public static FacesContext getFacesContext(){
        return FacesContext.getCurrentInstance();
    }
    
    public static Object getFactory(String factoryName) throws ServletException{
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
    
    public static String getLifecycleId(ServletConfig config){
        String lifecycleId = config.getServletContext().getInitParameter(FacesServlet.LIFECYCLE_ID_ATTR);
        if (lifecycleId == null) {
            lifecycleId = LifecycleFactory.DEFAULT_LIFECYCLE;
        }
        return lifecycleId;
    }
}
