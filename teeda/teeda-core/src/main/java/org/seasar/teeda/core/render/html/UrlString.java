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
package org.seasar.teeda.core.render.html;

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

    private Map parameters_ = new LinkedHashMap();

    private String url_;

    private String path_;

    private String queryString_;

    public void parse(String url) {
        url_ = url;
        if (url.indexOf('?') > -1) {
            path_ = url.substring(0, url.indexOf('?'));
            queryString_ = url.substring(url.indexOf('?') + 1);
        } else {
            path_ = url;
        }
        String[] params = StringUtil.split(queryString_, "&");
        for (int i = 0; i < params.length; i++) {
            String param = params[i];
            if (param.indexOf('=') > -1) {
                final String key = param.substring(0, param.indexOf('='));
                UrlParameter parameter = (UrlParameter) parameters_.get(key);
                if (parameter == null) {
                    parameter = new UrlParameter();
                    parameter.setKey(key);
                    parameters_.put(parameter.getKey(), parameter);
                }
                parameter.addValue(param.substring(param.indexOf('=') + 1));
            }
        }
    }

    public String getPath() {
        return path_;
    }

    public String getParameter(String key) {
        UrlParameter param = (UrlParameter) parameters_.get(key);
        if (param == null) {
            return null;
        }
        return param.getValue();
    }

    public String[] getParameters(String key) {
        UrlParameter param = (UrlParameter) parameters_.get(key);
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
            if (!org.seasar.teeda.core.util.ArrayUtil.equalsIgnoreSequence(
                    myValues, otherValues)) {
                return false;
            }
        }
        return true;
    }

    public Set getParameterNames() {
        return Collections.unmodifiableSet(parameters_.keySet());
    }

}
