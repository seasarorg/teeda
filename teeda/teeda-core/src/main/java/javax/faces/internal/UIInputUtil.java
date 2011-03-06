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

import java.lang.reflect.Array;
import java.util.List;

/**
 * @author shot
 * @author higa
 * 
 * This class might be changed without notice. Please do not use it
 * excluding the JSF specification part.
 */
public class UIInputUtil {

    private UIInputUtil() {
    }

    public static boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        } else if (value instanceof String) {
            String s = (String) value;
            if (s.length() < 1) {
                return true;
            }
        } else if (value.getClass().isArray()) {
            if (Array.getLength(value) == 0) {
                return true;
            }
        } else if (value instanceof List) {
            List list = (List) value;
            if (list.size() == 0) {
                return true;
            }
        }
        return false;
    }
}