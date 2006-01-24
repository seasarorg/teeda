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
package org.seasar.teeda.core.el.impl;

import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.el.ELParser;
import org.seasar.teeda.core.el.ExpressionProcessor;
import org.seasar.teeda.core.el.ValueBindingContext;
import org.seasar.teeda.core.mock.MockMultipleArgsValueBinding;
import org.seasar.teeda.core.mock.MockValueBinding;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ValueBindingContextTest extends TeedaTestCase {

    public void testCreateValueBinding1() {
        ValueBindingContext context = new ValueBindingContextImpl();
        context.setELParser(new MockELParser());
        context
                .setValueBindingName("org.seasar.teeda.core.mock.MockMultipleArgsValueBinding");
        ValueBinding vb = context.createValueBinding(getApplication(), "hoge");
        assertNotNull(vb);
        assertTrue(vb instanceof MockMultipleArgsValueBinding);
    }

    public void testCreateValueBinding2() {
        ValueBindingContext context = new ValueBindingContextImpl();
        context
                .setValueBindingName("org.seasar.teeda.core.mock.MockValueBinding");
        ValueBinding vb = context.createValueBinding(getApplication(), "hoge");
        assertNotNull(vb);
        assertTrue(vb instanceof MockValueBinding);
    }

    public void testCreateValueBinding3() {
        ValueBindingContext context = new ValueBindingContextImpl();
        context.setELParser(new MockELParser());
        context
                .setValueBindingName("org.seasar.teeda.core.mock.MockMultipleArgsValueBinding");
        ValueBinding vb = context.createValueBinding(getApplication(), "hoge");
        assertNotNull(vb);
        assertTrue(vb instanceof MockMultipleArgsValueBinding);

        context
                .setValueBindingName("org.seasar.teeda.core.mock.MockValueBinding");
        ValueBinding vbNew = context.createValueBinding(getApplication(),
                "hoge");
        assertTrue(vb.equals(vbNew));
    }

    private static class MockELParser implements ELParser {

        public Object parse(String expression) {
            throw new UnsupportedOperationException();
        }

        public void setExpressionProcessor(ExpressionProcessor processor) {
            throw new UnsupportedOperationException();
        }

        public ExpressionProcessor getExpressionProcessor() {
            throw new UnsupportedOperationException();
        }

    }
}
