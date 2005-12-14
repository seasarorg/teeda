package org.seasar.teeda.core.config.handler;

import org.seasar.framework.log.Logger;
import org.seasar.framework.xml.TagHandler;
import org.seasar.framework.xml.TagHandlerContext;
import org.xml.sax.Attributes;


public class JsfTagHandler extends TagHandler {

    private Logger logger_ = Logger.getLogger(JsfTagHandler.class);
    
    private static final long serialVersionUID = 3258415027739703344L;

    public JsfTagHandler(){
    }

    public void start(TagHandlerContext context, Attributes attributes) {
        super.start(context, attributes);
        logger_.info(context);
        logger_.info(attributes);
    }

    public void end(TagHandlerContext context, String body) {
        super.end(context, body);
        logger_.info(context);
        logger_.info(body);
    }
}
