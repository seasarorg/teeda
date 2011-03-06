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
package javax.faces.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.seasar.framework.util.ClassUtil;

/**
 * @author koichik
 */
public class EnumUtil {

    private static final String ENUM_SUPPORT_CLASS_NAME = "javax.faces.internal.EnumSupportImpl";

    private static EnumSupport enumSupport = null;
    static {
        try {
            enumSupport = (EnumSupport) ClassUtil
                    .newInstance(ENUM_SUPPORT_CLASS_NAME);
        } catch (final Throwable ignore) {
        }
    }

    public static Map convertEnumToName(final Map src) {
        if (src == null || src.isEmpty()) {
            return src;
        }
        Map dest = new HashMap(src.size());
        for (final Iterator it = src.entrySet().iterator(); it.hasNext();) {
            final Entry entry = (Entry) it.next();
            final String key = (String) entry.getKey();
            final Object value = entry.getValue();
            final Class valueType = value.getClass();
            if (isEnum(valueType)) {
                final EnumHolder holder = new EnumHolder(valueType.getName(),
                        toName(value));
                dest.put(key, holder);
            } else {
                dest.put(key, value);
            }
        }
        return dest;
    }

    public static Map convertNameToEnum(final Map src) {
        if (src == null || src.isEmpty()) {
            return src;
        }
        Map dest = new HashMap(src.size());
        for (final Iterator it = src.entrySet().iterator(); it.hasNext();) {
            final Entry entry = (Entry) it.next();
            final String key = (String) entry.getKey();
            final Object value = entry.getValue();
            if (value instanceof EnumHolder) {
                final Object enumValue = toEnum((EnumHolder) value);
                dest.put(key, enumValue);
            } else {
                dest.put(key, value);
            }
        }
        return dest;
    }

    public static boolean isEnum(final Class clazz) {
        return enumSupport != null && enumSupport.isEnum(clazz);
    }

    public static String toName(final Object enumObject) {
        return enumSupport.toName(enumObject);
    }

    public static Object toEnum(final EnumHolder enumHolder) {
        return enumSupport.toEnum(ClassUtil.forName(enumHolder.enumType),
                enumHolder.name);
    }

    public static Object toEnum(final Class enumType, final String name) {
        return enumSupport.toEnum(enumType, name);
    }

    public interface EnumSupport {

        boolean isEnum(Class clazz);

        String toName(Object enumObject);

        Object toEnum(Class enumType, String name);

    }

    public static class EnumHolder {

        public String enumType;

        public String name;

        public EnumHolder(final String enumType, final String name) {
            this.enumType = enumType;
            this.name = name;
        }

    }

}
