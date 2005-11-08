package javax.faces.model;

import java.util.Iterator;

import javax.faces.model.ResultSetDataModel.ResultSetMap;

/**
 * TODO TEST
 */

class ResultSetEntries_ extends ResultSetBaseSet_ {

	public ResultSetEntries_(ResultSetMap map) {
		super(map);		
	}

	public Iterator iterator() {
		return new ResultSetEntriesIterator_(map_);
	}
}
