package org.seasar.teeda.it.web.foreach;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ForeachSizePage {

    private int itemSize;
    private FooItem[] fooItems;
    private int fooIndex;

    public String initialize() {
        itemSize = 3;
        fooItems = createItems(itemSize);
        return null;
    }

    private FooItem[] createItems(final int size) {
        final ArrayList items = new ArrayList();
        for (int i = 0; i < size; i++) {
            final FooItem item = new FooItem();
            item.setAaa("aa" + i);
            item.setBbb(new BigDecimal(i * 10));
            final FooItem fooItem = item;
            items.add(fooItem);
        }
        FooItem[] a = new FooItem[items.size()];
        items.toArray(a);
        return a;
    }

    public String doChangeSize() {
        fooItems = createItems(itemSize);
        return null;
    }

    public int getFooIndex() {
        return fooIndex;
    }

    public void setFooIndex(int fooIndex) {
        this.fooIndex = fooIndex;
    }

    public FooItem[] getFooItems() {
        return fooItems;
    }

    public void setFooItems(FooItem[] fooItems) {
        this.fooItems = fooItems;
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

    public static class FooItem {

        private String aaa;

        private BigDecimal bbb;

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String foo) {
            this.aaa = foo;
        }

        public BigDecimal getBbb() {
            return bbb;
        }

        public void setBbb(BigDecimal eee) {
            this.bbb = eee;
        }

    }

    private String aaa;

    private BigDecimal bbb;

    public String getAaa() {
        return aaa;
    }

    public void setAaa(String foo) {
        this.aaa = foo;
    }

    public BigDecimal getBbb() {
        return bbb;
    }

    public void setBbb(BigDecimal eee) {
        this.bbb = eee;
    }

}
