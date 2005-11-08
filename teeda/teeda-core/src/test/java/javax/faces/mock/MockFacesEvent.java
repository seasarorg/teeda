package javax.faces.mock;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;


public class MockFacesEvent extends FacesEvent {

	public MockFacesEvent(UIComponent component) {
		super(component);
		
	}

	public boolean isAppropriateListener(FacesListener listener) {
		return false;
	}

	public void processListener(FacesListener listener) {
	}

}
