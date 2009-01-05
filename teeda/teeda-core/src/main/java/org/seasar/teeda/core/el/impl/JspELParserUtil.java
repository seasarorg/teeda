/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.el.impl;

import javax.faces.el.ReferenceSyntaxException;

import org.seasar.framework.exception.EmptyRuntimeException;

/**
 * @author shot
 */
public class JspELParserUtil {

    private static final String JSP_OPEN_BRACE = "${";

    private static final String JSP_CLOSE_BRACE = "}";

    private JspELParserUtil() {
    }

    public static String convertToJspExpression(final String expression) {
        return convertToJspExpression(expression, "#{", "}");
    }

    public static String convertToJspExpression(final String expression,
            final String openBrace, final String closeBrace) {
        if (expression == null) {
            throw new EmptyRuntimeException("expression");
        }
        if (openBrace == null || closeBrace == null) {
            throw new ReferenceSyntaxException();
        }
        String str = expression;
        if (str.startsWith(openBrace) && str.indexOf(closeBrace) > 0) {
            int pos = str.indexOf(closeBrace);
            String s = str.substring(JSP_OPEN_BRACE.length(), pos);
            String postfix = str.substring(pos + 1);
            str = JSP_OPEN_BRACE + s + JSP_CLOSE_BRACE
                    + convertToJspExpression(postfix);
        } else {
            int pos1 = str.indexOf(openBrace);
            int pos2 = str.indexOf(closeBrace);
            if (pos1 > 0) {
                if (pos2 > pos1) {
                    String prefix = str.substring(0, pos1);
                    String s = str.substring(pos1 + openBrace.length(), pos2);
                    String postfix = str.substring(pos2 + 1);
                    str = prefix + JSP_OPEN_BRACE + s + JSP_CLOSE_BRACE
                            + convertToJspExpression(postfix);
                } else if (pos2 >= 0 && pos2 <= pos1) {
                    String prefix = str.substring(0, pos2 + 1);
                    String s = str.substring(pos2 + 1);
                    str = prefix + convertToJspExpression(s);
                }
            }
        }
        return str;
    }

}
