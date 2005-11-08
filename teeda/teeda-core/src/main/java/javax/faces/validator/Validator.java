package javax.faces.validator;

import java.util.EventListener;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public interface Validator extends EventListener {

	public static final String NOT_IN_RANGE_MESSAGE_ID = "javax.faces.validator.NOT_IN_RANGE";

	public void validate(FacesContext context, UIComponent component,
			Object value) throws FacesException;
}
