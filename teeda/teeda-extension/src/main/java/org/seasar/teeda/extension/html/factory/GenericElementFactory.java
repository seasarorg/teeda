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

import java.util.Iterator;
import java.util.Map;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.ElementProcessor;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.processor.GenericElementProcessor;

/**
 * @author higa
 */
public class GenericElementFactory extends AbstractElementProcessorFactory {

    private static final String TAG_NAME = "element";

    public boolean isMatch(ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        String id = elementNode.getProperty(JsfConstants.ID_ATTR);
        if (pageDesc == null) {
            return false;
        }
        if (id == null) {
            return false;
        }
        final int pos = id.indexOf('-');
        if (pos > -1) {
            id = id.substring(0, pos) +
                    StringUtil.capitalize(id.substring(pos + 1));
        }
        for (Iterator i = elementNode.getPropertyNameIterator(); i.hasNext();) {
            String key = (String) i.next();
            if ("id".equalsIgnoreCase(key)) {
                continue;
            }
            String propName = id + StringUtil.capitalize(key);
            if (pageDesc.hasDynamicProperty(propName)) {
                return true;
            }
        }
        return false;
    }

    protected String getTagName() {
        return TAG_NAME;
    }

    protected String getUri() {
        return ExtensionConstants.TEEDA_EXTENSION_URI;
    }

    protected ElementProcessor createProcessor(ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc, String uri, String tagName) {
        Class tagClass = getTagClass(uri, tagName);
        Map props = createProperties(elementNode, pageDesc, actionDesc);
        ElementProcessor processor = new GenericElementProcessor(tagClass,
                props, elementNode.getTagName());
        customizeProcessor(processor, elementNode, pageDesc, actionDesc);
        return processor;
    }
}
