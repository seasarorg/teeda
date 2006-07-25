/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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

/**
 * @author shot
 */
public class AddExtBean {

    private double arg1;

    private double arg2;

    private double result;

    public double getArg1() {
        return arg1;
    }

    public double getArg2() {
        return arg2;
    }

    public double getResult() {
        return result;
    }

    public void setArg1(double arg1) {
        this.arg1 = arg1;
    }

    public void setArg2(double arg2) {
        this.arg2 = arg2;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String calculate() {
        result = arg1 + arg2;
        return null;
    }
}
