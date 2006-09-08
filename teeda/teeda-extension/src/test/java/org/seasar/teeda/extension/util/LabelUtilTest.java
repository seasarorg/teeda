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
package org.seasar.teeda.extension.util;

import javax.faces.internal.FacesConfigOptions;

import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.convention.impl.NamingConventionImpl;
import org.seasar.teeda.extension.html.PageDesc;
import org.seasar.teeda.extension.html.factory.sub.web.foo.FooPage;
import org.seasar.teeda.extension.unit.TeedaExtensionTestCase;

/**
 * @author shot
 */
public class LabelUtilTest extends TeedaExtensionTestCase {

    public void testGetPropertiesName() throws Exception {
        NamingConventionImpl nc = (NamingConventionImpl) getContainer()
                .getComponent(NamingConvention.class);
        nc.addRootPackageName("org.seasar.teeda.extension.html.factory.sub");
        PageDesc pd = createPageDesc(FooPage.class, "foo_fooPage");
        assertEquals(
                "org.seasar.teeda.extension.html.factory.sub.web.foo.label",
                LabelUtil.getPropertiesName(nc, pd.getPageName()));
    }

    public void testGetLabelKeySuffix() throws Exception {
        NamingConventionImpl nc = (NamingConventionImpl) getContainer()
                .getComponent(NamingConvention.class);
        nc.addRootPackageName("org.seasar.teeda.extension.html.factory.sub");
        PageDesc pd = createPageDesc(FooPage.class, "foo_fooPage");
        FacesConfigOptions.setDefaultSuffix(".html");
        assertEquals("foo", LabelUtil.getLabelKeySuffix(nc, pd.getPageName()));
    }

    public void testGetDefaultApplicationPropertiesName() throws Exception {
        NamingConventionImpl nc = (NamingConventionImpl) getContainer()
                .getComponent(NamingConvention.class);
        nc.addRootPackageName("org.seasar.teeda.extension.html.factory.sub");
        PageDesc pd = createPageDesc(FooPage.class, "foo_fooPage");
        assertEquals("org.seasar.teeda.extension.html.factory.sub.web.label",
                LabelUtil.getDefaultApplicationPropertiesName(nc, pd
                        .getPageName()));
    }
}
