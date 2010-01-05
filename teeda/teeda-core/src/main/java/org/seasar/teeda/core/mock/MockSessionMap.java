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
package org.seasar.teeda.core.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

public class MockSessionMap implements Map {

    private HttpSession session_ = null;

    public MockSessionMap(HttpSession session) {
        session_ = session;
    }

    public void clear() {
        Iterator keys = keySet().iterator();
        while (keys.hasNext()) {
            session_.removeAttribute((String) keys.next());
        }
    }

    public boolean containsKey(Object key) {
        return session_.getAttribute(key(key)) != null;
    }

    public boolean containsValue(Object value) {
        if (value == null) {
            return false;
        }
        Enumeration keys = session_.getAttributeNames();
        while (keys.hasMoreElements()) {
            Object next = session_.getAttribute((String) keys.nextElement());
            if (next == value) {
                return true;
            }
        }
        return false;
    }

    public Set entrySet() {
        Set set = new HashSet();
        Enumeration keys = session_.getAttributeNames();
        while (keys.hasMoreElements()) {
            set.add(session_.getAttribute((String) keys.nextElement()));
        }
        return set;
    }

    public boolean equals(Object o) {
        return session_.equals(o);
    }

    public Object get(Object key) {
        return session_.getAttribute(key(key));
    }

    public int hashCode() {
        return session_.hashCode();
    }

    public boolean isEmpty() {
        return size() < 1;
    }

    public Set keySet() {
        Set set = new HashSet();
        Enumeration keys = session_.getAttributeNames();
        while (keys.hasMoreElements()) {
            set.add(keys.nextElement());
        }
        return set;
    }

    public Object put(Object key, Object value) {
        if (value == null) {
            return remove(key);
        }
        String skey = key(key);
        Object previous = session_.getAttribute(skey);
        session_.setAttribute(skey, value);
        return previous;
    }

    public void putAll(Map map) {
        for (Iterator itr = map.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Entry) itr.next();
            String key = (String) entry.getKey();
            Object value = (Object) entry.getValue();
            session_.setAttribute(key, value);
        }
    }

    public Object remove(Object key) {
        String skey = key(key);
        Object previous = session_.getAttribute(skey);
        session_.removeAttribute(skey);
        return previous;
    }

    public int size() {
        int n = 0;
        Enumeration keys = session_.getAttributeNames();
        while (keys.hasMoreElements()) {
            keys.nextElement();
            n++;
        }
        return n;
    }

    public Collection values() {
        List list = new ArrayList();
        Enumeration keys = session_.getAttributeNames();
        while (keys.hasMoreElements()) {
            list.add(session_.getAttribute((String) keys.nextElement()));
        }
        return (list);

    }

    private String key(Object key) {
        if (key == null) {
            throw new IllegalArgumentException();
        } else if (key instanceof String) {
            return ((String) key);
        } else {
            return (key.toString());
        }

    }

}
