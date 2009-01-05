/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.util;

import java.util.List;

import javax.faces.component.UICommand;
import javax.faces.component.UIParameter;

import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class UIParameterUtilTest extends TeedaTestCase {

    public void testSaveParametersToRequest() throws Exception {
        // # Arrange #
        UICommand command = new UICommand();
        List childrenList = command.getChildren();
        UIParameter parameter1 = new UIParameter();
        parameter1.setName("aaa");
        parameter1.setValue("AAA");
        childrenList.add(parameter1);
        UIParameter parameter2 = new UIParameter();
        parameter2.setName("bbb");
        parameter2.setValue("BBB");
        childrenList.add(parameter2);

        // # Act #
        UIParameterUtil.saveParametersToRequest(command, getFacesContext());

        // # Assert #
        assertEquals("AAA", getFacesContext().getExternalContext()
                .getRequestMap().get("aaa"));
        assertEquals("BBB", getFacesContext().getExternalContext()
                .getRequestMap().get("bbb"));
    }

    public void testSaveParameterToInstance() throws Exception {
        // # Arrange #
        UICommand command = new UICommand();
        List childrenList = command.getChildren();
        UIParameter parameter1 = new UIParameter();
        parameter1.setName("aaa");
        parameter1.setValue("AAA");
        childrenList.add(parameter1);
        UIParameter parameter2 = new UIParameter();
        parameter2.setName("bbb");
        parameter2.setValue("2");
        childrenList.add(parameter2);
        Hoge hoge = new Hoge();

        // # Act #
        UIParameterUtil.saveParametersToInstance(command, hoge);

        // # Assert #
        assertEquals("AAA", hoge.getAaa());
        assertTrue(hoge.getBbb() == 2);
    }

    public static class Hoge {
        private String aaa;

        private int bbb;

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }

        public int getBbb() {
            return bbb;
        }

        public void setBbb(int bbb) {
            this.bbb = bbb;
        }
    }
}
