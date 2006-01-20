package javax.faces.webapp;

import javax.faces.component.UIComponent;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class AttributeTag extends TagSupport {

    private String name_ = null;

    private String value_ = null;

    public AttributeTag() {
    }

    public void setName(String name) {
        name_ = name;
    }

    public void setValue(String value) {
        value_ = value;
    }

    public int doStartTag() throws JspException {
        UIComponentTag tag = UIComponentTag
                .getParentUIComponentTag(pageContext);
        if (tag == null) {
            throw new JspException("Not nested in a UIComponentTag");
        }
        UIComponent component = tag.getComponentInstance();
        if (component == null) {
            throw new JspException(
                    "No component associated with UIComponentTag");
        }
        String name = name_;
        if (UIComponentTag.isValueReference(name_)) {
            name = (String) WebAppUtils_.getValueFromCreatedValueBinding(name_);
        }
        Object value = value_;
        if (UIComponentTag.isValueReference(value_)) {
            value = WebAppUtils_.getValueFromCreatedValueBinding(value_);
        }
        if (component.getAttributes().get(name) == null) {
            component.getAttributes().put(name, value);
        }
        return SKIP_BODY;
    }

    public void release() {
        name_ = null;
        value_ = null;
    }
}
