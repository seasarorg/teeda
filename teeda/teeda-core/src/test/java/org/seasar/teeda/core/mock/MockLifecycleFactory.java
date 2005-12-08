package org.seasar.teeda.core.mock;

import java.util.Iterator;

import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;


public class MockLifecycleFactory extends LifecycleFactory {

    public void addLifecycle(String lifecycleId, Lifecycle lifecycle) {
    }

    public Lifecycle getLifecycle(String lifecycleId) {
        return null;
    }

    public Iterator getLifecycleIds() {
        return null;
    }

}
