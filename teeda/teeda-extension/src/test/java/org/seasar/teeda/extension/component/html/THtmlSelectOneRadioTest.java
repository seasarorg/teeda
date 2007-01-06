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
package org.seasar.teeda.extension.component.html;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponent;
import org.seasar.teeda.extension.component.TUISelectItems;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author shot
 */
public class THtmlSelectOneRadioTest extends TeedaExtensionTestCase {

    public void testValidate() throws Exception {
        THtmlSelectOneRadio t = new THtmlSelectOneRadio();
        t.setId("aaa");
        MockUIComponent parent = new MockUIComponent();
        THtmlItemsSaveHidden hidden = new THtmlItemsSaveHidden();
        hidden.setId("aaaItemsSave");
        List list = new ArrayList();
        HogeDto dto = new HogeDto();
        dto.setValue("aaa");
        list.add(dto);
        hidden.setValue(list);
        parent.getChildren().add(hidden);
        t.setParent(parent);
        MockFacesContext context = getFacesContext();
        t.validate(context);
        TUISelectItems items = (TUISelectItems) t.getChildren().get(0);
        assertNotNull(items.getValue());
        Object target = items.getValue();
        assertTrue(target instanceof List);
        SelectItem d = (SelectItem) ((List) target).get(0);
        assertEquals("aaa", d.getValue());
    }

    public static class HogeDto {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }
}
