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

import junit.framework.TestCase;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * @author higa
 *
 */
public class TForEachRendererTest extends TestCase {

    public void testProcessMapItem() throws Exception {
        TForEachRenderer renderer = new TForEachRenderer();
        Map map = new HashMap();
        map.put("aaa", "111");
        map.put("bbb", "111");
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(Hoge.class);
        Hoge hoge = new Hoge();
        renderer.processMapItem(beanDesc, hoge, map);
        assertEquals("111", hoge.getAaa());
    }

    public void testProcessBeanItem() throws Exception {
        TForEachRenderer renderer = new TForEachRenderer();
        Hoge2 hoge2 = new Hoge2();
        hoge2.setAaa("111");
        hoge2.setBbb("111");
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(Hoge.class);
        Hoge hoge = new Hoge();
        renderer.processBeanItem(beanDesc, hoge, hoge2);
        assertEquals("111", hoge.getAaa());
    }

    public void testProcessItem() throws Exception {
        TForEachRenderer renderer = new TForEachRenderer();
        Map map = new HashMap();
        map.put("aaa", "111");
        map.put("bbb", "111");
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(Hoge.class);
        Hoge hoge = new Hoge();
        renderer.processItem(beanDesc, hoge, map, "foo", 1, "fooIndex");
        assertEquals("111", hoge.getAaa());
        assertSame(map, hoge.getFoo());
        assertEquals(1, hoge.getFooIndex());
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