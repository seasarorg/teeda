/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yone
 */
public class JavaScriptUtil {

    public static String getClearHiddenCommandFormParamsFunctionName(
            String formName) {
        return "clear_" + getValidJavascriptName(formName, false);
    }

    private static final Set RESERVED_WORDS = new HashSet(Arrays
            .asList(new String[] { "abstract", "boolean", "break", "byte",
                    "case", "catch", "char", "class", "const", "continue",
                    "default", "delete", "do", "double", "else", "enum",
                    "export", "extends", "false", "final", "finally", "float",
                    "for", "function", "goto", "if", "implements", "in",
                    "instanceof", "int", "long", "native", "new", "null",
                    "package", "private", "protected", "public", "return",
                    "short", "static", "super", "switch", "synchronized",
                    "this", "throw", "throws", "transient", "true", "try",
                    "typeof", "var", "void", "while", "with" }));

    private static String getValidJavascriptName(String s,
            boolean checkForReservedWord) {
        if (checkForReservedWord && RESERVED_WORDS.contains(s)) {
            return s + "_";
        }

        StringBuffer buf = null;
        for (int i = 0, len = s.length(); i < len; i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                if (buf != null)
                    buf.append(c);
            } else {
                if (buf == null) {
                    buf = new StringBuffer(s.length() + 10);
                    buf.append(s.substring(0, i));
                }

                buf.append('_');
                if (c < 16) {
                    buf.append('0');
                }

                if (c < 128) {
                    buf.append(Integer.toHexString(c).toUpperCase());
                } else {
                    byte[] bytes;
                    try {
                        bytes = Character.toString(c).getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }

                    for (int j = 0; j < bytes.length; j++) {
                        int intVal = bytes[j];
                        if (intVal < 0) {
                            intVal = 256 + intVal;
                        } else if (intVal < 16) {
                            buf.append('0');
                        }
                        buf.append(Integer.toHexString(intVal).toUpperCase());
                    }
                }
            }
        }
        return buf == null ? s : buf.toString();
    }

}
