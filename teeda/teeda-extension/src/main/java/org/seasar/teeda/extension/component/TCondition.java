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
package org.seasar.teeda.extension.component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.component.ComponentUtil_;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.PhaseId;
import javax.faces.internal.scope.PageScope;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.extension.ExtensionConstants;

/**
 * @author shot
 */
public class TCondition extends UIComponentBase {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.Condition";

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Condition";

    private static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.Condition";

    private static final int CONDITION_SIZE = 17;

    private Boolean renderSpan = null;

    public TCondition() {
        super.setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public boolean isRendered() {
        Boolean condition = getEncodedCondition();
        if (condition != null) {
            return condition.booleanValue();
        }
        return super.isRendered();
    }

    protected Boolean getEncodedCondition() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map conditions = getConditions(context);
        if (conditions == null) {
            return null;
        }
        return (Boolean) conditions.get(getClientId(context));
    }

    protected Map getConditions(FacesContext context) {
        Map map = PageScope.getContext(context);
        if (map == null) {
            return null;
        }
        return (Map) map.get(COMPONENT_TYPE);
    }

    protected Map getOrCreateConditions(FacesContext context) {
        Map map = PageScope.getOrCreateContext(context);
        Map conditions = (Map) map.get(COMPONENT_TYPE);
        if (conditions == null) {
            conditions = new HashMap(CONDITION_SIZE);
            map.put(COMPONENT_TYPE, conditions);
        }
        return conditions;
    }

    protected void clearEncodedCondition() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map conditions = getConditions(context);
        if (conditions == null) {
            return;
        }
        conditions.remove(getClientId(context));
    }

    protected void saveEncodedCondition() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map conditions = getOrCreateConditions(context);
        conditions.put(getClientId(context), Boolean
                .valueOf(super.isRendered()));
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public void processDecodes(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        decode(context);
        processAppropriateAction(context, PhaseId.APPLY_REQUEST_VALUES);
    }

    public void processUpdates(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        processAppropriateAction(context, PhaseId.UPDATE_MODEL_VALUES);
    }

    public void processValidators(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        processAppropriateAction(context, PhaseId.PROCESS_VALIDATIONS);
    }

    protected void processAppropriateAction(FacesContext context, PhaseId phase) {
        if (!isRendered()) {
            return;
        }
        for (Iterator children = getFacetsAndChildren(); children.hasNext();) {
            UIComponent component = (UIComponent) children.next();
            ComponentUtil_.processAppropriatePhaseAction(context, component,
                    phase);
        }
    }

    public void encodeBegin(FacesContext context) throws IOException {
        clearEncodedCondition();
        super.encodeBegin(context);
    }

    public void encodeEnd(FacesContext context) throws IOException {
        super.encodeEnd(context);
        saveEncodedCondition();
    }

    public boolean isRenderSpan() {
        if (renderSpan != null) {
            return renderSpan.booleanValue();
        }
        ValueBinding vb = getValueBinding(ExtensionConstants.RENDERSPAN_ATTR);
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v != null ? v.booleanValue() : false;
    }

    public void setRenderSpan(boolean renderSpan) {
        this.renderSpan = new Boolean(renderSpan);
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        renderSpan = (Boolean) values[1];
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[7];
        values[0] = super.saveState(context);
        values[1] = renderSpan;
        return values;
    }

}
