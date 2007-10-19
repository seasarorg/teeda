package org.seasar.teeda.extension.convert;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.CharacterConverter;
import javax.faces.convert.ConverterException;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.util.TargetCommandUtil;

public class TextareaSeparatorCharacterConverter extends CharacterConverter
        implements ConvertTargetSelectable, StateHolder {

    protected String target;

    protected String[] targets;

    protected String objectMessageId;

    protected String stringMessageId;

    protected boolean transientValue = false;

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
        if (!ConverterHelper.isTargetCommand(context, component, targets, this)) {
            return value;
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
        return TargetCommandUtil.isTargetCommand(context, targets);
    }

    public boolean isTransient() {
        return transientValue;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        target = (String) values[0];
        setTarget(target);
        objectMessageId = (String) values[1];
        stringMessageId = (String) values[2];
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[3];
        values[0] = target;
        values[1] = objectMessageId;
        values[2] = stringMessageId;
        return values;
    }

    public void setTransient(boolean transientValue) {
        this.transientValue = transientValue;
    }

    public String getObjectMessageId() {
        return (!StringUtil.isEmpty(objectMessageId)) ? objectMessageId : super
                .getObjectMessageId();
    }

    public void setObjectMessageId(String objectMessageId) {
        this.objectMessageId = objectMessageId;
    }

    public String getStringMessageId() {
        return (!StringUtil.isEmpty(stringMessageId)) ? stringMessageId : super
                .getStringMessageId();
    }

    public void setStringMessageId(String stringMessageId) {
        this.stringMessageId = stringMessageId;
    }

}
