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
public class UIMessage extends UIComponentBase {

    public static final String COMPONENT_FAMILY = "javax.faces.Message";

    public static final String COMPONENT_TYPE = "javax.faces.Message";

    private static final String DEFAULT_RENDER_TYPE = "javax.faces.Message";

    private String for_ = null;

    private boolean showDetail_ = false;

    private boolean showDetailSet_ = false;

    private boolean showSummary_ = false;

    private boolean showSummarySet_ = false;

    public UIMessage() {
        super();
        setRendererType(DEFAULT_RENDER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getFor() {
        if (for_ != null) {
            return for_;
        }
        return (String) ComponentUtils_.getValueBindingValue(this, "for");
    }

    public void setFor(String newFor) {
        for_ = newFor;
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
        for_ = (String) values[1];
        showDetail_ = ComponentUtils_.convertToPrimitiveBoolean(values[2]);
        showDetailSet_ = ComponentUtils_.convertToPrimitiveBoolean(values[3]);
        showSummary_ = ComponentUtils_.convertToPrimitiveBoolean(values[4]);
        showSummarySet_ = ComponentUtils_.convertToPrimitiveBoolean(values[5]);
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[6];
        values[0] = super.saveState(context);
        values[1] = for_;
        values[2] = ComponentUtils_.convertToBoolean(showDetail_);
        values[3] = ComponentUtils_.convertToBoolean(showDetailSet_);
        values[4] = ComponentUtils_.convertToBoolean(showSummary_);
        values[5] = ComponentUtils_.convertToBoolean(showSummarySet_);
        return values;
    }
}
