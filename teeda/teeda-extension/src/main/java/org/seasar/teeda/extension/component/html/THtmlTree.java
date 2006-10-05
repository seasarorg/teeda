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

import java.util.Map;

import javax.faces.component.UICommand;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.extension.component.UITreeData;

public class THtmlTree extends UITreeData {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.THtmlTree";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.THtmlTree";

    private UICommand expandControl;

    private String varNodeToggler;

    private Boolean showNav;

    private Boolean showLines;

    private Boolean clientSideToggle;

    private Boolean showRootNode;

    private Boolean preserveToggle;

    private String javascriptLocation;

    private String imageLocation;

    public THtmlTree() {
        setRendererType(DEFAULT_RENDERER_TYPE);
        expandControl = new HtmlCommandLink();
        expandControl.setParent(this);
        clientSideToggle = Boolean.TRUE;
        preserveToggle = Boolean.TRUE;
        showRootNode = Boolean.TRUE;
        showNav = Boolean.TRUE;
        showLines = Boolean.TRUE;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[9];
        values[0] = super.saveState(context);
        values[1] = varNodeToggler;
        values[2] = showLines;
        values[3] = showNav;
        values[4] = clientSideToggle;
        values[5] = showRootNode;
        values[6] = preserveToggle;
        values[7] = javascriptLocation;
        values[8] = imageLocation;
        return (Object) values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        setVarNodeToggler((String) values[1]);
        setShowLines(((Boolean) values[2]).booleanValue());
        setShowNav(((Boolean) values[3]).booleanValue());
        setClientSideToggle(((Boolean) values[4]).booleanValue());
        setShowRootNode(((Boolean) values[5]).booleanValue());
        setPreserveToggle(((Boolean) values[6]).booleanValue());
        setJavascriptLocation((String) values[7]);
        setImageLocation((String) values[8]);
    }

    public void setNodeId(String nodeId) {
        super.setNodeId(nodeId);
        if (varNodeToggler != null) {
            Map requestMap = getFacesContext().getExternalContext()
                    .getRequestMap();
            requestMap.put(varNodeToggler, this);
        }
    }

    public UICommand getExpandControl() {
        return expandControl;
    }

    public void setVarNodeToggler(String varNodeToggler) {
        this.varNodeToggler = varNodeToggler;
        String bindingString = BindingUtil.getExpression(varNodeToggler,
                "toggleExpanded");
        MethodBinding actionBinding = FacesContext.getCurrentInstance()
                .getApplication().createMethodBinding(bindingString, null);
        expandControl.setAction(actionBinding);
    }

    public void setShowNav(boolean showNav) {
        this.showNav = Boolean.valueOf(showNav);
    }

    public boolean isShowNav() {
        if (showNav != null)
            return showNav.booleanValue();
        ValueBinding vb = getValueBinding("showNav");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v == null || v.booleanValue();
    }

    public void setShowLines(boolean showLines) {
        this.showLines = Boolean.valueOf(showLines);
    }

    public boolean isShowLines() {
        if (showLines != null)
            return showLines.booleanValue();
        ValueBinding vb = getValueBinding("showLines");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v == null || v.booleanValue();
    }

    public void setClientSideToggle(boolean clientSideToggle) {
        this.clientSideToggle = Boolean.valueOf(clientSideToggle);
    }

    public boolean isClientSideToggle() {
        if (clientSideToggle != null)
            return clientSideToggle.booleanValue();
        ValueBinding vb = getValueBinding("clientSideToggle");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v == null || v.booleanValue();
    }

    public void setShowRootNode(boolean showRootNode) {
        this.showRootNode = Boolean.valueOf(showRootNode);
    }

    public boolean isShowRootNode() {
        if (showRootNode != null)
            return showRootNode.booleanValue();
        ValueBinding vb = getValueBinding("showRootNode");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v == null || v.booleanValue();
    }

    public void setPreserveToggle(boolean preserveToggle) {
        this.preserveToggle = Boolean.valueOf(preserveToggle);
    }

    public boolean isPreserveToggle() {
        if (preserveToggle != null)
            return preserveToggle.booleanValue();
        ValueBinding vb = getValueBinding("preserveToggle");
        Boolean v = vb != null ? (Boolean) vb.getValue(getFacesContext())
                : null;
        return v == null || v.booleanValue();
    }

    public void setJavascriptLocation(String javascriptLocation) {
        this.javascriptLocation = javascriptLocation;
    }

    public String getJavascriptLocation() {
        if (javascriptLocation != null)
            return javascriptLocation;

        ValueBinding vb = getValueBinding("javascriptLocation");
        if (vb == null) {
            return null;
        } else {
            return (String) vb.getValue(getFacesContext());
        }
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getImageLocation() {
        if (imageLocation != null)
            return imageLocation;
        ValueBinding vb = getValueBinding("imageLocation");
        if (vb == null) {
            return null;
        } else {
            return (String) vb.getValue(getFacesContext());
        }
    }
}
