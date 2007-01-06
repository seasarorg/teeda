/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.lifecycle;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;

import org.seasar.teeda.core.exception.LifecycleIdAlreadyExistRuntimeException;
import org.seasar.teeda.core.exception.LifecycleIdNotFoundRuntimeException;
import org.seasar.teeda.core.util.DIContainerUtil;

/**
 * @author higa
 * @author shot
 */
public class LifecycleFactoryImpl extends LifecycleFactory {

    private Map lifecycles_ = Collections.synchronizedMap(new HashMap());

    public LifecycleFactoryImpl() {
        Lifecycle lifecycle = (Lifecycle) DIContainerUtil
                .getComponentNoException(Lifecycle.class);
        addLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE, lifecycle);
    }

    public void addLifecycle(String id, Lifecycle lifecycle) {
        if (lifecycles_.put(id, lifecycle) != null) {
            throw new LifecycleIdAlreadyExistRuntimeException(id);
        }
    }

    public Lifecycle getLifecycle(String id) throws FacesException {
        Lifecycle lifecycle = (Lifecycle) lifecycles_.get(id);
        if (lifecycle == null) {
            throw new LifecycleIdNotFoundRuntimeException(id);
        }
        return lifecycle;
    }

    public Iterator getLifecycleIds() {
        return lifecycles_.keySet().iterator();
    }
}