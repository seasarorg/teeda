package javax.faces.component;

import javax.faces.context.FacesContext;

public class UISelectItem extends UIComponentBase{

	public static final String COMPORNENT_FAMILY = "javax.faces.SelectItem";
	
	public static final String COMPORNENT_TYPE = "javax.faces.SelectItem";
	
	private static final String ITEM_DESCRIPTION_BINDING_NAME = "itemDescription";
	
	private static final String ITEM_DISABLED_BINDING_NAME = "itemDisabled";
	
	private static final String ITEM_LABEL_BINDING_NAME = "itemLabel";

	private static final String ITEM_VALUE_BINDING_NAME = "itemValue";
	
	private static final String VALUE_BINDING_NAME = "value";
	
	private String itemDescription_ = null;
	
	private String itemLabel_ = null;
	
	private Object itemValue_ = null;
	
	private Object value_ = null;
	
	private boolean itemDisabled_ = false;
	
	private boolean itemDisabledSet_ = false;
	
	public UISelectItem(){
		super();
	}
	
	public String getFamily() {
		return COMPORNENT_FAMILY;
	}
	
	public String getItemDescription(){
		if(itemDescription_ != null){
			return itemDescription_;
		}
		return (String)ComponentUtils_.
			getValueBindingValue(this, ITEM_DESCRIPTION_BINDING_NAME);
	}
	
	public void setItemDescription(String itemDescription){
		itemDescription_ = itemDescription;
	}
	
	public boolean isItemDisabled(){
		if(itemDisabledSet_){
			return itemDisabled_;
		}
		Object value = ComponentUtils_.getValueBindingValue(this, ITEM_DISABLED_BINDING_NAME);
		return (value != null) ? Boolean.TRUE.equals(value) : itemDisabled_;
	}
	
	public void setItemDisabled(boolean itemDisabled){
		itemDisabled_ = itemDisabled;
		itemDisabledSet_ = true;
	}
	
	public String getItemLabel(){
		if(itemLabel_ != null){
			return itemLabel_;
		}
		return (String)ComponentUtils_.getValueBindingValue(this, ITEM_LABEL_BINDING_NAME);
	}
	
	public void setItemLabel(String itemLabel){
		itemLabel_ = itemLabel;
	}
	
	public Object getItemValue(){
		if(itemValue_ != null){
			return itemValue_;
		}
		return ComponentUtils_.getValueBindingValue(this, ITEM_VALUE_BINDING_NAME);
	}
	
	public void setItemValue(Object itemValue){
		itemValue_ = itemValue;
	}

	public Object getValue(){
		if(value_ != null){
			return value_;
		}
		return ComponentUtils_.getValueBindingValue(this, VALUE_BINDING_NAME);
	}
	
	public void setValue(Object value){
		value_ = value;
	}

	public void restoreState(FacesContext context, Object state) {
		Object[] values = (Object[])state;			
		super.restoreState(context, values[0]);
		itemDescription_ = (String)values[1];
		itemDisabled_ = ((Boolean)values[2]).booleanValue();
		itemDisabledSet_ = ((Boolean)values[3]).booleanValue();
		itemLabel_ = (String)values[4];
		itemValue_ = values[5];
	}
	
	public Object saveState(FacesContext context) {
		Object[] values = new Object[6];
		values[0] = super.saveState(context);
		values[1] = itemDescription_;
		values[2] = ComponentUtils_.convertToBoolean(itemDisabled_);
		values[3] = ComponentUtils_.convertToBoolean(itemDisabledSet_);
		values[4] = itemLabel_;
		values[5] = itemValue_;
		return values;
	}
}
