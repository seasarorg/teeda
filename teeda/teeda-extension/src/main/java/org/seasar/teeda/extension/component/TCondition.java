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

import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.extension.ExtensionConstants;

/**
 * @author shot
 */
public class TCondition extends UIComponentBase {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.Condition";

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Condition";

    private static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.Condition";

    public TCondition() {
        super.setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public void processDecodes(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        Object rendered = null;
        for (Iterator itr = getChildren().iterator(); itr.hasNext();) {
            UIComponent c = (UIComponent) itr.next();
            if (c instanceof HtmlInputHidden) {
                HtmlInputHidden hidden = (HtmlInputHidden) c;
                String hiddenId = c.getId();
                if (hiddenId.equals(getId()
                        + ExtensionConstants.TEEDA_HIDDEN_SUFFIX)) {
                    hidden.decode(context);
                    rendered = hidden.getSubmittedValue();
                    break;
                }
            }
        }
        if ("true".equals(rendered)) {
            for (Iterator children = getFacetsAndChildren(); children.hasNext();) {
                UIComponent component = (UIComponent) children.next();
                component.processDecodes(context);
            }
        }
    }

}
