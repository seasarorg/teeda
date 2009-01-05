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
package org.seasar.teeda.extension.html.impl.page;

import java.util.List;

public class FooPage {

    public static final String REDIRECT_SCOPE = "aaa";

    public static final String SUBAPPLICATION_SCOPE = "bbb";

    public static final String PAGE_SCOPE = "eee";

    public static final String doBar_REDIRECT = "protocol=https";

    private boolean initialized = false;

    private boolean prerendered = false;

    private String aaa;

    private int bbb;

    private int[] ccc;

    private List bbbItems;

    private List cccItems;

    private String dddItems;

    private Double eee;

    private AaaDto aaaDto;

    public Double getEee() {
        return eee;
    }

    public void setEee(Double eee) {
        this.eee = eee;
    }

    public String initialize() {
        initialized = true;
        return null;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public String prerender() {
        prerendered = true;
        return null;
    }

    public boolean isPrerendered() {
        return prerendered;
    }

    public String getAaa() {
        return aaa;
    }

    public void setAaa(String aaa) {
        this.aaa = aaa;
    }

    public List getCccItems() {
        return cccItems;
    }

    public void setCccItems(List cccItems) {
        this.cccItems = cccItems;
    }

    public static final String doBar_TAKE_OVER = "properties='aaa, bbb'";

    public String doBar() {
        return null;
    }

    public int getBbb() {
        return bbb;
    }

    public void setBbb(int bbb) {
        this.bbb = bbb;
    }

    public List getBbbItems() {
        return bbbItems;
    }

    public void setBbbItems(List bbbItems) {
        this.bbbItems = bbbItems;
    }

    public int[] getCcc() {
        return ccc;
    }

    public void setCcc(int[] ccc) {
        this.ccc = ccc;
    }

    public String getDddItems() {
        return dddItems;
    }

    public void setDddItems(String dddItems) {
        this.dddItems = dddItems;
    }

    /**
     * @return Returns the aaaDto.
     */
    public AaaDto getAaaDto() {
        return aaaDto;
    }

    /**
     * @param aaaDto The aaaDto to set.
     */
    public void setAaaDto(AaaDto aaaDto) {
        this.aaaDto = aaaDto;
    }

}
