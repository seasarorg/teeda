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
package javax.faces.internal;

import java.util.Map;
import java.util.Map.Entry;

import javax.faces.model.ResultSetDataModel;

/**
 * @author shot
 * 
 * This class might be changed without a previous notice. Please do not use it
 * excluding the JSF specification part.
 */
public class ResultSetEntry implements Entry {

    ResultSetDataModel.ResultSetMap map_ = null;

    Object key_ = null;

    public ResultSetEntry(ResultSetDataModel.ResultSetMap map, Object key) {
        map_ = map;
        key_ = key;
    }

    public Object getKey() {
        return key_;
    }

    public Object getValue() {
        return map_.get(key_);
    }

    public Object setValue(Object value) {
        return map_.put(key_, value);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }

        Map.Entry entry = (Map.Entry) obj;
        if ((this.getKey() == null && entry.getKey() != null)
                || (this.getKey() != null && entry.getKey() == null)) {
            return false;
        }

        if ((this.getValue() == null && entry.getValue() != null)
                || (this.getValue() != null && entry.getValue() == null)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return ((map_ != null) ? map_.hashCode() : 0) * 17;
    }

}
