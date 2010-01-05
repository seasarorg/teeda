/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
 * @author manhole
 */
public class UIMessage extends UIComponentBase {

    public static final String COMPONENT_FAMILY = "javax.faces.Message";

    public static final String COMPONENT_TYPE = "javax.faces.Message";

    private static final String DEFAULT_RENDER_TYPE = "javax.faces.Message";

    private String forTarget = null;

    private boolean showDetail = true;

    private boolean showDetailSet = false;

    private boolean showSummary = false;

    private boolean showSummarySet = false;

    public UIMessage() {
        setRendererType(DEFAULT_RENDER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public String getFor() {
        if (forTarget != null) {
            return forTarget;
        }
        return (String) ComponentUtil_.getValueBindingValue(this, "for");
    }

    public void setFor(String newFor) {
        forTarget = newFor;
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
        forTarget = (String) values[1];
        showDetail = ComponentUtil_.convertToPrimitiveBoolean(values[2]);
        showDetailSet = ComponentUtil_.convertToPrimitiveBoolean(values[3]);
        showSummary = ComponentUtil_.convertToPrimitiveBoolean(values[4]);
        showSummarySet = ComponentUtil_.convertToPrimitiveBoolean(values[5]);
    }

    public Object saveState(FacesContext context) {
        Object[] values = new Object[6];
        values[0] = super.saveState(context);
        values[1] = forTarget;
        values[2] = ComponentUtil_.convertToBoolean(showDetail);
        values[3] = ComponentUtil_.convertToBoolean(showDetailSet);
        values[4] = ComponentUtil_.convertToBoolean(showSummary);
        values[5] = ComponentUtil_.convertToBoolean(showSummarySet);
        return values;
    }

}
