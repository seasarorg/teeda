package javax.faces.webapp;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.PageContext;


class PageContextUtil_ {

    private static final int PAGE_CONTEXT_SCOPE = PageContext.REQUEST_SCOPE;

    private PageContextUtil_(){
    }
    
    public static List getComponentTagStackAttribute(PageContext pageContext){
        return (List)pageContext.getAttribute(WebAppConstants_.COMPONENT_TAG_STACK_ATTR, PAGE_CONTEXT_SCOPE);
    }

    public static void setComponentStackAttribute(PageContext pageContext, List list){
        pageContext.setAttribute(WebAppConstants_.COMPONENT_TAG_STACK_ATTR, list, PAGE_CONTEXT_SCOPE);
    }
    
    public static void removeComponentStackAttribute(PageContext pageContext){
        pageContext.removeAttribute(WebAppConstants_.COMPONENT_TAG_STACK_ATTR, PAGE_CONTEXT_SCOPE);
    }

    public static FacesContext getCurrentFacesContextAttribute(PageContext pageContext){
        return (FacesContext)pageContext.getAttribute(WebAppConstants_.CURRENT_FACES_CONTEXT);
    }
    
    public static void setCurrentFacesContextAttribute(PageContext pageContext, FacesContext context){
        pageContext.setAttribute(WebAppConstants_.CURRENT_FACES_CONTEXT, context);
    }
    
    public static UIComponent getCurrentViewRootAttribute(PageContext pageContext){
        return (UIComponent)pageContext.getAttribute(WebAppConstants_.CURRENT_VIEW_ROOT, PAGE_CONTEXT_SCOPE);
    }
    
    public static void setCurrentViewRootAttribute(PageContext pageContext, UIComponent component){
        pageContext.setAttribute(WebAppConstants_.CURRENT_VIEW_ROOT, component, PAGE_CONTEXT_SCOPE);
    }    
}
