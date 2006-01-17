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
package javax.faces.component.html;

import javax.faces.component.UIInput;

import junit.framework.TestCase;

/**
 * @author manhole
 */
public class HtmlInputTextOnlyTest extends TestCase {

    public void testDefaultRendererType() throws Exception {
        HtmlInputText input = new HtmlInputText();
        assertEquals("javax.faces.Text", input.getRendererType());
    }

    public void testGetFamily() {
        HtmlInputText input = new HtmlInputText();
        assertEquals(UIInput.COMPONENT_FAMILY, input.getFamily());
    }

}
