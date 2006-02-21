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

import org.seasar.framework.util.ArrayUtil;

/**
 * @author manhole
 */
public class UrlParameter {

    private String key_;

    private String[] values_ = {};

    public String getKey() {
        return key_;
    }

    public void setKey(String key) {
        key_ = key;
    }

    public String getValue() {
        if (ArrayUtil.isEmpty(values_)) {
            return "";
        }
        return values_[0];
    }

    public void setValue(String value) {
        values_ = new String[] { value };
    }

    public void addValue(String value) {
        values_ = (String[]) ArrayUtil.add(values_, value);
    }

    public String[] getValues() {
        return values_;
    }

    public void setValues(String[] values) {
        values_ = values;
    }

}
