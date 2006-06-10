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
package org.seasar.teeda.ajax.json;

import java.util.ArrayList;
import java.util.List;

import org.seasar.teeda.ajax.AjaxConstants;

/**
 * 
 * @author mopemope
 */
public class JSONArray {
    private List list = new ArrayList();

    public JSONArray() {
    }

    public JSONArray(List list) {
        this.list = list;
    }

    public void push(Object o) {
        this.list.add(o);
    }

    public Object get(int index) {
        return this.list.get(index);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(AjaxConstants.JSON_START_LIST_BRACE);
        for (int i = 0; i < this.list.size(); i++) {
            if (i > 0) {
                buf.append(AjaxConstants.JSON_ARRAY_SEPARATOR);
            }
            Object value = list.get(i);
            JSONObject.append(buf, value);
        }
        buf.append(AjaxConstants.JSON_END_LIST_BRACE);
        return buf.toString();
    }
}
