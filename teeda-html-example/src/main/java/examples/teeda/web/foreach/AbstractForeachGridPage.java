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
package examples.teeda.web.foreach;

import java.math.BigDecimal;

import examples.teeda.web.common.PageUtil;
import examples.teeda.web.dto.FooDto;

public abstract class AbstractForeachGridPage {

    protected Integer    aaa;

    protected String     bbb;

    protected BigDecimal ccc;

    protected FooDto[]   fooItems;

    protected Integer    fooIndex;

    protected Integer    fooIndexSelect;

    protected int        editStatus;

    protected int        bbbEditStatus;

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

    public Integer getFooIndexSelect() {
        return fooIndexSelect;
    }

    public void setFooIndexSelect(Integer fooIndexSelect) {
        this.fooIndexSelect = fooIndexSelect;
    }

    public FooDto[] getFooItems() {
        return fooItems;
    }

    public void setFooItems(FooDto[] fooItems) {
        this.fooItems = fooItems;
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

    public Boolean isEditStatusNormal() {
        return new Boolean((this.editStatus == PageUtil.EDIT_NOT_CHANGE));
    }

    public Boolean isEditStatusAdd() {
        return new Boolean(this.editStatus == PageUtil.EDIT_ADD);
    }

    public Boolean isEditStatusUpdate() {
        return new Boolean(this.editStatus == PageUtil.EDIT_UPDATE);
    }

    public Boolean isEditStatusDelete() {
        return new Boolean(this.editStatus == PageUtil.EDIT_DELETE);
    }
}
