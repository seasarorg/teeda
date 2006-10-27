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
package javax.faces.component;

import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.faces.internal.UICommandUtil;

/**
 * @author shot
 */
public class UICommand extends UIComponentBase implements ActionSource {

    public static final String COMPONENT_FAMILY = "javax.faces.Command";

    public static final String COMPONENT_TYPE = "javax.faces.Command";

    private MethodBinding action = null;

    private MethodBinding actionListener = null;

    private boolean immediate = false;

    private boolean immediateSet = false;

    private Object value = null;

    private static final String IMMEDIATE_BINDING_NAME = "immediate";

    private static final String VALUE_BINDING_NAME = "value";

    private static final String DEFAULT_RENDER_TYPE = "javax.faces.Button";

    public UICommand() {
        super();
        setRendererType(DEFAULT_RENDER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public MethodBinding getAction() {
        return action;
    }

    public void setAction(MethodBinding action) {
        this.action = action;
    }

    public MethodBinding getActionListener() {
        return actionListener;
    }

    public void setActionListener(MethodBinding actionListener) {
        this.actionListener = actionListener;
    }

    public boolean isImmediate() {
        if (immediateSet) {
            return immediate;
        }
        ValueBinding vb = getValueBinding(IMMEDIATE_BINDING_NAME);
        return (vb != null) ? isBindingValueTrue(vb) : immediate;
    }

    public void setImmediate(boolean immediate) {
        this.immediate = immediate;
        immediateSet = true;
    }

    public Object getValue() {
        if (value != null) {
            return value;
        }
        ValueBinding vb = getValueBinding(VALUE_BINDING_NAME);
        return (vb != null) ? getValueFromBinding(vb) : null;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void addActionListener(ActionListener listener) {
        addFacesListener(listener);
    }

    public ActionListener[] getActionListeners() {
        ActionListener[] listeners = (ActionListener[]) getFacesListeners(ActionListener.class);
        return listeners;
    }

    public void removeActionListener(ActionListener listener) {
        removeFacesListener(listener);
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        action = (MethodBinding) restoreAttachedState(context, values[1]);
        actionListener = (MethodBinding) restoreAttachedState(context,
                values[2]);
        immediate = ((Boolean) values[3]).booleanValue();
        immediateSet = ((Boolean) values[4]).booleanValue();
        value = values[5];
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[6];
        values[0] = super.saveState(context);
        values[1] = saveAttachedState(context, action);
        values[2] = saveAttachedState(context, actionListener);
        values[3] = immediate ? Boolean.TRUE : Boolean.FALSE;
        values[4] = immediateSet ? Boolean.TRUE : Boolean.FALSE;
        values[5] = value;
        return values;
    }

    public void broadcast(FacesEvent event) throws AbortProcessingException {
        super.broadcast(event);
        if (event instanceof ActionEvent) {
            FacesContext context = getFacesContext();
            MethodBinding mb = getActionListener();
            if (mb != null) {
                mb.invoke(context, new Object[] { event });
            }
            ActionListener listener = context.getApplication()
                    .getActionListener();
            if (listener != null) {
                listener.processAction((ActionEvent) event);
            }
        }
    }

    public void queueEvent(FacesEvent event) {
        if (event instanceof ActionEvent) {
            if (isImmediate()) {
                event.setPhaseId(PhaseId.APPLY_REQUEST_VALUES);
            } else {
                event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            }
            final FacesContext facesContext = getFacesContext();
            UICommandUtil.setSubmittedCommand(facesContext, this);
        }
        super.queueEvent(event);
    }

    private boolean isBindingValueTrue(ValueBinding vb) {
        Object value = getValueFromBinding(vb);
        return Boolean.TRUE.equals(value);
    }

    private Object getValueFromBinding(ValueBinding vb) {
        return vb.getValue(getFacesContext());
    }
}
