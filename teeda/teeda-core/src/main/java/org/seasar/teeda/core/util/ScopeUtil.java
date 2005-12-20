package org.seasar.teeda.core.util;

import java.util.HashMap;
import java.util.Map;

import org.seasar.framework.container.InstanceDef;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.teeda.core.managedbean.Scope;

public class ScopeUtil {

	private static final Map SCOPE_MAP = new HashMap();
	private static final Map INSTANCEDEF_MAP = new HashMap();
	
	static {
		SCOPE_MAP.put(Scope.NONE, InstanceDefFactory.OUTER);
		SCOPE_MAP.put(Scope.REQUEST, InstanceDefFactory.REQUEST);
		SCOPE_MAP.put(Scope.SESSION, InstanceDefFactory.SESSION);
		SCOPE_MAP.put(Scope.APPLICATION, InstanceDefFactory.SINGLETON);
		
		INSTANCEDEF_MAP.put(InstanceDefFactory.OUTER, Scope.NONE);
		INSTANCEDEF_MAP.put(InstanceDefFactory.REQUEST, Scope.REQUEST);
		INSTANCEDEF_MAP.put(InstanceDefFactory.SESSION, Scope.SESSION);
		INSTANCEDEF_MAP.put(InstanceDefFactory.SINGLETON, Scope.APPLICATION);
	}

	private ScopeUtil(){
	}
	
	public static InstanceDef toInstanceDef(Scope scope){
		return (InstanceDef)SCOPE_MAP.get(scope);
	}
	
	public static Scope toScope(InstanceDef instanceDef){
		return (Scope)INSTANCEDEF_MAP.get(instanceDef);
	}
}
