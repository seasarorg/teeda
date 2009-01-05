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
package org.seasar.teeda.core.util;

import javax.faces.internal.FacesConfigOptions;

import org.seasar.teeda.core.mock.MockExternalContext;
import org.seasar.teeda.core.unit.TeedaTestCase;

/**
 * @author shot
 */
public class JavaScriptPermissionUtilTest extends TeedaTestCase {

    public void testNotPermitted1() throws Exception {
        MockExternalContext context = getExternalContext();
        context.setRequestPathInfo("/i/hoge");
        FacesConfigOptions.setJavascriptNotPermittedPath(new String[] { "/i",
                "/vf", "/au" });
        assertFalse(JavaScriptPermissionUtil
                .isJavaScriptPermitted(getFacesContext()));
    }

    public void testNotPermitted2() throws Exception {
        MockExternalContext context = getExternalContext();
        context.setRequestPathInfo("/i/hoge");
        FacesConfigOptions.setJavascriptNotPermittedPath(new String[] { " /i/",
                "/vf", "/au" });
        assertFalse(JavaScriptPermissionUtil
                .isJavaScriptPermitted(getFacesContext()));
    }

    public void testNotPermitted3() throws Exception {
        MockExternalContext context = getExternalContext();
        context.setRequestPathInfo("/i/hoge");
        FacesConfigOptions.setJavascriptNotPermittedPath(new String[] { " i",
                "/vf", "/au" });
        assertFalse(JavaScriptPermissionUtil
                .isJavaScriptPermitted(getFacesContext()));
    }

    public void testNotPermitted5() throws Exception {
        MockExternalContext context = getExternalContext();
        context.setRequestPathInfo(null);
        FacesConfigOptions.setJavascriptNotPermittedPath(new String[] { "/" });
        assertFalse(JavaScriptPermissionUtil
                .isJavaScriptPermitted(getFacesContext()));
    }

    public void testPermitted1() throws Exception {
        MockExternalContext context = getExternalContext();
        context.setRequestPathInfo("/i/hoge");
        FacesConfigOptions.setJavascriptNotPermittedPath(null);
        assertTrue(JavaScriptPermissionUtil
                .isJavaScriptPermitted(getFacesContext()));
    }

    public void testPermitted2() throws Exception {
        MockExternalContext context = getExternalContext();
        context.setRequestPathInfo(null);
        FacesConfigOptions.setJavascriptNotPermittedPath(new String[] { "/i" });
        assertTrue(JavaScriptPermissionUtil
                .isJavaScriptPermitted(getFacesContext()));
    }

    public void testPermitted3() throws Exception {
        MockExternalContext context = getExternalContext();
        context.setRequestPathInfo("/i/hoge");
        FacesConfigOptions
                .setJavascriptNotPermittedPath(new String[] { "/au" });
        assertTrue(JavaScriptPermissionUtil
                .isJavaScriptPermitted(getFacesContext()));
    }

    public void testPermitted4() throws Exception {
        MockExternalContext context = getExternalContext();
        context.setRequestPathInfo("/index.html");
        FacesConfigOptions.setJavascriptNotPermittedPath(new String[] { "/i" });
        assertTrue(JavaScriptPermissionUtil
                .isJavaScriptPermitted(getFacesContext()));
    }

}
