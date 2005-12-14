package org.seasar.teeda.core.config.rule;

import org.seasar.framework.xml.TagHandlerRule;
import org.seasar.teeda.core.config.handler.SimpleStringTagHandler;


public abstract class JsfBaseTagHandlerRule extends TagHandlerRule {

    protected final void addTagHandler(String path){
        addTagHandler(path, new SimpleStringTagHandler(path));
    }
}
