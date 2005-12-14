package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.ApplicationElement;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.element.impl.ApplicationElementImpl;
import org.xml.sax.Attributes;


public class ApplicationTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 3545511803158016821L;

    public ApplicationTagHandler(){
    }
    
    public void start(TagHandlerContext context, Attributes attributes) {
        ApplicationElement application = createApplicationElement();
        context.push(application);
    }
    
    public void end(TagHandlerContext context, String body) {
        ApplicationElement application = (ApplicationElement)context.pop();
        FacesConfig facesConfig = (FacesConfig)context.peek();
        facesConfig.addApplicationElement(application);
    }
    
    protected ApplicationElement createApplicationElement(){
        return new ApplicationElementImpl();
    }
}
