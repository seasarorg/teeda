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
package org.seasar.teeda.it;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author manhole
 */
public class DataTableBean {

    private List items = new ArrayList();
    {
        items.add(createItem("item1", new BigDecimal("1000")));
        items.add(createItem("item2", new BigDecimal("2000")));
        items.add(createItem("item3", new BigDecimal("3000")));
    }

    public Item[] getItems() {
        return (Item[]) items.toArray(new Item[items.size()]);
    }

    public int getItemSize() {
        return items.size();
    }

    private Item createItem(String name, BigDecimal price) {
        return new Item(name, price);
    }

    public void setItems(Item[] items) {
        this.items = Arrays.asList(items);
    }

    public static class Item implements Serializable {

        private String name;

        private BigDecimal price;

        public Item(String name, BigDecimal price) {
            this.name = name;
            this.price = price;
        }

        private static final long serialVersionUID = 1L;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String toString() {
            return "{name=" + name + ", price=" + price + "}";
        }

    }

}
