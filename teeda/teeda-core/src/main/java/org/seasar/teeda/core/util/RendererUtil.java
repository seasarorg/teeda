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
package org.seasar.teeda.core.util;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.UIDefaultAttribute;

import org.seasar.framework.util.IntegerConversionUtil;
import org.seasar.teeda.core.JsfConstants;

/**
 * @author manhole
 */
public class RendererUtil {

    private RendererUtil() {
    }

    public static boolean renderAttributes(ResponseWriter writer,
            UIComponent component, String[] attributeNames) throws IOException {

        boolean somethingDone = false;
        for (int i = 0, len = attributeNames.length; i < len; i++) {
            String attrName = attributeNames[i];
            if (renderAttribute(writer, component, attrName)) {
                somethingDone = true;
            }
        }
        return somethingDone;
    }

    static boolean renderAttribute(ResponseWriter writer,
            UIComponent component, String attributeName) throws IOException {

        Object value = component.getAttributes().get(attributeName);
        return renderAttribute(writer, attributeName, value, attributeName);
    }

    public static void renderAttribute(ResponseWriter writer,
            String attributeName, Object value) throws IOException {
        renderAttribute(writer, attributeName, value, attributeName);
    }

    public static boolean renderAttribute(ResponseWriter writer,
            String attributeName, Object value, String propertyName)
            throws IOException {
        if (!shouldRenderAttribute(attributeName, value)) {
            return false;
        }
        attributeName = toHtmlAttributeName(attributeName);
        writer.writeAttribute(attributeName, value, propertyName);
        return true;
    }

    private static String toHtmlAttributeName(String attributeName) {
        if (attributeName.equalsIgnoreCase(JsfConstants.STYLE_CLASS_ATTR)) {
            return JsfConstants.CLASS_ATTR;
        } else if (attributeName
                .equalsIgnoreCase(JsfConstants.ACCEPTCHARSET_ATTR)) {
            return JsfConstants.ACCEPT_CHARSET_ATTR;
        } else {
            return attributeName;
        }
    }

    static boolean shouldRenderAttribute(String attributeName, Object value) {
        if (isDefaultAttributeValue(value)) {
            return false;
        }
        if (JsfConstants.COLSPAN_ATTR.equals(attributeName)) {
            Integer integerValue = IntegerConversionUtil.toInteger(value);
            if (integerValue == null) {
                return false;
            }
            if (integerValue.intValue() <= 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDefaultAttributeValue(Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof Boolean) {
            return UIDefaultAttribute.isDefaultBoolean(((Boolean) value)
                    .booleanValue());
        }
        if (value instanceof Integer) {
            return UIDefaultAttribute
                    .isDefaultInt(((Integer) value).intValue());
        }

        return false;
    }

    public static boolean shouldRenderIdAttribute(UIComponent component) {
        String id = component.getId();
        if (id != null && !id.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
            return true;
        }
        return false;
    }

    public static void renderIdAttributeIfNecessary(ResponseWriter writer,
            UIComponent component, String idValue) throws IOException {
        if (RendererUtil.shouldRenderIdAttribute(component)) {
            RendererUtil.renderAttribute(writer, JsfConstants.ID_ATTR, idValue);
        }
    }

    public static void renderCheckedAttribute(ResponseWriter writer)
            throws IOException {
        renderAttribute(writer, JsfConstants.CHECKED_ATTR,
                JsfConstants.CHECKED_VALUE);
    }

    public static void renderDisabledAttribute(ResponseWriter writer)
            throws IOException {
        renderAttribute(writer, JsfConstants.DISABLED_ATTR,
                JsfConstants.DISABLED_VALUE);
    }

    // TODO selected selected="true"

}
