package javax.faces.model;

import java.util.*;

public abstract class DataModel {

	private List listeners_ = null;

	public DataModel() {
		listeners_ = new ArrayList();
	}

	public abstract int getRowCount();
	
	public abstract Object getRowData();
	
	public abstract int getRowIndex();
	
	public abstract Object getWrappedData();
	
	public abstract boolean isRowAvailable();

	public abstract void setRowIndex(int rowIndex);
	
	public abstract void setWrappedData(Object data);
	
	public void addDataModelListener(DataModelListener listener) {
		assertNotNull(listener);
		listeners_.add(listener);
	}

	public DataModelListener[] getDataModelListeners() {

		DataModelListener[] listeners = null;
		if (listeners_ == null) {
			listeners = new DataModelListener[0];
		} else {
			listeners = (DataModelListener[]) listeners_.toArray(new DataModelListener[listeners_.size()]);
		}
		return listeners;
	}

	public void removeDataModelListener(DataModelListener listener){
		assertNotNull(listener);
		if(listeners_ != null){
			listeners_.remove(listener);
		}
	}
		
	private static void assertNotNull(Object obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
	}
}
