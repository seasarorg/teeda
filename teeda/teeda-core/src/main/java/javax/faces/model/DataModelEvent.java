package javax.faces.model;

import java.io.Serializable;
import java.util.EventObject;
import javax.faces.model.DataModel;

public class DataModelEvent extends EventObject implements Serializable{

	private int index_ = 0;
	private Object data_ = null;
	
	public DataModelEvent(DataModel model, int index, Object data){
		super(model);
		index_ = index;
		data_ = data;
	}
	
	public DataModel getDataModel(){
		return (DataModel)super.getSource();
	}

	public Object getRowData(){
		return data_;
	}
	
	public int getRowIndex(){
		return index_;
	}
}
