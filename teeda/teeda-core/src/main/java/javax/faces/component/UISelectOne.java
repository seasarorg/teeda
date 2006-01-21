package javax.faces.component;

import javax.faces.context.FacesContext;


public class UISelectOne extends UIInput {

	public static final String COMPONENT_FAMILY = "javax.faces.SelectOne";
	
	public static final String COMPONENT_TYPE = "javax.faces.SelectOne";
	
	public static final String INVALID_MESSAGE_ID = "javax.faces.component.UISelectOne.INVALID";
	
	private static final String DEFAULT_RENDER_TYPE = "javax.faces.Menu";
	
	public UISelectOne(){
		super();
		setRendererType(DEFAULT_RENDER_TYPE);
	}
	
	public String getFamily(){
		return COMPONENT_FAMILY;
	}
	
	protected void validateValue(FacesContext context, Object value){
		ComponentUtils_.assertNotNull("context", context);
		super.validateValue(context, value);
		if(!isValid() || value == null){
			return;
		}
		
		if(ComponentUtils_.valueMatches(value, new SelectItemsIterator_(this))){
			Object[] args = {getId()};
			FacesMessageUtils_.addErrorMessage(context, this, INVALID_MESSAGE_ID, args);
            setValid(false);
		}
	}
}
