/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.config.faces.handler;

import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.core.config.faces.element.MapEntriesElement;
import org.seasar.teeda.core.config.faces.element.MapEntryElement;
import org.seasar.teeda.core.config.faces.element.impl.MapEntryElementImpl;
import org.xml.sax.Attributes;

/**
 * @author shot
 */
public class MapEntryTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 1L;

    public MapEntryTagHandler() {
    }

    public void start(TagHandlerContext context, Attributes attributes) {
        MapEntryElement mapEntry = createMapEntryElement();
        context.push(mapEntry);
    }

    public void end(TagHandlerContext context, String body) {
        MapEntryElement mapEntry = (MapEntryElement) context.pop();
        MapEntriesElement mapEntries = (MapEntriesElement) context.peek();
        mapEntries.addMapEntry(mapEntry);
    }

    protected MapEntryElement createMapEntryElement() {
        return new MapEntryElementImpl();
    }

}
