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
package org.seasar.teeda.core.scope.impl;

import java.util.HashMap;
import java.util.Map;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.core.scope.Scope;

/**
 * @author shot
 */
public class DispatchScope implements Scope {

    private Map map = new HashMap();

    public void put(Object key, Object value) {
        AssertionUtil.assertNotNull("key", key);
        AssertionUtil.assertNotNull("value", value);
        map.put(key, value);
    }

    public Object get(Object key) {
        return map.get(key);
    }

    public String getScopeKey() {
        return JsfConstants.SCOPE_DISPATCH;
    }

    public boolean contains(Object key) {
        return map.containsKey(key);
    }

}
