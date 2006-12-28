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

/**
 * @author yone
 */
public final class HTMLEncodeUtil {

    private HTMLEncodeUtil() {
    }

    public static String encodeAll(final String s) {
        return encode(s, true, true);
    }

    public static String encode(final String s, final boolean quote,
            final boolean amp) {
        char[] chars = s.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            // &nbsp; 0xA0
            if ((int) c == '\u00A0') {
                sb.append("&nbsp;");
            } else if (c == '<') {
                sb.append("&lt;");
            } else if (c == '>') {
                sb.append("&gt;");
            } else if (amp && c == '&') {
                sb.append("&amp;");
            } else if (c == '"') {
                sb.append("&quot;");
            } else if (quote && c == '\'') {
                sb.append("&#39;");
            } else {
                sb.append(c);
            }
        }
        return new String(sb);
    }

}