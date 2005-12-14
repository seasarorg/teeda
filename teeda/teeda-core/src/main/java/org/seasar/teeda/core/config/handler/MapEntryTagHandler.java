package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.ListEntriesElement;
import org.seasar.teeda.core.config.element.ListEntriesHolder;
import org.seasar.teeda.core.config.element.MapEntriesElement;
import org.seasar.teeda.core.config.element.MapEntriesHolder;
import org.seasar.teeda.core.config.element.MapEntryElement;
import org.seasar.teeda.core.config.element.impl.ListEntriesElementImpl;
import org.seasar.teeda.core.config.element.impl.MapEntriesElementImpl;
import org.seasar.teeda.core.config.element.impl.MapEntryElementImpl;
import org.xml.sax.Attributes;


public class MapEntryTagHandler extends JsfTagHandler {

    public MapEntryTagHandler(){
    }
    
    public void start(TagHandlerContext context, String body) {
    	MapEntryElement mapEntry = createMapEntryElement();
        context.push(mapEntry);
    }
    
    public void end(TagHandlerContext context, Attributes body) {
    	MapEntryElement mapEntry = (MapEntryElement)context.pop();
        MapEntriesElement mapEntries = (MapEntriesElement)context.peek();
        mapEntries.addMapEntry(mapEntry);
    }
    
    protected MapEntryElement createMapEntryElement() {
        return new MapEntryElementImpl();
    }

}
