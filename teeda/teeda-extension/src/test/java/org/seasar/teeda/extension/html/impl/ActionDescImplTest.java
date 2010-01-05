/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.core.application.navigation.NavigationResource;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.RedirectDesc;
import org.seasar.teeda.extension.html.TakeOverDesc;
import org.seasar.teeda.extension.html.impl.page.FooAction;
import org.seasar.teeda.extension.html.impl.page.FooPage;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author higa
 *
 */
public class ActionDescImplTest extends TeedaExtensionTestCase {

    public void tearDown() {
        NavigationResource.removeAll();
    }

    public void testHasMethod() throws Exception {
        ActionDesc ad = createActionDesc(FooAction.class, "fooAction");
        assertTrue("1", ad.hasMethod("doAaa"));
        assertFalse("2", ad.hasMethod("xxx"));
        assertFalse("3", ad.hasMethod(null));
    }

    public void testIsModified() throws Exception {
        File file = ResourceUtil.getResourceAsFile(ClassUtil
                .getResourcePath(FooAction.class));
        ActionDesc ad = createActionDesc(FooAction.class, "fooAction", file);
        assertFalse("1", ad.isModified());
        Thread.sleep(1000);
        file.setLastModified(System.currentTimeMillis());
        assertTrue("2", ad.isModified());
    }

    public void testGetTakeOverDesc() throws Exception {
        ActionDesc ad = createActionDesc(FooAction.class, "fooAction");
        TakeOverDesc tod = ad.getTakeOverDesc("doAaa");
        assertNotNull(tod);
        assertEquals(TakeOverTypeDescFactory.INCLUDE, tod.getTakeOverTypeDesc());
        String[] props = tod.getProperties();
        assertEquals(2, props.length);
        assertEquals("aaa", props[0]);
        assertEquals("bbb", props[1]);
    }

    public void testGetRedirectDesc() throws Exception {
        PageDesc pd = createPageDesc(FooPage.class, "fooAction");
        assertTrue(pd.hasRedirectDesc("doBar"));
        RedirectDesc rd = pd.getRedirectDesc("doBar");
        assertNotNull(rd);
        assertEquals(RedirectDesc.HTTPS, rd.getProtocol());
    }

}
