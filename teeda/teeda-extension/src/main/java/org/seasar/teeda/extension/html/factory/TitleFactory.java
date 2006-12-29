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

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.PageDesc;

/**
 * @author shot
 */
public class TitleFactory extends OutputLabelFactory {

    private static final String TAG_NAME = "title";

    public boolean isMatch(ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        if (!ExtensionConstants.TITLE_ELEM.equalsIgnoreCase(elementNode
                .getTagName())) {
            return false;
        }
        String id = elementNode.getId();
        return (id != null);
    }

    protected void customizeDynamicProperty(String base, String name,
            Map properties, ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        if (pageDesc == null) {
            return;
        }
        final String pageName = pageDesc.getPageName();
        if (!StringUtil.isEmpty(base)) {
            String propName = base + "TitleText";
            if (pageDesc.hasDynamicProperty(propName)) {
                properties.put("value",
                        getBindingExpression(pageName, propName));
            }
        }
    }

    public boolean isLeaf() {
        return true;
    }

    protected String getTagName() {
        return TAG_NAME;
    }

}
