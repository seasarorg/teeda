package org.seasar.teeda.extension.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.CharacterConverter;
import javax.faces.convert.ConverterException;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.util.ConverterUtil;

public class TextareaSeparatorCharacterConverter extends CharacterConverter
        implements ConvertTargetSelectable {
    private String target;

    protected String[] targets;

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
        if (!isTargetCommandConvert(context, targets)) {
            return null;
        }
        return value;
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) throws ConverterException {
        if (!isTargetCommandConvert(context, targets)) {
            return null;
        }
        String s = super.getAsString(context, component, value);
        return s.replaceAll(SEP1, "<br/>").replaceAll(SEP2, "<br/>")
                .replaceAll(SEP3, "<br/>");
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
        if (StringUtil.isEmpty(target)) {
            return;
        }
        targets = StringUtil.split(target, ", ");
    }

    public boolean isTargetCommandConvert(FacesContext context, String[] targets) {
        return ConverterUtil.isTargetCommand(context, targets);
    }

}
