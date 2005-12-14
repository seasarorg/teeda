package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.FacetElement;
import org.seasar.teeda.core.config.element.FacetHolder;
import org.seasar.teeda.core.config.element.impl.FacetElementImpl;
import org.xml.sax.Attributes;


public class FacetTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 3258409534559892025L;

    public FacetTagHandler(){
    }
    
    public void start(TagHandlerContext context, String body) {
        FacetElement facet = createFacetElement();
        context.push(facet);
    }
    
    public void end(TagHandlerContext context, Attributes body) {
        FacetElement facet = (FacetElement)context.pop();
        FacetHolder holder = (FacetHolder)context.peek();
        holder.addFacetElement(facet);
    }
    
    protected FacetElement createFacetElement() {
        return new FacetElementImpl();
    }

}
