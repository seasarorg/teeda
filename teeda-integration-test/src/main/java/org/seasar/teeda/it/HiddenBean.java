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
package org.seasar.teeda.it;

/**
 * @author manhole
 */
public class HiddenBean {

    private String a_;

    private String b_;

    public String getA() {
        return a_;
    }

    public void setA(String a) {
        a_ = a;
    }

    public String getB() {
        return b_;
    }

    public void setB(String b) {
        b_ = b;
    }

    public void doSomething() {
        b_ = a_ + b_;
    }

}
