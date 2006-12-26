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

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.JsfConstants;

/**
 * @author shot
 * @author yone
 */
public class ContentTypeUtil {

    public static String getDefaultContentType() {
        return JsfConstants.HTML_CONTENT_TYPE;
    }

    public static String getContentType(String contentTypeList) {
        if (contentTypeList == null) {
            return getDefaultContentType();
        }
        String[] strs = StringUtil.split(contentTypeList, ",");
        String[] contentTypes = removeSemiColon(strs);
        for (int i = 0; i < contentTypes.length; i++) {
            if (isHtmlContentType(contentTypes[i].trim())) {
                return JsfConstants.HTML_CONTENT_TYPE;
            }
        }
        for (int i = 0; i < contentTypes.length; i++) {
            if (isXmlContentType(contentTypes[i].trim())) {
                return JsfConstants.XHTML_CONTENT_TYPE;
            }
        }
        return getDefaultContentType();
    }

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

    public static boolean isHtmlContentType(String type) {
        return type.indexOf(JsfConstants.HTML_CONTENT_TYPE) != -1
                || type.equals(JsfConstants.ANY_CONTENT_TYPE);
    }

    public static boolean isXmlContentType(String type) {
        return type.indexOf(JsfConstants.XHTML_CONTENT_TYPE) != -1
                || type.indexOf(JsfConstants.APPLICATION_XML_CONTENT_TYPE) != -1
                || type.indexOf(JsfConstants.TEXT_XML_CONTENT_TYPE) != -1;
    }
}
