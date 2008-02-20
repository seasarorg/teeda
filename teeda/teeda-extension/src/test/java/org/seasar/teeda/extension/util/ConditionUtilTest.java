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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.teeda.core.context.html.HtmlResponseWriter;
import org.seasar.teeda.extension.util.ConditionUtil.ConditionRendererListener;

/**
 * @author koichik
 * 
 */
public class ConditionUtilTest extends TestCase {

    public void testRenderJavascript() throws Exception {
        Map conditions = new LinkedHashMap();
        conditions.put("isXxx", Boolean.TRUE);
        conditions.put("isNotYyy", Boolean.FALSE);

        List forms = new ArrayList();
        forms.add("xxxForm");
        forms.add("yyyForm");

        StringWriter sw = new StringWriter();
        HtmlResponseWriter writer = new HtmlResponseWriter();
        writer.setWriter(sw);
        ConditionRendererListener.renderJavascript(writer, conditions, forms);
        writer.close();
        System.out.println(sw);

    }
}
