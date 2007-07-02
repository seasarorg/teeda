package org.seasar.teeda.extension.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.CharacterConverter;
import javax.faces.convert.ConverterException;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;

public class TextareaSeparatorCharacterConverter extends CharacterConverter {

    private static final String SEP1 = "\r\n";

    private static final String SEP2 = "\r";

    private static final String SEP3 = "\n";

    public Object getAsObject(FacesContext context, UIComponent component,
            String value) throws ConverterException {
        AssertionUtil.assertNotNull("FacesContext", context);
        AssertionUtil.assertNotNull("UIComponent", component);
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        return value;
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) throws ConverterException {
        String s = super.getAsString(context, component, value);
        return s.replaceAll(SEP1, "<br/>").replaceAll(SEP2, "<br/>")
                .replaceAll(SEP3, "<br/>");
    }

}
