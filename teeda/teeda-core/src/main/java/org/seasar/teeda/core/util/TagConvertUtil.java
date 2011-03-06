/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import java.util.StringTokenizer;

public class TagConvertUtil {

    private static final String[] EMPTY_STRING = new String[0];

    private TagConvertUtil() {
    }

    public static String[] convertToSetter(String tagName) {
        return convertToSetter(tagName, "-");
    }

    public static String[] convertToSetter(String tagName, String delim) {
        if (tagName == null) {
            return EMPTY_STRING;
        }

        if (tagName.indexOf(delim) < 0) {
            tagName = capitalizePropertyName(tagName);
            return createSetters(tagName);
        }

        StringBuffer buf = new StringBuffer(20);
        StringTokenizer st = new StringTokenizer(tagName, delim);
        String s = null;
        while (st.hasMoreElements()) {
            s = (String) st.nextElement();
            if (s.length() >= 1) {
                buf.append(capitalizePropertyName(s));
            }
        }
        return createSetters(buf.toString());
    }

    private static String capitalizePropertyName(String propertyName) {
        if (propertyName.length() >= 1) {
            char[] chars = propertyName.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            return new String(chars);
        }
        return propertyName;
    }

    private static String[] createSetters(String property) {
        return new String[] { "set" + property, "add" + property };
    }
}
