package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.ConverterElement;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.element.impl.ConverterElementImpl;
import org.xml.sax.Attributes;


public class ConverterTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 3257852090754807859L;

    public ConverterTagHandler(){
    }
    
    public void start(TagHandlerContext context, Attributes attributes) {
        super.start(context, attributes);
        ConverterElement converter = createConverterElement();
        context.push(converter);
    }
    
    public void end(TagHandlerContext context, String body) {
        ConverterElement converter = (ConverterElement)context.pop();
        FacesConfig facesConfig = (FacesConfig)context.peek();
        facesConfig.addConverterElement(converter);
    }
    
    protected ConverterElement createConverterElement(){
        return new ConverterElementImpl();
    }
}
