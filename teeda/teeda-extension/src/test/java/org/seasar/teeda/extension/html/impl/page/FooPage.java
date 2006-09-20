package org.seasar.teeda.extension.html.impl.page;

import java.util.List;

public class FooPage {

    private boolean initialized = false;

    private boolean prerendered = false;

    private String aaa;

    private int bbb;

    private int[] ccc;

    private List bbbItems;

    private List cccItems;

    private String dddItems;

    public String initialize() {
        initialized = true;
        return null;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public String prerender() {
        prerendered = true;
        return null;
    }

    public boolean isPrerendered() {
        return prerendered;
    }

    public String getAaa() {
        return aaa;
    }

    public void setAaa(String aaa) {
        this.aaa = aaa;
    }

    public List getCccItems() {
        return cccItems;
    }

    public void setCccItems(List cccItems) {
        this.cccItems = cccItems;
    }

    public String doBar() {
        return null;
    }

    public int getBbb() {
        return bbb;
    }

    public void setBbb(int bbb) {
        this.bbb = bbb;
    }

    public List getBbbItems() {
        return bbbItems;
    }

    public void setBbbItems(List bbbItems) {
        this.bbbItems = bbbItems;
    }

    public int[] getCcc() {
        return ccc;
    }

    public void setCcc(int[] ccc) {
        this.ccc = ccc;
    }

    public String getDddItems() {
        return dddItems;
    }

    public void setDddItems(String dddItems) {
        this.dddItems = dddItems;
    }

}
