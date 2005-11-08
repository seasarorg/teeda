package javax.faces.model;

import java.util.Iterator;
import java.util.Map;

/**
 * TODO TEST
 */

class ResultSetBaseIterator_ implements Iterator{

	ResultSetDataModel.ResultSetMap map_ = null;
	Iterator itr_ = null;
	public ResultSetBaseIterator_(ResultSetDataModel.ResultSetMap map){
		super();
		map_ = map;
		itr_ = map_.keySet().iterator();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	public boolean hasNext() {
		return itr_.hasNext();
	}

	public Object next() {
		return itr_.next();
	}
	
	
}
