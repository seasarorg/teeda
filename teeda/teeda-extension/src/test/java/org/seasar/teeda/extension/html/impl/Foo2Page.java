package org.seasar.teeda.extension.html.impl;

public class Foo2Page {
    
    private boolean initialized = false;
    
    private String aaa;

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
    
    public String doBbb() {
        return null;
    }
}
