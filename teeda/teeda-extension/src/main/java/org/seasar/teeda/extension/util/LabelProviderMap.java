/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.faces.internal.LabelUtil;

import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.extension.ExtensionConstants;

/**
 * @author koichik
 *
 */
public class LabelProviderMap implements Map {

    private static final long serialVersionUID = 7899530966928250287L;

    protected boolean suppressDecolate;

    public void setSuppressDecolate(boolean suppressDecolate) {
        this.suppressDecolate = suppressDecolate;
    }

    public Object get(final Object pageName) {
        return new LabelProviderMap() {
            public Object get(final Object key) {
                final String label = LabelUtil.getLabelValue((String) key,
                        (String) pageName);
                if (!StringUtil.isEmpty(label) || suppressDecolate) {
                    return label;
                }
                return "??" + key + ExtensionConstants.LABEL_ATTRIBUTE_SUFFIX +
                        "??";
            }
        };
    }

    public boolean containsKey(final Object key) {
        final String label = LabelUtil.getLabelValue((String) key);
        return !StringUtil.isEmpty(label);
    }

    public void clear() {
        throw new UnsupportedOperationException("clear");
    }

    public boolean containsValue(final Object value) {
        throw new UnsupportedOperationException("containsValue");
    }

    public Set entrySet() {
        throw new UnsupportedOperationException("entrySet");
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException("isEmpty");
    }

    public Set keySet() {
        throw new UnsupportedOperationException("keySet");
    }

    public Object put(final Object arg0, final Object arg1) {
        throw new UnsupportedOperationException("put");
    }

    public void putAll(final Map arg0) {
        throw new UnsupportedOperationException("putAll");
    }

    public Object remove(final Object key) {
        throw new UnsupportedOperationException("remove");
    }

    public int size() {
        throw new UnsupportedOperationException("size");
    }

    public Collection values() {
        throw new UnsupportedOperationException("values");
    }

}
