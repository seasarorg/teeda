package javax.faces.event;

import javax.faces.component.UIComponent;

/**
 * TODO TEST
 */
public class ValueChangeEvent extends FacesEvent {

	private Object oldValue_ = null;
	private Object newValue_ = null;
	
	public ValueChangeEvent(UIComponent component, Object oldValue, Object newValue){
		super(component);
		oldValue_ = oldValue;
		newValue_ = newValue;
	}
	
	public Object getNewValue(){
		return newValue_;
	}
	
	public Object getOldValue(){
		return oldValue_;
	}
	
	public boolean isAppropriateListener(FacesListener listener) {
		return (listener instanceof ValueChangeListener);
	}

	public void processListener(FacesListener listener) {
		((ValueChangeListener)listener).processValueChange(this);
	}

}
