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
package org.seasar.teeda.extension.util;

import java.util.Map;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;

/**
 * @author shot
 */
public class LayoutUtil {

    private LayoutUtil() {
    }

    public static void styleToLayout(final Map properties) {
        String style = (String) properties.get(JsfConstants.STYLE_ATTR);
        if (style.indexOf(JsfConstants.PAGE_DIRECTION_VALUE) >= 0) {
            setStyleAndLayout(properties, JsfConstants.PAGE_DIRECTION_VALUE,
                    style);
        } else if (style.indexOf(JsfConstants.LINE_DIRECTION_VALUE) >= 0) {
            setStyleAndLayout(properties, JsfConstants.LINE_DIRECTION_VALUE,
                    style);
        }
    }

    private static void setStyleAndLayout(Map properties, String layout,
            String style) {
        final String s = StringUtil.replace(style, layout, "");
        properties.put(JsfConstants.STYLE_ATTR, s);
        properties.put(JsfConstants.LAYOUT_ATTR, layout);
    }

}
