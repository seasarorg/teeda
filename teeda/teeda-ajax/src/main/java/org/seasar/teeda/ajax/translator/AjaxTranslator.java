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
package org.seasar.teeda.ajax.translator;

import java.lang.reflect.Array;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.seasar.teeda.ajax.AjaxConstants;
import org.seasar.teeda.ajax.QuoteUtil;
import org.seasar.teeda.ajax.json.JSONObject;

/**
 * @author shot
 * 
 */
public class AjaxTranslator {

    public static String translate(Object target) {
        if (target == null) {
            return null;
        }
        if (target instanceof String) {
            return QuoteUtil.quote(target.toString());
        } else if (target instanceof Number) {
            return target.toString();
        } else if (target.getClass().isArray()) {
            return translateToArrayType(target);
        } else if (target instanceof List) {
            List list = (List) target;
            return translateToArrayType(list.toArray());
        } else if (target instanceof Boolean) {
            Boolean b = (Boolean) target;
            return String.valueOf(b.booleanValue());
        } else {
            return new JSONObject(target).toJSON();
        }
    }

    public static void setContentType(HttpServletResponse response,
            String result) {
        String contentType;
        if (result == null || result.startsWith(AjaxConstants.JSON_START_BRACE)
                && result.endsWith(AjaxConstants.JSON_END_BRACE)) {
            contentType = AjaxConstants.CONTENT_TYPE_JSON;
        } else if (result.startsWith("<?xml")) {
            contentType = AjaxConstants.CONTENT_TYPE_XML;
        } else if (result.startsWith("<") && result.endsWith(">")) {
            contentType = AjaxConstants.CONTENT_TYPE_HTML;
        } else {
            contentType = AjaxConstants.CONTENT_TYPE_TEXT;
        }
        response.setContentType(contentType);
    }

    private static String translateToArrayType(Object array) {
        StringBuffer buf = new StringBuffer();
        buf.append(AjaxConstants.JSON_START_LIST_BRACE);
        final int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            Object o = Array.get(array, i);
            buf.append(translate(o));
            buf.append(AjaxConstants.JSON_ARRAY_SEPARATOR);
        }
        if (length > 0) {
            buf.setLength(buf.length()
                    - AjaxConstants.JSON_ARRAY_SEPARATOR.length());
        }
        buf.append(AjaxConstants.JSON_END_LIST_BRACE);
        return buf.toString();
    }

}
