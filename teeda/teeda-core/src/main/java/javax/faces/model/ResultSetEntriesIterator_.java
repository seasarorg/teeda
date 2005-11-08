package javax.faces.model;

import javax.faces.model.ResultSetDataModel.ResultSetMap;

/**
 * TODO TEST
 */

class ResultSetEntriesIterator_ extends ResultSetBaseIterator_ {

	public ResultSetEntriesIterator_(ResultSetMap map) {
		super(map);
		super.itr_ = map_.keySet().iterator();
	}

	
	public Object next() {
		Object key = itr_.next();
		return new ResultSetEntry_(map_, key);
	}
}
