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

/**
 * @author shot
 */
public class UIMessages extends UIComponentBase {

    public static final String COMPONENT_FAMILY = "javax.faces.Messages";

    public static final String COMPONENT_TYPE = "javax.faces.Messages";

    private static final String DEFAULT_RENDER_TYPE = "javax.faces.Messages";

    private boolean globalOnly_ = false;

    private boolean globalOnlySet_ = false;

    private boolean showDetail_ = false;

    private boolean showDetailSet_ = false;

    private boolean showSummary_ = true;

    private boolean showSummarySet_ = false;

    public UIMessages() {
        setRendererType(DEFAULT_RENDER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public boolean isGlobalOnly() {
        if (globalOnlySet_) {
            return globalOnly_;
        }
        Object value = ComponentUtils_.getValueBindingValue(this, "globalOnly");
        return (value != null) ? Boolean.TRUE.equals(value) : globalOnly_;
    }

    public void setGlobalOnly(boolean isGlobalOnly) {
        globalOnly_ = isGlobalOnly;
        globalOnlySet_ = true;
    }

    public boolean isShowDetail() {
        if (showDetailSet_) {
            return showDetail_;
        }
        Object value = ComponentUtils_.getValueBindingValue(this, "showDetail");
        return (value != null) ? Boolean.TRUE.equals(value) : showDetail_;
    }

    public void setShowDetail(boolean isShowDetail) {
        showDetail_ = isShowDetail;
        showDetailSet_ = true;
    }

    public boolean isShowSummary() {
        if (showSummarySet_) {
            return showSummary_;
        }
        Object value = ComponentUtils_
                .getValueBindingValue(this, "showSummary");
        return (value != null) ? Boolean.TRUE.equals(value) : showSummary_;
    }

    public void setShowSummary(boolean showSummary) {
        showSummary_ = showSummary;
        showSummarySet_ = true;
    }

    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        globalOnly_ = ((Boolean) values[1]).booleanValue();
        globalOnlySet_ = ((Boolean) values[2]).booleanValue();
        showDetail_ = ((Boolean) values[3]).booleanValue();
        showDetailSet_ = ((Boolean) values[4]).booleanValue();
        showSummary_ = ((Boolean) values[5]).booleanValue();
        showSummarySet_ = ((Boolean) values[6]).booleanValue();
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[7];
        values[0] = super.saveState(context);
        values[1] = ComponentUtils_.convertToBoolean(globalOnly_);
        values[2] = ComponentUtils_.convertToBoolean(globalOnlySet_);
        values[3] = ComponentUtils_.convertToBoolean(showDetail_);
        values[4] = ComponentUtils_.convertToBoolean(showDetailSet_);
        values[5] = ComponentUtils_.convertToBoolean(showSummary_);
        values[6] = ComponentUtils_.convertToBoolean(showSummarySet_);
        return values;
    }

}
