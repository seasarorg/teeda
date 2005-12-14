package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.element.LifecycleElement;
import org.seasar.teeda.core.config.element.impl.LifecycleElementImpl;
import org.xml.sax.Attributes;


public class LifecycleTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 3904960863155728944L;

    public LifecycleTagHandler(){
    }
    
    public void start(TagHandlerContext context, Attributes attributes) {
        LifecycleElement lifecycle = createLifecycleElement();
        context.push(lifecycle);
    }
    
    public void end(TagHandlerContext context, String body) {
        LifecycleElement lifecycle = (LifecycleElement)context.pop();
        FacesConfig facesConfig = (FacesConfig)context.peek();
        facesConfig.addLifecycleElement(lifecycle);
    }
    
    protected LifecycleElement createLifecycleElement(){
        return new LifecycleElementImpl();
    }
}
