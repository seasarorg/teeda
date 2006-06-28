package org.seasar.teeda.extension.html.impl;

public class FooPage {
    
    private boolean initialized = false;

    public String initialize() {
        initialized = true;
        return null;
    }
    
    public boolean isInitialized() {
        return initialized;
    }

    public String getAaa() {
        return null;
    }
    
    public String doBbb() {
        return null;
    }
}
