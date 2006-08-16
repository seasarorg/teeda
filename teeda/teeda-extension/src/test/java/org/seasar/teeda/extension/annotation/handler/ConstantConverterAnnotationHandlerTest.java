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
package org.seasar.teeda.extension.annotation.handler;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.internal.ConverterResource;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ConstantConverterAnnotationHandlerTest extends TeedaTestCase {

    public void testRegisterValidator_single() throws Exception {
        ComponentDef cd = new ComponentDefImpl(FooConverter.class,
                "fooConverter");
        cd.setInstanceDef(InstanceDefFactory.PROTOTYPE);
        getContainer().register(cd);
        ConstantConverterAnnotationHandler handler = new ConstantConverterAnnotationHandler();
        handler.registerConverters("hogeBean", HogeBean.class);
        FooConverter converter = (FooConverter) ConverterResource
                .getConverter("#{hogeBean.aaa}");
        assertEquals("bar", converter.getBbb());
    }

    public static class HogeBean {

        private int aaa = 0;

        public static final String aaa_CONVERTER = "#{'converter':'foo', 'bbb' : 'bar'}";

        public int getAaa() {
            return aaa;
        }

        public void setAaa(int aaa) {
            this.aaa = aaa;
        }
    }

    public static class FooConverter implements Converter {

        private String bbb;

        public String getBbb() {
            return bbb;
        }

        public void setBbb(String bbb) {
            this.bbb = bbb;
        }

        public Object getAsObject(FacesContext context, UIComponent component,
                String value) throws ConverterException {
            return null;
        }

        public String getAsString(FacesContext context, UIComponent component,
                Object value) throws ConverterException {
            return null;
        }

    }

}
