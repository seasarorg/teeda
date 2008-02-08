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
package org.seasar.teeda.extension.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.component.NamingContainer;

import org.seasar.framework.util.AssertionUtil;
import org.seasar.framework.util.StringUtil;

/**
 * @author shot
 */
public class AdjustValueHolderUtil {

    protected AdjustValueHolderUtil() {
    }

    public static String getAdjustedValue(String clientId) {
        AssertionUtil.assertNotNull("clientId", clientId);
        if (clientId.indexOf("-") < 0) {
            return clientId;
        }
        String[] ids = StringUtil.split(clientId, String
                .valueOf(NamingContainer.SEPARATOR_CHAR));
        StringBuffer buf = new StringBuffer(128);
        for (int i = 0; i < ids.length; i++) {
            String s = ids[i];
            int index = s.indexOf("-");
            if (index >= 0) {
                s = s.substring(0, index);
            }
            buf.append(s).append(NamingContainer.SEPARATOR_CHAR);
        }
        buf.setLength(buf.length() - 1);
        return buf.toString();
    }

    public static Map adjustParamMap(Map paramMap) {
        Map map = new HashMap();
        for (Iterator itr = paramMap.entrySet().iterator(); itr.hasNext();) {
            Map.Entry entry = (Entry) itr.next();
            String key = (String) entry.getKey();
            key = getAdjustedValue(key);
            map.put(key, entry.getValue());
        }
        return map;
    }

}
