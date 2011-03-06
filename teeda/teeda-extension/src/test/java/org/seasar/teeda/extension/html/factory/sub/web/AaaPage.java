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
package org.seasar.teeda.extension.html.factory.sub.web;

import java.math.BigDecimal;

/**
 * @author shot
 */
public class AaaPage {

    private String aaa;

    private int aaaFraction = 4;

    private String aaaFractionSeparator = ".";

    private String aaaGroupingSeparator = ",";

    private boolean bbb;

    private BigDecimal ccc;

    public boolean isBbb() {
        return bbb;
    }

    public void setBbb(boolean bbb) {
        this.bbb = bbb;
    }

    public String getAaaGroupingSeparator() {
        return aaaGroupingSeparator;
    }

    public void setAaaGroupingSeparator(String aaaGroupingSeparator) {
        this.aaaGroupingSeparator = aaaGroupingSeparator;
    }

    public String getAaaFractionSeparator() {
        return aaaFractionSeparator;
    }

    public void setAaaFractionSeparator(String aaaFractionSeparator) {
        this.aaaFractionSeparator = aaaFractionSeparator;
    }

    public int getAaaFraction() {
        return aaaFraction;
    }

    public void setAaaFraction(int aaaFraction) {
        this.aaaFraction = aaaFraction;
    }

    public String getAaa() {
        return aaa;
    }

    public void setAaa(String aaa) {
        this.aaa = aaa;
    }

    /**
     * @return Returns the ccc.
     */
    public BigDecimal getCcc() {
        return ccc;
    }

    /**
     * @param ccc The ccc to set.
     */
    public void setCcc(BigDecimal ccc) {
        this.ccc = ccc;
    }

}
