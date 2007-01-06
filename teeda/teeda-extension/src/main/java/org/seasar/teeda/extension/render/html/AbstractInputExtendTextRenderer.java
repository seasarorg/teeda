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
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreAttribute;

import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.JavaScriptPermissionUtil;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.ScriptEnhanceUIViewRoot;
import org.seasar.teeda.extension.component.html.THtmlInputText;
import org.seasar.teeda.extension.render.RenderPreparableRenderer;
import org.seasar.teeda.extension.util.VirtualResource;

/**
 * @author shot
 */
public abstract class AbstractInputExtendTextRenderer extends
        THtmlInputTextRenderer implements RenderPreparableRenderer {

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        UIViewRoot root = context.getViewRoot();
        if (root instanceof ScriptEnhanceUIViewRoot
                && JavaScriptPermissionUtil.isJavaScriptPermitted(context)) {
            THtmlInputText input = (THtmlInputText) component;
            encodeInputExtendTextEnd(context, input);
        } else {
            encodeHtmlInputTextEnd(context, (HtmlInputText) component);
        }
    }

    public void encodePrepare(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        encodeHtmlHtmlInputTextPrepare(context, (THtmlInputText) component);
    }

    protected void encodeHtmlHtmlInputTextPrepare(final FacesContext context,
            final THtmlInputText htmlGrid) throws IOException {
        String path = ResourceUtil.getResourcePath(getScriptKey(), "js");
        VirtualResource.addJSResource(context, path);
    }

    protected void encodeInputExtendTextEnd(FacesContext context,
            THtmlInputText htmlInputText) throws IOException {
        doEncodeEndStart(context, htmlInputText);
        doEncodeEndCustomize(context, htmlInputText);
        doEncodeEndEnd(context, htmlInputText);
    }

    protected void doEncodeEndStart(FacesContext context,
            THtmlInputText htmlInputText) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement(JsfConstants.INPUT_ELEM, htmlInputText);
        RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR,
                JsfConstants.TEXT_VALUE);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlInputText,
                getIdForRender(context, htmlInputText));
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR,
                htmlInputText.getClientId(context));
        final String value = getValue(context, htmlInputText);
        RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR, value);
        if (htmlInputText.isDisabled()) {
            renderDisabledAttribute(writer);
        }
    }

    protected abstract void doEncodeEndCustomize(FacesContext context,
            THtmlInputText htmlInputText) throws IOException;

    protected void doEncodeEndEnd(FacesContext context,
            THtmlInputText htmlInputText) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        renderRemain(htmlInputText, writer);
        writer.endElement(JsfConstants.INPUT_ELEM);
    }

    protected abstract String getScriptKey();

    protected String getValue(FacesContext context, UIComponent component) {
        return ValueHolderUtil.getValueForRender(context, component);

    }

    protected void renderRemain(THtmlInputText htmlInputText,
            ResponseWriter writer) throws IOException {
        IgnoreAttribute ignore = buildIgnoreComponent();
        renderRemainAttributes(htmlInputText, writer, ignore);
    }

    protected IgnoreAttribute buildIgnoreComponent() {
        IgnoreAttribute ignore = new IgnoreAttribute();
        ignore.addAttributeName(JsfConstants.ID_ATTR);
        ignore.addAttributeName(JsfConstants.TYPE_ATTR);
        ignore.addAttributeName(JsfConstants.VALUE_ATTR);
        ignore.addAttributeName(ExtensionConstants.ERROR_STYLE_CLASS);
        return ignore;
    };

    protected static String appendSemiColonIfNeed(String property) {
        if (StringUtil.isEmpty(property)) {
            return "";
        }
        if (property.endsWith(";")) {
            return property;
        }
        return property + ";";
    }

}
