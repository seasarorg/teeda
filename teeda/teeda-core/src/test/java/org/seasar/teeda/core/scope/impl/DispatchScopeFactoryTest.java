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
package org.seasar.teeda.core.scope.impl;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockFacesContextImpl;

/**
 * @author yone
 */
public class DispatchScopeFactoryTest extends TestCase {
    
    public void testGetRequestMapNotNull() {
        setCurrentInstanceMock();
        assertNotNull(DispatchScopeFactory.getRequestMap());
    }
    
    public void testGetRequestMapNull() {
        setCurrentInstanceNull();
        assertNull(DispatchScopeFactory.getRequestMap());
    }

    private void setCurrentInstanceNull() {
        OrgFacesContextImpl contextImpl = new OrgFacesContextImpl();
        contextImpl.setCurrentInstanceNull();
    }

    private void setCurrentInstanceMock() {
        OrgFacesContextImpl contextImpl = new OrgFacesContextImpl();
        contextImpl.setCurrentInstanceMock();
    }

    private class OrgFacesContextImpl extends MockFacesContextImpl {
        public void setCurrentInstanceNull() {
            setCurrentInstance(null);
        }

        public void setCurrentInstanceMock() {
            setCurrentInstance(new MockFacesContextImpl());
        }
    }
}
