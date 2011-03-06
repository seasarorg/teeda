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
package org.seasar.teeda.it;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.seasar.framework.util.ArrayUtil;

/**
 * @author manhole
 */
public class SelectManyListboxBbbBean {

    private List list_;

    {
        list_ = new ArrayList();
        list_.add(new SelectItem("1", "One"));
        list_.add(new SelectItem("2", "Two"));
        list_.add(new SelectItem("3", "Three"));
        list_.add(new SelectItem("4", "Four", null, true));
    }

    public List getList() {
        return list_;
    }

    public void setList(List list) {
        list_ = list;
    }

    private int[] bbbSelected_;
    {
        bbbSelected_ = new int[] { 2 };
    }

    public int[] getBbbSelected() {
        return bbbSelected_;
    }

    public void setBbbSelected(int[] bbbSelected) {
        bbbSelected_ = bbbSelected;
    }

    public String getSelectedString() {
        return ArrayUtil.toString(ArrayUtil.toObjectArray(bbbSelected_));
    }

}
