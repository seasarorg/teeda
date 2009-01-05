/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.PageDesc;

/**
 * @author shot
 */
public class CommandButtonFactory extends AbstractElementProcessorFactory {

    private static final String TAG_NAME = "commandButton";

    public boolean isMatch(ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        if (!JsfConstants.INPUT_ELEM.equalsIgnoreCase(elementNode.getTagName())) {
            return false;
        }
        String type = elementNode.getProperty(JsfConstants.TYPE_ATTR);
        if (!JsfConstants.SUBMIT_VALUE.equalsIgnoreCase(type)
                && !JsfConstants.BUTTON_VALUE.equalsIgnoreCase(type)
                && !JsfConstants.IMAGE_VALUE.equalsIgnoreCase(type)) {
            return false;
        }
        String id = elementNode.getId();
        if (id == null) {
            return false;
        }
        if (id.startsWith(ExtensionConstants.GO_PREFIX)) {
            return true;
        }
        if (id.startsWith(ExtensionConstants.DO_PREFIX)) {
            if (pageDesc != null && pageDesc.hasMethod(id)) {
                return true;
            }
            if (actionDesc != null && actionDesc.hasMethod(id)) {
                return true;
            }
        }
        if (id.startsWith(ExtensionConstants.JUMP_PREFIX)) {
            return true;
        }
        return false;
    }

    protected void customizeProperties(Map properties, ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc) {
        super
                .customizeProperties(properties, elementNode, pageDesc,
                        actionDesc);
        final String id = elementNode.getId();
        if (id.startsWith(ExtensionConstants.GO_PREFIX)) {
            String next = StringUtil.decapitalize(id.substring(2));
            properties.put(JsfConstants.ACTION_ATTR, next);
        } else if (id.startsWith(ExtensionConstants.JUMP_PREFIX)) {
            String next = StringUtil.decapitalize(id
                    .substring(ExtensionConstants.JUMP_PREFIX.length()));
            properties.put(JsfConstants.ACTION_ATTR, next);
            properties.put(JsfConstants.IMMEDIATE_ATTR, JsfConstants.TRUE);
        } else {
            String baseName = null;
            if (pageDesc != null && pageDesc.hasMethod(id)) {
                baseName = pageDesc.getPageName();
            } else {
                baseName = actionDesc.getActionName();
            }
            properties.put(JsfConstants.ACTION_ATTR, getBindingExpression(
                    baseName, id));
        }
        String onclick = elementNode.getProperty(JsfConstants.ONCLICK_ATTR);
        String dynamicOnclick = (String) properties
                .get(JsfConstants.ONCLICK_ATTR);
        if (!StringUtil.isEmpty(onclick)
                && !BindingUtil.isValueReference(dynamicOnclick)) {
            onclick = removeTemplateOnlyString(onclick, "location.href");
            onclick = removeTemplateOnlyString(onclick, "history.back()");
            properties.put(JsfConstants.ONCLICK_ATTR, onclick);
        }
        renameProperty(properties, JsfConstants.SRC_ATTR,
                JsfConstants.IMAGE_ATTR);
    }

    protected String removeTemplateOnlyString(String onclick, String target) {
        int start = onclick.indexOf(target);
        if (start >= 0) {
            int end = onclick.indexOf(";");
            if (end < 0) {
                onclick = "";
            } else {
                int end2 = onclick.indexOf(";", start);
                String prefix = onclick.substring(0, start);
                String suffix = onclick.substring(end2 + 1);
                onclick = prefix + suffix;
            }
        }
        return onclick;
    }

    protected String getTagName() {
        return TAG_NAME;
    }

    protected String getUri() {
        return ExtensionConstants.TEEDA_EXTENSION_URI;
    }
}
