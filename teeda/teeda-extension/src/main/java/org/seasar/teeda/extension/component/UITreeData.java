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

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.ComponentUtil_;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.internal.SavedState;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.extension.component.helper.TreeModel;
import org.seasar.teeda.extension.component.helper.TreeModelImpl;
import org.seasar.teeda.extension.component.helper.TreeNode;
import org.seasar.teeda.extension.component.helper.TreeState;
import org.seasar.teeda.extension.component.helper.TreeStateImpl;
import org.seasar.teeda.extension.component.helper.TreeWalker;
import org.seasar.teeda.extension.event.ToggleEvent;
import org.seasar.teeda.extension.event.TreeEventWrapper;

/**
 * @author shot
 */
public class UITreeData extends UIComponentBase implements NamingContainer {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.Tree";

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Tree";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.Tree";

    private TreeModel model;

    private String nodeId;

    private TreeNode node;

    private Object value;

    private String var;

    private Map saveMap = new HashMap();

    private TreeState restoredState = null;

    public UITreeData() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[3];
        values[0] = super.saveState(context);
        values[1] = var;
        values[2] = restoredState;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        var = (String) values[1];
        restoredState = (TreeState) values[2];
    }

    public void encodeBegin(FacesContext context) throws IOException {
        saveMap = new HashMap();
        model = null;
        super.encodeBegin(context);
    }

    public void encodeEnd(FacesContext context) throws IOException {
        super.encodeEnd(context);
        TreeModel model = getDataModel();
        if (model == null) {
            throw new IllegalStateException();
        }
        TreeState state = model.getTreeState();
        if (state == null) {
            state = createTreeState();
        }
        setRestoredState(state);
    }

    protected TreeState createTreeState() {
        return new TreeStateImpl();
    }

    protected void setRestoredState(TreeState state) {
        restoredState = (!state.isTransient()) ? state : null;
    }

    public void queueEvent(FacesEvent event) {
        super.queueEvent(new TreeEventWrapper(event, getNodeId(), this));
    }

    public void broadcast(FacesEvent event) throws AbortProcessingException {
        if (event instanceof TreeEventWrapper) {
            TreeEventWrapper childEvent = (TreeEventWrapper) event;
            String currNodeId = getNodeId();
            setNodeId(childEvent.getOriginalNodeId());
            FacesEvent originalEvent = childEvent.getOriginalEvent();
            originalEvent.getComponent().broadcast(originalEvent);
            setNodeId(currNodeId);
        } else if (event instanceof ToggleEvent) {
            ToggleEvent toggleEvent = (ToggleEvent) event;
            String currentNodeId = getNodeId();
            setNodeId(toggleEvent.getNodeId());
            toggleExpanded();
            setNodeId(currentNodeId);
        } else {
            super.broadcast(event);
        }
    }

    public void processDecodes(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        if (!isRendered()) {
            return;
        }
        model = null;
        saveMap = new HashMap();
        setNodeId(null);
        decode(context);
        processNodes(context, PhaseId.APPLY_REQUEST_VALUES);
    }

    public void processValidators(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        if (!isRendered()) {
            return;
        }
        processNodes(context, PhaseId.PROCESS_VALIDATIONS);
        setNodeId(null);
    }

    public void processUpdates(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        if (!isRendered()) {
            return;
        }
        processNodes(context, PhaseId.UPDATE_MODEL_VALUES);
        setNodeId(null);
    }

    public String getClientId(FacesContext context) {
        String ownClientId = super.getClientId(context);
        if (nodeId != null) {
            return ownClientId + NamingContainer.SEPARATOR_CHAR + nodeId;
        } else {
            return ownClientId;
        }
    }

    public void setValueBinding(String name, ValueBinding binding) {
        if ("value".equals(name)) {
            model = null;
        } else if ("nodeVar".equals(name) || "nodeId".equals(name)
                || "treeVar".equals(name)) {
            throw new IllegalArgumentException("name " + name);
        }
        super.setValueBinding(name, binding);
    }

    public void setValue(Object value) {
        model = null;
        this.value = value;
    }

    public Object getValue() {
        if (value != null) {
            return value;
        }
        ValueBinding vb = getValueBinding("value");
        return vb != null ? vb.getValue(getFacesContext()) : null;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getVar() {
        return var;
    }

    public TreeNode getNode() {
        return node;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        saveDescendantState();
        this.nodeId = nodeId;
        FacesContext context = getFacesContext();
        TreeModel model = getDataModel();
        if (model == null) {
            return;
        }
        try {
            node = model.getNodeById(nodeId);
        } catch (IndexOutOfBoundsException aob) {
            //TODO fix id
            FacesMessage message = FacesMessageUtil.getMessage(context,
                    "missing_node_message_id_here", new String[] { nodeId });
            message.setSeverity(FacesMessage.SEVERITY_WARN);
            context.addMessage(getId(), message);
        }
        restoreDescendantState();
        if (var != null) {
            Map requestMap = context.getExternalContext().getRequestMap();
            if (nodeId == null) {
                requestMap.remove(var);
            } else {
                requestMap.put(var, getNode());
            }
        }
    }

    public String[] getPathInformation(String nodeId) {
        return getDataModel().getPathInformation(nodeId);
    }

    public boolean isLastChild(String nodeId) {
        return getDataModel().isLastChild(nodeId);
    }

    public TreeModel getDataModel() {
        if (model != null) {
            return model;
        }
        Object value = getValue();
        if (value != null) {
            if (value instanceof TreeModel) {
                model = (TreeModel) value;
            } else if (value instanceof TreeNode) {
                model = wrapTreeNode((TreeNode) value);
            } else {
                throw new IllegalStateException(
                        "Value must be a TreeModel or TreeNode");
            }
        }
        if (restoredState != null) {
            model.setTreeState(restoredState);
        }
        return model;
    }

    protected TreeModel wrapTreeNode(TreeNode node) {
        return new TreeModelImpl(node);
    }

    public void expandAll() {
        toggleAll(true);
    }

    public void collapseAll() {
        toggleAll(false);
    }

    private void toggleAll(boolean expanded) {
        TreeWalker walker = getDataModel().getTreeWalker();
        walker.walkBegin(this);
        walker.setCheckState(false);
        TreeState state = getDataModel().getTreeState();
        while (walker.next()) {
            String id = getNodeId();
            if ((expanded && !state.isNodeExpanded(id))
                    || (!expanded && state.isNodeExpanded(id))) {
                state.toggleExpanded(id);
            }
        }
    }

    public void expandPath(String[] nodePath) {
        getDataModel().getTreeState().expandPath(nodePath);
    }

    public void collapsePath(String[] nodePath) {
        getDataModel().getTreeState().collapsePath(nodePath);
    }

    protected void processNodes(FacesContext context, PhaseId phaseId) {
        UIComponent facet = null;
        TreeWalker walker = getDataModel().getTreeWalker();
        walker.walkBegin(this);
        while (walker.next()) {
            TreeNode node = getNode();
            facet = getFacet(node.getType());
            if (facet == null) {
                continue;
            }
            ComponentUtil_.processAppropriatePhaseAction(context, facet,
                    phaseId);
        }
    }

    private void saveDescendantState() {
        FacesContext context = getFacesContext();
        Iterator i = getFacets().values().iterator();
        while (i.hasNext()) {
            UIComponent facet = (UIComponent) i.next();
            saveDescendantState(facet, context);
        }
    }

    private void saveDescendantState(UIComponent component, FacesContext context) {
        if (component instanceof EditableValueHolder) {
            EditableValueHolder input = (EditableValueHolder) component;
            String clientId = component.getClientId(context);
            SavedState state = (SavedState) saveMap.get(clientId);
            if (state == null) {
                state = new SavedState();
                saveMap.put(clientId, state);
            }
            state.save(input);
        }
        List kids = component.getChildren();
        for (int i = 0; i < kids.size(); i++) {
            saveDescendantState((UIComponent) kids.get(i), context);
        }
    }

    private void restoreDescendantState() {
        FacesContext context = getFacesContext();
        Iterator i = getFacets().values().iterator();
        while (i.hasNext()) {
            UIComponent facet = (UIComponent) i.next();
            restoreDescendantState(facet, context);
        }
    }

    private void restoreDescendantState(UIComponent component,
            FacesContext context) {
        String id = component.getId();
        component.setId(id);
        if (component instanceof EditableValueHolder) {
            EditableValueHolder holder = (EditableValueHolder) component;
            String clientId = component.getClientId(context);
            SavedState state = (SavedState) saveMap.get(clientId);
            if (state == null) {
                state = new SavedState();
            }
            state.restore(holder);
        }
        List child = component.getChildren();
        for (int i = 0; i < child.size(); i++) {
            restoreDescendantState((UIComponent) child.get(i), context);
        }
        Map facets = component.getFacets();
        for (Iterator i = facets.values().iterator(); i.hasNext();) {
            restoreDescendantState((UIComponent) i.next(), context);
        }
    }

    public void toggleExpanded() {
        getDataModel().getTreeState().toggleExpanded(getNodeId());
    }

    public boolean isNodeExpanded() {
        return getDataModel().getTreeState().isNodeExpanded(getNodeId());
    }

    public void setNodeSelected(ActionEvent event) {
        getDataModel().getTreeState().setSelected(getNodeId());
    }

    public boolean isNodeSelected() {
        return (getNodeId() != null) ? getDataModel().getTreeState()
                .isSelected(getNodeId()) : false;
    }
}
