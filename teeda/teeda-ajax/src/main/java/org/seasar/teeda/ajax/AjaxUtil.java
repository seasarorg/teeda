/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import javax.servlet.http.HttpServletResponse;

/**
 * @author mopemope
 * @author higa
 * @author yone
 */
public class AjaxUtil {

    private static String contentTypeJson = null;

    private static String contentTypeXml = null;

    private static String contentTypeHtml = null;

    private static String contentTypeText = null;

    public static void clear() {
        contentTypeJson = null;
        contentTypeXml = null;
        contentTypeHtml = null;
        contentTypeText = null;
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
            contentType = (AjaxUtil.contentTypeJson == null) ? AjaxConstants.CONTENT_TYPE_JSON
                    : AjaxUtil.contentTypeJson;
        } else if (result.startsWith("<?xml")) {
            contentType = (AjaxUtil.contentTypeXml == null) ? AjaxConstants.CONTENT_TYPE_XML
                    : AjaxUtil.contentTypeXml;
        } else if (result.startsWith("<") && result.endsWith(">")) {
            contentType = (AjaxUtil.contentTypeHtml == null) ? AjaxConstants.CONTENT_TYPE_HTML
                    : AjaxUtil.contentTypeHtml;
        } else {
            contentType = (AjaxUtil.contentTypeText == null) ? AjaxConstants.CONTENT_TYPE_TEXT
                    : AjaxUtil.contentTypeText;
        }
        response.setContentType(contentType);
    }

    //    public static String toJson(Object o) {
    //        StringBuffer buf = new StringBuffer(100);
    //        append(buf, o);
    //        return buf.toString();
    //    }
    //
    //    public static void append(StringBuffer buf, Object o) {
    //        if (o == null) {
    //            buf.append(AjaxConstants.NULL_STRING);
    //        } else if (o instanceof String) {
    //            buf.append(quote((String) o));
    //        } else if (o instanceof Float) {
    //            appendFloat(buf, (Float) o);
    //        } else if (o instanceof Double) {
    //            appendDouble(buf, (Double) o);
    //        } else if (o instanceof Number) {
    //            buf.append(o.toString());
    //        } else if (o instanceof Boolean) {
    //            appendBoolean(buf, (Boolean) o);
    //        } else if (o instanceof Collection) {
    //            appendArray(buf, ((Collection) o).toArray());
    //        } else if (o instanceof Object[]) {
    //            appendArray(buf, (Object[]) o);
    //        } else if (o instanceof Map) {
    //            appendMap(buf, (Map) o);
    //        } else if (o.getClass().isArray()) {
    //            appendArray(buf, o);
    //        } else {
    //            appendBean(buf, o);
    //        }
    //    }
    //
    //    public static void appendFloat(StringBuffer buf, Float f) {
    //        if (f.isNaN() || f.isInfinite()) {
    //            throw new IllegalArgumentException(f.toString());
    //        }
    //        buf.append(f.toString());
    //    }
    //
    //    public static void appendDouble(StringBuffer buf, Double d) {
    //        if (d.isNaN() || d.isInfinite()) {
    //            throw new IllegalArgumentException(d.toString());
    //        }
    //        buf.append(d.toString());
    //    }
    //
    //    public static void appendBoolean(StringBuffer buf, Boolean b) {
    //        if (Boolean.TRUE.equals(b)) {
    //            buf.append(AjaxConstants.TRUE_STRING);
    //        } else {
    //            buf.append(AjaxConstants.FALSE_STRING);
    //        }
    //    }
    //
    //    public static void appendArray(StringBuffer buf, Object[] array) {
    //        int length = array.length;
    //        buf.append(AjaxConstants.START_BRACKET);
    //        for (int i = 0; i < length; ++i) {
    //            append(buf, array[i]);
    //            buf.append(AjaxConstants.COMMA);
    //        }
    //        if (length > 0) {
    //            buf.setLength(buf.length() - 1);
    //        }
    //        buf.append(AjaxConstants.END_BRACKET);
    //    }
    //
    //    public static void appendMap(StringBuffer buf, Map map) {
    //        buf.append(AjaxConstants.START_BRACE);
    //        for (Iterator i = map.keySet().iterator(); i.hasNext();) {
    //            String key = (String) i.next();
    //            buf.append(key + AjaxConstants.COLON);
    //            append(buf, map.get(key));
    //            buf.append(AjaxConstants.COMMA);
    //        }
    //        if (map.size() > 0) {
    //            buf.setLength(buf.length() - 1);
    //        }
    //        buf.append(AjaxConstants.END_BRACE);
    //    }
    //
    //    public static void appendBean(StringBuffer buf, Object bean) {
    //        buf.append(AjaxConstants.START_BRACE);
    //        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(bean.getClass());
    //        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
    //            PropertyDesc pd = beanDesc.getPropertyDesc(i);
    //            buf.append(pd.getPropertyName() + AjaxConstants.COLON);
    //            append(buf, pd.getValue(bean));
    //            buf.append(AjaxConstants.COMMA);
    //        }
    //        if (beanDesc.getPropertyDescSize() > 0) {
    //            buf.setLength(buf.length() - 1);
    //        }
    //        buf.append(AjaxConstants.END_BRACE);
    //    }
    //
    //    public static void appendArray(StringBuffer buf, Object o) {
    //        int length = Array.getLength(o);
    //        buf.append(AjaxConstants.START_BRACKET);
    //        for (int i = 0; i < length; i++) {
    //            Object value = Array.get(o, i);
    //            append(buf, value);
    //            buf.append(AjaxConstants.COMMA);
    //        }
    //        if (length > 0) {
    //            buf.setLength(buf.length() - 1);
    //        }
    //        buf.append(AjaxConstants.END_BRACKET);
    //    }
    //
    //    public static String quote(String str) {
    //        if (str == null || str.length() == 0) {
    //            return "\"\"";
    //        }
    //        char current = 0;
    //        int len = str.length();
    //        StringBuffer sb = new StringBuffer(len + 4);
    //        sb.append('"');
    //        for (int i = 0; i < len; ++i) {
    //            current = str.charAt(i);
    //            switch (current) {
    //            case '\\':
    //            case '/':
    //            case '"':
    //                sb.append('\\');
    //                sb.append(current);
    //                break;
    //            case '\b':
    //                sb.append("\\b");
    //                break;
    //            case '\t':
    //                sb.append("\\t");
    //                break;
    //            case '\n':
    //                sb.append("\\n");
    //                break;
    //            case '\f':
    //                sb.append("\\f");
    //                break;
    //            case '\r':
    //                sb.append("\\r");
    //                break;
    //            default:
    //                if (current < ' ') {
    //                    String t = "000" + Integer.toHexString(current);
    //                    sb.append("\\u" + t.substring(t.length() - 4));
    //                } else {
    //                    sb.append(current);
    //                }
    //            }
    //        }
    //        sb.append('"');
    //        return sb.toString();
    //    }

    public static void setContentTypeJson(final String contentTypeJson) {
        AjaxUtil.contentTypeJson = contentTypeJson;
    }

    public static void setContentTypeXml(final String contentTypeXml) {
        AjaxUtil.contentTypeXml = contentTypeXml;
    }

    public static void setContentTypeHtml(final String contentTypeHtml) {
        AjaxUtil.contentTypeHtml = contentTypeHtml;
    }

    public static void setContentTypeText(final String contentTypeText) {
        AjaxUtil.contentTypeText = contentTypeText;
    }

}
