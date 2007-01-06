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
package examples.teeda.ajax;

/**
 * @author shot
 */
public class AjaxDto {

    private String menuName;

    private int price;

    public AjaxDto(String menuName, int price) {
        this.menuName = menuName;
        this.price = price;
    }

    private AjaxDto(int foodNo) {
        if (foodNo == 1) {
            menuName = "hoge";
            price = 100;
        } else if (foodNo == 2) {
            menuName = "foo";
            price = 200;
        } else {
            menuName = "bar";
            price = 300;
        }
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
