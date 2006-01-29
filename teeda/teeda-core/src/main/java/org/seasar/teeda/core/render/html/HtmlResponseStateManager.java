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
package org.seasar.teeda.core.render.html;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.StateManager.SerializedView;
import javax.faces.context.FacesContext;

import org.seasar.teeda.core.render.AbstractResponseStateManager;

/**
 * @author shot
 */
public class HtmlResponseStateManager extends AbstractResponseStateManager {

    private static final String VIEW_ID = "org.seasar.teeda.core.JSF_VIEW_ID";

    private static final String TREE_PARAMETER = "org.seasar.teeda.core.TREE_PARAMETER";

    private static final String STATE_PARAMETER = "org.seasar.teeda.core.STATE_PARAMETER";

    private static final String BASE64_TREE_PARAMETER = "org.seasar.teeda.core.BASE64_TREE_PARAMETER";

    private static final String BASE64_STATE_PARAMETER = "org.seasar.teeda.core.BASE64_STATE_PARAMETER";

    private static final String DEFAULT_ENCODING = "ISO-8859-1";

    public HtmlResponseStateManager() {
    }

    public void writeState(FacesContext context, SerializedView state)
            throws IOException {
        // TODO Auto-generated method stub

    }

    public Object getTreeStructureToRestore(FacesContext context, String viewId) {
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        Object state = paramMap.get(VIEW_ID);
        if (state == null || !state.equals(viewId)) {
            return null;
        }
        state = paramMap.get(TREE_PARAMETER);
        if (state != null) {
            return state;
        }
        state = paramMap.get(BASE64_TREE_PARAMETER);
        if (state != null) {
            String s = (String) state;
            return getDecoder().decode(s);
        }
        return null;
    }

    public Object getComponentStateToRestore(FacesContext context) {
        Map paramMap = context.getExternalContext().getRequestParameterMap();
        Object state = paramMap.get(STATE_PARAMETER);
        if (state != null) {
            return state;
        }
        state = paramMap.get(BASE64_STATE_PARAMETER);
        if (state != null) {
            String s = (String) state;
            return getDecoder().decode(s);
        }
        return null;
    }

}
