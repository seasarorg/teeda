/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

/**
 * @author shot
 */
public class UIMessages extends UIComponentBase {

    public static final String COMPONENT_FAMILY = "javax.faces.Messages";

    public static final String COMPONENT_TYPE = "javax.faces.Messages";

    private static final String DEFAULT_RENDER_TYPE = "javax.faces.Messages";

    private boolean globalOnly = false;

    private boolean globalOnlySet = false;

    private boolean showDetail = false;

    private boolean showDetailSet = false;

    private boolean showSummary = true;

    private boolean showSummarySet = false;

    public UIMessages() {
        setRendererType(DEFAULT_RENDER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public boolean isGlobalOnly() {
        if (globalOnlySet) {
            return globalOnly;
        }
        Object value = ComponentUtil_.getValueBindingValue(this, "globalOnly");
        return (value != null) ? Boolean.TRUE.equals(value) : globalOnly;
    }

    public void setGlobalOnly(boolean isGlobalOnly) {
        globalOnly = isGlobalOnly;
        globalOnlySet = true;
    }

    public boolean isShowDetail() {
        if (showDetailSet) {
            return showDetail;
        }
        Object value = ComponentUtil_.getValueBindingValue(this, "showDetail");
        return (value != null) ? Boolean.TRUE.equals(value) : showDetail;
    }

    public void setShowDetail(boolean isShowDetail) {
        showDetail = isShowDetail;
        showDetailSet = true;
    }

    public boolean isShowSummary() {
        if (showSummarySet) {
            return showSummary;
        }
        Object value = ComponentUtil_.getValueBindingValue(this, "showSummary");
        return (value != null) ? Boolean.TRUE.equals(value) : showSummary;
    }

    public void setShowSummary(boolean showSummary) {
        this.showSummary = showSummary;
        showSummarySet = true;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        globalOnly = ((Boolean) values[1]).booleanValue();
        globalOnlySet = ((Boolean) values[2]).booleanValue();
        showDetail = ((Boolean) values[3]).booleanValue();
        showDetailSet = ((Boolean) values[4]).booleanValue();
        showSummary = ((Boolean) values[5]).booleanValue();
        showSummarySet = ((Boolean) values[6]).booleanValue();
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[7];
        values[0] = super.saveState(context);
        values[1] = ComponentUtil_.convertToBoolean(globalOnly);
        values[2] = ComponentUtil_.convertToBoolean(globalOnlySet);
        values[3] = ComponentUtil_.convertToBoolean(showDetail);
        values[4] = ComponentUtil_.convertToBoolean(showDetailSet);
        values[5] = ComponentUtil_.convertToBoolean(showSummary);
        values[6] = ComponentUtil_.convertToBoolean(showSummarySet);
        return values;
    }

}
