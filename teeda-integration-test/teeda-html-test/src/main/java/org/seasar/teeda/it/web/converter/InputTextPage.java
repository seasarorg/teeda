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
package org.seasar.teeda.it.web.converter;

public class InputTextPage {

    private Integer aaa;
    private Integer bbb;
    private Integer ccc;
    private Integer ddd;

    public String initialize() {
        aaa = new Integer(123);
        bbb = new Integer(234);
        ccc = new Integer(345);
        ddd = new Integer(456);
        return null;
    }

    public String doSomething() {
        return null;
    }

    public Integer getAaa() {
        return aaa;
    }

    public void setAaa(Integer aaa) {
        this.aaa = aaa;
    }

    public Integer getBbb() {
        return bbb;
    }

    public void setBbb(Integer bbb) {
        this.bbb = bbb;
    }

    public Integer getCcc() {
        return ccc;
    }

    public void setCcc(Integer ccc) {
        this.ccc = ccc;
    }

    public Integer getDdd() {
        return ddd;
    }

    public void setDdd(Integer ddd) {
        this.ddd = ddd;
    }

}
