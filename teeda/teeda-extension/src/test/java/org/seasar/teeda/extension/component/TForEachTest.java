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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBaseTest;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
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

    public void testProcessValidators_ItemTypeWithoutDefaultConstructor()
            throws Exception {
        TForEach forEach = new TForEach();
        final Foo foo = new Foo(
                new Integer[] { new Integer(1), new Integer(2) });
        MockFacesContext context = getFacesContext();
        context.getApplication().setVariableResolver(new VariableResolver() {
            public Object resolveVariable(FacesContext context, String name)
                    throws EvaluationException {
                return foo;
            }
        });
        forEach.setItemsName("numItems");
        forEach.processValidators(context);
        assertEquals(new Integer(0), foo.getNum());
    }

    public void testProcessValidators_ItemTypeWithoutDefaultConstructorPrimitive()
            throws Exception {
        TForEach forEach = new TForEach();
        final Foo2 foo2 = new Foo2(new int[] { 1, 2 });
        MockFacesContext context = getFacesContext();
        context.getApplication().setVariableResolver(new VariableResolver() {
            public Object resolveVariable(FacesContext context, String name)
                    throws EvaluationException {
                return foo2;
            }
        });
        forEach.setItemsName("numItems");
        forEach.processValidators(context);
        assertTrue(foo2.getNum() == 0);
    }

    public void testProcessValidators_rowAdd() throws Exception {
        TForEach forEach = new TForEach() {
            public String getClientId(FacesContext context) {
                return "hogeItems";
            }
        };
        forEach.setRowSize(1);
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
        context.addMessage("hogeItems:0", new FacesMessage("a", "b"));

        forEach.processValidators(context);

        Iterator messages = context.getMessages("hogeItems:0");
        assertNotNull(messages);
        FacesMessage fm = (FacesMessage) messages.next();
        assertNotNull(fm);
        System.out.println(fm.getDetail());
    }

    public void testProcessMapItem() throws Exception {
        final TForEach forEach = createTForEach();
        Map item = new HashMap();
        item.put("aaa", "111");
        item.put("bbb", "112");
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(HogePage1.class);
        HogePage1 hoge = new HogePage1();
        forEach.processMapItem(beanDesc, hoge, item);
        assertEquals("111", hoge.getAaa());
    }

    public void testProcessBeanItem() throws Exception {
        final TForEach forEach = createTForEach();
        HogePage2 item = new HogePage2();
        item.setAaa("111");
        item.setBbb("112");
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(HogePage1.class);
        HogePage1 page = new HogePage1();
        forEach.processBeanItem(beanDesc, page, item);
        assertEquals("111", page.getAaa());
    }

    public void testProcessItem() throws Exception {
        final TForEach forEach = createTForEach();
        Map item = new HashMap();
        item.put("aaa", "111");
        item.put("bbb", "112");
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(HogePage1.class);
        HogePage1 page = new HogePage1();
        forEach.setItemsName("fooItems");
        forEach.processItem(beanDesc, page, item, 1);
        assertEquals("111", page.getAaa());
        assertSame(item, page.getFoo());
        assertEquals(1, page.getFooIndex());
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

    public static class Foo {
        private Integer num;

        private Integer[] numItems;

        public Foo() {
        }

        public Foo(Integer[] numItems) {
            this.numItems = numItems;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public Integer[] getNumItems() {
            return numItems;
        }

        public void setNumItems(Integer[] numItems) {
            this.numItems = numItems;
        }

    }

    public static class Foo2 {
        int num;

        int[] numItems;

        public Foo2() {
        }

        public Foo2(int[] numItems) {
            this.numItems = numItems;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int[] getNumItems() {
            return numItems;
        }

        public void setNumItems(int[] numItems) {
            this.numItems = numItems;
        }
    }

    public static class HogePage1 {

        private String aaa;

        private Map foo;

        private int fooIndex;

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }

        public Map getFoo() {
            return foo;
        }

        public void setFoo(Map foo) {
            this.foo = foo;
        }

        public int getFooIndex() {
            return fooIndex;
        }

        public void setFooIndex(int fooIndex) {
            this.fooIndex = fooIndex;
        }
    }

    public static class HogePage2 {

        private String aaa;

        private String bbb;

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }

        public String getBbb() {
            return bbb;
        }

        public void setBbb(String bbb) {
            this.bbb = bbb;
        }
    }

}
