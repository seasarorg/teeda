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
package org.seasar.teeda.it.web.select;

/**
 * https://www.seasar.org/issues/browse/TEEDA-234
 * 
 * @author manhole
 */
public class SelectOneMenuToPage {

    private Integer aaa;
    private String aaaLabel;

    public void prerender() {
        System.out.println("SelectOneMenuToPage.prerender() aaa=" + aaa
            + ", aaaLabel=" + aaaLabel);
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
