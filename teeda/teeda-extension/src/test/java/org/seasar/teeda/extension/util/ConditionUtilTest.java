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
package org.seasar.teeda.extension.util;

import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.component.UIForm;
import javax.faces.internal.FacesConfigOptions;

import org.seasar.teeda.core.context.html.HtmlResponseWriter;
import org.seasar.teeda.core.mock.MockExternalContext;
import org.seasar.teeda.core.unit.TeedaTestCase;
import org.seasar.teeda.core.util.JavaScriptPermissionUtil;
import org.seasar.teeda.extension.util.ConditionUtil.ConditionRendererListener;

/**
 * @author koichik
 * 
 */
public class ConditionUtilTest extends TeedaTestCase {

    public void testRenderJavascript_Allowed() throws Exception {

        Map conditions = new LinkedHashMap();
        conditions.put("isXxx", Boolean.TRUE);
        conditions.put("isNotYyy", Boolean.FALSE);
        ConditionUtil.setConditions(getFacesContext(), conditions);

        UIForm form = new UIForm();
        form.setId("xxxForm");
        ConditionUtil.addForm(getFacesContext(), form);

        StringWriter sw = new StringWriter();
        HtmlResponseWriter writer = new HtmlResponseWriter();
        writer.setWriter(sw);
        getFacesContext().setResponseWriter(writer);
        new ConditionRendererListener().renderBeforeBodyEnd(getFacesContext());
        writer.close();
        System.out.println(sw);
        assertTrue(sw.toString().indexOf("<script") != -1);
    }

    public void testRenderJavascript_NotAllow() throws Exception {
        MockExternalContext ec = getExternalContext();
        ec.setRequestPathInfo("/i/hoge");
        FacesConfigOptions.setJavascriptNotPermittedPath(new String[] { "/i" });
        assertFalse(JavaScriptPermissionUtil
                .isJavaScriptPermitted(getFacesContext()));

        Map conditions = new LinkedHashMap();
        conditions.put("isXxx", Boolean.TRUE);
        conditions.put("isNotYyy", Boolean.FALSE);
        ConditionUtil.setConditions(getFacesContext(), conditions);

        UIForm form = new UIForm();
        form.setId("xxxForm");
        ConditionUtil.addForm(getFacesContext(), form);

        StringWriter sw = new StringWriter();
        HtmlResponseWriter writer = new HtmlResponseWriter();
        writer.setWriter(sw);
        getFacesContext().setResponseWriter(writer);
        new ConditionRendererListener().renderBeforeBodyEnd(getFacesContext());
        writer.close();
        System.out.println(sw);
        assertTrue(sw.toString().indexOf("<script") == -1);
    }
}
