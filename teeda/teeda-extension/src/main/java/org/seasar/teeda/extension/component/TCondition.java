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
import javax.faces.context.FacesContext;

import org.seasar.framework.util.AssertionUtil;

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
        UIComponent component = null;
        for (Iterator itr = getFacetsAndChildren(); itr.hasNext();) {
            component = (UIComponent) itr.next();
            component.processDecodes(context);
        }
        try {
            decode(context);
        } catch (RuntimeException e) {
            context.renderResponse();
            throw e;
        }
    }

    public void processValidators(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        UIComponent component = null;
        for (Iterator itr = getFacetsAndChildren(); itr.hasNext();) {
            component = (UIComponent) itr.next();
            component.processValidators(context);
        }
    }

    public void processUpdates(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        UIComponent component = null;
        for (Iterator itr = getFacetsAndChildren(); itr.hasNext();) {
            component = (UIComponent) itr.next();
            component.processUpdates(context);
        }
    }

}
