/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package javax.faces.application;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * @author shot
 */
public abstract class StateManager {

	public static final String STATE_SAVING_METHOD_CLIENT = "client";

	public static final String STATE_SAVING_METHOD_SERVER = "server";

	public static final String STATE_SAVING_METHOD_PARAM_NAME = "javax.faces.STATE_SAVING_METHOD";

	public abstract SerializedView saveSerializedView(FacesContext context);

	public abstract void writeState(FacesContext context, SerializedView state)
			throws IOException;

	protected abstract Object getTreeStructureToSave(FacesContext context);

	protected abstract Object getComponentStateToSave(FacesContext context);

	public abstract UIViewRoot restoreView(FacesContext context, String viewId, String renderKitId);

	protected abstract UIViewRoot restoreTreeStructure(FacesContext context,
			String viewId, String renderKitId);

	protected abstract void restoreComponentState(FacesContext context,
			UIViewRoot viewRoot, String renderKitId);

	public boolean isSavingStateInClient(FacesContext context) {
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

		private static final long serialVersionUID = 3256443616359823160L;

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
