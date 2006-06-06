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
package org.seasar.teeda.ajax.json;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * 
 * @author mopemope
 * @author yone
 */
public class JSONObject {

    public static final String ARRAY_SEPARATOR = ",";

    public static final String JSON_SEPARATOR = ":";

    public static final String NULL_STRING = "null";

    public static final String END_BRACE = "}";

    public static final String START_BRACE = "{";

    public static final String END_LIST_BRACE = "]";

    public static final String START_LIST_BRACE = "[";

    private Map map = new HashMap();

    public JSONObject() {
    }

    public JSONObject(Object object) {
        this(createJSONMap(object));
    }

    public JSONObject(Map map) {
        this.map.putAll(map);
    }

    public void put(String key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        assertNotInfiniteOrNaN(value);
        map.put(key, value);
    }

    public static String quote(String str) {
        //TODO \t\b\n\r
        return "\"" + str + "\"";
    }

    public String toString() {
        //TODO support JSONFunction type Object
        StringBuffer buf = new StringBuffer();
        buf.append(JSONObject.START_BRACE);
        int index = 0;
        for (Iterator itr = map.entrySet().iterator(); itr.hasNext();) {
            if (index > 0) {
                buf.append(JSONObject.ARRAY_SEPARATOR);
            }
            Map.Entry entry = (Map.Entry) itr.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            buf.append(JSONObject.quote(key));
            buf.append(JSONObject.JSON_SEPARATOR);
            append(buf, value);
            index++;
        }
        buf.append(JSONObject.END_BRACE);
        return buf.toString();
    }

    public static void append(StringBuffer buf, Object value) {
        if (value == null) {
            buf.append(NULL_STRING);
        } else if (value instanceof Number) {
            buf.append(value.toString());
        } else if (value instanceof Boolean) {
            buf.append(value.toString());
        } else if (value instanceof String) {
            buf.append(JSONObject.quote(value.toString()));
        } else if (value instanceof List) {
            buf.append(new JSONArray((List) value).toString());
        } else if (value instanceof Map) {
            buf.append(new JSONObject((Map) value).toString());
        } else if (value instanceof JSONArray) {
            buf.append(value.toString());
        } else if (value instanceof JSONObject) {
            buf.append(value.toString());
        } else {
            if (value.getClass().isArray()) {
                buf.append(createArrayString(value));
            } else {
                Map map = createJSONMap(value);
                JSONObject json = new JSONObject(map);
                buf.append(json.toString());
            }
        }
    }

    private static Map createJSONMap(Object value) {
        Map map = new HashMap();
        //TODO  
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(value.getClass());
        for (int j = 0; j < beanDesc.getPropertyDescSize(); j++) {
            PropertyDesc propertyDesc = beanDesc.getPropertyDesc(j);
            String name = propertyDesc.getPropertyName();
            Object o = propertyDesc.getValue(value);
            map.put(name, o);
        }
        return map;
    }
    
    private static String createArrayString(Object array) {
        StringBuffer buf = new StringBuffer();
        buf.append(START_LIST_BRACE);
        final int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            append(buf, Array.get(array, i));
            buf.append(ARRAY_SEPARATOR);
        }
        if (length > 0) {
            buf.setLength(buf.length() - ARRAY_SEPARATOR.length());
        }
        buf.append(END_LIST_BRACE);
        return buf.toString();
    }

    private static void assertNotInfiniteOrNaN(Object value) {
        if (value == null) {
            return;
        }
        if (value instanceof Double) {
            Double d = (Double) value;
            if (d.isInfinite() || d.isNaN()) {
                //TODO create good runtime exception
                throw new IllegalArgumentException();
            }
        }
        if (value instanceof Float) {
            Float f = (Float) value;
            if (f.isInfinite() || f.isNaN()) {
                //TODO create good runtime exception
                throw new IllegalArgumentException();
            }
        }

    }

}
