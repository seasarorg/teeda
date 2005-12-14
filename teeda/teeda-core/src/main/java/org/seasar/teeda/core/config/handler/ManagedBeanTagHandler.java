package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.element.ManagedBeanElement;
import org.seasar.teeda.core.config.element.impl.ManagedBeanElementImpl;
import org.xml.sax.Attributes;


public class ManagedBeanTagHandler extends JsfTagHandler {

    public ManagedBeanTagHandler(){
    }
    
    public void start(TagHandlerContext context, Attributes attributes) {
        ManagedBeanElement managedBean = createManagedBeanElement();
        context.push(managedBean);
    }

    public void end(TagHandlerContext context, String body) {
        ManagedBeanElement managedBean = (ManagedBeanElement)context.pop();
        FacesConfig facesConfig = (FacesConfig)context.peek();
        facesConfig.addManagedBeanElement(managedBean);
    }
    
    protected ManagedBeanElement createManagedBeanElement(){
        return new ManagedBeanElementImpl();
    }
}