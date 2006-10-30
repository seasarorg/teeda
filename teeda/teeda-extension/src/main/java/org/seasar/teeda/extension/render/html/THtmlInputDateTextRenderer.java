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
import java.util.Locale;

import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.internal.FacesMessageUtil;
import javax.faces.internal.IgnoreComponent;

import org.seasar.framework.util.DateConversionUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.util.RendererUtil;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlInputDateText;
import org.seasar.teeda.extension.component.html.THtmlInputText;

/**
 * @author shot
 */
public class THtmlInputDateTextRenderer extends AbstractInputExtendTextRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Input";

    public static final String RENDERER_TYPE = THtmlInputDateText.DEFAULT_RENDERER_TYPE;

    private static final String DEFAULT_THRESHOLD = "71";

    private static final String JS_NAMESPACE_PREFIX = "Teeda.THtmlInputDateText.";

    protected void doEncodeEndCustomize(FacesContext context,
            THtmlInputText htmlInputText) throws IOException {
        assertHtmlInputDateText(htmlInputText);
        THtmlInputDateText htmlInputDateText = (THtmlInputDateText) htmlInputText;
        ResponseWriter writer = context.getResponseWriter();
        String pattern = getPattern(context, htmlInputDateText);
        String length = htmlInputDateText.getLength();
        if (length == null) {
            length = calculateLength(pattern);
        }
        String threshold = htmlInputDateText.getThreshold();
        if (threshold == null) {
            threshold = DEFAULT_THRESHOLD;
        }
        final String delim = DateConversionUtil
                .findDelimiterFromPattern(pattern);
        InputDateValueHolder holder = new InputDateValueHolder(pattern, length,
                threshold, delim);
        renderOnfocus(htmlInputDateText, writer, holder);
        renderOnblur(htmlInputDateText, writer, holder);
        renderOnkeydown(htmlInputDateText, writer, holder);
        renderOnkeypress(htmlInputDateText, writer, holder);
        renderOnkeyup(htmlInputDateText, writer, holder);
        renderStyle(htmlInputDateText, writer);
        renderStyleClass(context, htmlInputDateText, writer);
    }

    protected static String calculateLength(String pattern) {
        String p = DateConversionUtil.removeDelimiter(pattern);
        if (p.indexOf("yyyy") >= 0) {
            p = StringUtil.replace(p, "yyyy", "yy");
        }
        return String.valueOf(p.length());
    }

    protected static void assertHtmlInputDateText(HtmlInputText htmlInputText) {
        if (!(htmlInputText instanceof THtmlInputDateText)) {
            throw new IllegalStateException();
        }
    }

    protected String getPattern(FacesContext context,
            THtmlInputDateText htmlInputDateText) {
        String pattern = htmlInputDateText.getPattern();
        final Locale locale = context.getViewRoot().getLocale();
        if (pattern == null) {
            pattern = DateConversionUtil.getY4Pattern(locale);
        }
        return pattern;
    }

    protected void renderStyle(THtmlInputDateText htmlInputDateText,
            ResponseWriter writer) throws IOException {
        String style = createStyleAttribute(htmlInputDateText);
        if (StringUtil.isNotBlank(style)) {
            RendererUtil
                    .renderAttribute(writer, JsfConstants.STYLE_ATTR, style);
        }
    }

    protected String createStyleAttribute(THtmlInputDateText htmlInputDateText) {
        final String style = appendSemiColonIfNeed(htmlInputDateText.getStyle());
        final String s = "ime-mode:disabled;";
        if (StringUtil.contains(style, s)) {
            return style;
        }
        return style + s;
    }

    protected void renderStyleClass(FacesContext context,
            THtmlInputDateText htmlInputDateText, ResponseWriter writer)
            throws IOException {
        if (FacesMessageUtil.hasMessagesByClientId(context, htmlInputDateText)) {
            colorErrorComponent(context, htmlInputDateText);
        } else {
            final String styleClass = createStyleClassAttribute(htmlInputDateText);
            if (StringUtil.isNotBlank(styleClass)) {
                RendererUtil.renderAttribute(writer,
                        JsfConstants.STYLE_CLASS_ATTR, styleClass);
            }
        }
    }

    protected String createStyleClassAttribute(
            THtmlInputDateText htmlInputDateText) {
        return htmlInputDateText.getStyleClass();
    }

    protected void renderOnfocus(THtmlInputDateText htmlInputDateText,
            ResponseWriter writer, InputDateValueHolder holder)
            throws IOException {
        final String delim = holder.getDelim();
        final String length = holder.getLength();
        String onfocus = appendSemiColonIfNeed(htmlInputDateText.getOnfocus());
        String target = JS_NAMESPACE_PREFIX + "removeDelimeter(this, '" + delim
                + "', " + length + ");";
        if (!onfocus.endsWith(target)) {
            onfocus = onfocus + target;
        }
        if (StringUtil.isNotBlank(onfocus)) {
            RendererUtil.renderAttribute(writer, JsfConstants.ONFOCUS_ATTR,
                    onfocus);
        }
    }

    protected void renderOnblur(THtmlInputDateText htmlInputDateText,
            ResponseWriter writer, InputDateValueHolder holder)
            throws IOException {
        final String onblur = createOnblurAttribute(htmlInputDateText, holder);
        if (StringUtil.isNotBlank(onblur)) {
            RendererUtil.renderAttribute(writer, JsfConstants.ONBLUR_ATTR,
                    onblur);
        }
    }

    protected String createOnblurAttribute(
            THtmlInputDateText htmlInputDateText, InputDateValueHolder holder) {
        final String pattern = holder.getPattern();
        final String length = holder.getLength();
        final String threshold = holder.getThreshold();
        final String delim = holder.getDelim();
        final String onblur = appendSemiColonIfNeed(htmlInputDateText
                .getOnblur());
        final String s = JS_NAMESPACE_PREFIX + "convertByKey(this);"
                + JS_NAMESPACE_PREFIX + "addDelimeter(this, '" + pattern
                + "', " + length + ", " + threshold + ", '" + delim + "');";
        if (StringUtil.contains(onblur, s)) {
            return onblur;
        }
        return onblur + s;
    }

    protected void renderOnkeydown(THtmlInputDateText htmlInputDateText,
            ResponseWriter writer, InputDateValueHolder holder)
            throws IOException {
        String onkeydown = appendSemiColonIfNeed(htmlInputDateText
                .getOnkeydown());
        renderKeycheckEvent(writer, JsfConstants.ONKEYDOWN_ATTR, onkeydown,
                holder);
    }

    protected void renderOnkeypress(THtmlInputDateText htmlInputDateText,
            ResponseWriter writer, InputDateValueHolder holder)
            throws IOException {
        String onkeypress = appendSemiColonIfNeed(htmlInputDateText
                .getOnkeypress());
        renderKeycheckEvent(writer, JsfConstants.ONKEYPRESS_ATTR, onkeypress,
                holder);
    }

    protected void renderOnkeyup(THtmlInputDateText htmlInputDateText,
            ResponseWriter writer, InputDateValueHolder holder)
            throws IOException {
        String onkeyup = appendSemiColonIfNeed(htmlInputDateText.getOnkeyup());
        renderKeycheckEvent(writer, JsfConstants.ONKEYUP_ATTR, onkeyup, holder);
    }

    private void renderKeycheckEvent(ResponseWriter writer,
            String attributeName, String target, InputDateValueHolder holder)
            throws IOException {
        final String pattern = holder.getPattern();
        final String length = holder.getLength();
        final String threshold = holder.getThreshold();
        final String delim = holder.getDelim();
        final String script = "return " + JS_NAMESPACE_PREFIX
                + "keycheckForNumber(event, this, '" + pattern + "', " + length
                + ", " + threshold + ", '" + delim + "');";
        if (!target.endsWith(script)) {
            target = target + script;
            RendererUtil.renderAttribute(writer, attributeName, target);
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
        ignore.addIgnoreComponentName(ExtensionConstants.PATTERN_ATTR);
        ignore.addIgnoreComponentName(ExtensionConstants.LENGTH_ATTR);
        ignore.addIgnoreComponentName(ExtensionConstants.THRESHOD_ATTR);
        return ignore;
    };

    protected String getScriptKey() {
        return THtmlInputDateText.class.getName();
    }

    protected static class InputDateValueHolder {

        private String pattern;

        private String length;

        private String threshold;

        private String delim;

        public InputDateValueHolder(String pattern, String length,
                String threshold, String delim) {
            this.pattern = pattern;
            this.length = length;
            this.threshold = threshold;
            this.delim = delim;
        }

        public String getDelim() {
            return delim;
        }

        public String getLength() {
            return length;
        }

        public String getPattern() {
            return pattern;
        }

        public String getThreshold() {
            return threshold;
        }

    }

}
