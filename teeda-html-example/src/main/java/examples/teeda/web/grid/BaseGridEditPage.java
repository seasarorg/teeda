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

import examples.teeda.web.common.PageUtil;
import examples.teeda.web.dto.FooDto;

public class BaseGridEditPage {

    protected Integer    aaa;

    protected String     bbb;

    protected BigDecimal ccc;

    protected FooDto[]   fooItems;

    protected Integer    fooIndex;

    protected Integer    fooIndexSelect;

    protected int        editStatus;

    protected int        bbbEditStatus;

    public BaseGridEditPage() {}

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

    public Integer getFooIndex() {
        return fooIndex;
    }

    public void setFooIndex(Integer fooIndex) {
        this.fooIndex = fooIndex;
    }

    public FooDto[] getFooItems() {
        return fooItems;
    }

    public void setFooItems(FooDto[] fooItems) {
        this.fooItems = fooItems;
    }

    public Integer getFooIndexSelect() {
        return fooIndexSelect;
    }

    public void setFooIndexSelect(Integer selectFooIndex) {
        this.fooIndexSelect = selectFooIndex;
    }

//    public Integer getEditStatus() {
//        return editStatus;
//    }
//
//    public void setEditStatus(Integer editStatus) {
//        this.editStatus = editStatus;
//    }
//
//    public Integer getBbbEditStatus() {
//        return bbbEditStatus;
//    }
//
//    public void setBbbEditStatus(Integer bbbEditStatus) {
//        this.bbbEditStatus = bbbEditStatus;
//    }

    public String getFooRowStyleClass() {

        if (this.fooIndex == null) {
            return null;
        }

        FooDto dto = this.fooItems[this.fooIndex.intValue()];
        if (dto == null) {
            return null;
        }

//        Integer editStatus = dto.getEditStatus();
        int editStatus = dto.getEditStatus();
        String cssClass = PageUtil.getStyleClass(editStatus);

        return cssClass;
    }

    public String getFooRowStyle() {

        if (this.fooIndex == null) {
            return null;
        }

        FooDto dto = this.fooItems[this.fooIndex.intValue()];
        if (dto == null) {
            return null;
        }

        int editStatus = dto.getEditStatus();
//        Integer editStatus = dto.getEditStatus();
        String style = PageUtil.getStyle(editStatus);

        return style;
    }
    
    public int getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(int editStatus) {
        this.editStatus = editStatus;
    }

    public int getBbbEditStatus() {
        return bbbEditStatus;
    }

    public void setBbbEditStatus(int bbbEditStatus) {
        this.bbbEditStatus = bbbEditStatus;
    }

}
