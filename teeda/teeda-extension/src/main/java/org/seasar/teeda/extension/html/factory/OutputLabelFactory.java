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

import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.PageDesc;

/**
 * @author shot
 */
public class OutputLabelFactory extends AbstractElementProcessorFactory {

    private static final String TAG_NAME = "outputLabel";

    private NamingConvention namingConvention;

    public OutputLabelFactory() {
    }

    public boolean isLeaf() {
        return true;
    }

    public boolean isMatch(ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        final String tagName = elementNode.getTagName();
        if (!JsfConstants.LABEL_ELEM.equalsIgnoreCase(tagName)) {
            return false;
        }
        String id = elementNode.getId();
        return (id != null);
    }

    protected void customizeProperties(Map properties, ElementNode elementNode,
            PageDesc pageDesc, ActionDesc actionDesc) {
        super
                .customizeProperties(properties, elementNode, pageDesc,
                        actionDesc);
        if (pageDesc == null) {
            return;
        }
        LabelFactoryUtil.storeLabelAttributesTo(properties, elementNode,
                pageDesc, namingConvention);
    }

    protected void customizeDynamicProperties(String base, Map properties,
            ElementNode elementNode, PageDesc pageDesc, ActionDesc actionDesc) {
        if (base == null) {
            return;
        }
        for (Iterator i = properties.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            customizeDynamicProperty(base, key, properties, elementNode,
                    pageDesc, actionDesc);
        }
    }

    protected void customizeDynamicProperty(String base, String name,
            Map properties, ElementNode elementNode, PageDesc pageDesc,
            ActionDesc actionDesc) {
        if (pageDesc == null) {
            return;
        }
        final String pageName = pageDesc.getPageName();
        if (!StringUtil.isEmpty(base)) {
            String propName = base + "Value";
            if (pageDesc.hasDynamicProperty(propName)) {
                properties.put("value",
                        getBindingExpression(pageName, propName));
            }
        }
    }

    protected String getTagName() {
        return TAG_NAME;
    }

    protected String getUri() {
        return ExtensionConstants.TEEDA_EXTENSION_URI;
    }

    public NamingConvention getNamingConvention() {
        return namingConvention;
    }

    public void setNamingConvention(NamingConvention namingConvention) {
        this.namingConvention = namingConvention;
    }

}
