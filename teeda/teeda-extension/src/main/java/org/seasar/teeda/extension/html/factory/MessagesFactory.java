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
package org.seasar.teeda.extension.html.factory;

import java.util.Map;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.PageDesc;

/**
 * @author higa
 *
 */
public class MessagesFactory extends AbstractElementProcessorFactory {

    private static final String TAG_NAME = "messages";

    private static final String MESSAGES = "messages";

    public boolean isMatch(ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        if (!JsfConstants.SPAN_ELEM.equalsIgnoreCase(elementNode.getTagName())) {
            return false;
        }
        String id = elementNode.getId();
        if (id == null) {
            return false;
        }
        return id.equals(MESSAGES);
    }

    protected void customizeProperties(Map properties, ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc) {
        super
                .customizeProperties(properties, elementNode, pageDesc,
                        actionDesc);
        properties.put(JsfConstants.GLOBAL_ONLY_ATTR, JsfConstants.TRUE);
        properties.put(JsfConstants.SHOW_SUMMARY_ATTR, JsfConstants.FALSE);
        properties.put(JsfConstants.SHOW_DETAIL_ATTR, JsfConstants.TRUE);
    }

    protected String getTagName() {
        return TAG_NAME;
    }

    protected String getUri() {
        return JsfConstants.JSF_HTML_URI;
    }

    public boolean isLeaf() {
        return true;
    }

}