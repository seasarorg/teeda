/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import org.seasar.teeda.core.unit.TeedaTestCase;

public class HtmlSuffixImplTest extends TeedaTestCase {

    private HtmlSuffixImpl htmlSuffix = new HtmlSuffixImpl();

    public void testSetupSuffix() throws Exception {
        htmlSuffix.setupSuffix(getFacesContext(), "/view/aaa/ccc_i.html");
        assertEquals("_i", htmlSuffix.getSuffix(getFacesContext()));
    }

    public void testNormalizePath() throws Exception {
        assertEquals("/view/aaa/ccc.html", htmlSuffix
                .normalizePath("/view/aaa/ccc_i.html"));
        assertEquals("/view/aaa/ccc.html", htmlSuffix
                .normalizePath("/view/aaa/ccc.html"));
    }
}