/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.config.taglib.rule;

import org.seasar.framework.xml.TagHandlerRule;
import org.seasar.teeda.extension.config.taglib.handler.TaglibTagClassTagHandler;
import org.seasar.teeda.extension.config.taglib.handler.TaglibTagNameTagHandler;
import org.seasar.teeda.extension.config.taglib.handler.TaglibTagTagHandler;
import org.seasar.teeda.extension.config.taglib.handler.TaglibTaglibTagHandler;
import org.seasar.teeda.extension.config.taglib.handler.TaglibUriTagHandler;

/**
 * @author higa
 *
 */
public class TaglibTagHandlerRule extends TagHandlerRule {

    private static final long serialVersionUID = 1L;

    public TaglibTagHandlerRule() {
        addTagHandler("/taglib", new TaglibTaglibTagHandler());
        addTagHandler("/taglib/uri", new TaglibUriTagHandler());
        addTagHandler("/taglib/tag", new TaglibTagTagHandler());
        addTagHandler("/taglib/tag/name", new TaglibTagNameTagHandler());
        addTagHandler("/taglib/tag/tagclass", new TaglibTagClassTagHandler());
        addTagHandler("/taglib/tag/tag-class", new TaglibTagClassTagHandler());
    }
}
