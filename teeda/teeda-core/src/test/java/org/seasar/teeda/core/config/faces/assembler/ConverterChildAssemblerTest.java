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
package org.seasar.teeda.core.config.faces.assembler;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.ApplicationFactory;
import javax.faces.convert.Converter;
import javax.faces.internal.FactoryFinderUtil;

import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.application.ApplicationImpl;
import org.seasar.teeda.core.application.ConfigurationSupport;
import org.seasar.teeda.core.application.ConverterConfiguration;
import org.seasar.teeda.core.application.impl.DefaultComponentLookupStrategy;
import org.seasar.teeda.core.config.faces.element.ConverterElement;
import org.seasar.teeda.core.config.faces.element.PropertyElement;
import org.seasar.teeda.core.config.faces.element.impl.ConverterElementImpl;
import org.seasar.teeda.core.config.faces.element.impl.PropertyElementImpl;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ConverterChildAssemblerTest extends TeedaTestCase {

    public void testAssemble_withConverterId() throws Exception {
        ConverterElement cElement = new ConverterElementImpl();
        cElement.setConverterClass("org.seasar.teeda.core.mock.MockConverter");
        cElement.setConverterId("id");
        Map map = new HashMap();
        map.put(cElement.getConverterId(), cElement);
        MockConverterChildAssemblerForId assembler = new MockConverterChildAssemblerForId(
                map);

        assembler.assemble();

        Converter c = getApplication().createConverter("id");

        assertNotNull(c);
        assertTrue(c instanceof MockConverter);
    }

    public void testAssemble_withConverterForClass() throws Exception {
        ConverterElement cElement = new ConverterElementImpl();
        cElement.setConverterClass("org.seasar.teeda.core.mock.MockConverter");
        cElement.setConverterForClass(Hoge.class.getName());
        Map map = new HashMap();
        map.put(cElement.getConverterForClass(), cElement);
        MockConverterChildAssembler2 assembler = new MockConverterChildAssembler2(
                map);

        assembler.assemble();

        Converter c = getApplication().createConverter(Hoge.class);

        assertNotNull(c);
        assertTrue(c instanceof MockConverter);
    }

    public void testAssemble_withNoConverterId() throws Exception {
        ConverterElement cElement = new ConverterElementImpl();
        cElement.setConverterClass("org.seasar.teeda.core.mock.MockConverter");
        cElement.setConverterId("");
        Map map = new HashMap();
        map.put(cElement.getConverterId(), cElement);
        MockConverterChildAssemblerForId assembler = new MockConverterChildAssemblerForId(
                map);

        assembler.assemble();

        Converter c = getApplication().createConverter("");

        assertNull(c);
    }

    public void testAssemble_withConverterConfiguration() throws Exception {
        // TODO
        MockApplication previous = getApplication();
        ApplicationFactory appFactory = FactoryFinderUtil
                .getApplicationFactory();
        try {
            ApplicationImpl app = new ApplicationImpl();
            app
                    .setComponentLookupStrategy(new DefaultComponentLookupStrategy());
            appFactory.setApplication(app);
            ConverterElement cElement = new ConverterElementImpl();
            cElement
                    .setConverterClass("org.seasar.teeda.core.mock.MockConverter");
            cElement.setConverterId("id");
            PropertyElement pElement = new PropertyElementImpl();
            pElement.setPropertyName("name");
            pElement.setPropertyClass("String");
            pElement.setDefaultValue("hoge");
            cElement.addPropertyElement(pElement);
            Map map = new HashMap();
            map.put(cElement.getConverterId(), cElement);
            MockConverterChildAssemblerForId assembler = new MockConverterChildAssemblerForId(
                    map);

            assembler.assemble();

            Converter c = appFactory.getApplication().createConverter("id");

            assertNotNull(c);
            assertTrue(c instanceof MockConverter);
            assertEquals("hoge", ((MockConverter) c).getName());
        } finally {
            appFactory.setApplication(previous);
        }
    }

    private static class MockConverterChildAssemblerForId extends
            ConverterChildAssembler {

        public MockConverterChildAssemblerForId(Map targetMap) {
            super(targetMap);
        }

        protected void doAssemble(String converterId,
                ConverterElement converterElement) {
            String converterClass = converterElement.getConverterClass();
            if (!StringUtil.isEmpty(converterClass)) {
                getApplication().addConverter(converterId, converterClass);
            }
        }

        protected void doAddConverterConfiguration(
                ConfigurationSupport support, String converterKey,
                ConverterConfiguration configuration) {
            support.addConverterConfiguration(converterKey, configuration);
        }
    }

    private static class MockConverterChildAssembler2 extends
            ConverterChildAssembler {

        public MockConverterChildAssembler2(Map targetMap) {
            super(targetMap);
        }

        protected void doAssemble(String converterForClass,
                ConverterElement converterElement) {
            String converterClass = converterElement.getConverterClass();
            if (!StringUtil.isEmpty(converterClass)) {
                Class targetClazz = ClassUtil.forName(converterForClass);
                getApplication().addConverter(targetClazz, converterClass);
            }
        }

        protected void doAddConverterConfiguration(
                ConfigurationSupport support, String converterKey,
                ConverterConfiguration configuration) {
            // TODO Auto-generated method stub

        }
    }

    private static class Hoge {

    }

}
