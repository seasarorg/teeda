package javax.faces.model;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

/**
 * TODO TEST
 */
class ResultSetBaseCollection_ extends AbstractCollection {

	ResultSetDataModel.ResultSetMap map_ = null;
	
	public ResultSetBaseCollection_(ResultSetDataModel.ResultSetMap map){
		map_ = map;
	}

	public boolean contains(Object o) {
		return map_.containsKey(o);
	}

	public boolean isEmpty() {
		return map_.isEmpty();
	}

	public Iterator iterator(){
		return map_.keySet().iterator();
	}

	public int size() {
		return map_.size();
	}

	public final boolean add(Object o) {
		throw new UnsupportedOperationException();
	}

	public final boolean addAll(Collection c) {
		throw new UnsupportedOperationException();
	}

	public final void clear() {
		throw new UnsupportedOperationException();
	}
	
	public final boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	public final boolean removeAll(Collection c) {
		throw new UnsupportedOperationException();
	}

	public final boolean retainAll(Collection c) {
		throw new UnsupportedOperationException();
	}

}
