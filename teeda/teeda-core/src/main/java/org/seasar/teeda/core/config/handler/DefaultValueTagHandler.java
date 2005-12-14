package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.DefaultValueHolder;


public class DefaultValueTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 3256721779815954743L;

    public DefaultValueTagHandler(){
    }
    
    public void end(TagHandlerContext context, String body) {
        DefaultValueHolder holder = (DefaultValueHolder)context.peek();
        holder.setDefaultValue(body);
    }
}
