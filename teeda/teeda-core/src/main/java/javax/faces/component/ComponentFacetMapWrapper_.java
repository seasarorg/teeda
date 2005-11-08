package javax.faces.component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ComponentFacetMapWrapper_ implements Map, Serializable {

	private UIComponent component_ = null;

	private Map map_ = new HashMap();

	public ComponentFacetMapWrapper_(UIComponent component) {
		component_ = component;
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

	public Object put(Object key, Object value) {
		assertBothNotNull(key, value);
		checkKeyClass(key);
		checkValueClass(value);
		setNewParent((String) key, (UIComponent) value);

		return map_.put(key, value);
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
		facet.setParent(component_);
	}

	private static void assertBothNotNull(Object key, Object value) {
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
