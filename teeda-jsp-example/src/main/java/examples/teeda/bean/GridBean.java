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
package examples.teeda.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author manhole
 */
public class GridBean {

    private Item[] fooItems;

    private Integer fooIndex;

    public Item[] getFooItems() {
        if (fooItems == null) {
            List l = new ArrayList();
            l.add(createItem("aaa1", "bbbb1", "ccccc1"));
            l.add(createItem("aaa2", "bbbb2", "ccccc2"));
            l.add(createItem("aaa3", "bbbb3", "ccccc3"));
            l.add(createItem("aaa4", "bbbb4", "ccccc4"));
            l.add(createItem("aaa5", "bbbb5", "ccccc5"));
            l.add(createItem("aaa6", "bbbb6", "ccccc6"));
            l.add(createItem("aaa7", "bbbb7", "ccccc7"));
            fooItems = (Item[]) l.toArray(new Item[l.size()]);
        }
        return fooItems;
    }

    public void setFooItems(Item[] fooItems) {
        this.fooItems = fooItems;
    }

    private Item createItem(String aaa, String bbb, String ccc) {
        Item item = new Item();
        item.setAaa(aaa);
        item.setBbb(bbb);
        item.setCcc(ccc);
        item.setDdd(ccc + ", " + bbb + ", " + aaa);
        return item;
    }

    public static class Item {

        private String aaa;

        private String bbb;

        private String ccc;

        private String ddd;

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

        public String getCcc() {
            return ccc;
        }

        public void setCcc(String ccc) {
            this.ccc = ccc;
        }

        public String getDdd() {
            return ddd;
        }

        public void setDdd(String ddd) {
            this.ddd = ddd;
        }
    }

    private String aaa;

    private String bbb;

    private String ccc;

    private String ddd;

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

    public String getCcc() {
        return ccc;
    }

    public void setCcc(String ccc) {
        this.ccc = ccc;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public Integer getFooIndex() {
        return fooIndex;
    }

    public void setFooIndex(Integer fooIndex) {
        this.fooIndex = fooIndex;
    }

}
