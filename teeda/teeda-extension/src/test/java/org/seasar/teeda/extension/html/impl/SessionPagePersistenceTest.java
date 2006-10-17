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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.convention.impl.NamingConventionImpl;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.LruHashMap;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.PageDescCache;
import org.seasar.teeda.extension.html.impl.page.Foo2Page;
import org.seasar.teeda.extension.html.impl.page.Foo3Page;
import org.seasar.teeda.extension.html.impl.page.FooPage;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author higa
 * @author manhole
 */
public class SessionPagePersistenceTest extends TeedaExtensionTestCase {

    public void testConvertDefaultPageData() throws Exception {
        Hoge hoge = new Hoge();
        hoge.setInt1(1);
        hoge.setInt2(new Integer(2));
        hoge.setBool1(true);
        hoge.setBool2(Boolean.TRUE);
        hoge.setStr("str");
        Timestamp timestamp = new Timestamp(new Date().getTime());
        hoge.setTimestamp(timestamp);
        hoge.setList(new ArrayList());
        hoge.setStrArray(new String[0]);
        SessionPagePersistence persistence = new SessionPagePersistence();
        persistence.setNamingConvention(new NamingConventionImpl() {

            public Class fromComponentNameToClass(String componentName) {
                return Hoge.class;
            }

            public String fromPathToPageName(String path) {
                return "hoge";
            }

        });
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(Hoge.class);
        Set nextPageProperties = persistence.getNextPageProperties(null);
        Map map = persistence.convertDefaultPageData(getFacesContext(),
                beanDesc, hoge, nextPageProperties);
        assertEquals(new Integer(1), map.get("int1"));
        assertEquals(new Integer(2), map.get("int2"));
        assertEquals(Boolean.TRUE, map.get("bool1"));
        assertEquals(Boolean.TRUE, map.get("bool2"));
        assertEquals("str", map.get("str"));
        assertEquals(timestamp, map.get("timestamp"));
        // TODO https://www.seasar.org/issues/browse/TEEDA-88
        // でListも対応する際に、このassertは逆転する。
        assertNotNull(map.get("list"));
        assertNotNull(map.get("strArray"));
    }

    public void testConvertIncludePageData() throws Exception {
        Hoge hoge = new Hoge();
        hoge.setInt1(1);
        hoge.setInt2(new Integer(2));
        SessionPagePersistence persistence = new SessionPagePersistence();
        persistence.setNamingConvention(new NamingConventionImpl() {

            public Class fromComponentNameToClass(String componentName) {
                return Hoge.class;
            }

            public String fromPathToPageName(String path) {
                return "hoge";
            }

        });
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(Hoge.class);
        Set nextPageProperties = persistence.getNextPageProperties(null);
        Map map = persistence.convertIncludePageData(getFacesContext(),
                beanDesc, hoge, new String[] { "int1" }, nextPageProperties);
        assertEquals(new Integer(1), map.get("int1"));
        assertNull(map.get("int2"));
        assertEquals(1, map.size());
    }

    public void testConvertExcludePageData() throws Exception {
        Hoge hoge = new Hoge();
        hoge.setInt1(1);
        hoge.setInt2(new Integer(2));
        hoge.setBool1(true);
        hoge.setBool2(Boolean.TRUE);
        hoge.setStr("str");
        Timestamp timestamp = new Timestamp(new Date().getTime());
        hoge.setTimestamp(timestamp);
        hoge.setList(new ArrayList());
        hoge.setStrArray(new String[0]);
        SessionPagePersistence persistence = new SessionPagePersistence();
        persistence.setNamingConvention(new NamingConventionImpl() {

            public Class fromComponentNameToClass(String componentName) {
                return Hoge.class;
            }

            public String fromPathToPageName(String path) {
                return "hoge";
            }

        });
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(Hoge.class);
        Set nextPageProperties = persistence.getNextPageProperties(null);
        Map map = persistence.convertExcludePageData(getFacesContext(),
                beanDesc, hoge, new String[] { "int1" }, nextPageProperties);
        assertNull(map.get("int1"));
        assertEquals(new Integer(2), map.get("int2"));
        assertEquals(Boolean.TRUE, map.get("bool1"));
        assertEquals(Boolean.TRUE, map.get("bool2"));
        assertEquals("str", map.get("str"));
        assertEquals(timestamp, map.get("timestamp"));
        // TODO https://www.seasar.org/issues/browse/TEEDA-88
        // でListも対応する際に、このassertは逆転する。
        assertNotNull(map.get("list"));
        assertNotNull(map.get("strArray"));
    }

    public void testSaveAndRestore() throws Exception {
        SessionPagePersistence spp = new SessionPagePersistence();
        spp.setPageSize(2);
        NamingConventionImpl convention = new NamingConventionImpl();
        convention.addRootPackageName("org.seasar.teeda.extension.html.impl");
        String rootPath = "/"
                + ClassUtil.getPackageName(getClass()).replace('.', '/');
        convention.setViewRootPath(rootPath);
        convention.setViewExtension(".html");
        spp.setNamingConvention(convention);
        PageDescCacheImpl pageDescCache = new PageDescCacheImpl();
        pageDescCache.setNamingConvention(convention);
        pageDescCache.setContainer(getContainer());
        spp.setPageDescCache(pageDescCache);
        ActionDescCacheImpl actionDescCache = new ActionDescCacheImpl();
        actionDescCache.setNamingConvention(convention);
        actionDescCache.setContainer(getContainer());
        spp.setActionDescCache(actionDescCache);
        register(FooPage.class, "fooPage");
        register(Foo2Page.class, "foo2Page");
        register(Foo3Page.class, "foo3Page");
        String path = rootPath + "/foo.html";
        String path2 = rootPath + "/foo2.html";
        String path3 = rootPath + "/foo3.html";
        pageDescCache.createPageDesc(path);
        pageDescCache.createPageDesc(path2);
        pageDescCache.createPageDesc(path3);
        FacesContext context = getFacesContext();
        context.getViewRoot().setViewId(path);
        spp.save(context, path2);
        ExternalContext extCtx = context.getExternalContext();
        Map sessionMap = extCtx.getSessionMap();
        LruHashMap lru = (LruHashMap) sessionMap
                .get(SessionPagePersistence.class.getName());
        assertNotNull(lru);
        assertEquals(1, lru.size());

        context.getViewRoot().setViewId(path2);
        spp.save(context, path3);
        assertEquals(2, lru.size());

        Foo3Page foo3Page = (Foo3Page) getComponent(Foo3Page.class);
        foo3Page.setAaa("123");
        context.getViewRoot().setViewId(path3);
        spp.save(context, path);
        assertEquals(2, lru.size());
        assertNull(lru.get(path2));

        getExternalContext().getRequestParameterMap().put("redirect", "true");
        spp.restore(context, path);
        Map requestMap = extCtx.getRequestMap();
        assertEquals("123", requestMap.get("aaa"));
    }

    public void testSaveAndRestore1() throws Exception {
        // ## Assert ##
        final String fromViewId = "/fromViewId";
        final String toViewId = "/fooViewId";
        final MockPageDescCache pageDescCache = new MockPageDescCache();
        final PageDesc fromPageDesc = createPageDesc(FromPage.class, fromViewId);
        final PageDesc toPageDesc = createPageDesc(ToPage.class, toViewId);
        pageDescCache.putPageDesc(fromViewId, fromPageDesc);
        pageDescCache.putPageDesc(toViewId, toPageDesc);
        final SessionPagePersistence pagePersistence = new SessionPagePersistence();
        final NamingConvention namingConvention = new NamingConventionImpl() {
            public Class fromComponentNameToClass(String componentName) {
                return ToPage.class;
            }

            public String fromPathToPageName(String path) {
                return toViewId;
            }

        };
        pagePersistence.setNamingConvention(namingConvention);
        pagePersistence.setPageDescCache(pageDescCache);
        ActionDescCacheImpl actionDescCache = new ActionDescCacheImpl();
        actionDescCache.setNamingConvention(namingConvention);
        actionDescCache.setContainer(getContainer());
        pagePersistence.setActionDescCache(actionDescCache);
        final FacesContext context = getFacesContext();
        context.getViewRoot().setViewId(fromViewId);

        final FromPage fromPage = (FromPage) getComponent(FromPage.class);
        fromPage.setAaaaa(new String[] { "a", "bb", "c" });
        fromPage.setB(true);
        fromPage.setFromOnly("from");
        fromPage.setName("ccc");
        fromPage.setNum(2);
        fromPage.setPostback(true);
        fromPage.setPreviousViewId("/aaaViewId");
        final Map requestMap = context.getExternalContext().getRequestMap();
        assertNull(requestMap.get("aaaaa"));

        // ## Act ##
        pagePersistence.save(context, toViewId);
        getExternalContext().getRequestParameterMap().put("redirect", "true");
        pagePersistence.restore(context, toViewId);

        // ## Assert ##
        final String[] aaaaa = (String[]) requestMap.get("aaaaa");
        assertNotNull(aaaaa);
        assertEquals(3, aaaaa.length);
        assertEquals("a", aaaaa[0]);
        assertEquals("bb", aaaaa[1]);
        assertEquals("c", aaaaa[2]);
        assertTrue(((Boolean) requestMap.get("b")).booleanValue() == true);
        assertNull(requestMap.get("fromOnly"));
        assertEquals("ccc", requestMap.get("name"));
        assertTrue(((Integer) requestMap.get("num")).intValue() == 2);
        assertEquals(null, requestMap.get("previousViewId"));
        assertEquals(null, requestMap.get("postback"));
    }

    public void testSaveAndRestore2() throws Exception {
        // ## Assert ##
        final String fromViewId = "/fromViewId";
        final String toViewId = "/fooViewId";
        final MockPageDescCache pageDescCache = new MockPageDescCache();
        final PageDesc fromPageDesc = createPageDesc(From2Page.class,
                fromViewId);
        final PageDesc toPageDesc = createPageDesc(To2Page.class, toViewId);
        pageDescCache.putPageDesc(fromViewId, fromPageDesc);
        pageDescCache.putPageDesc(toViewId, toPageDesc);
        final SessionPagePersistence pagePersistence = new SessionPagePersistence();
        final NamingConvention namingConvention = new NamingConventionImpl() {
            public Class fromComponentNameToClass(String componentName) {
                return To2Page.class;
            }

            public String fromPathToPageName(String path) {
                return toViewId;
            }

        };
        pagePersistence.setNamingConvention(namingConvention);
        pagePersistence.setPageDescCache(pageDescCache);
        ActionDescCacheImpl actionDescCache = new ActionDescCacheImpl();
        actionDescCache.setNamingConvention(namingConvention);
        actionDescCache.setContainer(getContainer());
        pagePersistence.setActionDescCache(actionDescCache);
        final FacesContext context = getFacesContext();
        context.getViewRoot().setViewId(fromViewId);

        final From2Page fromPage = (From2Page) getComponent(From2Page.class);
        fromPage.setAaaaa(new String[] { "a", "bb", "c" });
        fromPage.setB(true);
        fromPage.setName("ccc");
        fromPage.setNum(2);
        String[][] ccc = new String[1][1];
        ccc[0][0] = "bar";
        fromPage.setCcc(ccc);
        final Map requestMap = context.getExternalContext().getRequestMap();
        assertNull(requestMap.get("aaaaa"));

        // ## Act ##
        pagePersistence.save(context, toViewId);
        getExternalContext().getRequestParameterMap().put("redirect", "true");
        pagePersistence.restore(context, toViewId);

        // ## Assert ##
        final String[] aaaaa = (String[]) requestMap.get("aaaaa");
        assertNotNull(aaaaa);
        assertEquals(3, aaaaa.length);
        assertEquals("a", aaaaa[0]);
        assertEquals("bb", aaaaa[1]);
        assertEquals("c", aaaaa[2]);
        assertTrue(((Boolean) requestMap.get("b")).booleanValue() == true);
        assertEquals("ccc", requestMap.get("name"));
        assertTrue(((Integer) requestMap.get("num")).intValue() == 2);
        assertNotNull(requestMap.get("ccc"));
        assertTrue(requestMap.get("ccc") instanceof String[][]);
    }

    public void testGetSubApplicationPath() throws Exception {
        getFacesContext().getViewRoot().setViewId("/view/emp/empConfirm.html");
        assertEquals("/view/emp", SessionPagePersistence
                .getSubApplicationPath(getFacesContext()));
    }

    public void testRemoveSubApplicationPages() throws Exception {
        LruHashMap lru = new LruHashMap(10);
        lru.put("/view/dept/deptList.html", "hoge");
        lru.put("/view/emp/empList.html", "hoge");
        Map sessionMap = getExternalContext().getSessionMap();
        sessionMap.put(SessionPagePersistence.class.getName(), lru);
        getFacesContext().getViewRoot().setViewId("/view/emp/empConfirm.html");
        SessionPagePersistence pagePersistence = new SessionPagePersistence();
        pagePersistence.removeSubApplicationPages(getFacesContext());
        assertNull(lru.get("/view/emp/empList.html"));
        assertNotNull(lru.get("/view/dept/deptList.html"));
    }

    private static class MockPageDescCache implements PageDescCache {
        Map cache = new HashMap();

        public void putPageDesc(String viewId, Object PageDesc) {
            cache.put(viewId, PageDesc);
        }

        public PageDesc getPageDesc(String viewId) {
            return (PageDesc) cache.get(viewId);
        }

        public PageDesc createPageDesc(String viewId) {
            return null;
        }
    }

    public static class Hoge {
        private int int1;

        private Integer int2;

        private boolean bool1;

        private Boolean bool2;

        private String str;

        private Timestamp timestamp;

        private List list;

        private String[] strArray;

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

        /**
         * @return Returns the strArray.
         */
        public String[] getStrArray() {
            return strArray;
        }

        /**
         * @param strArray The strArray to set.
         */
        public void setStrArray(String[] strArray) {
            this.strArray = strArray;
        }
    }

    public static class FromPage {
        private String[] aaaaa;

        private String name;

        private int num;

        private boolean b;

        private boolean postback;

        private String previousViewId;

        private String fromOnly;

        public String getFromOnly() {
            return fromOnly;
        }

        public void setFromOnly(String fromOnly) {
            this.fromOnly = fromOnly;
        }

        public boolean isB() {
            return b;
        }

        public String getName() {
            return name;
        }

        public int getNum() {
            return num;
        }

        public void setB(boolean b) {
            this.b = b;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String[] getAaaaa() {
            return aaaaa;
        }

        public void setAaaaa(String[] aaaaa) {
            this.aaaaa = aaaaa;
        }

        public boolean isPostback() {
            return postback;
        }

        public void setPostback(boolean postback) {
            this.postback = postback;
        }

        public String getPreviousViewId() {
            return previousViewId;
        }

        public void setPreviousViewId(String previousViewId) {
            this.previousViewId = previousViewId;
        }
    }

    public static class ToPage {
        private String[] aaaaa;

        private String name;

        private int num;

        private boolean b;

        private boolean postback;

        private String previousViewId;

        private String toOnly = "toOnly";

        public String getToOnly() {
            return toOnly;
        }

        public void setToOnly(String toOnly) {
            this.toOnly = toOnly;
        }

        public boolean isB() {
            return b;
        }

        public String getName() {
            return name;
        }

        public int getNum() {
            return num;
        }

        public void setB(boolean b) {
            this.b = b;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String[] getAaaaa() {
            return aaaaa;
        }

        public void setAaaaa(String[] aaaaa) {
            this.aaaaa = aaaaa;
        }

        public boolean isPostback() {
            return postback;
        }

        public void setPostback(boolean postback) {
            this.postback = postback;
        }

        public String getPreviousViewId() {
            return previousViewId;
        }

        public void setPreviousViewId(String previousViewId) {
            this.previousViewId = previousViewId;
        }

    }

    public static abstract class AbstractAaaPage {
        private String[] aaaaa;

        private String name;

        private int num;

        private boolean b;

        private String[][] ccc;

        public String[][] getCcc() {
            return ccc;
        }

        public void setCcc(String[][] ccc) {
            this.ccc = ccc;
        }

        public boolean isB() {
            return b;
        }

        public String getName() {
            return name;
        }

        public int getNum() {
            return num;
        }

        public void setB(boolean b) {
            this.b = b;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String[] getAaaaa() {
            return aaaaa;
        }

        public void setAaaaa(String[] aaaaa) {
            this.aaaaa = aaaaa;
        }
    }

    public static class From2Page extends AbstractAaaPage {

    }

    public static class To2Page extends AbstractAaaPage {

    }

}