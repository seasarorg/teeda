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
package org.seasar.teeda.extension.render;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.teeda.extension.component.TForEach;

/**
 * @author higa
 * @author manhole
 */
public class TForEachRendererTest extends RendererTest {

    public void testDecode() throws Exception {
        TForEachRenderer renderer = new TForEachRenderer();
        FacesContext context = getFacesContext();
        Map param = context.getExternalContext().getRequestParameterMap();
        param.put("hogeItems-1:0:aaa", "aaa");
        param.put("hogeItems-1:1:aaa", "bbb");
        param.put("hogeItems-1:2:aaa", "ccc");
        TForEach t = new TForEach() {

            public String getClientId(FacesContext context) {
                return "hogeItems";
            }
            
        };
        renderer.decode(context, t);
        assertTrue(t.getRowSize() == 3);
    }
    
    public void testProcessMapItem() throws Exception {
        TForEachRenderer renderer = new TForEachRenderer();
        Map item = new HashMap();
        item.put("aaa", "111");
        item.put("bbb", "112");
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(Hoge.class);
        Hoge hoge = new Hoge();
        renderer.processMapItem(beanDesc, hoge, item);
        assertEquals("111", hoge.getAaa());
    }

    public void testProcessBeanItem() throws Exception {
        TForEachRenderer renderer = new TForEachRenderer();
        Hoge2 item = new Hoge2();
        item.setAaa("111");
        item.setBbb("112");
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(Hoge.class);
        Hoge page = new Hoge();
        renderer.processBeanItem(beanDesc, page, item);
        assertEquals("111", page.getAaa());
    }

    public void testProcessItem() throws Exception {
        TForEachRenderer renderer = new TForEachRenderer();
        Map item = new HashMap();
        item.put("aaa", "111");
        item.put("bbb", "112");
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(Hoge.class);
        Hoge page = new Hoge();
        renderer.processItem(beanDesc, page, item, "foo", 1, "fooIndex");
        assertEquals("111", page.getAaa());
        assertSame(item, page.getFoo());
        assertEquals(1, page.getFooIndex());
    }

    protected Renderer createRenderer() {
        return new TForEachRenderer();
    }

    public static class Hoge {

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

    public static class Hoge2 {

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
