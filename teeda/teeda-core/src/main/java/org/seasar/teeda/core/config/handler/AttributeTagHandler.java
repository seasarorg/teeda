package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.AttributeElement;
import org.seasar.teeda.core.config.element.AttributeHolder;
import org.seasar.teeda.core.config.element.impl.AttributeElementImpl;
import org.xml.sax.Attributes;


public class AttributeTagHandler extends JsfTagHandler {

    public AttributeTagHandler(){
    }
    
    public void start(TagHandlerContext context, String body) {
        AttributeElement attribute = createAttributeElement();
        context.push(attribute);
    }
    
    public void end(TagHandlerContext context, Attributes body) {
        AttributeElement attribute = (AttributeElement)context.pop();
        AttributeHolder holder = (AttributeHolder)context.peek();
        holder.addAttributeElement(attribute);
    }
    
    protected AttributeElement createAttributeElement() {
        return new AttributeElementImpl();
    }

}
