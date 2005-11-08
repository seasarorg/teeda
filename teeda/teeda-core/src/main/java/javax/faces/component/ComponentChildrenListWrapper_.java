package javax.faces.component;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.*;

class ComponentChildrenListWrapper_ extends AbstractList implements
		Serializable {

	private List list_ = new ArrayList();
	private UIComponent component_ = null;
	
	public ComponentChildrenListWrapper_(UIComponent component){
		component_ = component;
	}

	public Object get(int num) {
		return list_.get(num);
	}

	public Object remove(int num) {
		
		UIComponent child = (UIComponent)list_.get(num);
		if(child != null){
			child.setParent(null);
		}
		return child;
	}
	
	public int size() {
		return list_.size();
	}
	
	public void add(int num, Object obj) {
		checkValue(obj);
		setNewParent((UIComponent)obj);
		list_.add(num, obj);
	}

	public boolean add(Object obj) {
		checkValue(obj);
		setNewParent((UIComponent)obj);
		return list_.add(obj);
	}

	public boolean addAll(Collection collection) {
		
		boolean changed = false;
		Object obj = null;
		for(Iterator itr = collection.iterator();itr.hasNext();){
			obj = itr.next();
			checkValue(obj);
			add((UIComponent)obj);
			changed = true;
		}
		return changed;
	}
	
	protected void setNewParent(UIComponent child){
		UIComponent parent = child.getParent();
		if(parent != null){		
			removeFromParent(parent, child);
		}
		child.setParent(component_);
	}
	
	protected void removeFromParent(UIComponent parent, UIComponent child){
		//if should care about facet map, do this here.
		parent.getChildren().remove(child);
	}
	
	protected static void checkValue(Object obj) {
		if(obj == null){
			throw new NullPointerException("value");
		}
		
		if(obj instanceof UIComponent){
			throw new ClassCastException("value");
		}
	}
}
