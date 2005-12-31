/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.core.scope.impl;

import java.util.HashMap;
import java.util.Map;

import org.seasar.framework.container.InstanceDef;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.teeda.core.scope.Scope;
import org.seasar.teeda.core.scope.ScopeAlreadyRegisteredException;
import org.seasar.teeda.core.scope.ScopeTranslator;

/**
 * @author Shinpei Ohtani
 * 
 * Scope translation between Teeda and S2.
 */
public class S2ScopeTranslator implements ScopeTranslator{
	
	private static final Map SCOPE_TO_INSTANCEDEF_MAP = new HashMap();
	private static final Map INSTANCEDEF_TO_SCOPE_MAP = new HashMap();
	static{
		SCOPE_TO_INSTANCEDEF_MAP.put(Scope.NONE, InstanceDefFactory.OUTER);
		SCOPE_TO_INSTANCEDEF_MAP.put(Scope.REQUEST, InstanceDefFactory.REQUEST);
		SCOPE_TO_INSTANCEDEF_MAP.put(Scope.SESSION, InstanceDefFactory.SESSION);
		SCOPE_TO_INSTANCEDEF_MAP.put(Scope.APPLICATION, InstanceDefFactory.SINGLETON);
		
		INSTANCEDEF_TO_SCOPE_MAP.put(InstanceDefFactory.OUTER, Scope.NONE);
		INSTANCEDEF_TO_SCOPE_MAP.put(InstanceDefFactory.REQUEST, Scope.REQUEST);
		INSTANCEDEF_TO_SCOPE_MAP.put(InstanceDefFactory.SESSION, Scope.SESSION);
		INSTANCEDEF_TO_SCOPE_MAP.put(InstanceDefFactory.SINGLETON, Scope.APPLICATION);

	}
	public Scope toScope(Object obj){
		if(!(obj instanceof InstanceDef)){
			throw new IllegalArgumentException();
		}
		return (Scope)INSTANCEDEF_TO_SCOPE_MAP.get(obj);
	}
	
	public Object toExternalComponentScope(Scope scope){
		return SCOPE_TO_INSTANCEDEF_MAP.get(scope);
	}
	
	public void addScope(Scope scope, Object externalComponentScope) throws ScopeAlreadyRegisteredException {
		if(scope == null || externalComponentScope == null){
			throw new IllegalArgumentException();
		}
		if(SCOPE_TO_INSTANCEDEF_MAP.containsKey(scope)){
			throw new ScopeAlreadyRegisteredException(new Object[]{scope.getScopeKey()});
		}
		SCOPE_TO_INSTANCEDEF_MAP.put(scope, externalComponentScope);
		INSTANCEDEF_TO_SCOPE_MAP.put(externalComponentScope, scope);
	}
	
}
