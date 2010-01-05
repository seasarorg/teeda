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
package org.seasar.teeda.extension.component.html;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.context.FacesContext;

import org.seasar.teeda.extension.component.TViewRoot;

/**
 * @author koichik
 * @since 1.0.12
 */
public class THtmlGraphicImage extends HtmlGraphicImage {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.HtmlGraphicImage";

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.HtmlGraphicImage";

    public static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.HtmlGraphicImage";

    private String baseViewId;

    public THtmlGraphicImage() {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public void setParent(UIComponent parent) {
        super.setParent(parent);
        if (baseViewId == null) {
            while (parent != null) {
                if (parent instanceof TViewRoot) {
                    baseViewId = ((TViewRoot) parent).getViewId();
                    break;
                }
                parent = parent.getParent();
            }
        }
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        baseViewId = (String) values[1];
    }

    public Object saveState(FacesContext context) {
        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = baseViewId;
        return values;
    }

    public String getBaseViewId() {
        return baseViewId;
    }

    public void setBaseViewId(String baseViewId) {
        this.baseViewId = baseViewId;
    }

}
