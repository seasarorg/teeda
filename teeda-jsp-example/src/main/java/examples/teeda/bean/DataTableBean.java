/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author manhole
 */
public class DataTableBean {

    private Integer selectedItemId;

    private List items = new ArrayList();
    {
        items.add(createItem(1, "item1", new BigDecimal("1000")));
        items.add(createItem(2, "item2", new BigDecimal("2000")));
        items.add(createItem(3, "item3", new BigDecimal("3000")));
    }

    public Item[] getItems() {
        System.out.println("DataTableBean.getItems() " + items);
        return (Item[]) items.toArray(new Item[items.size()]);
    }

    public int getItemSize() {
        System.out.println("DataTableBean.getItemSize() " + items);
        return items.size();
    }

    private Item createItem(int id, String name, BigDecimal price) {
        final Item item = new Item();
        item.setId(new Integer(id));
        item.setName(name);
        item.setPrice(price);
        return item;
    }

    public String viewItem() {
        // TODO
        System.out.println("*** DataTableBean.viewItem() ***");
        System.out.println("selectedItemId=" + selectedItemId);
        return null;
    }

    public void setItems(Item[] items) {
        System.out.println("DataTableBean.setItems() " + items);
        this.items = Arrays.asList(items);
    }

    public static class Item implements Serializable {

        private static final long serialVersionUID = 1L;

        private Integer id;

        private String name;

        private BigDecimal price;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

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

    public void setSelectedItemId(Integer selected) {
        this.selectedItemId = selected;
    }

}
