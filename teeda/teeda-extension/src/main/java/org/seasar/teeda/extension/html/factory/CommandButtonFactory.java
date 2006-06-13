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
package org.seasar.teeda.extension.html.factory;

import java.util.Map;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.PageDesc;

/**
 * @author shot
 */
public class CommandButtonFactory extends AbstractElementProcessorFactory {

    private static final String TAG_NAME = "commandButton";

    public boolean isMatch(ElementNode elementNode) {
        if (!JsfConstants.INPUT_ELEM.equalsIgnoreCase(elementNode.getTagName())) {
            return false;
        }
        String type = elementNode.getProperty(JsfConstants.TYPE_ATTR);
        return (JsfConstants.SUBMIT_VALUE.equalsIgnoreCase(type) || JsfConstants.BUTTON_VALUE
                .equalsIgnoreCase(type));
    }

    protected void customizeProperties(Map properties, ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc) {
        super
                .customizeProperties(properties, elementNode, pageDesc,
                        actionDesc);
        String baseName = null;
        String id = elementNode.getId();
        if (pageDesc != null && pageDesc.isValid(id)) {
            baseName = pageDesc.getPageName();
        } else {
            baseName = actionDesc.getActionName();
        }
        properties.put(JsfConstants.ACTION_ATTR, getBindingExpression(baseName,
                id));
    }

    protected String getTagName() {
        return TAG_NAME;
    }

    protected String getUri() {
        return JsfConstants.JSF_HTML_URI;
    }

}
