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
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.context.FacesContext;
import javax.faces.internal.WindowIdUtil;

import org.seasar.framework.util.DateConversionUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.render.html.HtmlOutputLinkRenderer;
import org.seasar.teeda.core.render.html.support.PortletUrlBuilder;
import org.seasar.teeda.core.render.html.support.UrlBuilder;
import org.seasar.teeda.core.util.PortletUtil;
import org.seasar.teeda.core.util.ValueHolderUtil;
import org.seasar.teeda.extension.ExtensionConstants;

/**
 * @author shot
 * @author higa
 */
public class THtmlOutputLinkRenderer extends HtmlOutputLinkRenderer {

    public static final String COMPONENT_FAMILY = "javax.faces.Output";

    public static final String RENDERER_TYPE = "org.seasar.teeda.extension.Link";

    protected String buildHref(FacesContext context,
            HtmlOutputLink htmlOutputLink, String encoding) throws IOException {
        // PortletSupport
        if (PortletUtil.isPortlet(context)) {
            if (htmlOutputLink.getId().startsWith(ExtensionConstants.GO_PREFIX)
                    || htmlOutputLink.getId().startsWith(
                            ExtensionConstants.JUMP_PREFIX)) {
                PortletUrlBuilder urlBuilder = new PortletUrlBuilder();
                urlBuilder.setBase(ValueHolderUtil.getValueForRender(context,
                        htmlOutputLink));
                for (Iterator it = htmlOutputLink.getChildren().iterator(); it
                        .hasNext();) {
                    UIComponent child = (UIComponent) it.next();
                    if (child instanceof UIParameter) {
                        UIParameter parameter = (UIParameter) child;
                        String encodedName = URLEncoder.encode(parameter
                                .getName(), encoding);
                        String encodedValue = URLEncoder.encode(
                                toBlankIfNull(parameter.getValue()), encoding);
                        urlBuilder.add(encodedName, encodedValue);
                    }
                }
                //TODO newwindow on portal??
                //                if (WindowIdUtil.isNewWindowTarget(htmlOutputLink.getTarget())) {
                //                    urlBuilder.add(URLEncoder.encode(WindowIdUtil.NEWWINDOW, encoding),
                //                            URLEncoder.encode(JsfConstants.TRUE, encoding));
                //                }

                return context.getExternalContext().encodeResourceURL(
                        urlBuilder.build());
            } else {
                return super.buildHref(context, htmlOutputLink, encoding);
            }
        } else {
            UrlBuilder urlBuilder = new UrlBuilder();
            urlBuilder.setBase(ValueHolderUtil.getValueForRender(context,
                    htmlOutputLink));
            for (Iterator it = htmlOutputLink.getChildren().iterator(); it
                    .hasNext();) {
                UIComponent child = (UIComponent) it.next();
                if (child instanceof UIParameter) {
                    UIParameter parameter = (UIParameter) child;
                    String encodedName = URLEncoder.encode(parameter.getName(),
                            encoding);
                    String convertedValue = convertDateIfNeed(parameter
                            .getValue(), context);
                    String encodedValue = URLEncoder.encode(convertedValue,
                            encoding);
                    urlBuilder.add(encodedName, encodedValue);
                }
            }
            if (WindowIdUtil.isNewWindowTarget(htmlOutputLink.getTarget())) {
                urlBuilder.add(URLEncoder.encode(WindowIdUtil.NEWWINDOW,
                        encoding), URLEncoder.encode(JsfConstants.TRUE,
                        encoding));
            }
            return context.getExternalContext().encodeResourceURL(
                    urlBuilder.build());

        }
    }

    protected String convertDateIfNeed(final Object value,
            final FacesContext context) {
        if (value == null) {
            return "";
        }
        String ret = value.toString();
        if (value instanceof Date) {
            Locale locale = context.getViewRoot().getLocale();
            SimpleDateFormat dateFormat = DateConversionUtil
                    .getY4DateFormat(locale);
            ret = dateFormat.format((Date) value);
        }
        return ret;
    }

}
