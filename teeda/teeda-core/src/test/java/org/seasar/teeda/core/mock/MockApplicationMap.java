package org.seasar.teeda.core.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

public class MockApplicationMap implements Map {

    private ServletContext context_ = null;
    public MockApplicationMap(ServletContext context) {
        context_ = context;
    }
    
    public void clear() {
        Iterator keys = keySet().iterator();
        while (keys.hasNext()){
            context_.removeAttribute((String)keys.next());
        }
    }

    public boolean containsKey(Object key) {
        return context_.getAttribute(key(key)) != null;
    }

    public boolean containsValue(Object value) {
        if(value == null){
            return false;
        }
        Enumeration keys = context_.getAttributeNames();
        while (keys.hasMoreElements()){
            Object next = context_.getAttribute((String)keys.nextElement());
            if(next == value){
                return true;
            }
        }
        return false;
    }

    public Set entrySet() {
        Set set = new HashSet();
        Enumeration keys = context_.getAttributeNames();
        while (keys.hasMoreElements()){
            set.add(context_.getAttribute((String)keys.nextElement()));
        }
        return set;
    }

    public boolean equals(Object o) {
        return context_.equals(o);
    }

    public Object get(Object key) {
        return context_.getAttribute(key(key));
    }

    public int hashCode() {
        return context_.hashCode();
    }

    public boolean isEmpty() {
        return size() < 1;
    }

    public Set keySet() {
        Set set = new HashSet();
        Enumeration keys = context_.getAttributeNames();
        while (keys.hasMoreElements()){
            set.add(keys.nextElement());
        }
        return set;
    }

    public Object put(Object key, Object value) {
        if(value == null){
            return remove(key);
        }
        String skey = key(key);
        Object previous = context_.getAttribute(skey);
        context_.setAttribute(skey, value);
        return previous;
    }

    public void putAll(Map map) {
        Iterator keys = map.keySet().iterator();
        while (keys.hasNext()){
            String key = (String)keys.next();
            context_.setAttribute(key, map.get(key));
        }
    }

    public Object remove(Object key) {
        String skey = key(key);
        Object previous = context_.getAttribute(skey);
        context_.removeAttribute(skey);
        return previous;
    }

    public int size() {
        int n = 0;
        Enumeration keys = context_.getAttributeNames();
        while (keys.hasMoreElements()){
            keys.nextElement();
            n++;
        }
        return n;
    }

    public Collection values() {
        List list = new ArrayList();
        Enumeration keys = context_.getAttributeNames();
        while (keys.hasMoreElements()){
            list.add(context_.getAttribute((String)keys.nextElement()));
        }
        return list;
    }

    private String key(Object key) {
        if(key == null){
            throw new IllegalArgumentException();
        }else if(key instanceof String){
            return (String)key;
        }else{
            return key.toString();
        }
    }

}
