package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.element.NavigationRuleElement;
import org.seasar.teeda.core.config.element.impl.NavigationRuleElementImpl;
import org.xml.sax.Attributes;


public class NavigationRuleTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 3257569520461099317L;

    public NavigationRuleTagHandler(){
    }
    
    public void start(TagHandlerContext context, Attributes attributes) {
        NavigationRuleElement rule = createNavigationRuleElement();
        context.push(rule);
    }

    
    public void end(TagHandlerContext context, String body) {
        NavigationRuleElement rule = (NavigationRuleElement)context.pop();
        FacesConfig facesConfig = (FacesConfig)context.peek();
        facesConfig.addNavigationRuleElement(rule);
    }
    
    protected NavigationRuleElement createNavigationRuleElement(){
        return new NavigationRuleElementImpl();
    }
}
