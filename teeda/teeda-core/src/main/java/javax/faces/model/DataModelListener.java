package javax.faces.model;

import java.util.EventListener;
import javax.faces.model.DataModelEvent;

public interface DataModelListener extends EventListener {

	public void rowSelected(DataModelEvent event);
}
