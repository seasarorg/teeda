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
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreComponent;
import javax.faces.internal.UIComponentUtil;

import org.seasar.framework.util.BigDecimalConversionUtil;
import org.seasar.framework.util.NumberConversionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.html.HtmlInputTextRenderer;
import org.seasar.teeda.core.util.JavaScriptPermissionUtil;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.ScriptEnhanceUIViewRoot;
import org.seasar.teeda.extension.component.html.THtmlInputCommaText;
import org.seasar.teeda.extension.util.JavaScriptContext;

/**
 * @author shot
 * @author manhole
 */
public class THtmlInputCommaTextRenderer extends HtmlInputTextRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Input";

    public static final String RENDERER_TYPE = THtmlInputCommaText.DEFAULT_RENDERER_TYPE;

    private static final String DEFAULT_FRACTION = "0";

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        assertNotNull(context, component);
        if (!component.isRendered()) {
            return;
        }
        UIViewRoot root = context.getViewRoot();
        if (root instanceof ScriptEnhanceUIViewRoot
                && JavaScriptPermissionUtil.isJavaScriptPermitted(context)) {
            encodeHtmlInputCommaTextEnd(context,
                    (THtmlInputCommaText) component);
        } else {
            encodeHtmlInputTextEnd(context, (HtmlInputText) component);
        }
    }

    protected void encodeHtmlInputCommaTextEnd(FacesContext context,
            THtmlInputCommaText htmlInputCommaText) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        UIViewRoot root = context.getViewRoot();
        ScriptEnhanceUIViewRoot sRoot = (ScriptEnhanceUIViewRoot) root;
        final String scriptKey = THtmlInputCommaText.class.getName();
        if (!sRoot.containsScript(scriptKey)) {
            JavaScriptContext scriptContext = new JavaScriptContext();
            scriptContext.loadScript(scriptKey);
            sRoot.addScript(scriptKey, scriptContext);
            writer.write(sRoot.getAllScripts());
        }
        writer.startElement(JsfConstants.INPUT_ELEM, htmlInputCommaText);
        RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR,
                JsfConstants.TEXT_VALUE);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlInputCommaText,
                getIdForRender(context, htmlInputCommaText));
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR,
                htmlInputCommaText.getClientId(context));
        final String value = getValue(context, htmlInputCommaText);
        RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR, value);
        if (htmlInputCommaText.isDisabled()) {
            renderDisabledAttribute(writer);
        }
        final Locale locale = context.getViewRoot().getLocale();
        final String fraction = getFraction(htmlInputCommaText);
        final String groupingSeparator = getGroupingSeparator(
                htmlInputCommaText, locale);
        final String fractionSeparator = getFractionSeparator(
                htmlInputCommaText, locale);
        renderOnfocus(htmlInputCommaText, writer, groupingSeparator);
        renderOnblur(htmlInputCommaText, writer, fraction, groupingSeparator,
                fractionSeparator);
        renderOnkeydown(htmlInputCommaText, writer);
        renderOnkeypress(htmlInputCommaText, writer);
        renderOnkeyup(htmlInputCommaText, writer);
        renderStyle(htmlInputCommaText, writer);
        renderStyleClass(htmlInputCommaText, writer);

        renderRemain(htmlInputCommaText, writer);

        writer.endElement(JsfConstants.INPUT_ELEM);
        markJavaScriptRendererd(context, scriptKey);
    }

    private String getValue(FacesContext context,
            THtmlInputCommaText htmlInputCommaText) {
        final String value = ValueHolderUtil.getValueForRender(context,
                htmlInputCommaText);
        if (StringUtil.isEmpty(value)) {
            return value;
        }
        //TODO BigDecimal only?
        try {
            return convertToFormattedValue(context, BigDecimalConversionUtil
                    .toBigDecimal(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String getGroupingSeparator(THtmlInputCommaText htmlInputCommaText,
            final Locale locale) {
        String groupingSeparator = htmlInputCommaText.getGroupingSeparator();
        if (StringUtil.isEmpty(groupingSeparator)) {
            groupingSeparator = NumberConversionUtil
                    .findGroupingSeparator(locale);
        }
        return groupingSeparator;
    }

    private String getFractionSeparator(THtmlInputCommaText htmlInputCommaText,
            Locale locale) {
        String fractionSeparator = htmlInputCommaText.getFractionSeparator();
        if (StringUtil.isEmpty(fractionSeparator)) {
            fractionSeparator = NumberConversionUtil
                    .findDecimalSeparator(locale);
        }
        return fractionSeparator;
    }

    private String getFraction(THtmlInputCommaText htmlInputCommaText) {
        String fraction = htmlInputCommaText.getFraction();
        if (StringUtil.isEmpty(fraction)) {
            fraction = DEFAULT_FRACTION;
        }
        return fraction;
    }

    protected void markJavaScriptRendererd(FacesContext context,
            String scriptKey) {
        context.getExternalContext().getRequestMap().put(scriptKey, scriptKey);
    }

    protected void renderRemain(THtmlInputCommaText htmlInputCommaText,
            ResponseWriter writer) throws IOException {
        IgnoreComponent ignore = buildIgnoreComponent();
        Map map = UIComponentUtil.getAllAttributesAndProperties(
                htmlInputCommaText, ignore);
        for (final Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            String name = (String) entry.getKey();
            Object value = entry.getValue();
            RendererUtil.renderAttribute(writer, name, value, name);
        }
    }

    protected void renderStyle(THtmlInputCommaText htmlInputCommaText,
            ResponseWriter writer) throws IOException {
        String style = createStyleAttribute(htmlInputCommaText);
        if (StringUtil.isNotBlank(style)) {
            RendererUtil
                    .renderAttribute(writer, JsfConstants.STYLE_ATTR, style);
        }
    }

    protected String createStyleAttribute(THtmlInputCommaText htmlInputCommaText) {
        final String style = appendSemiColonIfNeed(htmlInputCommaText
                .getStyle());
        final String s = "ime-mode:disabled;";
        if (StringUtil.contains(style, s)) {
            return style;
        }
        return style + s;
    }

    protected void renderStyleClass(THtmlInputCommaText htmlInputCommaText,
            ResponseWriter writer) throws IOException {
        final String styleClass = createStyleClassAttribute(htmlInputCommaText);
        if (StringUtil.isNotBlank(styleClass)) {
            RendererUtil.renderAttribute(writer, JsfConstants.STYLE_CLASS_ATTR,
                    styleClass);
        }
    }

    protected String createStyleClassAttribute(
            THtmlInputCommaText htmlInputCommaText) {
        return htmlInputCommaText.getStyleClass();
    }

    protected void renderOnfocus(THtmlInputCommaText htmlInputCommaText,
            ResponseWriter writer, String groupingSeparator) throws IOException {
        String onfocus = appendSemiColonIfNeed(htmlInputCommaText.getOnfocus());
        String target = "removeComma(this, '" + groupingSeparator + "');";
        if (!onfocus.endsWith(target)) {
            onfocus = onfocus + target;
        }
        if (StringUtil.isNotBlank(onfocus)) {
            RendererUtil.renderAttribute(writer, JsfConstants.ONFOCUS_ATTR,
                    onfocus);
        }
    }

    protected void renderOnblur(THtmlInputCommaText htmlInputCommaText,
            ResponseWriter writer, String fraction, String groupingSeparator,
            String fractionSeparator) throws IOException {
        final String onblur = createOnblurAttribute(htmlInputCommaText,
                fraction, groupingSeparator, fractionSeparator);
        if (StringUtil.isNotBlank(onblur)) {
            RendererUtil.renderAttribute(writer, JsfConstants.ONBLUR_ATTR,
                    onblur);
        }
    }

    protected String createOnblurAttribute(
            THtmlInputCommaText htmlInputCommaText, String fraction,
            String groupingSeparator, String fractionSeparator) {
        final String onblur = appendSemiColonIfNeed(htmlInputCommaText
                .getOnblur());
        final String s = "convertByKey(this);addComma(this, '" + fraction
                + "', '" + groupingSeparator + "', '" + fractionSeparator
                + "');";
        if (StringUtil.contains(onblur, s)) {
            return onblur;
        }
        return onblur + s;
    }

    protected void renderOnkeydown(THtmlInputCommaText htmlInputCommaText,
            ResponseWriter writer) throws IOException {
        String onkeydown = appendSemiColonIfNeed(htmlInputCommaText
                .getOnkeydown());
        renderKeycheckEvent(writer, JsfConstants.ONKEYDOWN_ATTR, onkeydown);
    }

    protected void renderOnkeypress(THtmlInputCommaText htmlInputCommaText,
            ResponseWriter writer) throws IOException {
        String onkeypress = appendSemiColonIfNeed(htmlInputCommaText
                .getOnkeypress());
        renderKeycheckEvent(writer, JsfConstants.ONKEYPRESS_ATTR, onkeypress);
    }

    private void renderKeycheckEvent(ResponseWriter writer,
            String attributeName, String target) throws IOException {
        final String script = "return keycheckForNumber(event);";
        if (!target.endsWith(script)) {
            target = target + script;
            RendererUtil.renderAttribute(writer, attributeName, target);
        }
    }

    protected void renderOnkeyup(THtmlInputCommaText htmlInputCommaText,
            ResponseWriter writer) throws IOException {
        String onkeyup = appendSemiColonIfNeed(htmlInputCommaText.getOnkeyup());
        final String script = "convertByKey(this);";
        if (!onkeyup.endsWith(script)) {
            onkeyup = onkeyup + script;
            RendererUtil.renderAttribute(writer, JsfConstants.ONKEYUP_ATTR,
                    onkeyup);
        }
    }

    protected IgnoreComponent buildIgnoreComponent() {
        IgnoreComponent ignore = new IgnoreComponent();
        ignore.addIgnoreComponentName(JsfConstants.ID_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.TYPE_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.VALUE_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.STYLE_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.STYLE_CLASS_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.ONFOCUS_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.ONBLUR_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.ONKEYDOWN_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.ONKEYPRESS_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.ONKEYUP_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.DISABLED_ATTR);
        ignore.addIgnoreComponentName(ExtensionConstants.FRACTION_ATTR);
        ignore
                .addIgnoreComponentName(ExtensionConstants.GROUPING_SEPARATOR_ATTR);
        ignore
                .addIgnoreComponentName(ExtensionConstants.FRACTION_SEPARATOR_ATTR);
        return ignore;
    };

    private static String appendSemiColonIfNeed(String property) {
        if (property == null) {
            return "";
        }
        if (property.endsWith(";")) {
            return property;
        }
        return property + ";";
    }

    //TODO move to S2 util.
    private static String convertToFormattedValue(FacesContext context,
            Object value) {
        Locale locale = context.getViewRoot().getLocale();
        NumberFormat format = NumberFormat.getInstance(locale);
        String n = format.format(value);
        return n;
    }

}
