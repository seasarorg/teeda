package org.seasar.teeda.extension.html.impl;

import java.util.List;

public class FooPage {

    private boolean initialized = false;

    private String aaa;

    private List cccItems;

    private String dddItems;

    public String initialize() {
        initialized = true;
        return null;
    }

    public boolean isInitialized() {
        return initialized;
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

    public String getDddItems() {
        return dddItems;
    }

    public void setDddItems(String dddItems) {
        this.dddItems = dddItems;
    }

    public String doBbb() {
        return null;
    }
}
