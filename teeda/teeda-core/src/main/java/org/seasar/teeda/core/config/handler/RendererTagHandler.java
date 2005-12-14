package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.RenderKitElement;
import org.seasar.teeda.core.config.element.RendererElement;
import org.seasar.teeda.core.config.element.impl.RendererElementImpl;
import org.xml.sax.Attributes;


public class RendererTagHandler extends JsfTagHandler {

    public RendererTagHandler(){
    }
    
    public void start(TagHandlerContext context, Attributes attributes) {
        RendererElement renderer = createRendererElement();
        context.push(renderer);
    }

    
    public void end(TagHandlerContext context, String body) {
        RendererElement renderer = (RendererElement)context.pop();
        RenderKitElement config = (RenderKitElement)context.peek();
        config.addRendererElement(renderer);
    }
    
    protected RendererElement createRendererElement(){
        return new RendererElementImpl();
    }
}
