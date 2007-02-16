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

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.internal.SubApplicationUtil;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author higa
 */
public class TInclude extends UIComponentBase implements NamingContainer {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.Include";

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Include";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.Include";

    private String src;

    private boolean included = false;

    public TInclude() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public static String calcViewId(FacesContext context, String src,
            String viewRootPath) {
        AssertionUtil.assertNotNull("context", context);
        AssertionUtil.assertNotNull("src", src);
        AssertionUtil.assertNotNull("viewRootPath", viewRootPath);
        if (src.startsWith("/")) {
            if ("/".endsWith(viewRootPath)) {
                viewRootPath = "";
            }
            return viewRootPath + src;
        }
        return SubApplicationUtil.getSubApplicationPath(context.getViewRoot()
                .getViewId())
                + "/" + src;
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    /**
     * @return Returns the src.
     */
    public String getSrc() {
        if (src != null) {
            return src;
        }
        ValueBinding vb = getValueBinding("src");
        return vb != null ? (String) vb.getValue(getFacesContext()) : null;
    }

    /**
     * @param src The src to set.
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * @return Returns the included.
     */
    public boolean isIncluded() {
        return included;
    }

    /**
     * @param included The included to set.
     */
    public void setIncluded(boolean included) {
        this.included = included;
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = src;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        src = (String) values[1];
    }
}