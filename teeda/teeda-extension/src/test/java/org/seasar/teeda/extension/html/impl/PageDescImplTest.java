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

import javax.faces.internal.ValidatorResource;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.unit.S2FrameworkTestCase;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.extension.validator.RequiredValidator;

import junit.framework.TestCase;

/**
 * @author higa
 *
 */
public class PageDescImplTest extends S2FrameworkTestCase {
	
    protected void tearDown() {
        ValidatorResource.removeAll();
    }

    public void testIsValid() throws Exception {
        PageDescImpl pd = new PageDescImpl(FooPage.class, "fooPage");
        assertTrue(pd.isValid("aaa"));
        assertTrue(pd.isValid("doBbb"));
        assertFalse(pd.isValid("xxx"));
        assertTrue(pd.isValid("fooForm"));
        assertFalse(pd.isValid(null));
    }

    public void testIsModified() throws Exception {
        File file = ResourceUtil.getResourceAsFile(
                ClassUtil.getResourcePath(FooPage.class));
        PageDescImpl pd = new PageDescImpl(FooPage.class, "fooPage", file);
        assertFalse("1", pd.isModified());
        Thread.sleep(1000);
        file.setLastModified(System.currentTimeMillis());
        assertTrue("2", pd.isModified());
    }
    
    public void testValidator() throws Exception {
        ComponentDef cd = new ComponentDefImpl(RequiredValidator.class, "requiredValidator");
        register(cd);
        new PageDescImpl(HogePage.class, "hogePage");
        assertNotNull(ValidatorResource.getValidator("#{hogePage.aaa}"));
    }
}