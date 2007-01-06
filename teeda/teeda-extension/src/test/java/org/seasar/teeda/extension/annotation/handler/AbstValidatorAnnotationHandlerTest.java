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
package org.seasar.teeda.extension.annotation.handler;

import javax.faces.internal.ValidatorResource;
import javax.faces.validator.DoubleRangeValidator;

import junit.framework.TestCase;

import org.seasar.teeda.extension.validator.TByteLengthValidator;

/**
 * @author higa
 *
 */
public class AbstValidatorAnnotationHandlerTest extends TestCase {

    protected void tearDown() {
        ValidatorResource.removeAll();
    }

    public void testAddAndRemoveValidator() throws Exception {
        MockAnnotationHandler handler = new MockAnnotationHandler();
        handler.registerValidator("aaa", "bbb", new TByteLengthValidator());
        handler.registerValidator("aaa", "bbb", new DoubleRangeValidator());
        handler.registerValidator("aaa", "ccc", new DoubleRangeValidator());
        assertNotNull(ValidatorResource.getValidator("#{aaa.bbb}"));
        assertEquals(2, handler.getExpressionSize("aaa"));

        handler.removeValidators("aaa");
        assertNull(ValidatorResource.getValidator("#{aaa.bbb}"));
        assertEquals(0, handler.getExpressionSize("aaa"));
    }

    public void testRemoveAll() throws Exception {
        MockAnnotationHandler handler = new MockAnnotationHandler();
        handler.registerValidator("aaa", "bbb", new TByteLengthValidator());
        handler.registerValidator("aaa", "bbb", new DoubleRangeValidator());
        handler.registerValidator("aaa", "ccc", new DoubleRangeValidator());
        handler.removeAll();
        assertNull(ValidatorResource.getValidator("#{aaa.bbb}"));
        assertEquals(0, handler.getExpressionSize("aaa"));
    }

    public static class MockAnnotationHandler extends
            AbstractValidatorAnnotationHandler {

        public void registerValidators(String componentName) {
        }
    }
}