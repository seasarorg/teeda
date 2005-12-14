package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.element.RenderKitElement;
import org.seasar.teeda.core.config.element.impl.RenderKitElementImpl;
import org.xml.sax.Attributes;


public class RenderKitTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 3258125877739861555L;

    public RenderKitTagHandler(){
    }
    
    public void start(TagHandlerContext context, Attributes attributes) {
        RenderKitElement rule = createRenderKitElement();
        context.push(rule);
    }

    public void end(TagHandlerContext context, String body) {
        RenderKitElement rule = (RenderKitElement)context.pop();
        FacesConfig facesConfig = (FacesConfig)context.peek();
        facesConfig.addRenderKitElement(rule);
    }
    
    protected RenderKitElement createRenderKitElement(){
        return new RenderKitElementImpl();
    }
}
