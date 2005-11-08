package javax.faces.mock;

import javax.faces.model.DataModelEvent;
import javax.faces.model.DataModelListener;

public class MockDataModelListener implements DataModelListener {

	private DataModelEvent event_ = null;

	private String name_ = null;

	public MockDataModelListener() {
	}

	public MockDataModelListener(String name) {
		name_ = name;
	}

	public void rowSelected(DataModelEvent event) {
		setDataModelEvent(event);
	}

	private void setDataModelEvent(DataModelEvent event) {
		event_ = event;
	}

	public DataModelEvent getDataModelEvent() {
		return event_;
	}

	public String toString() {
		return name_;
	}
}
