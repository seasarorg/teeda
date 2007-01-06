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
package org.seasar.teeda.it;

import java.math.BigDecimal;

/**
 * @author manhole
 */
public class CalculatorBean {

    private BigDecimal x_;

    private BigDecimal y_;

    private BigDecimal result_;

    public BigDecimal getX() {
        return x_;
    }

    public void setX(BigDecimal x) {
        x_ = x;
    }

    public BigDecimal getY() {
        return y_;
    }

    public void setY(BigDecimal y) {
        y_ = y;
    }

    public BigDecimal getResult() {
        return result_;
    }

    public void add() {
        result_ = x_.add(y_);
    }

    public void subtract() {
        result_ = x_.subtract(y_);
    }

    public void multiply() {
        result_ = x_.multiply(y_);
    }

    public void divide() {
        result_ = x_.divide(y_, BigDecimal.ROUND_HALF_EVEN);
    }

}
