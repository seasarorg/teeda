package javax.faces.component;

import java.util.Iterator;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

/**
 * TODO TEST
 */
public class UIForm extends UIComponentBase implements NamingContainer {

	public static final String COMPONENT_FAMILY = "javax.faces.Form";

	public static final String COMPONENT_TYPE = "javax.faces.Form";

	private static final String DEFAULT_RENDER_TYPE = "javax.faces.Form";

	private transient boolean submitted_ = false;
	
	public UIForm(){
		super();
		setRendererType(DEFAULT_RENDER_TYPE);
	}
	
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public boolean isSubmitted(){
		return submitted_;
	}
	
	public void setSubmitted(boolean submitted) {
		submitted_ = submitted;
	}
	
	public void processDecodes(FacesContext context) {
		ComponentUtils_.assertNotNull("context", context);
		decode(context);
		processAppropriateAction(context, PhaseId.APPLY_REQUEST_VALUES);
	}
	
	public void processUpdates(FacesContext context) {
		ComponentUtils_.assertNotNull("context", context);
		processAppropriateAction(context, PhaseId.UPDATE_MODEL_VALUES);
	}
	
	public void processValidators(FacesContext context) {
		ComponentUtils_.assertNotNull("context", context);
		processAppropriateAction(context, PhaseId.PROCESS_VALIDATIONS);
	}
	
	protected void processAppropriateAction(FacesContext context, PhaseId phase){
		if(!isRendered()){
			return;
		}
		for(Iterator children = getFacetsAndChildren(); children.hasNext();){
			UIComponent component = (UIComponent)children.next();
			ComponentUtils_.processAppropriatePhaseAction(context, component, phase);
		}
	}
}
