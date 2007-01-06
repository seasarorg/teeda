/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.render;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.render.EditableValueHolderDecoder;
import org.seasar.teeda.extension.util.AdjustValueHolderUtil;

/**
 * @author shot
 *
 */
public class AdjustableInputDecoder extends EditableValueHolderDecoder {

    protected String getClientId(UIComponent component, FacesContext context) {
        String clientId = component.getClientId(context);
        return AdjustValueHolderUtil.getAdjustedValue(clientId);
    }

    protected Map getRequestParameterMap(FacesContext context) {
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        return AdjustValueHolderUtil.adjustParamMap(paramMap);
    }

    protected Map getRequestParameterValuesMap(FacesContext context) {
        Map paramMap = context.getExternalContext()
                .getRequestParameterValuesMap();
        return AdjustValueHolderUtil.adjustParamMap(paramMap);
    }

}
