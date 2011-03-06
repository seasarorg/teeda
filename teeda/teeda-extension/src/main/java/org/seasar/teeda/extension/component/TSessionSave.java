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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.internal.scope.PageScope;

import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.extension.util.SessionSaveUtil;

/**
 * @author higa
 */
public class TSessionSave extends UIInput {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.SessionSave";

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.SessionSave";

    public static final String DEFAULT_RENDERER_TYPE = null;

    private static final String SESSION_SAVE = TSessionSave.class.getName();

    public TSessionSave() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public void decode(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        Map saveValues = getSaveValues(context);
        if (saveValues == null) {
            return;
        }
        ValueBinding valueBinding = getValueBinding("value");
        if (valueBinding == null) {
            return;
        }
        String targetId = getTargetId();
        Object value = HotdeployUtil.rebuildValue(saveValues.get(targetId));
        setValue(value);
        valueBinding.setValue(context, value);
    }

    protected Map getSaveValues(FacesContext context) {
        Map viewContext = PageScope.getOrCreateContext(context);
        return (Map) viewContext.get(SESSION_SAVE);
    }

    protected Map getOrCreateSaveValues(FacesContext context) {
        Map saveValues = getSaveValues(context);
        if (saveValues == null) {
            Map viewContext = PageScope.getOrCreateContext(context);
            saveValues = new HashMap();
            viewContext.put(SESSION_SAVE, saveValues);
        }
        return saveValues;
    }

    protected String getTargetId() {
        return SessionSaveUtil.convertTargetId(getId());
    }

    public void encodeEnd(FacesContext context) throws IOException {
        AssertionUtil.assertNotNull("context", context);
        Object value = getValue();
        if (value == null) {
            return;
        }
        Map saveValues = getOrCreateSaveValues(context);
        saveValues.put(getTargetId(), value);
    }
}