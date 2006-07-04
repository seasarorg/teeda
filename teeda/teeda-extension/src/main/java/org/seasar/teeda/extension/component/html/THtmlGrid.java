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
package org.seasar.teeda.extension.component.html;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.ComponentUtil_;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.internal.NamingContainerUtil;
import javax.faces.internal.SavedState;

import org.seasar.framework.util.AssertionUtil;

public class THtmlGrid extends UIComponentBase implements NamingContainer {

    static final String GRID = "Grid";

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Grid";

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlGrid";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.Grid";

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    private int rowIndex;

    private Object value;

    private String width;

    private String height;

    private Map savedStates = new HashMap();

    public String getClientId(FacesContext context) {
        String clientId = super.getClientId(context);
        int rowIndex = getRowIndex();
        if (rowIndex == -1) {
            return clientId;
        }
        return clientId + "_" + rowIndex;
    }

    private int getRowIndex() {
        return rowIndex;
    }

    private void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public void eachRow(Each block) {
        FacesContext context = FacesContext.getCurrentInstance();
        final List items = getItems();
        for (int i = 0; i < items.size(); i++) {
            setRowIndex(i);
            final Object rowBean = items.get(i);
            enterRow(context, rowBean);
            try {
                block.each();
            } finally {
                leaveRow(context);
            }
        }
    }

    public Object getValue() {
        if (value != null) {
            return value;
        }
        return ComponentUtil_.getValueBindingValue(this, "value");
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String width) {
        this.height = width;
    }

    List getItems() {
        final Object value = getValue();
        if (value == null) {
            return Collections.EMPTY_LIST;
        }
        if (!(value instanceof List)) {
            throw new ClassCastException(value.getClass().getName());
        }
        return (List) value;
    }

    void enterRow(final FacesContext context, Object rowBean) {
        final String itemName = getNaturalId();
        context.getExternalContext().getRequestMap().put(itemName, rowBean);
        restoreDescendantState(context, this);
    }

    void leaveRow(FacesContext context) {
        final String itemName = getNaturalId();
        context.getExternalContext().getRequestMap().remove(itemName);
        saveDescendantComponentStates(context, this);
    }

    private String getNaturalId() {
        final String id = getId();
        final int pos = id.lastIndexOf(GRID);
        if (-1 == pos) {
            throw new IllegalStateException(
                    "Grid component should contain \"Grid\" in it's id");
        }
        final String naturalId = id.substring(0, pos);
        return naturalId;
    }

    protected void restoreDescendantState(FacesContext context,
            UIComponent component) {
        for (final Iterator it = component.getChildren().iterator(); it
                .hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            NamingContainerUtil.refreshClientId(child);
            if (child instanceof EditableValueHolder) {
                final EditableValueHolder holder = (EditableValueHolder) child;
                final String clientId = child.getClientId(context);
                SavedState state = (SavedState) savedStates.get(clientId);
                if (state == null) {
                    state = new SavedState();
                    savedStates.put(clientId, state);
                }
                state.restore(holder);
            }
            restoreDescendantState(context, child);
        }
    }

    protected void saveDescendantComponentStates(FacesContext context,
            UIComponent component) {
        for (final Iterator it = component.getChildren().iterator(); it
                .hasNext();) {
            final UIComponent child = (UIComponent) it.next();
            if (child instanceof EditableValueHolder) {
                final EditableValueHolder holder = (EditableValueHolder) child;
                final SavedState state = new SavedState();
                state.save(holder);
                savedStates.put(child.getClientId(context), state);
            }
            saveDescendantComponentStates(context, child);
        }
    }

    public void processDecodes(final FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        if (!isRendered()) {
            return;
        }
        eachRow(new Each() {
            public void each() {
                processDecodes0(context);
            }
        });
    }

    private void processDecodes0(final FacesContext context) {
        super.processDecodes(context);
    }

    public void processValidators(final FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        if (!isRendered()) {
            return;
        }
        eachRow(new Each() {
            public void each() {
                processValidators0(context);
            }
        });
    }

    private void processValidators0(FacesContext context) {
        super.processValidators(context);
    }

    public void processUpdates(final FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        if (!isRendered()) {
            return;
        }
        eachRow(new Each() {
            public void each() {
                processUpdates0(context);
            }
        });
    }

    private void processUpdates0(FacesContext context) {
        super.processUpdates(context);
    }

    public static interface Each {
        public void each();
    }

}
