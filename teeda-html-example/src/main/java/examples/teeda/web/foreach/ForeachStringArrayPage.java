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
package examples.teeda.web.foreach;

public class ForeachStringArrayPage {

    private String aaa;

    private String[] aaaItems;

    public String[] getAaaItems() {
        if (aaaItems == null) {
            aaaItems = new String[] { "a", "b", "c", "d" };
        }
        return aaaItems;
    }

    public void setAaaItems(String[] aaaItems) {
        this.aaaItems = aaaItems;
    }

    public String getAaa() {
        System.out.println("ForeachArrayPage.getAaa() " + aaa);
        return aaa;
    }

    public void setAaa(String aaa) {
        System.out.println("ForeachArrayPage.setAaa() " + aaa);
        this.aaa = aaa;
    }

}
