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
package org.seasar.teeda.ajax;

/**
 * @author shot
 * 
 */
public class QuoteUtil {

    private QuoteUtil() {
    }
    
    public static String quote(String str) {
        if (str == null || str.length() == 0) {
            return "\"\"";
        }
        char previous;
        char current = 0;
        int len = str.length();
        StringBuffer sb = new StringBuffer(len + 4);
        sb.append('"');
        for (int i = 0; i < len; i += 1) {
            previous = current;
            current = str.charAt(i);
            switch (current) {
            case '\\':
            case '"':
                sb.append('\\');
                sb.append(current);
                break;
            case '/':
                if (previous == '<') {
                    sb.append('\\');
                }
                sb.append(current);
                break;
            case '\b':
                sb.append("\\b");
                break;
            case '\t':
                sb.append("\\t");
                break;
            case '\n':
                sb.append("\\n");
                break;
            case '\f':
                sb.append("\\f");
                break;
            case '\r':
                sb.append("\\r");
                break;
            default:
                if (current < ' ') {
                    String t = "000" + Integer.toHexString(current);
                    sb.append("\\u" + t.substring(t.length() - 4));
                } else {
                    sb.append(current);
                }
            }
        }
        sb.append('"');
        return sb.toString();
    }
}
