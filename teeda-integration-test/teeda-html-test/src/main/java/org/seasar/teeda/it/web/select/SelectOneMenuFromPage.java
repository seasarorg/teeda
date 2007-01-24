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
package org.seasar.teeda.it.web.select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://www.seasar.org/issues/browse/TEEDA-234
 * 
 * @author manhole
 */
public class SelectOneMenuFromPage {

    private List aaaItems;
    private Integer aaa = new Integer(2);
    private String aaaLabel;

    public void initialize() {
        aaaItems = new ArrayList();
        final Map map1 = new HashMap();
        map1.put("label", "AAAA");
        map1.put("value", new Integer(1));
        aaaItems.add(map1);
        final Map map2 = new HashMap();
        map2.put("label", "BBBB");
        map2.put("value", new Integer(2));
        aaaItems.add(map2);
        final Map map3 = new HashMap();
        map3.put("label", "CCCC");
        map3.put("value", new Integer(3));
        aaaItems.add(map3);
    }

    /*
     * https://www.seasar.org/issues/browse/TEEDA-234
     */
    public Class doAction() {
        System.out.println("SelectOneMenuPage.doActionAndGoNext() selected="
            + aaa);
        return SelectOneMenuToPage.class;
    }

    public List getAaaItems() {
        return aaaItems;
    }

    public void setAaaItems(final List aaaItems) {
        this.aaaItems = aaaItems;
    }

    public Integer getAaa() {
        return aaa;
    }

    public void setAaa(final Integer aaa) {
        this.aaa = aaa;
    }

    public String getAaaLabel() {
        return aaaLabel;
    }

    public void setAaaLabel(final String aaaLabel) {
        this.aaaLabel = aaaLabel;
    }

}
