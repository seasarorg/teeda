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

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * @author mopemope
 * @author higa
 *
 */
public class AjaxUtil {

    protected AjaxUtil() {
    }

    public static boolean isJSON(String str) {
        return str != null
                && (str.startsWith(AjaxConstants.START_BRACE) && str
                        .endsWith(AjaxConstants.END_BRACE));
    }

    public static void setContentType(HttpServletResponse response,
            String result) {
        String contentType;
        if (result == null || result.startsWith(AjaxConstants.START_BRACE)
                && result.endsWith(AjaxConstants.END_BRACE)) {
            contentType = AjaxConstants.CONTENT_TYPE_JSON;
        } else if (result.startsWith("<?xml")) {
            contentType = AjaxConstants.CONTENT_TYPE_XML;
        } else if (result.startsWith("<") && result.endsWith(">")) {
            contentType = AjaxConstants.CONTENT_TYPE_HTML;
        } else {
            contentType = AjaxConstants.CONTENT_TYPE_TEXT;
        }
        response.setContentType(contentType);
    }

    public static String toJson(Object o) {
        StringBuffer buf = new StringBuffer(100);
        append(buf, o);
        return buf.toString();
    }

    public static void append(StringBuffer buf, Object o) {
        if (o == null) {
            buf.append(AjaxConstants.NULL_STRING);
        } else if (o instanceof String) {
            buf.append(quote((String) o));
        } else if (o instanceof Float) {
            appendFloat(buf, (Float) o);
        } else if (o instanceof Double) {
            appendDouble(buf, (Double) o);
        } else if (o instanceof Number) {
            buf.append(o.toString());
        } else if (o instanceof Boolean) {
            appendBoolean(buf, (Boolean) o);
        } else if (o instanceof Collection) {
            appendArray(buf, ((Collection) o).toArray());
        } else if (o instanceof Object[]) {
            appendArray(buf, (Object[]) o);
        } else if (o instanceof Map) {
            appendMap(buf, (Map) o);
        } else {
            appendBean(buf, o);
        }
    }

    public static void appendFloat(StringBuffer buf, Float f) {
        if (f.isNaN() || f.isInfinite()) {
            throw new IllegalArgumentException(f.toString());
        }
        buf.append(f.toString());
    }

    public static void appendDouble(StringBuffer buf, Double d) {
        if (d.isNaN() || d.isInfinite()) {
            throw new IllegalArgumentException(d.toString());
        }
        buf.append(d.toString());
    }

    public static void appendBoolean(StringBuffer buf, Boolean b) {
        if (Boolean.TRUE.equals(b)) {
            buf.append(AjaxConstants.TRUE_STRING);
        } else {
            buf.append(AjaxConstants.FALSE_STRING);
        }
    }

    public static void appendArray(StringBuffer buf, Object[] array) {
        int length = array.length;
        buf.append(AjaxConstants.START_BRACKET);
        for (int i = 0; i < length; ++i) {
            append(buf, array[i]);
            buf.append(AjaxConstants.COMMA);
        }
        if (length > 0) {
            buf.setLength(buf.length() - 1);
        }
        buf.append(AjaxConstants.END_BRACKET);
    }

    public static void appendMap(StringBuffer buf, Map map) {
        buf.append(AjaxConstants.START_BRACE);
        for (Iterator i = map.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            buf.append(key + AjaxConstants.COLON);
            append(buf, map.get(key));
            buf.append(AjaxConstants.COMMA);
        }
        if (map.size() > 0) {
            buf.setLength(buf.length() - 1);
        }
        buf.append(AjaxConstants.END_BRACE);
    }

    public static void appendBean(StringBuffer buf, Object bean) {
        buf.append(AjaxConstants.START_BRACE);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(bean.getClass());
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            buf.append(pd.getPropertyName() + AjaxConstants.COLON);
            append(buf, pd.getValue(bean));
            buf.append(AjaxConstants.COMMA);
        }
        if (beanDesc.getPropertyDescSize() > 0) {
            buf.setLength(buf.length() - 1);
        }
        buf.append(AjaxConstants.END_BRACE);
    }

    public static String quote(String str) {
        if (str == null || str.length() == 0) {
            return "\"\"";
        }
        char current = 0;
        int len = str.length();
        StringBuffer sb = new StringBuffer(len + 4);
        sb.append('"');
        for (int i = 0; i < len; ++i) {
            current = str.charAt(i);
            switch (current) {
            case '\\':
            case '/':
            case '"':
                sb.append('\\');
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
