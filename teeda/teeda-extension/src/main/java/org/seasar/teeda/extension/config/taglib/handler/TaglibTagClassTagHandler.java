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

import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.xml.TagHandler;
import org.seasar.framework.xml.TagHandlerContext;
import org.seasar.teeda.extension.config.taglib.element.TagElement;

/**
 * @author higa
 *
 */
public class TaglibTagClassTagHandler extends TagHandler {

    private static final long serialVersionUID = 1L;

    public void end(TagHandlerContext context, String body) {
        if (body != null) {
            body = body.trim();
        }
        if (!StringUtil.isEmpty(body)) {
            Class tagClass = ClassUtil.forName(body);
            TagElement tagElement = (TagElement) context.peek();
            tagElement.setTagClass(tagClass);
        }
    }
}
