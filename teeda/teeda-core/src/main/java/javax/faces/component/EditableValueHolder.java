package javax.faces.component;

import javax.faces.el.MethodBinding;
import javax.faces.event.ValueChangeListener;
import javax.faces.validator.Validator;

public interface EditableValueHolder extends ValueHolder {

	public Object getSubmittedValue();

	public void setSubmittedValue(Object submittedValue);

	public boolean isLocalValueSet();

	public void setLocalValueSet(boolean localValueSet);

	public boolean isValid();

	public void setValid(boolean valid);

	public boolean isRequired();

	public void setRequired(boolean required);

	public boolean isImmediate();

	public void setImmediate(boolean immediate);

	public MethodBinding getValidator();

	public void setValidator(MethodBinding validatorBinding);

	public MethodBinding getValueChangeListener();

	public void setValueChangeListener(MethodBinding valueChangeMethod);

	public void addValidator(Validator validator);

	public Validator[] getValidators();

	public void removeValidator(Validator validator);

	public void addValueChangeListener(ValueChangeListener listener);

	public ValueChangeListener[] getValueChangeListeners();

	public void removeValueChangeListener(ValueChangeListener listener);
}
