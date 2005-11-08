package javax.faces.model;

import java.util.Map;
import java.util.Map.Entry;

/**
 * TODO TEST
 */

class ResultSetEntry_ implements Entry {

	ResultSetDataModel.ResultSetMap map_ = null;
	Object key_ = null;
	
	public ResultSetEntry_(ResultSetDataModel.ResultSetMap map, Object key){
		map_ = map;
		key_ = key;
	}
	
	public Object getKey() {
		return key_;
	}

	public Object getValue() {
		return map_.get(key_);
	}

	public Object setValue(Object value) {
		return map_.put(key_, value);
	}

	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		
		if(!(obj instanceof Map.Entry)){
			return false;
		}
		
		Map.Entry entry = (Map.Entry)obj;
		if((this.getKey() == null && entry.getKey() != null) || (this.getKey() != null && entry.getKey() == null)){
			return false;
		}

		if((this.getValue() == null && entry.getValue() != null) || (this.getValue() != null && entry.getValue() == null)){
			return false;
		}
		return true;
	}
}
