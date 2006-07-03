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
package org.seasar.teeda.extension.html.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.seasar.framework.unit.S2FrameworkTestCase;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.core.application.navigation.NavigationContext;
import org.seasar.teeda.core.application.navigation.NavigationResource;

/**
 * @author higa
 *
 */
public class ActionDescImplTest extends S2FrameworkTestCase {
	
    public void tearDown() {
        NavigationResource.removeAll();
    }

    public void testHasMethod() throws Exception {
        ActionDescImpl ad = new ActionDescImpl(FooAction.class, "fooAction");
        assertTrue("1", ad.hasMethod("doAaa"));
        assertFalse("2", ad.hasMethod("xxx"));
        assertFalse("3", ad.hasMethod(null));
    }

    public void testIsModified() throws Exception {
        File file = ResourceUtil.getResourceAsFile(
                ClassUtil.getResourcePath(FooAction.class));
        ActionDescImpl ad = new ActionDescImpl(FooAction.class, "fooAction", file);
        assertFalse("1", ad.isModified());
        Thread.sleep(1000);
        file.setLastModified(System.currentTimeMillis());
        assertTrue("2", ad.isModified());
    }
    
    public void testNavigation() throws Exception {
        register(FooPage.class);
        register(Foo2Action.class);
        new ActionDescImpl(Foo2Action.class, "foo2Action");
        Map m = NavigationResource.getNavigationContexts();
        assertNotNull(m);
        List l = (List) m.get("/view/foo2.html");
        assertNotNull(l);
        assertEquals(1, l.size());
        NavigationContext nc = (NavigationContext) l.get(0);
        assertEquals("/view/foo2.html", nc.getFromViewId());
    }
}