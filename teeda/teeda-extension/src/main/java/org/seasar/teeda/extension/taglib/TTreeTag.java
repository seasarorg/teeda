/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.taglib;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.taglib.UIComponentTagBase;
import org.seasar.teeda.extension.component.html.THtmlTree;

public class TTreeTag extends UIComponentTagBase {

    private String value;

    private String var;

    private String varNodeToggler;

    private String showLines;

    private String showNav;

    private String clientSideToggle;

    private String showRootNode;

    private String preserveToggle;

    public void release() {
        super.release();

        value = null;
        var = null;
        varNodeToggler = null;
        showLines = null;
        showNav = null;
        clientSideToggle = null;
        showRootNode = null;
        preserveToggle = null;
    }

    public String getComponentType() {
        return THtmlTree.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return THtmlTree.DEFAULT_RENDERER_TYPE;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setVarNodeToggler(String varNodeToggler) {
        this.varNodeToggler = varNodeToggler;
    }

    public void setShowLines(String showLines) {
        this.showLines = showLines;
    }

    public void setShowNav(String showNav) {
        this.showNav = showNav;
    }

    public void setClientSideToggle(String clientSideToggle) {
        this.clientSideToggle = clientSideToggle;
    }

    public void setShowRootNode(String showRootNode) {
        this.showRootNode = showRootNode;
    }

    public void setPreserveToggle(String preserveToggle) {
        this.preserveToggle = preserveToggle;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        FacesContext context = getFacesContext();
        if (value != null) {
            ValueBinding vb = context.getApplication()
                    .createValueBinding(value);
            component.setValueBinding("value", vb);
        }
        if (var != null) {
            ((THtmlTree) component).setVar(var);
        }
        if (varNodeToggler != null) {
            ((THtmlTree) component).setVarNodeToggler(varNodeToggler);
        }
        if (showNav != null) {
            setComponentProperty(component, "showNav", showNav);
        }
        if (showLines != null) {
            setComponentProperty(component, "showLines", showLines);
        }
        if (clientSideToggle != null) {
            setComponentProperty(component, "clientSideToggle",
                    clientSideToggle);
        }
        if (showRootNode != null) {
            setComponentProperty(component, "showRootNode", showRootNode);
        }
        if (preserveToggle != null) {
            setComponentProperty(component, "preserveToggle", preserveToggle);
        }
    }
}
