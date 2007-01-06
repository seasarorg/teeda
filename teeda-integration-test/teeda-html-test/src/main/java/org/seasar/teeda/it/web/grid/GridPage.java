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
package org.seasar.teeda.it.web.grid;

import java.io.Serializable;
import java.math.BigDecimal;

public class GridPage {

    private FooItem[] fooItems;

    private int fooIndex;

    public String getFooRowStyleClass() {
        if (fooIndex % 2 == 0) {
            return "row_even";
        }
        return "row_odd";
    }

    public String initialize() {
        fooItems = new FooItem[4];
        fooItems[0] = createItem("a1", "b1", "c1", "d1", new BigDecimal(
            "11111111"), "f1", "g1");
        fooItems[1] = createItem("a2", "b2", "c2", "d2",
            new BigDecimal("2222"), "f2", "g2");
        fooItems[2] = createItem("a3", "b3", "c3", "d3",
            new BigDecimal("33333"), "f3", "g3");
        fooItems[3] = createItem("a4", "b4", "c4", "d4", new BigDecimal("44"),
            "f4", "g4");
        return null;
    }

    public String doSomething() {
        return null;
    }

    public FooItem[] getFooItems() {
        return fooItems;
    }

    private FooItem createItem(String aaa, String bbb, String ccc, String ddd,
        BigDecimal eee, String fff, String ggg) {
        final FooItem item = new FooItem();
        item.setAaa(aaa);
        item.setBbb(bbb);
        item.setCcc(ccc);
        item.setDdd(ddd);
        item.setEee(eee);
        item.setFff(fff);
        item.setGgg(ggg);
        return item;
    }

    public void setFooItems(FooItem[] fooItems) {
        this.fooItems = fooItems;
    }

    public static class FooItem implements Serializable {

        private String aaa;

        private String bbb;

        private String ccc;

        private String ddd;

        private BigDecimal eee;

        private String fff;

        private String ggg;

        public String getGgg() {
            return ggg;
        }

        public void setGgg(String ggg) {
            this.ggg = ggg;
        }

        public String getBbb() {
            return bbb;
        }

        public void setBbb(String bar) {
            this.bbb = bar;
        }

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String foo) {
            this.aaa = foo;
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

        public BigDecimal getEee() {
            return eee;
        }

        public void setEee(BigDecimal eee) {
            this.eee = eee;
        }

        public String getFff() {
            return fff;
        }

        public void setFff(String fff) {
            this.fff = fff;
        }
    }

    private String aaa;

    private String bbb;

    private String ccc;

    private String ddd;

    private BigDecimal eee;

    private String fff;

    private String ggg;

    public String getGgg() {
        return ggg;
    }

    public void setGgg(String ggg) {
        this.ggg = ggg;
    }

    public String getBbb() {
        return bbb;
    }

    public void setBbb(String bar) {
        this.bbb = bar;
    }

    public String getAaa() {
        return aaa;
    }

    public void setAaa(String foo) {
        this.aaa = foo;
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

    public BigDecimal getEee() {
        return eee;
    }

    public void setEee(BigDecimal eee) {
        this.eee = eee;
    }

    public String getFff() {
        return fff;
    }

    public void setFff(String fff) {
        this.fff = fff;
    }

    public void setFooIndex(int fooIndex) {
        this.fooIndex = fooIndex;
    }

}
