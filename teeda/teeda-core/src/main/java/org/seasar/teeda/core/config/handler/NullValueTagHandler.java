package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.NullValueHolder;


public class NullValueTagHandler extends JsfTagHandler {

    public NullValueTagHandler(){
    }
    
    public void end(TagHandlerContext context, String body) {
        NullValueHolder holder = (NullValueHolder)context.peek();
        holder.setNullValue(true);
    }
}
