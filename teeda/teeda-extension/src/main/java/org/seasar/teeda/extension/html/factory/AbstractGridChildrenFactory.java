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
package org.seasar.teeda.extension.html.factory;

import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.PageDesc;

/**
 * @author manhole
 */
public abstract class AbstractGridChildrenFactory extends
        AbstractElementProcessorFactory {

    public boolean isMatch(ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        if (!getHtmlTagName().equalsIgnoreCase(elementNode.getTagName())) {
            return false;
        }
        return GridFactoryUtil.isGridChild(elementNode, pageDesc, actionDesc);
    }

    protected abstract String getHtmlTagName();

    protected String getUri() {
        return ExtensionConstants.TEEDA_EXTENSION_URI;
    }

}
