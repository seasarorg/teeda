package javax.faces.webapp;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


public class ConverterTag extends TagSupport {

    private String converterId_ = null;
    
    public ConverterTag(){
    }
    
    public void setConverterId(String converterId){
        converterId_ = converterId;
    }
    
    public int doStartTag() throws JspException{
        UIComponentTag tag =
            UIComponentTag.getParentUIComponentTag(pageContext);
        if (tag == null) {
            throw new JspException("No nested in UIComponentTag");
        }

        if (!tag.getCreated()) {
            return (SKIP_BODY);
        }

        Converter converter = createConverter();
        
        UIComponent component = tag.getComponentInstance();
        if(component == null || !(component instanceof ValueHolder)){
            throw new JspException("Component is null or not value holder.");
        }
        ValueHolder valueHolder = (ValueHolder)component;
        
        valueHolder.setConverter(converter);

        Object localValue = valueHolder.getLocalValue();
        if (localValue instanceof String) {
            try {
                String str = (String)localValue;
                FacesContext context = WebAppUtils_.getFacesContext();
                localValue = converter.getAsObject(context, component, str);
                valueHolder.setValue(localValue);
            }catch (ConverterException e) {
                JspException ex = new JspException(e);
                throw ex;
            }
        }
        return SKIP_BODY;
    }
    
    public void release(){
        converterId_ = null;
    }
    
    protected Converter createConverter() throws JspException{
        try {
            String converterId = converterId_;
            if (UIComponentTag.isValueReference(converterId_)) {
                converterId = 
                    (String)WebAppUtils_.getValueFromCreatedValueBinding(converterId_);
            }
            return WebAppUtils_.createConverter(converterId);
        } catch (Exception e) {
            throw new JspException(e);
        }
    }
}
