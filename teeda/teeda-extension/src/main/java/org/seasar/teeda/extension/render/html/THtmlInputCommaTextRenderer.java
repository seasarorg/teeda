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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.IgnoreComponent;

import org.seasar.framework.util.BigDecimalConversionUtil;
import org.seasar.framework.util.NumberConversionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlInputCommaText;

/**
 * @author shot
 * @author manhole
 */
public class THtmlInputCommaTextRenderer extends
        AbstractInputExtendTextRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Input";

    public static final String RENDERER_TYPE = THtmlInputCommaText.DEFAULT_RENDERER_TYPE;

    private static final String DEFAULT_FRACTION = "0";

    private static final String JS_NAMESPACE_PREFIX = "Teeda.THtmlInputCommaText.";

    protected void doEncodeEndCustomize(FacesContext context,
            HtmlInputText htmlInputText) throws IOException {
        assertHtmlInputCommaText(htmlInputText);
        THtmlInputCommaText htmlInputCommaText = (THtmlInputCommaText) htmlInputText;
        ResponseWriter writer = context.getResponseWriter();
        final Locale locale = context.getViewRoot().getLocale();
        final String fraction = getFraction(htmlInputCommaText);
        final String groupingSeparator = getGroupingSeparator(
                htmlInputCommaText, locale);
        final String fractionSeparator = getFractionSeparator(
                htmlInputCommaText, locale);
        renderOnfocus(htmlInputCommaText, writer, groupingSeparator);
        renderOnblur(htmlInputCommaText, writer, fraction, groupingSeparator,
                fractionSeparator);
        renderOnkeydown(htmlInputCommaText, writer, fraction, fractionSeparator);
        renderOnkeypress(htmlInputCommaText, writer, fraction,
                fractionSeparator);
        renderOnkeyup(htmlInputCommaText, writer);
        renderStyle(htmlInputCommaText, writer);
        renderStyleClass(htmlInputCommaText, writer);
    }

    protected static void assertHtmlInputCommaText(HtmlInputText htmlInputText) {
        if (!(htmlInputText instanceof THtmlInputCommaText)) {
            throw new IllegalStateException();
        }
    }

    protected String getValue(FacesContext context, UIComponent component) {
        final String value = ValueHolderUtil.getValueForRender(context,
                component);
        if (StringUtil.isEmpty(value)) {
            return value;
        }
        THtmlInputCommaText htmlInputCommaText = (THtmlInputCommaText) component;
        try {
            final Locale locale = context.getViewRoot().getLocale();
            final String fraction = getFractionSeparator(htmlInputCommaText, locale);
            final DecimalFormat df = new DecimalFormat();
            int pos = value.indexOf(fraction);
            if (pos < 0) {
                BigDecimal bd = BigDecimalConversionUtil.toBigDecimal(value);
                return df.format(bd);
            } else {
                String intPart = value.substring(0, pos);
                String fractPart = value.substring(pos);
                BigDecimal bd = BigDecimalConversionUtil.toBigDecimal(intPart);
                String s = df.format(bd);
                return s + fractPart;
            }
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
        String target = JS_NAMESPACE_PREFIX + "removeComma(this, '"
                + groupingSeparator + "');";
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
        final String s = JS_NAMESPACE_PREFIX + "convertByKey(this);"
                + JS_NAMESPACE_PREFIX + "addComma(this, " + fraction + ", '"
                + groupingSeparator + "', '" + fractionSeparator + "');";
        if (StringUtil.contains(onblur, s)) {
            return onblur;
        }
        return onblur + s;
    }

    protected void renderOnkeydown(THtmlInputCommaText htmlInputCommaText,
            ResponseWriter writer, String fraction, String fractionSeparator)
            throws IOException {
        String onkeydown = appendSemiColonIfNeed(htmlInputCommaText
                .getOnkeydown());
        renderKeycheckEvent(writer, JsfConstants.ONKEYDOWN_ATTR, onkeydown,
                fraction, fractionSeparator);
    }

    protected void renderOnkeypress(THtmlInputCommaText htmlInputCommaText,
            ResponseWriter writer, String fraction, String fractionSeparator)
            throws IOException {
        String onkeypress = appendSemiColonIfNeed(htmlInputCommaText
                .getOnkeypress());
        renderKeycheckEvent(writer, JsfConstants.ONKEYPRESS_ATTR, onkeypress,
                fraction, fractionSeparator);
    }

    private void renderKeycheckEvent(ResponseWriter writer,
            String attributeName, String target, String fraction,
            String fractionSeparator) throws IOException {
        final String script = "return " + JS_NAMESPACE_PREFIX
                + "keycheckForNumber(event, this, " + fraction + ", '"
                + fractionSeparator + "');";
        if (!target.endsWith(script)) {
            target = target + script;
            RendererUtil.renderAttribute(writer, attributeName, target);
        }
    }

    protected void renderOnkeyup(THtmlInputCommaText htmlInputCommaText,
            ResponseWriter writer) throws IOException {
        String onkeyup = appendSemiColonIfNeed(htmlInputCommaText.getOnkeyup());
        final String script = JS_NAMESPACE_PREFIX + "convertByKey(this);";
        if (!onkeyup.endsWith(script)) {
            onkeyup = onkeyup + script;
            RendererUtil.renderAttribute(writer, JsfConstants.ONKEYUP_ATTR,
                    onkeyup);
        }
    }

    protected IgnoreComponent buildIgnoreComponent() {
        IgnoreComponent ignore = super.buildIgnoreComponent();
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

    protected String getScriptKey() {
        return THtmlInputCommaText.class.getName();
    }

}
