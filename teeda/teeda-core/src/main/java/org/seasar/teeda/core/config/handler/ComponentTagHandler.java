package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.ComponentElement;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.element.impl.ComponentElementImpl;
import org.xml.sax.Attributes;


public class ComponentTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 4050486720388413494L;

    public ComponentTagHandler(){
    }
    
    public void start(TagHandlerContext context, Attributes attributes) {
        ComponentElement component = createComponentElement();
        context.push(component);
    }

    public void end(TagHandlerContext context, String body) {
        ComponentElement component = (ComponentElement)context.pop();
        FacesConfig facesConfig = (FacesConfig)context.peek();
        facesConfig.addComponentElement(component);
    }
    
    protected ComponentElement createComponentElement(){
        return new ComponentElementImpl();
    }

}
