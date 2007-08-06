/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import java.io.Serializable;

/**
 * @author shot
 * @author higa
 */
public class PagePersistenceUtil {

    public static boolean isPersistenceType(Class clazz) {
        if (clazz.isPrimitive()) {
            return true;
        }
        if (!Serializable.class.isAssignableFrom(clazz)) {
            return false;
        }
        if (clazz.isArray()) {
            Class componentType = clazz.getComponentType();
            if (componentType.isPrimitive()) {
                return true;
            } else if (componentType.isArray()) {
                return isPersistenceType(componentType);
            }
            return Serializable.class.isAssignableFrom(componentType);
        }
        return true;
    }

}
