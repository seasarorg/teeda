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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author shot
 * 
 */
public class JSONDesc {

    private Map properties = new HashMap();

    public JSONDesc() {
    }

    public JSONDesc(Map properties) {
        this.properties.putAll(properties);
    }
    
    public void put(String key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        assertNotInfiniteOrNaN(value);
        properties.put(key, value);
    }

    public String describe() {
        JSONStringBuffer buf = new JSONStringBuffer();
        for (Iterator itr = properties.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Map.Entry) itr.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            buf.append(key);
            buf.appendColon();
            buf.append(value);
        }
        return buf.toString();
    }
    
    public String toString() {
        return describe();
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
