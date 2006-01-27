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
package org.seasar.teeda.core.util;

import javax.faces.context.FacesContext;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;

/**
 * @author shot
 */
public class HtmlRenderKitUtil {

    public static String[] removeSemiColon(String[] contentTypes) {
        for (int i = 0; i < contentTypes.length; i++) {
            String type = contentTypes[i];
            int index = type.indexOf(";");
            if (index != -1) {
                type = type.substring(0, index);
                contentTypes[i] = type;
            }
        }
        return contentTypes;
    }

    public static String getContentType(String contentTypeList) {
        if (contentTypeList == null) {
            FacesContext context = FacesContext.getCurrentInstance();
            contentTypeList = getContentType(context);
            if (contentTypeList == null) {
                return JsfConstants.HTML_CONTENT_TYPE;
            }
        }

        String[] strs = StringUtil.split(contentTypeList, ",");
        String[] contentTypes = removeSemiColon(strs);

        for (int i = 0; i < contentTypes.length; i++) {
            String type = contentTypes[i];
            if (isHtmlContentType(type)) {
                return JsfConstants.HTML_CONTENT_TYPE;
            } else if (isXmlContentType(type)) {
                return JsfConstants.XHTML_CONTENT_TYPE;
            }
        }
        return null;
    }

    public static String getContentType(FacesContext context) {
        return (String) context.getExternalContext().getRequestHeaderMap().get(
                "Accept");

    }

    public static boolean isHtmlContentType(String type) {
        return type.indexOf("html") != -1
                || type.equals(JsfConstants.ANY_CONTENT_TYPE);
    }

    public static boolean isXmlContentType(String type) {
        return type.indexOf("xml") != -1;
    }
}
