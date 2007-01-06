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
package org.seasar.teeda.extension.component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FactoryFinder;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBaseTest;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;
import javax.faces.internal.FacesMessageResource;
import javax.faces.render.Renderer;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.container.impl.S2ContainerImpl;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.framework.mock.servlet.MockHttpServletResponseImpl;
import org.seasar.framework.mock.servlet.MockServletContextImpl;
import org.seasar.teeda.core.application.ApplicationFactoryImpl;
import org.seasar.teeda.core.application.ApplicationImpl;
import org.seasar.teeda.core.context.servlet.ServletExternalContextImpl;
import org.seasar.teeda.core.context.servlet.ServletFacesContextImpl;
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
        assertEquals("b(line : 1)", fm.getDetail());
    }

    public void testProcessValidators_rowMultipleAdd() throws Exception {
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
        context.addMessage("hogeItems:0", new FacesMessage("a", "b1"));
        context.addMessage("hogeItems:0", new FacesMessage("a", "b2"));

        forEach.processValidators(context);

        Iterator messages = context.getMessages("hogeItems:0");
        assertNotNull(messages);
        FacesMessage fm1 = (FacesMessage) messages.next();
        assertNotNull(fm1);
        assertEquals("b1(line : 1)", fm1.getDetail());

        FacesMessage fm2 = (FacesMessage) messages.next();
        assertNotNull(fm2);
        assertEquals("b2(line : 1)", fm2.getDetail());
    }

    protected void tearDown() throws Exception {
        FacesMessageResource.removeAll();
        FactoryFinder.releaseFactories();
        SingletonS2ContainerFactory.destroy();
    }

    public void testProcessValidators_rowAdd_FacesMessageCompression()
            throws Exception {
        TForEach forEach = new TForEach() {
            public String getClientId(FacesContext context) {
                return "hogeItems";
            }

            protected Renderer getRenderer(FacesContext context) {
                return new Renderer() {
                };
            }

        };
        forEach.setId("hogeItems");
        forEach.setPageName("hoge");
        forEach.setRowSize(1);
        final Hoge2 hoge2 = new Hoge2();
        hoge2.setName("aaa");
        MockServletContextImpl servletContext = new MockServletContextImpl(
                "s2-example");
        MockHttpServletRequestImpl request = servletContext
                .createRequest("/hello.html");
        MockHttpServletResponseImpl response = new MockHttpServletResponseImpl(
                request);
        ExternalContext extContext = new ServletExternalContextImpl(
                servletContext, request, response);
        SingletonS2ContainerFactory.setContainer(new S2ContainerImpl());
        SingletonS2ContainerFactory.getContainer().register(
                ApplicationImpl.class);
        FactoryFinder.setFactory(FactoryFinder.APPLICATION_FACTORY,
                ApplicationFactoryImpl.class.getName());
        ServletFacesContextImpl context = new ServletFacesContextImpl(
                extContext);
        context.getApplication().setVariableResolver(new VariableResolver() {
            public Object resolveVariable(FacesContext context, String name)
                    throws EvaluationException {
                return hoge2;
            }
        });
        forEach.setItemsName("nameItems");
        context.addMessage("hogeItems:0", new FacesMessage("a", "b1"));
        context.addMessage("hogeItems:0", new FacesMessage("a", "b2"));
        FacesMessageResource.addFacesMessage("#{hoge.hogeItems}",
                new FacesMessage("hoge", "foo"));

        forEach.processValidators(context);

        Iterator messages = context.getMessages("hogeItems:0");
        assertNotNull(messages);
        FacesMessage fm1 = (FacesMessage) messages.next();
        assertNotNull(fm1);
        assertEquals("foo", fm1.getDetail());
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

    public static class Hoge2 {

        public static final String nameItems_MESSAGE_AGGREGATION = "aaa";

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
