package javax.faces.model;

import javax.faces.model.ResultSetDataModel.ResultSetMap;

/**
 * TODO TEST
 */

class ResultSetValuesIterator_ extends ResultSetBaseIterator_ {

	public ResultSetValuesIterator_(ResultSetMap map) {
		super(map);
		super.itr_ = map_.keySet().iterator();
	}

	public Object next() {
		return map_.get(itr_.next());
	}
}
