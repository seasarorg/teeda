/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author shot
 */
public class EnvironmentInfo {

    private EnvironmentInfo() {
    }

    public static List getEnvironmentInfo() {
        List list = new ArrayList();
        final Properties properties = System.getProperties();
        int length = 0;
        Map map = new HashMap();
        for (Enumeration e = properties.propertyNames(); e.hasMoreElements();) {
            String key = (String) e.nextElement();
            String value = null;
            if (key.length() > length) {
                length = key.length();
            }
            Object object = properties.get(key);
            value = object.toString();
            map.put(key, value);
        }
        for (Iterator itr = map.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Map.Entry) itr.next();
            String key = (String) entry.getKey();
            if (key.length() < length) {
                key = key + appendBlank(length - key.length());
            }
            list.add(key + " : " + entry.getValue());
        }
        return list;
    }

    private static String appendBlank(int len) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < len; i++) {
            buf.append(" ");
        }
        return buf.toString();
    }
}
