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
package org.seasar.teeda.extension.render.html;

import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreComponent;
import javax.faces.internal.UIComponentUtil;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.AbstractRenderer;
import org.seasar.teeda.core.util.FacesContextUtil;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlOutputLabel;
import org.seasar.teeda.extension.util.HotdeployResourceBundle;
import org.seasar.teeda.extension.util.MessageResourceBundle;

/**
 * @author shot
 */
public class THtmlOutputLabelRenderer extends AbstractRenderer {

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.Label";

    public static final String COMPONENT_FAMILY = "javax.faces.Output";

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
        renderRemain(label, writer);
        final Locale locale = getCalculatedLocale(context);
        String value = null;
        final String key = label.getKey();
        final String propertiesName = label.getPropertiesName();
        final String defaultKey = label.getDefaultKey();
        if (propertiesName != null) {
            MessageResourceBundle bundle = HotdeployResourceBundle.getBundle(
                    propertiesName, locale);
            value = (String) bundle.get(key);
            if (value == null) {
                value = (String) bundle.get(defaultKey);
            }
        }
        if (value == null) {
            final String defaultPropertiesName = label
                    .getDefaultPropertiesName();
            if (defaultPropertiesName != null) {
                MessageResourceBundle bundle = HotdeployResourceBundle
                        .getBundle(defaultPropertiesName, locale);
                value = (String) bundle.get(defaultKey);
            }
        }
        if (value != null) {
            writer.writeText(value, null);
        }
    }

    //TODO cache for locale is needed.
    protected Locale getCalculatedLocale(FacesContext context) {
        ViewHandler viewHandler = FacesContextUtil.getViewHandler(context);
        return viewHandler.calculateLocale(context);
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

    protected void renderRemain(THtmlOutputLabel label, ResponseWriter writer)
            throws IOException {
        IgnoreComponent ignore = buildIgnoreComponent();
        Map map = UIComponentUtil.getAllAttributesAndProperties(label, ignore);
        for (final Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            String name = (String) entry.getKey();
            Object value = entry.getValue();
            RendererUtil.renderAttribute(writer, name, value, name);
        }
    }

    protected IgnoreComponent buildIgnoreComponent() {
        IgnoreComponent ignore = new IgnoreComponent();
        ignore.addIgnoreComponentName(JsfConstants.FOR_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.ID_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.VALUE_ATTR);
        ignore.addIgnoreComponentName(ExtensionConstants.KEY_ATTR);
        ignore.addIgnoreComponentName(ExtensionConstants.PROPERTIES_NAME_ATTR);
        ignore.addIgnoreComponentName(ExtensionConstants.PROPERTIES_NAME_ATTR);
        ignore.addIgnoreComponentName(ExtensionConstants.DEFAULT_KEY);
        ignore
                .addIgnoreComponentName(ExtensionConstants.DEFAULT_PROPERTIES_NAME_ATTR);
        return ignore;
    }
}
