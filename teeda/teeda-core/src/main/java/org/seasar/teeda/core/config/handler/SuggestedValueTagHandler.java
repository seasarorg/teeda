package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.SuggestedValueHolder;


public class SuggestedValueTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 3258413932522910512L;

    public SuggestedValueTagHandler(){
    }
    
    public void end(TagHandlerContext context, String body) {
        SuggestedValueHolder holder = (SuggestedValueHolder)context.peek();
        holder.setSuggestedValue(body);
    }
}
