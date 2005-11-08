package javax.faces.model;

/**
 * TODO TEST
 */

public class ScalarDataModel extends DataModel {

	private Object scalar_ = null;
	private int index_ = -1;
	
	public ScalarDataModel(){
		this(null);
	}
	
	public ScalarDataModel(Object scalar){
		super();
		setWrappedData(scalar);
	}
	
	public int getRowCount() {
		return (scalar_ != null) ? 1 : -1;
	}

	public Object getRowData() {
		if(!isRowAvailable()){
			throw new IllegalArgumentException();
		}
		return scalar_;
	}

	public int getRowIndex() {
		return (scalar_ != null) ? 0 : -1;
	}

	public Object getWrappedData() {
		return scalar_;
	}

	public boolean isRowAvailable() {
		if(scalar_ == null){
			return false;
		}
		return (index_ == 0) ? true : false;
	}

	public void setRowIndex(int index){
		if(index < -1){
			throw new IllegalArgumentException();
		}
		
		int oldIndex = index_;
		index_ = index;
		
		if(scalar_ == null){
			return;
		}
		
		DataModelListener[] listeners = getDataModelListeners();
		if((oldIndex != index_) && (listeners != null)){
			Object rowData = null;
			if(isRowAvailable()){
				rowData = getRowData();
			}
			
			DataModelEvent event = new DataModelEvent(this, index_, scalar_);
			for(int i = 0; i < listeners.length; i++){
				listeners[i].rowSelected(event);
			}
		}
	}
	
	public void setWrappedData(Object data){
		if(data == null){
			scalar_ = null;
			setRowIndex(-1);
		}else{
			scalar_ = data;
			setRowIndex(0);
		}
	}
}
