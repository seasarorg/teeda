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
package org.seasar.teeda.extension.html.factory.sub.web.foo;

import java.util.List;

import org.seasar.teeda.extension.component.TreeNode;

public class FooPage {

    private int hogeNo;

    private String hogeNama;

    private List hogeItems;

    private String ccc;

    private TreeNode tttt;

    private String abc;

    private Boolean[] januaryHolidays;

    private Integer holidaysYear;

    private String bbbXHref;

    /**
     * @return Returns the holidaysYear.
     */
    public Integer getHolidaysYear() {
        return holidaysYear;
    }

    /**
     * @param holidaysYear The holidaysYear to set.
     */
    public void setHolidaysYear(Integer holidaysYear) {
        this.holidaysYear = holidaysYear;
    }

    /**
     * @return Returns the januaryHolidays.
     */
    public Boolean[] getJanuaryHolidays() {
        return januaryHolidays;
    }

    /**
     * @param januaryHolidays The januaryHolidays to set.
     */
    public void setJanuaryHolidays(Boolean[] januaryHolidays) {
        this.januaryHolidays = januaryHolidays;
    }

    public String getAbcValue() {
        return "ABC";
    }

    public String getAaaLabelValue() {
        return "LabelValue";
    }

    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }

    public TreeNode getTttt() {
        return tttt;
    }

    public void setTttt(TreeNode tttt) {
        this.tttt = tttt;
    }

    public String getAaa() {
        return null;
    }

    public String getAaaStyleClass() {
        return "mystyle";
    }

    public String getAaaId() {
        return "hoge";
    }

    public String getCcc() {
        return ccc;
    }

    public void setCcc(String ccc) {
        this.ccc = ccc;
    }

    public String doBbb() {
        return null;
    }

    public String getHogeNama() {
        return hogeNama;
    }

    public void setHogeNama(String hogeNama) {
        this.hogeNama = hogeNama;
    }

    public int getHogeNo() {
        return hogeNo;
    }

    public void setHogeNo(int hogeNo) {
        this.hogeNo = hogeNo;
    }

    public List getHogeItems() {
        return hogeItems;
    }

    public void setHogeItems(List hogeItems) {
        this.hogeItems = hogeItems;
    }

    public String getHogeRowStyleClass() {
        return "mystyle";
    }

    public String getHogeRowStyle() {
        return "mystyle";
    }

    public String getBbbXHref() {
        return bbbXHref;
    }

    public void setBbbXHref(String bbbXHref) {
        this.bbbXHref = bbbXHref;
    }

}
