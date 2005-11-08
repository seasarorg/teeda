package javax.faces.model;

import java.io.Serializable;

/**
 * TODO TEST
 */

public class SelectItem implements Serializable {

	private Object value_ = null;
	private String label_ = null;
	private String description_ = null;
	private boolean disabled_ = false;
	
	public SelectItem(){
	}
	
	public SelectItem(Object value){
		assertNotNull(value);
		value_ = value;
	}

	public SelectItem(Object value, String label){
		this(value, label, null);
	}

	public SelectItem(Object value, String label, String description){
		this(value, label, description, false);
	}

	public SelectItem(Object value, String label, 
			String description, boolean disabled){
		assertNotNull(value);
		assertNotNull(label);
		value_ = value;
		label_ = label;
		description_ = description;
		disabled_ = disabled;
	}

	public String getDescription() {
		return description_;
	}
	
	public void setDescription(String description) {
		this.description_ = description;
	}

	public boolean isDisabled() {
		return disabled_;
	}

	public void setDisabled(boolean disabled) {
		this.disabled_ = disabled;
	}
	
	public String getLabel() {
		return label_;
	}
	
	public void setLabel(String label) {
		assertNotNull(label);
		this.label_ = label;
	}
	
	public Object getValue() {
		return value_;
	}
	
	public void setValue(Object value) {
		assertNotNull(value);
		this.value_ = value;
	}
	
	private static void assertNotNull(Object obj){
		if(obj == null){
			throw new NullPointerException();
		}
	}

}
