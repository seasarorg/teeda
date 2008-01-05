/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package javax.faces.internal;

import java.io.Serializable;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;

import org.seasar.framework.log.Logger;

/**
 * @author shot
 * @author manhole
 *
 * A Helper class for UIComponentBase.saveAttachedState
 *
 * This class might be changed without notice. Please do not use it
 * excluding the JSF specification part.
 */
public class AttachedObjectStateWrapper implements Serializable {

    private static final Logger logger = Logger
            .getLogger(AttachedObjectStateWrapper.class);

    private static final long serialVersionUID = 3256726169255885111L;

    private Object savedState = null;

    private String className = null;

    private boolean isSavedStateHolder = false;

    public AttachedObjectStateWrapper(FacesContext context, Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }
        className = obj.getClass().getName();
        if (obj instanceof StateHolder) {
            StateHolder stateHolder = (StateHolder) obj;
            if (!stateHolder.isTransient()) {
                savedState = stateHolder.saveState(context);
                isSavedStateHolder = true;
            }
        } else {
            if (!(obj instanceof Serializable)) {
                logger.debug("class : " + className
                        + " should be serializable.");
            }
            savedState = obj;
        }
    }

    public Object restore(FacesContext context) throws IllegalStateException {
        Object result = null;
        if (!isSavedStateHolder) {
            result = savedState;
        } else if (isSavedStateHolder) {
            try {
                ClassLoader loader = ClassLoaderUtil.getClassLoader(context);
                Class clazz = ClassLoaderUtil.loadClass(loader, className);
                result = clazz.newInstance();
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e.getMessage());
            } catch (InstantiationException e) {
                throw new IllegalStateException(e.getMessage());
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e.getMessage());
            }
            if (savedState != null && result instanceof StateHolder) {
                ((StateHolder) result).restoreState(context, savedState);
            }
        }
        return result;
    }

}
