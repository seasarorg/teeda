package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.ListEntriesElement;
import org.seasar.teeda.core.config.element.ListEntriesHolder;
import org.seasar.teeda.core.config.element.impl.ListEntriesElementImpl;
import org.xml.sax.Attributes;


public class ListEntriesTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 4050200834429957941L;

    public ListEntriesTagHandler(){
    }
    
    public void start(TagHandlerContext context, String body) {
    	ListEntriesElement listEntries = createListEntriesElement();
        context.push(listEntries);
    }
    
    public void end(TagHandlerContext context, Attributes body) {
    	ListEntriesElement listEntries = (ListEntriesElement)context.pop();
        ListEntriesHolder holder = (ListEntriesHolder)context.peek();
        holder.setListEntries(listEntries);
    }
    
    protected ListEntriesElement createListEntriesElement() {
        return new ListEntriesElementImpl();
    }

}
