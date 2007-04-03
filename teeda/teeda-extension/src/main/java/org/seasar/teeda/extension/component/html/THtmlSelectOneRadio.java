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
package org.seasar.teeda.extension.component.html;

import javax.faces.component.ComponentUtil_;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;

import org.seasar.teeda.extension.ExtensionConstants;

/**
 * @author shot
 */
public class THtmlSelectOneRadio extends HtmlSelectOneRadio {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlSelectOneRadio";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.HtmlSelectOneRadio";

    private Integer col = null;

    public Object saveState(final FacesContext context) {
        final Object[] values = new Object[2];
        values[0] = super.saveState(context);
        values[1] = col;
        return values;
    }

    public void restoreState(final FacesContext context, final Object state) {
        final Object[] values = (Object[]) state;
        super.restoreState(context, values[0]);
        col = (Integer) values[1];
    }

    public Integer getCol() {
        if (col != null) {
            return col;
        }
        Object value = ComponentUtil_.getValueBindingValue(this,
                ExtensionConstants.COL_ATTR);
        return (value != null) ? (Integer) value : null;

    }

    public void setCol(Integer col) {
        this.col = col;
    }

}
