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

import javax.faces.internal.LabelUtil;

import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.PageDesc;

/**
 * @author shot
 */
public class LabelFactoryUtil {

    public static void storeLabelAttributesTo(final Map properties,
            final ElementNode elementNode, final PageDesc pageDesc,
            final NamingConvention nc) {
        if (properties == null || elementNode == null || pageDesc == null) {
            return;
        }
        final String pageName = pageDesc.getPageName();
        final String propertiesName = LabelUtil.getPropertiesName(nc, pageName);
        final String id = StringUtil.trimSuffix(elementNode.getId(), "Label");
        final String key = LabelUtil.getLabelKeySuffix(nc, pageName) + "." + id;
        final String defaultPropertiesName = LabelUtil
                .getDefaultApplicationPropertiesName(nc, pageName);

        properties.put(ExtensionConstants.KEY_ATTR, key);
        properties.put(ExtensionConstants.PROPERTIES_NAME_ATTR, propertiesName);
        properties.put(ExtensionConstants.DEFAULT_KEY, id);
        properties.put(ExtensionConstants.DEFAULT_PROPERTIES_NAME_ATTR,
                defaultPropertiesName);

    }
}
