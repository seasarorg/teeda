package javax.faces.component;

import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;


public class UICommand extends UIComponentBase implements ActionSource{

	public static final String COMPONENT_FAMILY = "javax.faces.Column";
	
	public static final String COMPONENT_TYPE = "javax.faces.Column";
	
	private MethodBinding action_ = null;
	
	private MethodBinding actionListener_ = null;
	
	private boolean immediate_ = false;
	
	private boolean immediateSet_ = false;
	
	private Object value_ = null;
	
	private static final String IMMEDIATE_BINDING_NAME = "immediate";
	
	private static final String VALUE_BINDING_NAME = "value";
	
	private static final String DEFAULT_RENDER_TYPE = "javax.faces.Button";
	
	public UICommand(){
		super();
		setRendererType(DEFAULT_RENDER_TYPE);
	}
	
	public String getFamily() {
		return COMPONENT_FAMILY;
	}
	
	public MethodBinding getAction() {
		return action_;
	}

	public void setAction(MethodBinding action) {
		action_ = action;
	}

	public MethodBinding getActionListener() {
		return actionListener_;
	}

	public void setActionListener(MethodBinding actionListener) {
		actionListener_ = actionListener;
	}

	public boolean isImmediate() {
		if(immediateSet_){
			return immediate_;
		}
		ValueBinding vb = getValueBinding(IMMEDIATE_BINDING_NAME);
		return (vb != null) ? isBindingValueTrue(vb) : immediate_;
	}

	public void setImmediate(boolean immediate) {
		immediate_ = immediate;
		immediateSet_ = true;
	}

	public Object getValue(){
		if(value_ != null){
			return value_;
		}
		ValueBinding vb = getValueBinding(VALUE_BINDING_NAME);
		return (vb != null) ? getValueFromBinding(vb) : null;
	}
	
	public void setValue(Object value){
		value_ = value;
	}
	
	public void addActionListener(ActionListener listener) {
		addFacesListener(listener);
	}

	public ActionListener[] getActionListeners() {
		ActionListener[] listeners = 
			(ActionListener[])getFacesListeners(ActionListener.class);
		return listeners;
	}

	public void removeActionListener(ActionListener listener) {
		removeFacesListener(listener);
	}
	
	public void restoreState(FacesContext context, Object state) {
		Object[] values = (Object[])state;
		super.restoreState(context, values[0]);
		action_ = (MethodBinding)restoreAttachedState(context, values[1]);
		actionListener_ = (MethodBinding)restoreAttachedState(context, values[2]);
		immediate_ = ((Boolean)values[3]).booleanValue();
		immediateSet_ = ((Boolean)values[4]).booleanValue();
		value_ = values[5];
	}
	
	public Object saveState(FacesContext context) {
        Object[] values = new Object[6];
        values[0] = super.saveState(context);
        values[1] = saveAttachedState(context, action_);
        values[2] = saveAttachedState(context, actionListener_);
        values[3] = immediate_ ? Boolean.TRUE : Boolean.FALSE;
        values[4] = immediateSet_ ? Boolean.TRUE : Boolean.FALSE;
        values[5] = value_;
        return values;
	}
	
	public void broadcast(FacesEvent event) throws AbortProcessingException {
		super.broadcast(event);
        if (event instanceof ActionEvent) {
            FacesContext context = getFacesContext();
            MethodBinding mb = getActionListener();
            if (mb != null) {
                mb.invoke(context, new Object[] { event });
            }
            ActionListener listener = context.getApplication().getActionListener();
            if (listener != null) {
                listener.processAction((ActionEvent) event);
            }
        }
	}
	
	public void queueEvent(FacesEvent event) {
		if(event instanceof ActionEvent){
			if(isImmediate()){
				event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
			}else{
				event.setPhaseId(PhaseId.INVOKE_APPLICATION);
			}
		}
	}
	
	private boolean isBindingValueTrue(ValueBinding vb){
		Object value = getValueFromBinding(vb);
		return Boolean.TRUE.equals(value);
	}
	
	private Object getValueFromBinding(ValueBinding vb){
		return vb.getValue(getFacesContext());
	}
}
