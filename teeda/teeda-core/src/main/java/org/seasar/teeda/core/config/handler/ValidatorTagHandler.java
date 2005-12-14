package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.element.ValidatorElement;
import org.seasar.teeda.core.config.element.impl.ValidatorElementImpl;
import org.xml.sax.Attributes;


public class ValidatorTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 3904959746464493872L;

    public ValidatorTagHandler(){
    }
    
    public void start(TagHandlerContext context, Attributes attributes) {
        super.start(context, attributes);
        ValidatorElement validator = createValidatorElement();
        context.push(validator);
    }

    
    public void end(TagHandlerContext context, String body) {
        ValidatorElement validator = (ValidatorElement)context.pop();
        FacesConfig facesConfig = (FacesConfig)context.peek();
        facesConfig.addValidatorElement(validator);
    }
    
    protected ValidatorElement createValidatorElement(){
        return new ValidatorElementImpl();
    }
}
