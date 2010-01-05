/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

public class GridValidationPage {

    private FooItem[] fooItems;

    public String initialize() {
        fooItems = new FooItem[4];
        fooItems[0] = createItem("b1", "c1", "d1", new BigDecimal("1"));
        fooItems[1] = createItem("b2", "c2", "d2", new BigDecimal("2"));
        fooItems[2] = createItem("b3", "c3", "d3", new BigDecimal("3"));
        fooItems[3] = createItem("b4", "c4", "d4", new BigDecimal("4"));
        return null;
    }

    public String doSomething() {
        return null;
    }

    public FooItem[] getFooItems() {
        return fooItems;
    }

    private FooItem createItem(String bbb, String ccc, String ddd,
        BigDecimal eee) {
        final FooItem item = new FooItem();
        item.setBbb(bbb);
        item.setCcc(ccc);
        item.setDdd(ddd);
        item.setEee(eee);
        return item;
    }

    public void setFooItems(FooItem[] fooItems) {
        this.fooItems = fooItems;
    }

    public static class FooItem implements Serializable {

        private String bbb;

        private String ccc;

        private String ddd;

        private BigDecimal eee;

        public String getBbb() {
            return bbb;
        }

        public void setBbb(String bar) {
            this.bbb = bar;
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
    }

    private String bbb;

    private String ccc;

    private String ddd;

    private BigDecimal eee;

    public String getBbb() {
        return bbb;
    }

    public void setBbb(String bar) {
        this.bbb = bar;
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

}
