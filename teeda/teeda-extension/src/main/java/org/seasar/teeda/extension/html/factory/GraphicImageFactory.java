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

import java.util.Map;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.PageDesc;

/**
 * @author koichik
 * @since 1.0.12
 */
public class GraphicImageFactory extends AbstractElementProcessorFactory {

    private static final String TAG_NAME = "img";

    public boolean isMatch(final ElementNode elementNode,
            final PageDesc pageDesc, final ActionDesc actionDesc) {
        if (elementNode.getTagName().equals(JsfConstants.IMG_ELEM)) {
            final String src = elementNode.getProperty(JsfConstants.SRC_ATTR);
            if (!StringUtil.isEmpty(src) && !src.startsWith("/")) {
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

    protected void customizeProperties(final Map properties,
            final ElementNode elementNode, final PageDesc pageDesc,
            final ActionDesc actionDesc) {
        super
                .customizeProperties(properties, elementNode, pageDesc,
                        actionDesc);
        renameProperty(properties, JsfConstants.SRC_ATTR, JsfConstants.URL_ATTR);
    }

}
