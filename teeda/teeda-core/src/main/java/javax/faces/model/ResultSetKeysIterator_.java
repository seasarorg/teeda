package javax.faces.model;

/**
 * TODO TEST
 */

class ResultSetKeysIterator_ extends ResultSetBaseIterator_ {

	public ResultSetKeysIterator_(ResultSetDataModel.ResultSetMap map){
		super(map);
		super.itr_ = super.map_.realKeys();
	}
	
	public boolean hasNext() {
		return super.itr_.hasNext();
	}
	public Object next() {
		return super.itr_.next();
	}
}
