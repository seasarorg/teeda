package javax.faces.component;

import javax.faces.el.ValueBinding;

public class UISelectBoolean extends UIInput {


    public static final String COMPONENT_TYPE = "javax.faces.SelectBoolean";

    public static final String COMPONENT_FAMILY = "javax.faces.SelectBoolean";
    
    private static final String DEFAULT_RENDERER_TYPE = "javax.faces.Checkbox";
    
    public UISelectBoolean() {
        super();
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public boolean isSelected() {
        Boolean value = (Boolean) getValue();
        if (value != null) {
            return value.booleanValue();
        }else{
            return false;
        }
    }

    public void setSelected(boolean selected) {
        if (selected) {
            setValue(Boolean.TRUE);
        } else {
            setValue(Boolean.FALSE);
        }
    }

    public ValueBinding getValueBinding(String name) {
        if ("selected".equals(name)) {
            return super.getValueBinding("value");
        } else {
            return super.getValueBinding(name);
        }
    }

    public void setValueBinding(String name, ValueBinding binding) {
        if ("selected".equals(name)) {
            super.setValueBinding("value", binding);
        } else {
            super.setValueBinding(name, binding);
        }
    }

}
