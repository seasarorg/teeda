package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.element.impl.FacesConfigImpl;
import org.xml.sax.Attributes;


public class FacesConfigTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 3256718494182421296L;

    public FacesConfigTagHandler(){
    }
    
    public void start(TagHandlerContext context, Attributes attributes) {
        FacesConfig facesConfig = createFacesConfig();
        context.push(facesConfig);
    }
    
    protected FacesConfig createFacesConfig(){
        return new FacesConfigImpl();
    }
}
