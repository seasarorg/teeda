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
package javax.faces.component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Shinpei Ohtani
 */
class ComponentFacetMapWrapper_ implements Map, Serializable {

	private static final long serialVersionUID = 3977016266726585651L;

    private UIComponent parent_ = null;

	private Map map_ = new HashMap();

	public ComponentFacetMapWrapper_(UIComponent parent) {
		parent_ = parent;
	}

	public int size() {
		return map_.size();
	}

	public void clear() {
		map_.clear();
	}

	public boolean isEmpty() {
		return map_.isEmpty();
	}

	public boolean containsKey(Object key) {
		return map_.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map_.containsValue(value);
	}

	public Collection values() {
		return map_.values();
	}

	public Set entrySet() {
		return map_.entrySet();
	}

	public Set keySet() {
		return map_.keySet();
	}

	public Object get(Object key) {
		return map_.get(key);
	}

	public Object remove(Object key) {
		UIComponent facet = (UIComponent) map_.remove(key);
		if (facet != null) {
			facet.setParent(null);
		}
		return facet;
	}

	public Object put(Object key, Object facet) {
		assertKeyValueNotNull(key, facet);
		checkKeyClass(key);
		checkValueClass(facet);
		setNewParent((String)key, (UIComponent)facet);
		return map_.put(key, facet);
	}

	public void putAll(Map map) {
		for (Iterator itr = map.entrySet().iterator(); itr.hasNext();) {
			Map.Entry entry = (Map.Entry) itr.next();
			put(entry.getKey(), entry.getValue());
		}
	}

	private void setNewParent(String facetName, UIComponent facet) {
		UIComponent parent = facet.getParent();
		if (parent != null) {
			parent.getFacets().remove(facetName);
		}
		facet.setParent(parent_);
	}

	private static void assertKeyValueNotNull(Object key, Object value) {
		if (key == null || value == null) {
			throw new NullPointerException();
		}
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
