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
package org.seasar.teeda.extension.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.seasar.framework.util.StringUtil;

/**
 * @author shot
 * @author higa
 */
public class PagePersistenceUtil {

    private static final String ACTION_METHOD_KEY = PagePersistenceUtil.class
            .getName()
            + ".ACTION_METHOD";

    public static boolean isPersistenceType(Class clazz) {
        if (clazz.isPrimitive()) {
            return true;
        }
        return clazz == String.class || clazz == Boolean.class
                || Number.class.isAssignableFrom(clazz)
                || Date.class.isAssignableFrom(clazz)
                || Calendar.class.isAssignableFrom(clazz)
                || Map.class.isAssignableFrom(clazz) || clazz.isArray();
    }

    public static void setupActionMethodName(FacesContext context,
            String fromAction, String outcome) {
        context.getExternalContext().getRequestMap().put(ACTION_METHOD_KEY,
                calcActionMethodName(fromAction, outcome));
    }

    public static String getActionMethodName(FacesContext context) {
        return (String) context.getExternalContext().getRequestMap().get(
                ACTION_METHOD_KEY);
    }

    protected static String calcActionMethodName(String fromAction,
            String outcome) {
        if (fromAction != null) {
            int index = fromAction.lastIndexOf('.');
            if (index < 0) {
                throw new IllegalArgumentException(fromAction);
            }
            return fromAction.substring(index + 1, fromAction.length() - 1)
                    .trim();
        }
        return "go" + StringUtil.capitalize(outcome);
    }
}
