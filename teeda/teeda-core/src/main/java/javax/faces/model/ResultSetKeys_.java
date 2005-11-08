package javax.faces.model;

import java.util.Iterator;

import javax.faces.model.ResultSetDataModel.ResultSetMap;

/**
 * TODO TEST
 */

class ResultSetKeys_ extends ResultSetBaseSet_{

	public ResultSetKeys_(ResultSetMap map) {
		super(map);
	}

	public Iterator iterator() {
		return new ResultSetKeysIterator_(map_);
	}
}
