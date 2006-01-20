package org.seasar.teeda.core.mock;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;


public class MockLifecycleFactory extends LifecycleFactory {

    private Map lifecycles_ = new HashMap();
    
    public MockLifecycleFactory(){
        addLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE, new MockLifecycleImpl());
    }
    
    public void addLifecycle(String lifecycleId, Lifecycle lifecycle) {
        lifecycles_.put(lifecycleId, lifecycle);
    }

    public Lifecycle getLifecycle(String lifecycleId) {
        return (Lifecycle)lifecycles_.get(lifecycleId);
    }

    public Iterator getLifecycleIds() {
        return lifecycles_.keySet().iterator();
    }

}
