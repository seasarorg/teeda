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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.seasar.framework.util.BooleanConversionUtil;
import org.seasar.framework.util.DoubleConversionUtil;
import org.seasar.framework.util.FloatConversionUtil;
import org.seasar.framework.util.IntegerConversionUtil;
import org.seasar.framework.util.LongConversionUtil;
import org.seasar.framework.util.ShortConversionUtil;

/**
 * @author shot
 * @author manhole
 */
public class ArrayUtil {

    private ArrayUtil() {
    }

    public static boolean equalsIgnoreSequence(Object[] array1, Object[] array2) {
        if (array1 == null && array2 == null) {
            return true;
        } else if (array1 == null || array2 == null) {
            return false;
        }
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

    public static void setArrayValue(Object array, Class valueType,
            Object value, int index) {
        if (value == null) {
            return;
        }
        if (valueType == int.class) {
            Array.setInt(array, index, IntegerConversionUtil
                    .toPrimitiveInt(value));
        } else if (valueType == double.class) {
            Array.setDouble(array, index, DoubleConversionUtil
                    .toPrimitiveDouble(value));
        } else if (valueType == long.class) {
            Array.setLong(array, index, LongConversionUtil
                    .toPrimitiveLong(value));
        } else if (valueType == float.class) {
            Array.setFloat(array, index, FloatConversionUtil
                    .toPrimitiveFloat(value));
        } else if (valueType == short.class) {
            Array.setShort(array, index, ShortConversionUtil
                    .toPrimitiveShort(value));
        } else if (valueType == boolean.class) {
            Array.setBoolean(array, index, BooleanConversionUtil
                    .toPrimitiveBoolean(value));
        } else if (valueType == char.class) {
            Array.setChar(array, index, ((Character) value).charValue());
        }
        Array.set(array, index, value);
    }

}
