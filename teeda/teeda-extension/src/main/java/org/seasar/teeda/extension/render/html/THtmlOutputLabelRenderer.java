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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreAttribute;
import javax.faces.internal.LabelUtil;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlOutputLabel;

/**
 * @author shot
 */
public class THtmlOutputLabelRenderer extends AbstractRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Output";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.Label";

    private static final IgnoreAttribute IGNORE_COMPONENT;

    static {
        IGNORE_COMPONENT = buildIgnoreComponent();
    }

    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeTHtmlOutputLabelBegin(context, (THtmlOutputLabel) component);
    }

    protected void encodeTHtmlOutputLabelBegin(FacesContext context,
            THtmlOutputLabel label) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.LABEL_ELEM, label);
        RendererUtil.renderIdAttributeIfNecessary(writer, label,
                getIdForRender(context, label));
        String forAttr = label.getFor();
        RendererUtil.renderAttribute(writer, JsfConstants.FOR_ATTR, forAttr,
                null);
        renderRemainAttributes(label, writer, IGNORE_COMPONENT);
        String value = ValueHolderUtil.getValueForRender(context, label);
        if (StringUtil.isEmpty(value)) {
            final String key = label.getKey();
            final String propertiesName = label.getPropertiesName();
            final String defaultKey = label.getDefaultKey();
            final String defaultPropertiesName = label
                    .getDefaultPropertiesName();
            value = LabelUtil.getLabelValue(key, propertiesName, defaultKey,
                    defaultPropertiesName);
        }
        if (value != null) {
            writer.writeText(value, null);
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeTHtmlOutputLabelEnd(context, (THtmlOutputLabel) component);
    }

    protected void encodeTHtmlOutputLabelEnd(FacesContext context,
            THtmlOutputLabel label) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement(JsfConstants.LABEL_ELEM);
    }

    protected static IgnoreAttribute buildIgnoreComponent() {
        IgnoreAttribute ignore = new IgnoreAttribute();
        ignore.addAttributeName(JsfConstants.FOR_ATTR);
        ignore.addAttributeName(JsfConstants.ID_ATTR);
        ignore.addAttributeName(JsfConstants.VALUE_ATTR);
        ignore.addAttributeName(ExtensionConstants.KEY_ATTR);
        ignore.addAttributeName(ExtensionConstants.PROPERTIES_NAME_ATTR);
        ignore.addAttributeName(ExtensionConstants.PROPERTIES_NAME_ATTR);
        ignore.addAttributeName(ExtensionConstants.DEFAULT_KEY);
        ignore
                .addAttributeName(ExtensionConstants.DEFAULT_PROPERTIES_NAME_ATTR);
        return ignore;
    }
}
