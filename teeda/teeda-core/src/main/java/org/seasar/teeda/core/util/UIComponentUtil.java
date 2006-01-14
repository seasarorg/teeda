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

import java.util.Map;

import javax.faces.component.UIComponent;

/**
 * @author manhole
 */
public class UIComponentUtil {

    private UIComponentUtil() {
    }

    public static boolean containsAttributes(UIComponent component,
            String[] attributeNames) {
        Map attributes = component.getAttributes();
        for (int i = 0, len = attributeNames.length; i < len; i++) {
            String attributeName = attributeNames[i];
            /*
             * don't use #containsKey method!
             * 
             * because when attributeName matches a property of this
             * UIComponent, containsKey returns false. See
             * UIComponent#getAttributes API document.
             */
            Object value = attributes.get(attributeName);
            if (value != null) {
                return true;
            }
        }
        return false;
    }

}
