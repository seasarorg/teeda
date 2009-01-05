/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import javax.faces.component.UIViewRoot;
import javax.faces.internal.web.foo.FooPage;

import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.convention.impl.NamingConventionImpl;
import org.seasar.framework.util.ClassUtil;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class LabelUtilTest extends TeedaTestCase {

    public void testGetLabelValue() throws Exception {
        NamingConventionImpl nc = (NamingConventionImpl) getContainer()
                .getComponent(NamingConvention.class);
        nc.addRootPackageName(ClassUtil.getPackageName(getClass()));
        register(FooPage.class, "foo_fooPage");
        FacesConfigOptions.setDefaultSuffix(".html");
        getFacesContext().getViewRoot().setViewId(
                nc.getViewRootPath() + "/foo/foo.html");
        assertEquals("AAA", LabelUtil.getLabelValue("aaa"));
        assertEquals("AAA", LabelUtil.getLabelValue("aaa-input"));
    }

    public void testGetLabelValue_contextIsNull() throws Exception {
        MockFacesContext orgFacesContext = getFacesContext();
        NamingConventionImpl nc = (NamingConventionImpl) getContainer()
                .getComponent(NamingConvention.class);
        nc.addRootPackageName(ClassUtil.getPackageName(getClass()));
        register(FooPage.class, "foo_fooPage");
        FacesConfigOptions.setDefaultSuffix(".html");
        orgFacesContext.getViewRoot().setViewId(
                nc.getViewRootPath() + "/foo/foo.html");
        MockFacesContext.setFacesContext(null);
        assertNull(LabelUtil.getLabelValue("aaa"));
        setFacesContext(orgFacesContext);
        MockFacesContext.setFacesContext(orgFacesContext);
    }

    public void testGetLabelValue_viewRootIsNull() throws Exception {
        MockFacesContext orgFacesContext = getFacesContext();
        NamingConventionImpl nc = (NamingConventionImpl) getContainer()
                .getComponent(NamingConvention.class);
        nc.addRootPackageName(ClassUtil.getPackageName(getClass()));
        register(FooPage.class, "foo_fooPage");
        FacesConfigOptions.setDefaultSuffix(".html");
        orgFacesContext.getViewRoot().setViewId(
                nc.getViewRootPath() + "/foo/foo.html");
        setFacesContext(new MockFacesContextImpl() {

            public UIViewRoot getViewRoot() {
                return null;
            }

        });
        assertNull(LabelUtil.getLabelValue("aaa"));
        setFacesContext(orgFacesContext);
    }

    public void testGetLabelValue_viewRootPathIsInvalid() throws Exception {
        NamingConventionImpl nc = new NamingConventionImpl();
        nc.setViewRootPath("/view");
        nc.setViewExtension(".html");
        FacesConfigOptions.setDefaultSuffix(".html");
        getFacesContext().getViewRoot().setViewId("/foo/foo.html");
        assertNull(LabelUtil.getLabelValue("/foo/bar.html"));
    }

    public void testGetPropertiesName() throws Exception {
        NamingConventionImpl nc = (NamingConventionImpl) getContainer()
                .getComponent(NamingConvention.class);
        nc.addRootPackageName(ClassUtil.getPackageName(getClass()));
        register(FooPage.class, "foo_fooPage");
        assertEquals("javax.faces.internal.web.foo.label", LabelUtil
                .getPropertiesName(nc, "foo_fooPage"));
    }

    public void testGetLabelKeySuffix() throws Exception {
        NamingConventionImpl nc = (NamingConventionImpl) getContainer()
                .getComponent(NamingConvention.class);
        nc.addRootPackageName(ClassUtil.getPackageName(getClass()));
        register(FooPage.class, "foo_fooPage");
        FacesConfigOptions.setDefaultSuffix(".html");
        assertEquals("foo", LabelUtil.getLabelKeySuffix(nc, "foo_fooPage"));
    }

    public void testGetDefaultApplicationPropertiesName() throws Exception {
        NamingConventionImpl nc = (NamingConventionImpl) getContainer()
                .getComponent(NamingConvention.class);
        nc.addRootPackageName(ClassUtil.getPackageName(getClass()));
        register(FooPage.class, "foo_fooPage");
        assertEquals(ClassUtil.getPackageName(getClass()) + ".web.label",
                LabelUtil
                        .getDefaultApplicationPropertiesName(nc, "foo_fooPage"));
    }

    public void testGetDefaultApplicationPropertiesName_packageNameNull()
            throws Exception {
        NamingConventionImpl nc = (NamingConventionImpl) getContainer()
                .getComponent(NamingConvention.class);
        nc.addRootPackageName(ClassUtil.getPackageName(getClass()));
        register(FooPage.class, "foo_fooPage");
        assertNull(LabelUtil.getDefaultApplicationPropertiesName(nc,
                "noSuchPackage_fooPage"));
    }

}