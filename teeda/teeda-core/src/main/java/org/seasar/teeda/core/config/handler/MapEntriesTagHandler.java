/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.config.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.element.ListEntriesElement;
import org.seasar.teeda.core.config.element.ListEntriesHolder;
import org.seasar.teeda.core.config.element.MapEntriesElement;
import org.seasar.teeda.core.config.element.MapEntriesHolder;
import org.seasar.teeda.core.config.element.impl.ListEntriesElementImpl;
import org.seasar.teeda.core.config.element.impl.MapEntriesElementImpl;
import org.xml.sax.Attributes;

/**
 * @author shot
 */
public class MapEntriesTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 1L;

    public MapEntriesTagHandler(){
    }
    
    public void start(TagHandlerContext context, Attributes attributes) {
    	MapEntriesElement mapEntries = createMapEntriesElement();
        context.push(mapEntries);
    }
    
    public void end(TagHandlerContext context, String body) {
    	MapEntriesElement mapEntries = (MapEntriesElement)context.pop();
        MapEntriesHolder holder = (MapEntriesHolder)context.peek();
        holder.setMapEntries(mapEntries);
    }
    
    protected MapEntriesElement createMapEntriesElement() {
        return new MapEntriesElementImpl();
    }

}
