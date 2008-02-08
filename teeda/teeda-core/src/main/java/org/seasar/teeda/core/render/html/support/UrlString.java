/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.render.html.support;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.seasar.framework.util.StringUtil;

/**
 * @author manhole
 */
public class UrlString {

    private Map parameters = new LinkedHashMap();

    private String path;

    private String queryString;

    public void parse(String url) {
        if (StringUtil.contains(url, '?')) {
            int questionPos = url.indexOf('?');
            path = url.substring(0, questionPos);
            queryString = url.substring(questionPos + 1);
        } else {
            path = url;
        }
        String[] params = StringUtil.split(queryString, "&");
        for (int i = 0; i < params.length; i++) {
            final String param = params[i];
            if (StringUtil.contains(param, '=')) {
                final String key = param.substring(0, param.indexOf('='));
                UrlParameter urlParameter = (UrlParameter) parameters.get(key);
                if (urlParameter == null) {
                    urlParameter = new UrlParameter();
                    urlParameter.setKey(key);
                    parameters.put(urlParameter.getKey(), urlParameter);
                }
                urlParameter.addValue(param.substring(param.indexOf('=') + 1));
            }
        }
    }

    public String getPath() {
        return path;
    }

    public String getParameter(String key) {
        UrlParameter param = (UrlParameter) parameters.get(key);
        if (param == null) {
            return null;
        }
        return param.getValue();
    }

    public String[] getParameters(String key) {
        UrlParameter param = (UrlParameter) parameters.get(key);
        if (param == null) {
            return null;
        }
        return param.getValues();
    }

    public boolean isIdentical(UrlString otherUrl) {
        if (!getPath().equals(otherUrl.getPath())) {
            return false;
        }
        Set myParamNames = getParameterNames();
        Set otherParamNames = otherUrl.getParameterNames();
        if (myParamNames.size() != otherParamNames.size()) {
            return false;
        }
        for (Iterator it = myParamNames.iterator(); it.hasNext();) {
            String key = (String) it.next();
            String[] myValues = getParameters(key);
            String[] otherValues = otherUrl.getParameters(key);
            if (!org.seasar.framework.util.ArrayUtil.equalsIgnoreSequence(
                    myValues, otherValues)) {
                return false;
            }
        }
        return true;
    }

    public Set getParameterNames() {
        return Collections.unmodifiableSet(parameters.keySet());
    }

}
