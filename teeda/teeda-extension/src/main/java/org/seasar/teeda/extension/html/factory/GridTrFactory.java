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
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.PageDesc;

/**
 * @author manhole
 */
public class GridTrFactory extends AbstractGridChildrenFactory {

    private static final String ROW_STYLE_CLASS = "Row"
            + StringUtil.capitalize(JsfConstants.STYLE_CLASS_ATTR);

    protected String getHtmlTagName() {
        return JsfConstants.TR_ELEM;
    }

    protected String getTagName() {
        return "gridTr";
    }

    protected void customizeProperties(Map properties, ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc) {
        super
                .customizeProperties(properties, elementNode, pageDesc,
                        actionDesc);
        final ElementNode gridNode = getGridNode(elementNode, pageDesc,
                actionDesc);
        final String naturalName = getNaturalName(gridNode.getId());
        final String attrName = naturalName + ROW_STYLE_CLASS;
        if (!pageDesc.hasProperty(attrName)) {
            return;
        }
        properties.put(JsfConstants.STYLE_CLASS_ATTR, getBindingExpression(
                pageDesc.getPageName(), attrName));
    }

}
