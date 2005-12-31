/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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
