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
package javax.faces.internal;

import javax.faces.convert.Converter;
import javax.faces.convert.IntegerConverter;

import junit.framework.TestCase;

public class ConverterResourceTest extends TestCase {

    public void tearDown() throws Exception {
        ConverterResource.removeAll();
    }

    public void testGetConverter() throws Exception {
        IntegerConverter c = new IntegerConverter();
        ConverterResource.addConverter("#{a.b}", c);
        Converter converter = ConverterResource.getConverter("#{a.b}");
        assertNotNull(converter);
        assertTrue(converter instanceof IntegerConverter);
    }
}
