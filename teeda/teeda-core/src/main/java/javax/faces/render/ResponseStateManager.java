package javax.faces.render;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.application.StateManager;

public abstract class ResponseStateManager {

	public abstract void writeState(FacesContext context,
			StateManager.SerializedView state) throws IOException;

	public abstract Object getTreeStructureToRestore(FacesContext context,
			String viewId);

	public abstract Object getComponentStateToRestore(FacesContext context);

}