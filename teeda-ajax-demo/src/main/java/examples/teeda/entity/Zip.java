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
package examples.teeda.entity;

import java.io.Serializable;

/**
 * @author yone
 */
public class Zip implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String TABLE = "DEMO_POST";

    private String zipcode;

    private String address;

    public Zip() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Zip)) {
            return false;
        }
        Zip castOther = (Zip) other;
        return getZipcode() == castOther.getZipcode();
    }

    public String toString() {
        StringBuffer buf = new StringBuffer("[");
        setupToString(buf);
        buf.append("]");
        return buf.toString();
    }

    protected void setupToString(StringBuffer buf) {
        buf.append(zipcode).append(", ");
        buf.append(address);
    }
    
}