package org.seasar.teeda.core.util;

import java.util.List;

import javax.faces.component.ActionSource;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;


public class UIParameterUtil {

    private UIParameterUtil(){
    }

    public static void saveParametersToRequest(ActionSource source, FacesContext context) {
        if(!(source instanceof UICommand)){
            throw new ClassCastException();
        }
        UICommand command = (UICommand)source;
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
        List children = command.getChildren();
        for (int i = 0; i < children.size(); ++i) {
            UIComponent child = (UIComponent) children.get(i);
            if (child instanceof UIParameter) {
                UIParameter param = (UIParameter) child;
                request.setAttribute(param.getName(), param.getValue());
            }
        }
    }
    
}
