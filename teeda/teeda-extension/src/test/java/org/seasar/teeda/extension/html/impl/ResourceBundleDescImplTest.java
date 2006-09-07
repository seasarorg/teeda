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

import java.util.Locale;

import junit.framework.TestCase;

import org.seasar.framework.util.ClassUtil;

/**
 * @author shot
 */
public class ResourceBundleDescImplTest extends TestCase {

    public void testPropertiesName() throws Exception {
        String packageName = ClassUtil.getPackageName(this.getClass());
        ResourceBundleDescImpl rbd = new ResourceBundleDescImpl(packageName
                + ".aaa", Locale.JAPANESE);
        String s = packageName.replace('.', '/');
        assertEquals(s + "/aaa_ja.properties", rbd.getPropertiesName());
    }

    public void testProperties() throws Exception {
        String packageName = ClassUtil.getPackageName(this.getClass());
        ResourceBundleDescImpl rbd = new ResourceBundleDescImpl(packageName
                + ".aaa", Locale.JAPANESE);
        assertNotNull(rbd.getBundle());
    }

    public void testIsModified() throws Exception {
        String packageName = ClassUtil.getPackageName(this.getClass());
        ResourceBundleDescImpl rbd = new ResourceBundleDescImpl(packageName
                + ".aaa", Locale.JAPANESE);
        assertTrue(rbd.isModified(Locale.CANADA));
        assertFalse(rbd.isModified(Locale.JAPANESE));
    }

}
