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
package javax.faces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shot
 */
public final class FactoryFinder {

	public static final String APPLICATION_FACTORY = "javax.faces.application.ApplicationFactory";

	public static final String FACES_CONTEXT_FACTORY = "javax.faces.context.FacesContextFactory";

	public static final String LIFECYCLE_FACTORY = "javax.faces.lifecycle.LifecycleFactory";

	public static final String RENDER_KIT_FACTORY = "javax.faces.render.RenderKitFactory";

	private static final Map registeredFactoryNamesMap_ = Collections.synchronizedMap(new HashMap());
	
	private static final Map factories_ = new HashMap();

	public static Object getFactory(String factoryName) throws FacesException {
		FactoryFinderUtil_.assertNotNull(factoryName, "factoryName");
		ClassLoader classLoader = FactoryFinderUtil_.getClassLoader();
		Map factoryClassNames = (Map)registeredFactoryNamesMap_.get(classLoader);
		if (factoryClassNames == null) {
			throw new IllegalStateException("No factories configured.");
		}
        if (!factoryClassNames.containsKey(factoryName)) {
            throw new IllegalStateException("no factory " + factoryName + " configured for this appliction");
        }
        Map factoryMap = (Map)factories_.get(classLoader);
        if (factoryMap == null) {
            factoryMap = new HashMap();
            factories_.put(classLoader, factoryMap);
        }
		Object factory = factoryMap.get(factoryName);
		if (factory == null) {
			List classNames = (List)factoryClassNames.get(factoryName);
			factory = FactoryFinderUtil_.createFactoryInstance(factoryName, classNames, classLoader);
			factoryMap.put(factoryName, factory);
			return factory;
		} else {
			return factory;
		}
	}

	public static void setFactory(String factoryName, String implName) {
        FactoryFinderUtil_.assertNotNull(factoryName, "factoryName");
		FactoryFinderUtil_.checkValidFactoryNames(factoryName);
		ClassLoader classLoader = FactoryFinderUtil_.getClassLoader();
		Map factoryMap = (Map)factories_.get(classLoader);
		if(FactoryFinderUtil_.isAlreadySetFactory(factoryMap, factoryName)){
			return;
		}
        Map factoryClassNames = (Map)registeredFactoryNamesMap_.get(classLoader);
        if (factoryClassNames == null){
            factoryClassNames = new HashMap();
            registeredFactoryNamesMap_.put(classLoader, factoryClassNames);
        }
        List classNameList = (List) factoryClassNames.get(factoryName);
        if (classNameList == null) {
            classNameList = new ArrayList();
            factoryClassNames.put(factoryName, classNameList);
        }
        classNameList.add(implName);		
	}

	public static void releaseFactories() throws FacesException {
		ClassLoader classLoader = FactoryFinderUtil_.getClassLoader();
		factories_.remove(classLoader);
	}

}
