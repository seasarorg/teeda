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
package org.seasar.teeda.extension.component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.seasar.teeda.extension.util.JavaScriptContext;

/**
 * @author shot
 *
 */
public class ScriptEnhanceUIViewRoot extends UIViewRoot {

    private Map scripts = null;

    public ScriptEnhanceUIViewRoot() {
        scripts = new HashMap();
    }

    public void addScript(String scriptId, JavaScriptContext context) {
        if (!scripts.containsKey(scriptId)) {
            scripts.put(scriptId, context);
        }
    }

    public String getAllScripts() {
        StringBuffer buf = new StringBuffer();
        for (Iterator itr = scripts.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Map.Entry) itr.next();
            JavaScriptContext context = (JavaScriptContext) entry.getValue();
            if (context.hasContext()) {
                buf.append(context.getResult());
            }
        }
        return buf.toString();
    }

    public boolean containsScript(String scriptId) {
        Map requestMap = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestMap();
        return requestMap.containsKey(scriptId)
                && scripts.containsKey(scriptId);
    }

    public void clearScripts() {
        scripts.clear();
    }

    public void clearScript(String scriptId) {
        scripts.remove(scriptId);
        Map requestMap = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestMap();
        requestMap.remove(scriptId);
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        scripts = (Map) values[1];
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = scripts;
        return values;
    }

}
