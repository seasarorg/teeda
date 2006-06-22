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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.seasar.framework.util.StringUtil;

/**
 * @author manhole
 */
public class UrlBuilder {

    private String base;

    private Map urlParameters = new LinkedHashMap() {

        private static final long serialVersionUID = 1L;

        public Object get(Object key) {
            Object value = super.get(key);
            if (value != null) {
                return value;
            }
            UrlParameter p = new UrlParameter();
            p.setKey((String) key);
            put(key, p);
            return p;
        }
    };

    public void setBase(String base) {
        this.base = base;
    }

    public String build() {
        StringBuffer sb = new StringBuffer(100);
        sb.append(base);
        boolean questionAppeared = false;
        if (StringUtil.contains(base, '?')) {
            questionAppeared = true;
        }
        for (Iterator it = urlParameters.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            UrlParameter parameter = (UrlParameter) entry.getValue();
            String[] values = parameter.getValues();
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                if (questionAppeared) {
                    sb.append('&');
                } else {
                    sb.append('?');
                    questionAppeared = true;
                }
                sb.append(key);
                sb.append('=');
                sb.append(value);
            }
        }
        return new String(sb);
    }

    public void add(String key, String value) {
        UrlParameter p = (UrlParameter) urlParameters.get(key);
        p.addValue(value);
    }

}
