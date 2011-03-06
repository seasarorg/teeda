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
package examples.teeda.bean;

import java.math.BigDecimal;

/**
 * @author shot
 */
public class AddExtBean {

    private BigDecimal arg1;

    private BigDecimal arg2;

    private BigDecimal result;

    public BigDecimal getArg1() {
        return arg1;
    }

    public BigDecimal getArg2() {
        return arg2;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setArg1(BigDecimal arg1) {
        this.arg1 = arg1;
    }

    public void setArg2(BigDecimal arg2) {
        this.arg2 = arg2;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    public String calculate() {
        if (arg1 == null) {
            arg1 = new BigDecimal("0");
        }
        if (arg2 == null) {
            arg2 = new BigDecimal("0");
        }
        result = arg1.add(arg2);
        return null;
    }
}
