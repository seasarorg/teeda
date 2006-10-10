/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.render.DefaultDecoder;


/**
 * @author shot
 * 
 */
public class AdjustableDecoder extends DefaultDecoder {

    protected String getClientId(UIComponent component, FacesContext context) {
        String clientId = component.getClientId(context);
        int sepIndex = clientId.lastIndexOf(NamingContainer.SEPARATOR_CHAR) + 1;
        String prefix = clientId.substring(0, sepIndex);
        String s = clientId.substring(sepIndex);
        if (s.indexOf("-") < 0) {
            return clientId;
        } else {
            return prefix + s.substring(0, s.indexOf("-"));
        }
    }

    protected Map getRequestParameterMap(FacesContext context) {
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        return adjustParamMap(paramMap);
    }

    protected Map getRequestParameterValuesMap(FacesContext context) {
        Map paramMap = context.getExternalContext()
                .getRequestParameterValuesMap();
        return adjustParamMap(paramMap);
    }

    protected Map adjustParamMap(Map paramMap) {
        Map map = new HashMap();
        for (Iterator itr = paramMap.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Entry) itr.next();
            String key = (String) entry.getKey();
            if(key.lastIndexOf("-") > 0) {
                String s = key.substring(0, key.lastIndexOf("-"));
                map.put(s, entry.getValue());
            } else {
                map.put(key, entry.getValue());
            }
        }
        return map;
    }
}
