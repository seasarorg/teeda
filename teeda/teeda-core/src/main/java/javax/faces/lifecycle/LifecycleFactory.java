package javax.faces.lifecycle;

import java.util.Iterator;

public abstract class LifecycleFactory {
	
	public static final String DEFAULT_LIFECYCLE = "DEFAULT";

	public LifecycleFactory(){	
	}
	
	public abstract void addLifecycle(String lifecycleId, Lifecycle lifecycle);

	public abstract Lifecycle getLifecycle(String lifecycleId);

	public abstract Iterator getLifecycleIds();
}
