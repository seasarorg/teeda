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
package org.seasar.teeda.extension.config.taglib.handler;

import org.seasar.framework.xml.TagHandler;
import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.extension.config.taglib.element.TagElement;
import org.seasar.teeda.extension.config.taglib.element.TaglibElement;
import org.seasar.teeda.extension.config.taglib.element.impl.TagElementImpl;
import org.xml.sax.Attributes;

/**
 * @author higa
 *
 */
public class TaglibTagTagHandler extends TagHandler {

    private static final long serialVersionUID = 1L;

    /**
     * @see org.seasar.framework.xml.sax.handler.TagHandler#start(org.seasar.framework.xml.sax.handler.TagHandlerContext, org.xml.sax.Attributes)
     */
    public void start(TagHandlerContext context, Attributes attributes) {
        TagElement tagElement = new TagElementImpl();
        context.push(tagElement);
    }

    /**
     * @see org.seasar.framework.xml.sax.handler.TagHandler#end(org.seasar.framework.xml.sax.handler.TagHandlerContext, java.lang.String)
     */
    public void end(TagHandlerContext context, String body) {
        TagElement tagElement = (TagElement) context.pop();
        TaglibElement taglibElement = (TaglibElement) context.peek();
        taglibElement.addTagElement(tagElement);

    }

}
