package examples.teeda.web.grid;

import java.math.BigDecimal;
import java.util.ArrayList;

public class GridManyXYPage {

    private int itemSize = 10;

    private FooItem[] fooItems;

    private int fooIndex;

    public GridManyXYPage() {
    }

    public String getFooRowStyleClass() {
        if (fooIndex % 2 == 0) {
            return "row_even";
        }
        return "row_odd";
    }

    public String prerender() {
        final ArrayList items = new ArrayList();
        for (int i = 0; i < itemSize; i++) {
            final FooItem item = new FooItem();
            item.setAaa("a" + i);
            item.setBbb("b" + i);
            item.setCcc("c" + i);
            item.setDdd("d" + i);
            item.setEee(new BigDecimal(i));
            item.setFff("f" + i);
            item.setGgg("g" + i);
            final FooItem fooItem = item;
            items.add(fooItem);
        }
        fooItems = new FooItem[items.size()];
        items.toArray(fooItems);
        return null;
    }

    public String doChangeSize() {
        return null;
    }

    public FooItem[] getFooItems() {
        return fooItems;
    }

    public void setFooItems(FooItem[] fooItems) {
        this.fooItems = fooItems;
    }

    public static class FooItem {

        private String aaa;

        private String bbb;

        private String ccc;

        private String ddd;

        private BigDecimal eee;

        private String fff;

        private String ggg;

        public String getGgg() {
            return ggg;
        }

        public void setGgg(String ggg) {
            this.ggg = ggg;
        }

        public String getBbb() {
            return bbb;
        }

        public void setBbb(String bar) {
            this.bbb = bar;
        }

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String foo) {
            this.aaa = foo;
        }

        public String getCcc() {
            return ccc;
        }

        public void setCcc(String ccc) {
            this.ccc = ccc;
        }

        public String getDdd() {
            return ddd;
        }

        public void setDdd(String ddd) {
            this.ddd = ddd;
        }

        public BigDecimal getEee() {
            return eee;
        }

        public void setEee(BigDecimal eee) {
            this.eee = eee;
        }

        public String getFff() {
            return fff;
        }

        public void setFff(String fff) {
            this.fff = fff;
        }
    }

    private String aaa;

    private String bbb;

    private String ccc;

    private String ddd;

    private BigDecimal eee;

    private String fff;

    private String ggg;

    public String getGgg() {
        return ggg;
    }

    public void setGgg(String ggg) {
        this.ggg = ggg;
    }

    public String getBbb() {
        return bbb;
    }

    public void setBbb(String bar) {
        this.bbb = bar;
    }

    public String getAaa() {
        return aaa;
    }

    public void setAaa(String foo) {
        this.aaa = foo;
    }

    public String getCcc() {
        return ccc;
    }

    public void setCcc(String ccc) {
        this.ccc = ccc;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public BigDecimal getEee() {
        return eee;
    }

    public void setEee(BigDecimal eee) {
        this.eee = eee;
    }

    public String getFff() {
        return fff;
    }

    public void setFff(String fff) {
        this.fff = fff;
    }

    public void setFooIndex(int fooIndex) {
        this.fooIndex = fooIndex;
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

}
