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

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreComponent;
import javax.faces.internal.UIComponentUtil;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.NumberConversionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.html.HtmlInputTextRenderer;
import org.seasar.teeda.core.util.JavaScriptPermissionUtil;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;
import org.seasar.teeda.extension.component.HtmlInputCommaText;
import org.seasar.teeda.extension.component.ScriptEnhanceUIViewRoot;
import org.seasar.teeda.extension.util.JavaScriptContext;

/**
 * @author shot
 *
 */
public class HtmlInputCommaTextRenderer extends HtmlInputTextRenderer {

    private static final String DEFAULT_FRACTION = "0";

    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        AssertionUtil.assertNotNull("FacesContext", context);
        AssertionUtil.assertNotNull("UIComponent", component);
        if (!component.isRendered()) {
            return;
        }
        UIViewRoot root = context.getViewRoot();
        if (root instanceof ScriptEnhanceUIViewRoot
                && JavaScriptPermissionUtil.isJavaScriptPermitted(context)) {
            encodeHtmlInputCommaTextEnd(context, (HtmlInputCommaText) component);
        } else {
            encodeHtmlInputTextEnd(context, (HtmlInputText) component);
        }
    }

    protected void encodeHtmlInputCommaTextEnd(FacesContext context,
            HtmlInputCommaText htmlInputCommaText) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        UIViewRoot root = context.getViewRoot();
        ScriptEnhanceUIViewRoot sRoot = (ScriptEnhanceUIViewRoot) root;
        String scriptPath = htmlInputCommaText.getClass().getName();
        if (!sRoot.containsScript(scriptPath)) {
            JavaScriptContext scriptContext = new JavaScriptContext();
            scriptContext.loadScript(scriptPath);
            sRoot.addScript(scriptPath, scriptContext);
            writer.write(sRoot.getAllScripts());
        }

        writer.startElement(JsfConstants.INPUT_ELEM, htmlInputCommaText);
        RendererUtil.renderAttribute(writer, JsfConstants.TYPE_ATTR,
                JsfConstants.TEXT_VALUE);
        RendererUtil.renderIdAttributeIfNecessary(writer, htmlInputCommaText,
                getIdForRender(context, htmlInputCommaText));
        RendererUtil.renderAttribute(writer, JsfConstants.NAME_ATTR,
                htmlInputCommaText.getClientId(context));

        String value = ValueHolderUtil.getValueForRender(context,
                htmlInputCommaText);
        RendererUtil.renderAttribute(writer, JsfConstants.VALUE_ATTR, value);
        if (htmlInputCommaText.isDisabled()) {
            RendererUtil.renderDisabledAttribute(writer);
        }

        Locale locale = context.getViewRoot().getLocale();

        String fraction = htmlInputCommaText.getFraction();
        if (StringUtil.isEmpty(fraction)) {
            fraction = DEFAULT_FRACTION;
        }

        String groupingSeparator = htmlInputCommaText.getGroupingSeparator();
        if (StringUtil.isEmpty(groupingSeparator)) {
            groupingSeparator = NumberConversionUtil
                    .findGroupingSeparator(locale);
        }
        String fractionSeparator = htmlInputCommaText.getFractionSeparator();
        if (StringUtil.isEmpty(fractionSeparator)) {
            fractionSeparator = NumberConversionUtil
                    .findDecimalSeparator(locale);
        }
        renderOnfocus(htmlInputCommaText, writer, groupingSeparator);
        renderOnblur(htmlInputCommaText, writer, fraction, groupingSeparator,
                fractionSeparator);
        renderOnkeydown(htmlInputCommaText, writer);
        renderOnkeypress(htmlInputCommaText, writer);
        renderOnkeyup(htmlInputCommaText, writer);
        renderStyle(htmlInputCommaText, writer);

        renderRemain(htmlInputCommaText, writer);

        writer.endElement(JsfConstants.INPUT_ELEM);
    }

    protected void renderRemain(HtmlInputCommaText htmlInputCommaText,
            ResponseWriter writer) throws IOException {
        IgnoreComponent ignore = buildIgnoreComponent();
        Map map = UIComponentUtil.getAllAttributesAndProperties(
                htmlInputCommaText, ignore);
        for (Iterator itr = map.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Map.Entry) itr.next();
            String name = (String) entry.getKey();
            Object value = entry.getValue();
            RendererUtil.renderAttribute(writer, name, value, name);
        }
    }

    protected void renderStyle(HtmlInputCommaText htmlInputCommaText,
            ResponseWriter writer) throws IOException {
        String style = appendSemiColonIfNeed(htmlInputCommaText.getStyle());
        if (!style.endsWith("ime-mode:disabled;")) {
            style = style + "ime-mode:disabled;";
            RendererUtil
                    .renderAttribute(writer, JsfConstants.STYLE_ATTR, style);
        }
    }

    protected void renderOnfocus(HtmlInputCommaText htmlInputCommaText,
            ResponseWriter writer, String groupingSeparator) throws IOException {
        String onfocus = appendSemiColonIfNeed(htmlInputCommaText.getOnfocus());
        if (!onfocus.endsWith("removeComma(this);")) {
            onfocus = onfocus + "removeComma(this);";
            RendererUtil.renderAttribute(writer, JsfConstants.ONFOCUS_ATTR,
                    onfocus);
        }
    }

    protected void renderOnblur(HtmlInputCommaText htmlInputCommaText,
            ResponseWriter writer, String fraction, String groupingSeparator,
            String fractionSeparator) throws IOException {
        String onblur = appendSemiColonIfNeed(htmlInputCommaText.getOnblur());
        if (!onblur.endsWith("convertByKey(this);addComma(this, '" + fraction
                + "', '" + groupingSeparator + "', '" + fractionSeparator
                + "');")) {
            onblur = onblur + "convertByKey(this);addComma(this, '" + fraction
                    + "', '" + groupingSeparator + "', '" + fractionSeparator
                    + "');";
            RendererUtil.renderAttribute(writer, JsfConstants.ONBLUR_ATTR,
                    onblur);
        }
    }

    protected void renderOnkeydown(HtmlInputCommaText htmlInputCommaText,
            ResponseWriter writer) throws IOException {
        String onkeydown = appendSemiColonIfNeed(htmlInputCommaText
                .getOnkeydown());
        renderKeycheckEvent(writer, JsfConstants.ONKEYDOWN_ATTR, onkeydown);
    }

    protected void renderOnkeypress(HtmlInputCommaText htmlInputCommaText,
            ResponseWriter writer) throws IOException {
        String onkeypress = appendSemiColonIfNeed(htmlInputCommaText
                .getOnkeypress());
        renderKeycheckEvent(writer, JsfConstants.ONKEYPRESS_ATTR, onkeypress);
    }

    private void renderKeycheckEvent(ResponseWriter writer,
            String attributeName, String target) throws IOException {
        if (!target.endsWith("return keycheckForNumber(event);")) {
            target = target + "return keycheckForNumber(event);";
            RendererUtil.renderAttribute(writer, attributeName, target);
        }
    }

    protected void renderOnkeyup(HtmlInputCommaText htmlInputCommaText,
            ResponseWriter writer) throws IOException {
        String onkeyup = appendSemiColonIfNeed(htmlInputCommaText.getOnkeyup());
        if (!onkeyup.endsWith("convertByKey(this);")) {
            onkeyup = onkeyup + "convertByKey(this);";
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
        ignore.addIgnoreComponentName(JsfConstants.ONFOCUS_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.ONBLUR_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.ONKEYDOWN_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.ONKEYPRESS_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.ONKEYUP_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.DISABLED_ATTR);
        ignore.addIgnoreComponentName(JsfConstants.FRACTION_ATTR);
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

}
