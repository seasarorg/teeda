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
package org.seasar.teeda.core.el.impl;

import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.ExpressionProcessor;
import org.seasar.teeda.core.el.impl.commons.CommonsELParser;
import org.seasar.teeda.core.el.impl.commons.CommonsExpressionProcessorImpl;

/**
 * @author shot
 * 
 */
public class ELParserFactory {

    public static ELParser createELParser() {
        ExpressionProcessor processor = new CommonsExpressionProcessorImpl();
        ELParser parser = new CommonsELParser();
        parser.setExpressionProcessor(processor);
        return parser;
    }

}
