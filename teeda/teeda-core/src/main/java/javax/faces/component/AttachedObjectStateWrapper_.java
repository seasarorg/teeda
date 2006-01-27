/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package javax.faces.component;

import java.io.Serializable;

import javax.faces.context.FacesContext;

/**
 * @author Shinpei Ohtani
 * A Helper class for UIComponentBase.saveAttachedState
 */
class AttachedObjectStateWrapper_ implements Serializable {

	private static final long serialVersionUID = 3256726169255885111L;

    private Object savedState_ = null;

	private String className_ = null;

	private boolean isSavedStateHolder_ = false;

	public AttachedObjectStateWrapper_(FacesContext context, Object obj) {
		if (obj == null) {
			throw new IllegalArgumentException();
		}
		className_ = obj.getClass().getName();
		if (obj instanceof StateHolder) {
			StateHolder stateHolder = (StateHolder) obj;
			if (!stateHolder.isTransient()) {
				savedState_ = stateHolder.saveState(context);
				isSavedStateHolder_ = true;
			}
		} else {
			if (obj instanceof Serializable) {
				savedState_ = obj;
			}
		}
	}

	public Object restore(FacesContext context) throws IllegalStateException{
		Object result = null;
		if (savedState_ == null) {
			return null;
		}
		if (!isSavedStateHolder_) {
			result = savedState_;
		} else if (isSavedStateHolder_) {
			try {
				ClassLoader loader = ClassLoaderUtil_.getClassLoader(context);
				Class clazz = ClassLoaderUtil_.loadClass(loader, className_);
				result = clazz.newInstance();
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException(e.getMessage());					
			} catch (InstantiationException e) {
				throw new IllegalStateException(e.getMessage());
			} catch (IllegalAccessException e) {
				throw new IllegalStateException(e.getMessage());
			}
			if(result != null && result instanceof StateHolder){
				((StateHolder)result).restoreState(context, savedState_);
			}
		}
		return result;
	}

}
