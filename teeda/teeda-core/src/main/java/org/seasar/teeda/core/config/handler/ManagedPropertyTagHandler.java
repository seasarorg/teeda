package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.ManagedBeanElement;
import org.seasar.teeda.core.config.element.ManagedPropertyElement;
import org.seasar.teeda.core.config.element.impl.ManagedPropertyElementImpl;
import org.xml.sax.Attributes;


public class ManagedPropertyTagHandler extends JsfTagHandler {

    public ManagedPropertyTagHandler(){
    }
    
    public void start(TagHandlerContext context, Attributes attributes) {
        ManagedPropertyElement managedProperty = createManagedPropertyElement();
        context.push(managedProperty);
    }
    
    public void end(TagHandlerContext context, String body) {
        ManagedPropertyElement managedProperty = (ManagedPropertyElement)context.pop();
        ManagedBeanElement managedBean = (ManagedBeanElement)context.peek();
        managedBean.addManagedPropertyElement(managedProperty);
    }
    
    protected ManagedPropertyElement createManagedPropertyElement(){
        return new ManagedPropertyElementImpl();
    }
}
