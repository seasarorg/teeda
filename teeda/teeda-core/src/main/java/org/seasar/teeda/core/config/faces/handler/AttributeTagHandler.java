/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
import org.seasar.teeda.core.config.faces.element.AttributeElement;
import org.seasar.teeda.core.config.faces.element.AttributeHolder;
import org.seasar.teeda.core.config.faces.element.impl.AttributeElementImpl;
import org.xml.sax.Attributes;

/**
 * @author shot
 */
public class AttributeTagHandler extends JsfTagHandler {

    private static final long serialVersionUID = 1L;

    public AttributeTagHandler() {
    }

    public void start(TagHandlerContext context, Attributes attributes) {
        AttributeElement attribute = createAttributeElement();
        context.push(attribute);
    }

    public void end(TagHandlerContext context, String body) {
        AttributeElement attribute = (AttributeElement) context.pop();
        AttributeHolder holder = (AttributeHolder) context.peek();
        holder.addAttributeElement(attribute);
    }

    protected AttributeElement createAttributeElement() {
        return new AttributeElementImpl();
    }

}
