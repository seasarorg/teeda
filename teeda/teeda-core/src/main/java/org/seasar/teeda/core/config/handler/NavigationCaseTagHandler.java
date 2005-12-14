package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.NavigationCaseElement;
import org.seasar.teeda.core.config.element.NavigationRuleElement;
import org.seasar.teeda.core.config.element.impl.NavigationCaseElementImpl;
import org.xml.sax.Attributes;


public class NavigationCaseTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 3617016355648647984L;

    public NavigationCaseTagHandler(){
    }
    
    public void start(TagHandlerContext context, Attributes attributes) {
        NavigationCaseElement navigationCase = createNavigationCaseElement();
        context.push(navigationCase);
    }

    
    public void end(TagHandlerContext context, String body) {
        NavigationCaseElement navigationCase = (NavigationCaseElement)context.pop();
        NavigationRuleElement rule = (NavigationRuleElement)context.peek();
        rule.addNavigationCase(navigationCase);
    }
    
    protected NavigationCaseElement createNavigationCaseElement(){
        return new NavigationCaseElementImpl();
    }
}
