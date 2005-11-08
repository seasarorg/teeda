package javax.faces.component;

import javax.faces.context.FacesContext;


public class UISelectItems extends UIComponentBase {

	public static final String COMPONETN_FAMILY = "javax.faces.SelectItems";

	public static final String COMPONETN_TYPE = "javax.faces.SelectItems";

	private Object value_ = null;
	
	private static final String VALUE_BINDING_NAME = "value";
	
	public UISelectItems(){
		super();
	}
	
	public String getFamily() {
		return COMPONETN_FAMILY;
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
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        value_ = values[1];
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = value_;
        return values;

    }
}
