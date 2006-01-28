package org.seasar.teeda.core.config.handler;

import java.util.List;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.AttributeElement;
import org.seasar.teeda.core.config.element.AttributeHolder;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.element.impl.ComponentElementImpl;

public class AttributeTagHandlerTest extends TagHandlerTestCase {

    public AttributeTagHandlerTest(String name) {
        super(name);
    }

    public void testAttributeTagHandler() throws Exception {
        AttributeTagHandler handler = new AttributeTagHandler();
        TagHandlerContext context = new TagHandlerContext();
        AttributeHolder holder = new ComponentElementImpl();
        context.push(holder);
        handler.start(context, "body");
        handler.end(context, new NullAttributes());
        
        assertNotNull(holder.getAttributeElements());
        assertEquals(1, holder.getAttributeElements().size());
        List list = holder.getAttributeElements();
        AttributeElement element = (AttributeElement) list.get(0);
        assertNotNull(element);
    }
    
    public void testAttributeTagHandlerByXMLParse() throws Exception {
        FacesConfig facesConfig = parse("testApplicationTagHandler.xml");
        //TODO testing
    }
}
