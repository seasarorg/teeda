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
package org.seasar.teeda.ajax;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * JSON形式の文字列を解析します。
 * @author mopemope
 */
public class JSONSerializer {

    public static String serialize(Object o) {
        StringBuffer buf = new StringBuffer(100);
        appendSerializeObject(buf, o);
        return buf.toString();
    }

    public static void appendSerializeObject(StringBuffer buf, Object o) {
        if (o == null) {
            buf.append(AjaxConstants.NULL_STRING);
        } else if (o instanceof String) {
            buf.append(quote((String) o));
        } else if (o instanceof Float) {
            appendSerializeFloat(buf, (Float) o);
        } else if (o instanceof Double) {
            appendSerializeDouble(buf, (Double) o);
        } else if (o instanceof Number) {
            buf.append(o.toString());
        } else if (o instanceof Boolean) {
            appendSerializeBoolean(buf, (Boolean) o);
        } else if (o instanceof Collection) {
            appendSerializeArray(buf, ((Collection) o).toArray());
        } else if (o instanceof Object[]) {
            appendSerializeArray(buf, (Object[]) o);
        } else if (o instanceof Map) {
            appendSerializeMap(buf, (Map) o);
        } else if (o.getClass().isArray()) {
            appendSerializeObjectArray(buf, o);
        } else {
            appendSerializeBean(buf, o);
        }
    }

    public static void appendSerializeFloat(StringBuffer buf, Float f) {
        if (f.isNaN() || f.isInfinite()) {
            throw new IllegalArgumentException(f.toString());
        }
        buf.append(f.toString());
    }

    public static void appendSerializeDouble(StringBuffer buf, Double d) {
        if (d.isNaN() || d.isInfinite()) {
            throw new IllegalArgumentException(d.toString());
        }
        buf.append(d.toString());
    }

    public static void appendSerializeBoolean(StringBuffer buf, Boolean b) {
        if (Boolean.TRUE.equals(b)) {
            buf.append(AjaxConstants.TRUE_STRING);
        } else {
            buf.append(AjaxConstants.FALSE_STRING);
        }
    }

    public static void appendSerializeArray(StringBuffer buf, Object[] array) {
        int length = array.length;
        buf.append(AjaxConstants.START_BRACKET);
        for (int i = 0; i < length; ++i) {
            appendSerializeObject(buf, array[i]);
            buf.append(AjaxConstants.COMMA);
        }
        if (length > 0) {
            buf.setLength(buf.length() - 1);
        }
        buf.append(AjaxConstants.END_BRACKET);
    }

    public static void appendSerializeMap(StringBuffer buf, Map map) {
        buf.append(AjaxConstants.START_BRACE);
        for (Iterator i = map.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            buf.append(key + AjaxConstants.COLON);
            appendSerializeObject(buf, map.get(key));
            buf.append(AjaxConstants.COMMA);
        }
        if (map.size() > 0) {
            buf.setLength(buf.length() - 1);
        }
        buf.append(AjaxConstants.END_BRACE);
    }

    public static void appendSerializeBean(StringBuffer buf, Object bean) {
        buf.append(AjaxConstants.START_BRACE);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(bean.getClass());
        for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
            PropertyDesc pd = beanDesc.getPropertyDesc(i);
            buf.append(pd.getPropertyName() + AjaxConstants.COLON);
            appendSerializeObject(buf, pd.getValue(bean));
            buf.append(AjaxConstants.COMMA);
        }
        if (beanDesc.getPropertyDescSize() > 0) {
            buf.setLength(buf.length() - 1);
        }
        buf.append(AjaxConstants.END_BRACE);
    }

    public static void appendSerializeObjectArray(StringBuffer buf, Object o) {
        int length = Array.getLength(o);
        buf.append(AjaxConstants.START_BRACKET);
        for (int i = 0; i < length; i++) {
            Object value = Array.get(o, i);
            appendSerializeObject(buf, value);
            buf.append(AjaxConstants.COMMA);
        }
        if (length > 0) {
            buf.setLength(buf.length() - 1);
        }
        buf.append(AjaxConstants.END_BRACKET);
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

    public static boolean isObject(String str) {
        return str.startsWith(AjaxConstants.START_BRACE)
                && str.endsWith(AjaxConstants.END_BRACE);
    }

    public static boolean isArray(String str) {
        return str.startsWith(AjaxConstants.START_BRACKET)
                && str.endsWith(AjaxConstants.END_BRACKET);
    }

    public static boolean isString(String str) {
        return str.startsWith(AjaxConstants.SINGLE_QUOTE)
                && str.endsWith(AjaxConstants.SINGLE_QUOTE);
    }

    public static Map evalMap(String str) {
        Map map = new HashMap();
        StringBuffer buf = new StringBuffer();
        String key = null;
        String value = null;
        boolean valueParse = false;
        int braceStart = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
            case '{':
                if (valueParse) {
                    buf.append(c);
                    braceStart++;
                }
                break;
            case '}':
                if (valueParse) {
                    if (braceStart > 0) {
                        buf.append(c);
                        braceStart--;
                    }
                }
                break;
            case '[':
                if (valueParse) {
                    buf.append(c);
                    braceStart++;
                }
                break;
            case ']':
                if (valueParse) {
                    buf.append(c);
                    braceStart--;
                }
                break;
            case ':':
                if (braceStart > 0) {
                    buf.append(c);
                } else {
                    key = JSONSerializer.evalString(buf.toString().trim());
                    valueParse = true;
                    buf = new StringBuffer();
                }
                break;
            case ',':
                value = buf.toString().trim();
                valueParse = false;
                buf = new StringBuffer();
                evalAndAddMap(key, value, map);
                break;
            default:
                buf.append(c);
                break;
            }
        }
        if (buf.length() > 0) {
            value = buf.toString().trim();
        }
        evalAndAddMap(key, value, map);
        return map;
    }

    public static List evalArray(String str) {
        List list = new ArrayList();
        StringBuffer buf = new StringBuffer();
        String value = null;
        int braceStart = 0;
        for (int i = 1; i < str.length() - 1; i++) {
            char c = str.charAt(i);
            switch (c) {
            case '{':
                buf.append(c);
                braceStart++;
                break;
            case '}':
                if (braceStart > 0) {
                    buf.append(c);
                    braceStart--;
                }
                break;
            case '[':
                buf.append(c);
                braceStart++;
                break;
            case ']':
                buf.append(c);
                braceStart--;
                break;
            case ':':
                if (braceStart > 0) {
                    buf.append(c);
                }
                break;
            case ',':
                if (braceStart > 0) {
                    buf.append(c);
                } else {
                    value = buf.toString().trim();
                    buf = new StringBuffer();
                    JSONSerializer.evalAndAddList(value, list);
                }
                break;
            default:
                buf.append(c);
                break;
            }
        }
        if (buf.length() > 0) {
            value = buf.toString().trim();
        }
        JSONSerializer.evalAndAddList(value, list);
        return list;
    }

    private static void evalAndAddList(String value, List list) {
        if (JSONSerializer.isObject(value)) {
            list.add(JSONSerializer.evalMap(value));
        } else if (JSONSerializer.isArray(value)) {
            list.add(JSONSerializer.evalArray(value));
        } else {
            Integer intValue = evalInt(value);
            if (intValue != null) {
                list.add(intValue);
            } else {
                list.add(JSONSerializer.evalString(value));
            }
        }
    }

    private static void evalAndAddMap(String key, String value, Map map) {
        if (JSONSerializer.isObject(value)) {
            Map v = JSONSerializer.evalMap(value);
            map.put(key, v);
        } else if (JSONSerializer.isArray(value)) {
            List list = JSONSerializer.evalArray(value);
            map.put(key, list);
        } else {
            Integer intValue = evalInt(value);
            if (intValue != null) {
                map.put(key, intValue);
            } else {
                map.put(key, JSONSerializer.evalString(value));
            }
        }
    }

    public static String evalString(String str) {
        if (JSONSerializer.isString(str)) {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }

    public static Integer evalInt(String str) {
        try {
            int i = Integer.parseInt(str);
            return new Integer(i);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}