/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package examples.teeda.web.grid;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import examples.teeda.web.dto.FooDto;

public class GridPagingPage extends BaseGridPage {

    private NumberFormat formater = new DecimalFormat("0.0000#");

    private Integer      aaa;

    private String       bbb;

    private BigDecimal   ccc;

    private FooDto[]     fooItems;

    public GridPagingPage() {}

    public String initialize() {

        if (this.fooItems == null) {
            this.fooItems = new FooDto[10];
            for (int i = 0; i < this.fooItems.length; i++) {
                Integer aaa = new Integer(i);
                String bbb = "str" + i;
                BigDecimal ccc = new BigDecimal(
                        this.formater.format(aaa.intValue()));
                this.fooItems[i] = new FooDto(aaa, bbb, ccc);
            }
            
            super.setCount(this.fooItems.length);
        }

        return null;
    }

    public Integer getAaa() {
        return aaa;
    }

    public void setAaa(Integer aaa) {
        this.aaa = aaa;
    }

    public String getBbb() {
        return bbb;
    }

    public void setBbb(String bbb) {
        this.bbb = bbb;
    }

    public BigDecimal getCcc() {
        return ccc;
    }

    public void setCcc(BigDecimal ccc) {
        this.ccc = ccc;
    }

    public FooDto[] getFooItems() {
        return fooItems;
    }

    public void setFooItems(FooDto[] fooItems) {
        this.fooItems = fooItems;
    }


    public String doSort() {
        return null;
    }

    public String doPaging() {
        return null;
    }

}
