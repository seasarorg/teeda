package javax.faces.component;

import java.io.Serializable;


class SavedState_ implements Serializable{

	private Object localeValue_ = null;
	
	private Object submittedValue_ = null;
	
	private boolean valid_ = true;
	
	private boolean localeValueSet_ = false;
	
	public SavedState_(){
	}
	
	public Object getLocalValue() {
		return localeValue_;
	}
	public void setLocaleValue(Object localeValue) {
		localeValue_ = localeValue;
	}
	public boolean isLocaleValueSet() {
		return localeValueSet_;
	}
	public void setLocaleValueSet(boolean localeValueSet) {
		localeValueSet_ = localeValueSet;
	}
	public Object getSubmittedValue() {
		return submittedValue_;
	}
	public void setSubmittedValue(Object submittedValue) {
		submittedValue_ = submittedValue;
	}
	public boolean isValid() {
		return valid_;
	}
	public void setValid(boolean valid) {
		valid_ = valid;
	}
}
