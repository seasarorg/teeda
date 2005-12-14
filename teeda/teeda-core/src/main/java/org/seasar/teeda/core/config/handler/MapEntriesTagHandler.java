package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.ListEntriesElement;
import org.seasar.teeda.core.config.element.ListEntriesHolder;
import org.seasar.teeda.core.config.element.MapEntriesElement;
import org.seasar.teeda.core.config.element.MapEntriesHolder;
import org.seasar.teeda.core.config.element.impl.ListEntriesElementImpl;
import org.seasar.teeda.core.config.element.impl.MapEntriesElementImpl;
import org.xml.sax.Attributes;


public class MapEntriesTagHandler extends JsfTagHandler {

    public MapEntriesTagHandler(){
    }
    
    public void start(TagHandlerContext context, String body) {
    	MapEntriesElement mapEntries = createMapEntriesElement();
        context.push(mapEntries);
    }
    
    public void end(TagHandlerContext context, Attributes body) {
    	MapEntriesElement mapEntries = (MapEntriesElement)context.pop();
        MapEntriesHolder holder = (MapEntriesHolder)context.peek();
        holder.setMapEntries(mapEntries);
    }
    
    protected MapEntriesElement createMapEntriesElement() {
        return new MapEntriesElementImpl();
    }

}
