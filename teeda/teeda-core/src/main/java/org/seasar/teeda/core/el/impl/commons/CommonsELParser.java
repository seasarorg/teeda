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
package org.seasar.teeda.core.el.impl.commons;

import java.io.StringReader;

import javax.faces.el.ReferenceSyntaxException;

import org.apache.commons.el.parser.ELParser;
import org.apache.commons.el.parser.ParseException;
import org.seasar.teeda.core.el.ExpressionProcessor;
import org.seasar.teeda.core.el.impl.JspELParserUtil;

/**
 * @author shot
 */
public class CommonsELParser implements org.seasar.teeda.core.el.ELParser {

    private static final long serialVersionUID = 1L;

    private ExpressionProcessor processor;

    public CommonsELParser() {
    }

    public Object parse(final String expression) {
        String jspExpression = JspELParserUtil
                .convertToJspExpression(expression);
        ELParser parser = new ELParser(new StringReader(jspExpression));
        try {
            Object obj = parser.ExpressionString();
            getExpressionProcessor().processExpression(obj, Object.class);
            return obj;
        } catch (ParseException e) {
            throw new ReferenceSyntaxException();
        }
    }

    public ExpressionProcessor getExpressionProcessor() {
        return processor;
    }

    public void setExpressionProcessor(ExpressionProcessor processor) {
        this.processor = processor;
    }
}
