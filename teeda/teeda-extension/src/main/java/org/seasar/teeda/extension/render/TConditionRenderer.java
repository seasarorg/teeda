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
package org.seasar.teeda.extension.render;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreAttribute;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.TCondition;

/**
 * @author shot
 */
public class TConditionRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Condition";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.Condition";

    private final IgnoreAttribute attribute = new IgnoreAttribute();
    {
        attribute.addAttributeName(JsfConstants.ID_ATTR);
        attribute.addAttributeName("submitted");
    }

    public void decode(FacesContext context, UIComponent component) {
        assertNotNull(context, component);
        decodeTCondition(context, (TCondition) component);
    }

    protected void decodeTCondition(FacesContext context, TCondition condition) {
        Object submitted = null;
        for (Iterator itr = condition.getChildren().iterator(); itr.hasNext();) {
            UIComponent c = (UIComponent) itr.next();
            if (c instanceof HtmlInputHidden) {
                HtmlInputHidden hidden = (HtmlInputHidden) c;
                String hiddenId = c.getId();
                if (hiddenId.equals(condition.getId()
                        + ExtensionConstants.TEEDA_HIDDEN_SUFFIX)) {
                    hidden.decode(context);
                    submitted = hidden.getSubmittedValue();
                    break;
                }
            }
        }
        if ("true".equals(submitted)) {
            condition.setSubmitted(true);
        } else {
            condition.setSubmitted(false);
        }
    }

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeBegin(context, component);
        boolean rendered = component.isRendered();

        for (Iterator itr = component.getChildren().iterator(); itr.hasNext();) {
            UIComponent c = (UIComponent) itr.next();
            if (c instanceof HtmlInputHidden) {
                String hiddenId = c.getId();
                if (hiddenId.equals(component.getId()
                        + ExtensionConstants.TEEDA_HIDDEN_SUFFIX)) {
                    ((HtmlInputHidden) c).setValue(new Boolean(rendered));
                    break;
                }
            }
        }
        if (!rendered) {
            return;
        }
        TCondition condition = (TCondition) component;
        final ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.DIV_ELEM, condition);
        RendererUtil.renderIdAttributeIfNecessary(writer, component,
                getIdForRender(context, condition));
        renderRemainAttributes(condition, writer, attribute);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
        if (!component.isRendered()) {
            return;
        }
        final ResponseWriter writer = context.getResponseWriter();
        writer.endElement(JsfConstants.DIV_ELEM);
    }

    public boolean getRendersChildren() {
        return true;
    }

}
