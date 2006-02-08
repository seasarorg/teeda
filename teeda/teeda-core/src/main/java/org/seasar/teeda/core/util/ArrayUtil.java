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
package org.seasar.teeda.core.util;

import java.util.Arrays;
import java.util.List;

/**
 * @author shot
 * @author manhole
 */
public class ArrayUtil {

    private ArrayUtil() {
    }

    public static boolean equalsIgnoreSequence(Object[] array1, Object[] array2) {
        if (array1.length != array2.length) {
            return false;
        }
        List list = Arrays.asList(array2);
        for (int i = 0; i < array1.length; i++) {
            Object o1 = array1[i];
            if (!list.contains(o1)) {
                return false;
            }
        }
        return true;
    }

    public static String toString(Object[] array) {
        if (array == null) {
            return "null";
        }
        if (array.length == 0) {
            return "[]";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                sb.append('[');
            } else {
                sb.append(", ");
            }
            sb.append(String.valueOf(array[i]));
        }
        sb.append("]");
        return sb.toString();
    }

}
