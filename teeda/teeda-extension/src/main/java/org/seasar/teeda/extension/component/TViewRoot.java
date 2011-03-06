/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
import javax.faces.event.PhaseId;

import org.seasar.teeda.core.scope.impl.DispatchScope;
import org.seasar.teeda.core.scope.impl.DispatchScopeFactory;
import org.seasar.teeda.extension.util.JavaScriptContext;

/**
 * @author shot
 *
 */
public class TViewRoot extends UIViewRoot {

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.ViewRoot";

    private static final String SCRIPTS_KEY = TViewRoot.class.getName()
            + ".SCRIPTS_KEY";

    private Map scriptsMap = new HashMap();

    private String rootViewId;

    public TViewRoot() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    /**
     * @return Returns the rootViewId.
     */
    public String getRootViewId() {
        return rootViewId;
    }

    /**
     * @param rootViewId The rootViewId to set.
     */
    public void setRootViewId(String rootViewId) {
        this.rootViewId = rootViewId;
    }

    public void processDecodes(FacesContext context) {
        decode(context);
        broadcastEvents(context, PhaseId.APPLY_REQUEST_VALUES);
        clearEventsIfResponseRendered(context);
    }

    public void addScript(String scriptId, JavaScriptContext jsContext) {
        Map scripts = getScriptsMap();
        if (!scripts.containsKey(scriptId)) {
            scripts.put(scriptId, jsContext);
        }
        scriptsMap.putAll(scripts);
    }

    public String getAllScripts() {
        StringBuffer buf = new StringBuffer(128);
        for (Iterator itr = scriptsMap.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Map.Entry) itr.next();
            JavaScriptContext context = (JavaScriptContext) entry.getValue();
            if (context.hasContext()) {
                buf.append(context.getResult());
            }
        }
        return buf.toString();
    }

    public boolean containsScript(String scriptId) {
        return getScriptsMap().containsKey(scriptId);
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        scriptsMap = (Map) values[1];
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = scriptsMap;
        return values;
    }

    protected Map getScriptsMap() {
        DispatchScope dispatchScope = getDispatchScope();
        Map scripts = (Map) dispatchScope.get(SCRIPTS_KEY);
        if (scripts == null) {
            scripts = new HashMap();
            dispatchScope.put(SCRIPTS_KEY, scripts);
        }
        return scripts;
    }

    protected DispatchScope getDispatchScope() {
        return DispatchScopeFactory.getDispatchScope();
    }

}
