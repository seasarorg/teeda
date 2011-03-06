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

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.internal.FacesMessageResource;

import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class ConstantFacesMessageAnnotationHandlerTest extends TeedaTestCase {

    protected void tearDown() throws Exception {
        FacesMessageResource.removeAll();
    }

    public void testRegisterFacesMessage_useId() throws Exception {
        getApplication().setMessageBundle(
                "org.seasar.teeda.extension.annotation.handler.TestMessages");
        ConstantFacesMessageAnnotationHandler handler = new ConstantFacesMessageAnnotationHandler();
        getContainer().register(HogeBean.class, "hogeBean");
        handler.registerFacesMessages("hogeBean");
        FacesMessage fm = FacesMessageResource
                .getFacesMessage("#{hogeBean.aaaItems}");
        assertEquals("A_A_A", fm.getSummary());
    }

    public void testRegisterFacesMessage_useSummartAndDetailDirectly()
            throws Exception {
        getApplication().setMessageBundle(
                "org.seasar.teeda.extension.annotation.handler.TestMessages");
        ConstantFacesMessageAnnotationHandler handler = new ConstantFacesMessageAnnotationHandler();
        getContainer().register(Hoge2Bean.class, "hoge2Bean");
        handler.registerFacesMessages("hoge2Bean");
        FacesMessage fm = FacesMessageResource
                .getFacesMessage("#{hoge2Bean.aaaItems}");
        assertEquals("foo", fm.getSummary());
        assertEquals("bar", fm.getDetail());
    }

    public void testRegisterFacesMessage_allowNosuchFieldForGrid()
            throws Exception {
        ConstantFacesMessageAnnotationHandler handler = new ConstantFacesMessageAnnotationHandler();
        getContainer().register(Hoge3Bean.class, "hoge3Bean");
        handler.registerFacesMessages("hoge3Bean");
        FacesMessage fm = FacesMessageResource
                .getFacesMessage("#{hoge3Bean.hogeItems}");
        assertEquals("foo", fm.getSummary());
        assertEquals("bar", fm.getDetail());
    }

    public static class HogeBean {

        public static final String aaaItems_MESSAGE_AGGREGATION = "id=aaa";

        private List aaaItems;

        public List getAaaItems() {
            return aaaItems;
        }

        public void setAaaItems(List aaaItems) {
            this.aaaItems = aaaItems;
        }

    }

    public static class Hoge2Bean {

        public static final String aaaItems_MESSAGE_AGGREGATION = "summary=foo, detail=bar";

        private List aaaItems;

        public List getAaaItems() {
            return aaaItems;
        }

        public void setAaaItems(List aaaItems) {
            this.aaaItems = aaaItems;
        }

    }

    public static class Hoge3Bean {

        public static final String hogeItems_MESSAGE_AGGREGATION = "summary=foo, detail=bar";

        private List aaaItems;

        public List getAaaItems() {
            return aaaItems;
        }

        public void setAaaItems(List aaaItems) {
            this.aaaItems = aaaItems;
        }

    }

}
