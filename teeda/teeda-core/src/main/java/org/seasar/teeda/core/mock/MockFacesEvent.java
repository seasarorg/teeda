package org.seasar.teeda.core.mock;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;


public class MockFacesEvent extends FacesEvent {

	private static final long serialVersionUID = 1L;

    public MockFacesEvent(UIComponent component) {
		super(component);
		
	}

	public boolean isAppropriateListener(FacesListener listener) {
		return false;
	}

	public void processListener(FacesListener listener) {
	}

}
