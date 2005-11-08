package javax.faces.component.html;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;


class ComponentHtmlUtils_ {

	private ComponentHtmlUtils_(){
	}
	
	public static Object getValueBindingValue(UIComponent component, String bindingName, FacesContext context){
		ValueBinding vb = component.getValueBinding(bindingName);
		return vb != null ? (String) vb.getValue(context) : null;
	}
}
