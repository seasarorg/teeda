package javax.faces.model;

import java.util.Iterator;

import javax.faces.model.ResultSetDataModel.ResultSetMap;

/**
 * TODO TEST
 */

class ResultSetValues_ extends ResultSetBaseCollection_ {

	public ResultSetValues_(ResultSetMap map) {
		super(map);
	}
	
	public Iterator iterator() {
		return new ResultSetValuesIterator_(map_);
	}
}
