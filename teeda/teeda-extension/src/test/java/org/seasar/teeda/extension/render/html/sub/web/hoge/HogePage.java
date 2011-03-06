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
package org.seasar.teeda.extension.render.html.sub.web.hoge;

import java.util.List;

import org.seasar.teeda.extension.component.TreeNode;

public class HogePage {

    private int hogeNo;

    private String hogeNama;

    private List hogeItems;

    private String ccc;

    private TreeNode tttt;

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

}
