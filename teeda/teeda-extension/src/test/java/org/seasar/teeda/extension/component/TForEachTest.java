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
package org.seasar.teeda.extension.component;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBaseTest;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

import org.seasar.teeda.core.mock.MockFacesContext;

/**
 * @author higa
 * @author manhole
 */
public class TForEachTest extends UIComponentBaseTest {

    public void testGetItemName() throws Exception {
        TForEach component = createTForEach();
        component.setItemsName("fooItems");
        assertEquals("foo", component.getItemName());
    }

    public void testGetItemIndex() throws Exception {
        TForEach component = createTForEach();
        component.setItemsName("fooItems");
        assertEquals("fooIndex", component.getIndexName());
    }

    public void testProcessUpdate() throws Exception {
        TForEach forEach = new TForEach();
        final Hoge hoge = new Hoge();
        hoge.setName("aaa");
        MockFacesContext context = getFacesContext();
        context.getApplication().setVariableResolver(new VariableResolver() {
            public Object resolveVariable(FacesContext context, String name)
                    throws EvaluationException {
                return hoge;
            }
        });
        forEach.setItemsName("nameItems");
        forEach.processUpdates(context);
        assertNull(hoge.getNameItems());
    }

    private TForEach createTForEach() {
        return (TForEach) createUIComponent();
    }

    protected UIComponent createUIComponent() {
        return new TForEach();
    }

    public static class Hoge {
        private String name;
        
        private List nameItems;

        public List getNameItems() {
            return nameItems;
        }

        public void setNameItems(List nameItems) {
            this.nameItems = nameItems;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
