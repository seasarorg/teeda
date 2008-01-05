/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.taglib;

import javax.faces.el.ValueBinding;

import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 *
 */
public class UIComponentTagPropertyUtilTest extends TeedaTestCase {

    public void testSetComponentProperty_beanProperty() throws Exception {
        MockHogeUIComponent component = new MockHogeUIComponent();
        UIComponentTagPropertyUtil.setComponentProperty(component, "hoge",
                "HOGE");
        assertEquals("HOGE", component.getHoge());

        UIComponentTagPropertyUtil
                .setComponentProperty(component, "foo", "123");
        assertTrue(component.getFoo() instanceof Integer);
        assertTrue(component.getFoo().intValue() == 123);

        UIComponentTagPropertyUtil.setComponentProperty(component, "bar",
                "false");
        assertFalse(component.isBar());

        UIComponentTagPropertyUtil.setComponentProperty(component,
                "no_such_property", "aaa");
        assertEquals("aaa", component.getAttributes().get("no_such_property"));
    }

    public void testSetComponentProperty_ELProperty() throws Exception {
        MockHogeUIComponent component = new MockHogeUIComponent();
        UIComponentTagPropertyUtil.setComponentProperty(component, "hoge",
                "#{aaa.bbb}");
        ValueBinding vb = component.getValueBinding("hoge");
        assertNotNull(vb);
    }

    public static class MockHogeUIComponent extends MockUIComponent {

        private String hoge;

        private Integer foo;

        private boolean bar;

        public boolean isBar() {
            return bar;
        }

        public Integer getFoo() {
            return foo;
        }

        public String getHoge() {
            return hoge;
        }

        public void setBar(boolean bar) {
            this.bar = bar;
        }

        public void setFoo(Integer foo) {
            this.foo = foo;
        }

        public void setHoge(String hoge) {
            this.hoge = hoge;
        }

    }
}
