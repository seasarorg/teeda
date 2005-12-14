package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.element.NavigationCaseElement;
import org.seasar.teeda.core.config.element.NavigationRuleElement;
import org.seasar.teeda.core.config.element.ReferencedBeanElement;
import org.seasar.teeda.core.config.element.impl.NavigationCaseElementImpl;
import org.seasar.teeda.core.config.element.impl.NavigationRuleElementImpl;
import org.seasar.teeda.core.config.element.impl.ReferencedBeanElementImpl;
import org.xml.sax.Attributes;


public class ReferencedBeanTagHandler extends JsfTagHandler {

    public ReferencedBeanTagHandler(){
    }
    
    public void start(TagHandlerContext context, Attributes attributes) {
        ReferencedBeanElement navigationCase = createReferencedBeanElement();
        context.push(navigationCase);
    }

    
    public void end(TagHandlerContext context, String body) {
        ReferencedBeanElement refBean = (ReferencedBeanElement)context.pop();
        FacesConfig config = (FacesConfig)context.peek();
        config.addReferencedBeanElement(refBean);
    }
    
    protected ReferencedBeanElement createReferencedBeanElement(){
        return new ReferencedBeanElementImpl();
    }
}
