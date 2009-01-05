/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.BindingUtil;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.component.TCondition;

/**
 * @author shot
 */
public class TConditionRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Condition";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.Condition";

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeBegin(context, component);
        if (!component.isRendered()) {
            return;
        }
        TCondition condition = (TCondition) component;
        final boolean invisible = condition.isInvisible();
        final boolean omittag = condition.isOmittag();
        if (!(invisible || omittag)) {
            final ResponseWriter writer = context.getResponseWriter();
            writer.startElement(condition.getTagName(), condition);
            RendererUtil.renderIdAttributeIfNecessary(writer, component,
                    getIdForRender(context, condition));
            renderAttributes(writer, condition);
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        super.encodeEnd(context, component);
        if (!component.isRendered()) {
            return;
        }
        TCondition condition = (TCondition) component;
        final boolean invisible = condition.isInvisible();
        final boolean omittag = condition.isOmittag();
        if (!(invisible || omittag)) {
            final ResponseWriter writer = context.getResponseWriter();
            writer.endElement(condition.getTagName());
        }
    }

    public boolean getRendersChildren() {
        return true;
    }

    protected void renderAttributes(ResponseWriter writer, TCondition component)
            throws IOException {
        Map attrs = component.getAttributes();
        for (Iterator i = attrs.keySet().iterator(); i.hasNext();) {
            String attrName = (String) i.next();
            if (attrName.indexOf('.') > 0) {
                continue;
            }
            Object value = component.getAttributes().get(attrName);
            RendererUtil.renderAttribute(writer, attrName, value, attrName);
        }
        String[] bindingPropertyNames = component.getBindingPropertyNames();
        for (int i = 0; i < bindingPropertyNames.length; ++i) {
            String name = bindingPropertyNames[i];
            Object value = BindingUtil.getBindingValue(component, name);
            RendererUtil.renderAttribute(writer, name, value, name);
        }
    }

}
