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

import java.util.HashMap;
import java.util.Map;

import javax.faces.convert.BigDecimalConverter;
import javax.faces.convert.IntegerConverter;

import org.seasar.teeda.core.unit.TeedaTestCase;

public class ConverterResourceTest extends TeedaTestCase {

    public void tearDown() throws Exception {
        ConverterResource.removeAll();
    }

    public void setUpGetConverter() throws Exception {
        register(IntegerConverter.class, "IntegerConverter");
        HotDeployConverterBuilderImpl builder = new HotDeployConverterBuilderImpl();
        builder.setContainer(getContainer());
        ConverterResource.setConverterBuilder(builder);
    }

    public void tearDownGetConverter() throws Exception {
        ConverterResource.setConverterBuilder(null);
    }

    public void testGetConverter() throws Exception {
        ConverterResource.addConverter("#{a.name}", "IntegerConverter");
        assertNotNull(ConverterResource.getConverter("#{a.name}"));
        assertTrue(ConverterResource.getConverter("#{a.name}") instanceof IntegerConverter);
    }

    public void setUpGetConverter2() throws Exception {
        register(HogeBigDecimalConverter.class, "hogeBigDecimalConverter");
        HotDeployConverterBuilderImpl builder = new HotDeployConverterBuilderImpl();
        builder.setContainer(getContainer());
        ConverterResource.setConverterBuilder(builder);
    }

    public void tearDownGetConverter2() throws Exception {
        ConverterResource.setConverterBuilder(null);
    }

    public void testGetConverter2() throws Exception {
        Map properties = new HashMap();
        properties.put("pattern", "YYYY/MM/dd");
        ConverterResource.addConverter("#{a.name}", "hogeBigDecimalConverter",
                properties);
        assertNotNull(ConverterResource.getConverter("#{a.name}"));
        assertTrue(ConverterResource.getConverter("#{a.name}") instanceof HogeBigDecimalConverter);
        HogeBigDecimalConverter dv = (HogeBigDecimalConverter) ConverterResource
                .getConverter("#{a.name}");
        assertEquals("YYYY/MM/dd", dv.getPattern());
    }

    public void setUpGetConverter3() throws Exception {
        register(HogeBigDecimalConverter.class, "hogeBigDecimalConverter");
        NormalConverterBuilderImpl builder = new NormalConverterBuilderImpl();
        builder.setContainer(getContainer());
        ConverterResource.setConverterBuilder(builder);
    }

    public void tearDownGetConverter3() throws Exception {
        ConverterResource.setConverterBuilder(null);
    }

    public void testGetConverter3() throws Exception {
        Map properties = new HashMap();
        properties.put("pattern", "yyyy/MM/dd");
        ConverterResource.addConverter("#{a.name}", "hogeBigDecimalConverter",
                properties);
        assertNotNull(ConverterResource.getConverter("#{a.name}"));
        assertTrue(ConverterResource.getConverter("#{a.name}") instanceof HogeBigDecimalConverter);
        HogeBigDecimalConverter dv = (HogeBigDecimalConverter) ConverterResource
                .getConverter("#{a.name}");
        assertEquals("yyyy/MM/dd", dv.getPattern());
        assertTrue(dv.NUM == 1);

        HogeBigDecimalConverter dv2 = (HogeBigDecimalConverter) ConverterResource
                .getConverter("#{a.name}");
        assertEquals("yyyy/MM/dd", dv2.getPattern());
        assertTrue(dv.NUM == 2);
    }

    public static class HogeBigDecimalConverter extends BigDecimalConverter {

        private String pattern;

        public int NUM = 0;

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        public String getPattern() {
            NUM++;
            return pattern;
        }

    }
}
