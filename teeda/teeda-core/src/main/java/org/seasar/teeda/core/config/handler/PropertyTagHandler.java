package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.AttributeElement;
import org.seasar.teeda.core.config.element.AttributeHolder;
import org.seasar.teeda.core.config.element.PropertyElement;
import org.seasar.teeda.core.config.element.PropertyHolder;
import org.seasar.teeda.core.config.element.impl.AttributeElementImpl;
import org.seasar.teeda.core.config.element.impl.PropertyElementImpl;
import org.xml.sax.Attributes;


public class PropertyTagHandler extends JsfTagHandler {

    public PropertyTagHandler(){
    }
    
    public void start(TagHandlerContext context, String body) {
        PropertyElement propertyTag = createPropertyTag();
        context.push(propertyTag);
    }
    
    public void end(TagHandlerContext context, Attributes body) {
        PropertyElement propertyTag = (PropertyElement)context.pop();
        PropertyHolder holder = (PropertyHolder)context.peek();
        holder.addPropertyElement(propertyTag);
    }
    
    protected PropertyElement createPropertyTag() {
        return new PropertyElementImpl();
    }

}
