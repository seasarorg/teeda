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
package javax.faces.internal;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 * 
 * This class might be changed without notice. Please do not use it
 * excluding the JSF specification part.
 */
public class ComponentFacetMapWrapper implements Map, Serializable {

    private static final long serialVersionUID = 3977016266726585651L;

    private UIComponent parent = null;

    private Map facetMap = new HashMap();

    public ComponentFacetMapWrapper(UIComponent parent) {
        this.parent = parent;
    }

    public int size() {
        return facetMap.size();
    }

    public void clear() {
        facetMap.clear();
    }

    public boolean isEmpty() {
        return facetMap.isEmpty();
    }

    public boolean containsKey(Object key) {
        return facetMap.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return facetMap.containsValue(value);
    }

    public Collection values() {
        return facetMap.values();
    }

    public Set entrySet() {
        return facetMap.entrySet();
    }

    public Set keySet() {
        return facetMap.keySet();
    }

    public Object get(Object key) {
        return facetMap.get(key);
    }

    public Object remove(Object key) {
        UIComponent facet = (UIComponent) facetMap.remove(key);
        if (facet != null) {
            facet.setParent(null);
        }
        return facet;
    }

    public Object put(Object key, Object facet) {
        AssertionUtil.assertNotNull("key", key);
        AssertionUtil.assertNotNull("facet", facet);
        checkKeyClass(key);
        checkValueClass(facet);
        setNewParent((String) key, (UIComponent) facet);
        return facetMap.put(key, facet);
    }

    public void putAll(Map map) {
        for (Iterator itr = map.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Map.Entry) itr.next();
            put(entry.getKey(), entry.getValue());
        }
    }

    private void setNewParent(String facetName, UIComponent facet) {
        UIComponent oldParent = facet.getParent();
        if (oldParent != null) {
            oldParent.getFacets().remove(facetName);
        }
        facet.setParent(parent);
    }

    private static void checkKeyClass(Object key) {
        if (!(key instanceof String)) {
            throw new ClassCastException("key");
        }
    }

    private static void checkValueClass(Object value) {
        if (!(value instanceof UIComponent)) {
            throw new ClassCastException("value");
        }
    }

}
