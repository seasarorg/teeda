package javax.faces.application;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public abstract class StateManager {

	public static final String STATE_SAVING_METHOD_CLIENT = "client";

	public static final String STATE_SAVING_METHOD_SERVER = "server";

	public static final String STATE_SAVING_METHOD_PARAM_NAME = "javax.faces.STATE_SAVING_METHOD";

	public abstract SerializedView saveSerializedView(FacesContext context);

	public abstract void writeState(FacesContext context, SerializedView state)
			throws IOException;

	protected abstract Object getTreeStructureToSave(FacesContext context);

	protected abstract Object getComponentStateToSave(FacesContext context);

	public abstract UIViewRoot restoteView(FacesContext context, String viewId);

	protected abstract UIViewRoot restoreTreeStructure(FacesContext context,
			String viewId);

	protected abstract void restoreComponentState(FacesContext context,
			UIViewRoot viewRoot);

	public boolean isSavingStateClient(FacesContext context) {
		String state = getSavingState(context);
		if (STATE_SAVING_METHOD_CLIENT.equalsIgnoreCase(state)) {
			return true;
		}
		return false;
	}

	private static String getSavingState(FacesContext context) {
		return context.getExternalContext()
				.getInitParameter(STATE_SAVING_METHOD_PARAM_NAME);
	}

	public class SerializedView implements Serializable{

		private Object structure_ = null;

		private Object state_ = null;

		public SerializedView(Object structure, Object state) {
			structure_ = structure;
			state_ = state;
		}

		public Object getState() {
			return state_;
		}

		public Object getStructure() {
			return structure_;
		}
	}

}
