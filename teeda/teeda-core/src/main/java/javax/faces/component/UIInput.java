package javax.faces.component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.render.Renderer;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


public class UIInput extends UIOutput implements EditableValueHolder {

	public static final String COMPONENT_FAMILY = "javax.faces.Input";

	public static final String COMPONENT_TYPE = "javax.faces.Input";

	public static final String CONVERSION_MESSAGE_ID = "javax.faces.component.UIInput.CONVERSION";

	public static final String REQUIRED_MESSAGE_ID = "javax.faces.component.UIInput.REQUIRED";

	private Object submittedValue_ = null;

	private boolean localValueSet_ = false;

	private boolean required_ = false;

	private boolean requiredSet_ = false;

	private boolean valid_ = false;

	private boolean validSet_ = false;

	private boolean immediate_ = false;

	private boolean immediateSet_ = false;

	private MethodBinding validatorBinding_ = null;

	private MethodBinding valueChangeMethod_ = null;

	private List validators_ = null;

	private static final String DEFAULT_RENDER_TYPE = "javax.faces.Text";

	private static final Validator[] EMPTY_VALIDATOR_ARRAY = new Validator[0];

	public UIInput() {
		super();
		setRendererType(DEFAULT_RENDER_TYPE);
	}

	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public Object getSubmittedValue() {
		return submittedValue_;
	}

	public void setSubmittedValue(Object submittedValue) {
		submittedValue_ = submittedValue;
	}

	public void setValue(Object value) {
		super.setValue(value);
		setLocalValueSet(true);
	}

	public boolean isLocalValueSet() {
		return localValueSet_;
	}

	public void setLocalValueSet(boolean localValueSet) {
		localValueSet_ = localValueSet;
	}

	public boolean isRequired() {
		if (requiredSet_) {
			return required_;
		}
		Boolean value = (Boolean) ComponentUtils_.getValueBindingValue(
				this, "required");
		return (value != null) ? Boolean.TRUE.equals(value) : required_;
	}

	public void setRequired(boolean required) {
		required_ = required;
		requiredSet_ = true;
	}

	public boolean isValid() {
		if (validSet_) {
			return valid_;
		}
		Boolean value = (Boolean) ComponentUtils_.getValueBindingValue(
				this, "valid");
		return (value != null) ? Boolean.TRUE.equals(value) : valid_;
	}

	public void setValid(boolean valid) {
		valid_ = valid;
		validSet_ = true;
	}

	public boolean isImmediate() {
		if (immediateSet_) {
			return immediate_;
		}
		Boolean value = (Boolean) ComponentUtils_.getValueBindingValue(
				this, "immediate");
		return (value != null) ? Boolean.TRUE.equals(value) : immediate_;
	}

	public void setImmediate(boolean immediate) {
		immediate_ = immediate;
		immediateSet_ = true;
	}

	public MethodBinding getValidator() {
		return validatorBinding_;
	}

	public void setValidator(MethodBinding validatorBinding) {
		validatorBinding_ = validatorBinding;
	}

	public MethodBinding getValueChangeListener() {
		return valueChangeMethod_;
	}

	public void setValueChangeListener(MethodBinding valueChangeMethod) {
		valueChangeMethod_ = valueChangeMethod;
	}

	public void processDecodes(FacesContext context) {
		ComponentUtils_.assertNotNull("context", context);
		if (!isRendered()) {
			return;
		}
		super.processDecodes(context);
		if (isImmediate()) {
			executeValidate(context);
		}
	}

	public void processValidators(FacesContext context) {
		ComponentUtils_.assertNotNull("context", context);
		if (isRendered()) {
			return;
		}
		super.processValidators(context);
		if (!isImmediate()) {
			executeValidate(context);
		}
	}

	public void processUpdates(FacesContext context) {
		ComponentUtils_.assertNotNull("context", context);
		if (isRendered()) {
			return;
		}
		try {
			updateModel(context);
		} catch (RuntimeException e) {
			context.renderResponse();
			throw e;
		}
		renderResponseIfNotValid(context);
	}

	public void decode(FacesContext context) {
		setValid(true);
		super.decode(context);
	}

	public void broadcast(FacesEvent event) throws AbortProcessingException {
		super.broadcast(event);
		if (event instanceof ValueChangeEvent) {
			throw new IllegalArgumentException("FacesEvent of class "
					+ event.getClass().getName()
					+ " not supported by UIInput");
		}
		MethodBinding valueChangeListenerBinding = getValueChangeListener();
		if (valueChangeListenerBinding != null) {
			try {
				valueChangeListenerBinding.invoke(getFacesContext(), new Object[] { event });
			} catch (EvaluationException e) {
				Throwable cause = e.getCause();
				if (cause != null && cause instanceof AbortProcessingException) {
					throw (AbortProcessingException) cause;
				} else {
					throw e;
				}
			}
		}
	}

	public void updateModel(FacesContext context) {
		ComponentUtils_.assertNotNull("context", context);
		if (!isValid() || !isLocalValueSet()) {
			return;
		}
		if (getValueBinding("value") == null) {
			return;
		}
		try {
			getValueBinding("value").setValue(context, getLocalValue());
			setValue(null);
			setLocalValueSet(false);
		} catch (RuntimeException e) {
			Object[] args = { getId() };
			context.getExternalContext().log(e.getMessage(), e);
			FacesMessageUtils_.addErrorMessage(context, this,
					CONVERSION_MESSAGE_ID, args);
			setValid(false);
		}
	}
	
	public void validate(FacesContext context) {
		ComponentUtils_.assertNotNull("context", context);
		Object submittedValue = getSubmittedValue();
		if (submittedValue == null) {
			return;
		}
		Object newValue = getConvertedValue(context, submittedValue);
		validateValue(context, newValue);
		if (isValid()) {
			Object previous = getValue();
			setValue(newValue);
			setSubmittedValue(null);
			if (compareValues(previous, newValue)) {
				queueEvent(new ValueChangeEvent(this, previous, newValue));
			}
		}
	}

	protected Object getConvertedValue(FacesContext context,
			Object submittedValue) throws ConverterException {
		Object value = null;
		try {
			Renderer renderer = getRenderer(context);
			if (renderer != null) {
				value = renderer.getConvertedValue(context, this,
						submittedValue);
			} else if (submittedValue instanceof String) {
				Converter converter = getConverterWithType(context);
				if (converter != null) {
					value = converter.getAsObject(context, this,
							(String) submittedValue);
				} else {
					value = submittedValue;
				}
			} else {
				value = submittedValue;
			}
		} catch (ConverterException e) {
			FacesMessage facesMessage = e.getFacesMessage();
			if (facesMessage != null) {
				context.addMessage(getClientId(context), facesMessage);
			} else {
				FacesMessageUtils_.addErrorMessage(context, this,
						CONVERSION_MESSAGE_ID, new Object[] { getId() });
			}
			setValid(false);
		}
		return value;
	}

	protected void validateValue(FacesContext context, Object newValue) {
		if (isValid() && isRequired() && isEmpty(newValue)) {
			FacesMessageUtils_.addErrorMessage(context, this,
					CONVERSION_MESSAGE_ID, new Object[] { getId() });
			setValid(false);
		}
		if (isValid() && !isEmpty(newValue)) {
			validateValueReally(context, newValue);
			validateFromBinding(context, newValue);
		}
	}

	protected boolean compareValues(Object previous, Object value) {
		if (previous != null && value != null) {
			return (!(previous.equals(value)));
		} else if (previous == null || value != null) {
			return false;
		} else if (previous != null || value == null) {
			return false;
		} else {
			return true;
		}
	}

	public void addValidator(Validator validator) {
		ComponentUtils_.assertNotNull("validator", validator);
		if (validators_ == null) {
			validators_ = new ArrayList();
		}
		validators_.add(validator);
	}

	public Validator[] getValidators() {
		if (validators_ == null) {
			return EMPTY_VALIDATOR_ARRAY;
		}
		return ((Validator[]) validators_.toArray(new Validator[validators_.size()]));
	}

	public void removeValidator(Validator validator) {
		if (validators_ != null) {
			validators_.remove(validator);
		}
	}

	public void addValueChangeListener(ValueChangeListener listener) {
		ComponentUtils_.assertNotNull("listener", listener);
		addFacesListener(listener);
	}

	public ValueChangeListener[] getValueChangeListeners() {
		return (ValueChangeListener[]) getFacesListeners(ValueChangeListener.class);
	}

	public void removeValueChangeListener(ValueChangeListener listener) {
		removeFacesListener(listener);
	}

	public Object saveState(FacesContext context) {
		Object values[] = new Object[10];
		values[0] = super.saveState(context);
		values[1] = localValueSet_ ? Boolean.TRUE : Boolean.FALSE;
		values[2] = required_ ? Boolean.TRUE : Boolean.FALSE;
		values[3] = requiredSet_ ? Boolean.TRUE : Boolean.FALSE;
		values[4] = valid_ ? Boolean.TRUE : Boolean.FALSE;
		values[5] = immediate_ ? Boolean.TRUE : Boolean.FALSE;
		values[6] = immediateSet_ ? Boolean.TRUE : Boolean.FALSE;
		values[7] = saveAttachedState(context, validators_);
		values[8] = saveAttachedState(context, validatorBinding_);
		values[9] = saveAttachedState(context, valueChangeMethod_);
		return values;
	}

	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[])state;
		super.restoreState(context, values[0]);
		localValueSet_ = ((Boolean) values[1]).booleanValue();
		required_ = ((Boolean) values[2]).booleanValue();
		requiredSet_ = ((Boolean) values[3]).booleanValue();
		valid_ = ((Boolean) values[4]).booleanValue();
		immediate_ = ((Boolean) values[5]).booleanValue();
		immediateSet_ = ((Boolean) values[6]).booleanValue();
		List restoredValidators = (List)restoreAttachedState(context, values[7]);
		if(restoredValidators != null) {
			if (validators_ != null) {
				for(Iterator itr = restoredValidators.iterator();itr.hasNext();) {
					validators_.add(itr.next());
				}
			} else {
				validators_ = restoredValidators;
			}
		}
		validatorBinding_ = (MethodBinding)restoreAttachedState(context, values[8]);
		valueChangeMethod_ = (MethodBinding)restoreAttachedState(context, values[9]);
	}

	private void executeValidate(FacesContext context) {
		try {
			validate(context);
		} catch (RuntimeException e) {
			context.renderResponse();
			throw e;
		}
		renderResponseIfNotValid(context);
	}

	private Converter getConverterWithType(FacesContext context) {
		Converter converter = getConverter();
		if (converter != null) {
			return converter;
		}
		Class type = ComponentUtils_.getValueBindingType(this, "value");
		if (ComponentUtils_.isPerformNoConversion(type)) {
			return null;
		}
		try {
			return ComponentUtils_.createConverter(context, type);
		} catch (Exception ignore) {
			return null;
		}
	}

	private boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		} else if (value instanceof String) {
			String s = (String) value;
			if (s.length() < 1) {
				return true;
			}
		} else if (value.getClass().isArray()) {
			if (Array.getLength(value) == 0) {
				return true;
			}
		} else if (value instanceof List) {
			List list = (List) value;
			if (list.size() == 0) {
				return true;
			}
		}
		return false;
	}

	private void renderResponseIfNotValid(FacesContext context) {
		if (!isValid()) {
			context.renderResponse();
		}
	}

	private void validateValueReally(FacesContext context, Object value) {
		if (validators_ == null) {
			return;
		}
		for (Iterator validators = validators_.iterator(); validators
				.hasNext();) {
			Validator validator = (Validator) validators.next();
			try {
				validator.validate(context, this, value);
			} catch (ValidatorException e) {
				setValid(false);
				FacesMessage message = e.getFacesMessage();
				if (message != null) {
					message.setSeverity(FacesMessage.SEVERITY_ERROR);
					context.addMessage(getClientId(context), message);
				}
			}
		}
	}

	private void validateFromBinding(FacesContext context, Object value) {
		if (validatorBinding_ == null) {
			return;
		}
		try {
			validatorBinding_.invoke(context, new Object[] { context, this,
					value });
		} catch (EvaluationException e) {
			Throwable cause = e.getCause();
			if (cause instanceof ValidatorException) {
				ValidatorException ve = (ValidatorException) e.getCause();
				setValid(false);
				FacesMessage message = ve.getFacesMessage();
				if (message != null) {
					message.setSeverity(FacesMessage.SEVERITY_ERROR);
					context.addMessage(getClientId(context), message);
				}
			} else {
				throw e;
			}
		}
	}

}
