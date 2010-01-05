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
package org.seasar.teeda.extension.util;

import junit.framework.TestCase;

import org.seasar.teeda.core.JsfConstants;

public class CssStyleTest extends TestCase {

    private CssStyle cssStyle = new CssStyle();

    public void testStartStyle() throws Exception {
        cssStyle.startStyle();
        assertEquals("<style>" + JsfConstants.LINE_SP, cssStyle.getString());
    }

    public void testEndStyle() throws Exception {
        cssStyle.endStyle();
        assertEquals("</style>" + JsfConstants.LINE_SP, cssStyle.getString());
    }

    public void testStartSelector() throws Exception {
        cssStyle.startSelector(".aaa");
        assertEquals(".aaa {" + JsfConstants.LINE_SP, cssStyle.getString());
    }

    public void testEndSelector() throws Exception {
        cssStyle.endSelector();
        assertEquals("}" + JsfConstants.LINE_SP, cssStyle.getString());
    }

    public void testAddProperty() throws Exception {
        cssStyle.addProperty("overflow", "hidden");
        assertEquals("  overflow : hidden;" + JsfConstants.LINE_SP, cssStyle
                .getString());
    }
}
