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

import org.apache.commons.el.Expression;
import org.apache.commons.el.ExpressionString;
import org.seasar.teeda.core.el.ExpressionProcessor;
import org.seasar.teeda.core.el.Replacer;

/**
 * @author shot
 */
public class ObjectReplacer implements Replacer {

    private ExpressionProcessor processor_;

    public ObjectReplacer(ExpressionProcessor processor) {
        processor_ = processor;
    }

    public void replace(Object expression) {
        if (!(expression instanceof Expression || expression instanceof ExpressionString)) {
            throw new IllegalStateException();
        }
        processor_.processExpression(expression, expression.getClass());
    }

}