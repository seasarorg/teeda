package javax.faces.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class BooleanConverter implements Converter {

	public static final String CONVERTER_ID = "javax.faces.Boolean";

	public BooleanConverter() {
	}

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {
		ConvertUtils_.assertNotNull(context, "FacesContext");
		ConvertUtils_.assertNotNull(component, "UIComponent");

		if (value == null) {
			return null;
		}

		value = value.trim();
		if (value.length() < 1) {
			return null;
		}
		
		try {
			return Boolean.valueOf(value);
		} catch (Exception e) {
			Object[] args = ConvertUtils_.createExceptionMessageArgs(component, value);
			throw ConvertUtils_.wrappedByConverterException(this, context, args, e);
		}
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException {
		ConvertUtils_.assertNotNull(context, "FacesContext");
		ConvertUtils_.assertNotNull(component, "UIComponent");

		if (value == null) {
			return "";
		}

		try {
			return (value instanceof String) ? (String) value
					: ((Boolean) value).toString();
		} catch (Exception e) {
			throw ConvertUtils_.wrappedByConverterException(e);
		}
	}
}
