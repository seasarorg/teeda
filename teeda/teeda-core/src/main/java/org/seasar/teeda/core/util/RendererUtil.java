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
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

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

    public static boolean renderAttribute(ResponseWriter writer,
            UIComponent component, String attributeName) throws IOException {

        Object value = component.getAttributes().get(attributeName);
        return renderAttribute(writer, attributeName, value, attributeName);
    }

    public static boolean renderAttribute(ResponseWriter writer,
            String attributeName, Object value, String propertyName)
            throws IOException {

        if (value == null) {
            return false;
        }
        if (attributeName.equalsIgnoreCase(JsfConstants.STYLE_CLASS_ATTR)) {
            attributeName = JsfConstants.CLASS_ATTR;
        }
        writer.writeAttribute(attributeName, value, propertyName);
        return true;
    }

}