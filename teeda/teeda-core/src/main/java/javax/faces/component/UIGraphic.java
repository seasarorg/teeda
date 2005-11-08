package javax.faces.component;

import java.util.Iterator;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.PhaseId;

/**
 * TODO TEST
 */
public class UIGraphic extends UIComponentBase {

	public static final String COMPONENT_FAMILY = "javax.faces.Graphic";

	public static final String COMPONENT_TYPE = "javax.faces.Graphic";

	private static final String DEFAULT_RENDER_TYPE = "javax.faces.Image";

	private static final String VALUE_BINDING_NAME = "value";

	private static final String URL_BINDING_NAME = "url";

	private Object value_ = null;
	
	private boolean valueSet_ = false;
	
	public UIGraphic(){
		super();
		setRendererType(DEFAULT_RENDER_TYPE);
	}
	
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public String getUrl(){
		return (String)getValue();
	}
	
	public void setUrl(String url){
		setValue(url);
	}
	
	public Object getValue(){
		if(valueSet_){
			return value_;
		}
		return ComponentUtils_.getValueBindingValue(this, VALUE_BINDING_NAME);
	}
	
	public void setValue(Object value){
		value_ = value;
		valueSet_ = true;
	}
	
	public ValueBinding getValueBinding(String name){
		if(URL_BINDING_NAME.equals(name)){
			return super.getValueBinding(VALUE_BINDING_NAME);
		}else{
			return super.getValueBinding(name);
		}
	}
	
	public void setValueBinding(String name, ValueBinding vb){
		ComponentUtils_.assertNotNull(name, "name");
		if(URL_BINDING_NAME.equals(name)){
			super.setValueBinding(VALUE_BINDING_NAME, vb);
		}else{
			super.setValueBinding(name, vb);
		}		
	}
	
	public void restoreState(FacesContext context, Object state) {
		Object[] values = (Object[])state;
		super.restoreState(context, values[0]);
		value_ = values[1];
		valueSet_ = ((Boolean)values[2]).booleanValue();
	}
	
	public Object saveState(FacesContext context) {
		Object[] values = new Object[3];
		values[0] = super.saveState(context);
		values[1] = value_;
		values[2] = (valueSet_) ? Boolean.TRUE : Boolean.FALSE;
		return values;
	}
}
