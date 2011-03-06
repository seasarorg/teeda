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
package org.seasar.teeda.extension.annotation.handler;

import java.util.HashMap;

import javax.faces.internal.ConverterResource;

import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.convert.TDateTimeConverter;
import org.seasar.teeda.extension.convert.TDoubleConverter;

/**
 * @author higa
 * @author shot
 */
public class AbstConverterAnnotationHandlerTest extends TeedaTestCase {

    protected void tearDown() {
        ConverterResource.removeAll();
    }

    public void testAddAndRemoveConverter() throws Exception {
        MockAnnotationHandler handler = new MockAnnotationHandler();
        register(TDateTimeConverter.class, "dateTimeConverter");
        register(TDoubleConverter.class, "doubleConverter");
        handler.registerConverter("aaa", "bbb", "dateTimeConverter",
                new HashMap());
        handler.registerConverter("aaa", "bbb", "doubleConverter",
                new HashMap());
        handler.registerConverter("aaa", "ccc", "doubleConverter",
                new HashMap());
        assertNotNull(ConverterResource.getConverter("#{aaa.bbb}"));
        assertEquals(2, handler.getExpressionSize("aaa"));

        handler.removeConverters("aaa");
        assertNull(ConverterResource.getConverter("#{aaa.bbb}"));
        assertEquals(0, handler.getExpressionSize("aaa"));
    }

    public void testRemoveAll() throws Exception {
        MockAnnotationHandler handler = new MockAnnotationHandler();
        register(TDateTimeConverter.class, "dateTimeConverter");
        register(TDoubleConverter.class, "doubleConverter");
        handler.registerConverter("aaa", "bbb", "dateTimeConverter",
                new HashMap());
        handler.registerConverter("aaa", "bbb", "doubleConverter",
                new HashMap());
        handler.registerConverter("aaa", "ccc", "doubleConverter",
                new HashMap());
        handler.removeAll();
        assertNull(ConverterResource.getConverter("#{aaa.bbb}"));
        assertEquals(0, handler.getExpressionSize("aaa"));
    }

    public static class MockAnnotationHandler extends
            AbstractConverterAnnotationHandler {

        public void registerConverters(String componentName) {
        }
    }
}