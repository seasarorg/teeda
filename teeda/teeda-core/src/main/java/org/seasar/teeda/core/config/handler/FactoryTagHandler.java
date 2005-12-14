package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.element.FactoryElement;
import org.seasar.teeda.core.config.element.impl.FactoryElementImpl;
import org.xml.sax.Attributes;


public class FactoryTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 3256999964814357043L;

    public FactoryTagHandler(){
    }
    
    public void start(TagHandlerContext context, Attributes attributes) {
        FactoryElement factoryTag = createFactoryElement();
        context.push(factoryTag);
    }

    public void end(TagHandlerContext context, String body) {
        FactoryElement factoryConfig = (FactoryElement)context.pop();
        FacesConfig facesConfig = (FacesConfig)context.peek();
        facesConfig.addFactoryElement(factoryConfig);
    }
    
    protected FactoryElement createFactoryElement(){
        return new FactoryElementImpl();
    }
    
}
