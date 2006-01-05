package org.seasar.teeda.core.mock;

import javax.faces.FacesException;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;


public class NullValidator implements Validator, StateHolder {

    private boolean transientValue_ = false;
    public NullValidator(){
    }
    
    public void validate(FacesContext context, UIComponent component,
            Object value) throws FacesException {
        //do nothing
    }

    public boolean isTransient() {
        return transientValue_;
    }

    public void setTransient(boolean transientValue) {
        transientValue_ = transientValue;
    }

    public Object saveState(FacesContext context) {
        return null;
    }

    public void restoreState(FacesContext context, Object state) {
    }

}
