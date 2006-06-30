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
package org.seasar.teeda.extension.html.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.seasar.framework.util.ClassUtil;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.extension.html.impl.SessionPagePersistence.PersistenceData;

/**
 * @author higa
 *
 */
public class SessionPagePersistenceTest extends TeedaTestCase {

    public void testConvertPageData() throws Exception {
        Hoge hoge = new Hoge();
        hoge.setInt1(1);
        hoge.setInt2(new Integer(2));
        hoge.setBool1(true);
        hoge.setBool2(Boolean.TRUE);
        hoge.setStr("str");
        Timestamp timestamp = new Timestamp(new Date().getTime());
        hoge.setTimestamp(timestamp);
        hoge.setList(new ArrayList());
        Map map = SessionPagePersistence.convertPageData(hoge);
        assertEquals(new Integer(1), map.get("int1"));
        assertEquals(new Integer(2), map.get("int2"));
        assertEquals(Boolean.TRUE, map.get("bool1"));
        assertEquals(Boolean.TRUE, map.get("bool2"));
        assertEquals("str", map.get("str"));
        assertEquals(timestamp, map.get("timestamp"));
        assertNull(map.get("list"));
    }
    
    public void testSaveAndRestore() throws Exception {
        SessionPagePersistence spp = new SessionPagePersistence();
        spp.setPageSize(2);
        DefaultHtmlAutoNaming naming = new DefaultHtmlAutoNaming();
        String rootPath = "/" + ClassUtil.getPackageName(getClass()).replace('.', '/');
        naming.setHtmlRootPath(rootPath);
        PageDescCacheImpl cache = new PageDescCacheImpl();
        cache.setHtmlAutoNaming(naming);
        cache.setContainer(getContainer());
        spp.setPageDescCache(cache);
        register(FooPage.class, "fooPage");
        register(Foo2Page.class, "foo2Page");
        register(Foo3Page.class, "foo3Page");
        String path = rootPath + "/foo.html";
        String path2 = rootPath + "/foo2.html";
        String path3 = rootPath + "/foo3.html";
        cache.createPageDesc(path);
        cache.createPageDesc(path2);
        cache.createPageDesc(path3);
        FacesContext context = getFacesContext();
        spp.save(context, path);
        ExternalContext extCtx = context.getExternalContext();
        Map sessionMap = extCtx.getSessionMap();
        PersistenceData pd = (PersistenceData) sessionMap.get(SessionPagePersistence.class.getName());
        assertNotNull(pd);
        assertEquals(1, pd.getSize());
        
        spp.save(context, path2);
        assertEquals(2, pd.getSize());
        
        Foo3Page foo3Page = (Foo3Page) getComponent(Foo3Page.class);
        foo3Page.setAaa("123");
        spp.save(context, path3);
        assertEquals(2, pd.getSize());
        assertNull(pd.get(path));
        
        spp.restore(context, path3);
        Map requestMap = extCtx.getRequestMap();
        assertEquals("123", requestMap.get("aaa"));
    }
    
    public static class Hoge {
        private int int1;
        private Integer int2;
        private boolean bool1;
        private Boolean bool2;
        private String str;
        private Timestamp timestamp;
        private List list;
        public boolean isBool1() {
            return bool1;
        }
        public void setBool1(boolean bool1) {
            this.bool1 = bool1;
        }
        public Boolean getBool2() {
            return bool2;
        }
        public void setBool2(Boolean bool2) {
            this.bool2 = bool2;
        }
        public int getInt1() {
            return int1;
        }
        public void setInt1(int int1) {
            this.int1 = int1;
        }
        public Integer getInt2() {
            return int2;
        }
        public void setInt2(Integer int2) {
            this.int2 = int2;
        }
        public List getList() {
            return list;
        }
        public void setList(List list) {
            this.list = list;
        }
        public String getStr() {
            return str;
        }
        public void setStr(String str) {
            this.str = str;
        }
        public Timestamp getTimestamp() {
            return timestamp;
        }
        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }
        
    }
}