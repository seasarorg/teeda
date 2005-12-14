package org.seasar.teeda.core.el.impl.commons;

import org.apache.commons.el.Logger;


public class CommonsElLogger {

    private static final Logger logger_ = new Logger(System.out);
    private CommonsElLogger(){
    }
    
    public static Logger getLogger(){
        return logger_;
    }
}
