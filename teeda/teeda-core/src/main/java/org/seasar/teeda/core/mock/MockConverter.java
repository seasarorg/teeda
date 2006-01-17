package org.seasar.teeda.core.mock;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class MockConverter implements Converter {

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) throws ConverterException {
        return value;
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) throws ConverterException {
        return (String) value;
    }

}
