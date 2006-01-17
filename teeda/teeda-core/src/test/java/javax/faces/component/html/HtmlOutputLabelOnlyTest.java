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

import javax.faces.component.UIOutput;

import junit.framework.TestCase;

/**
 * @author manhole
 */
public class HtmlOutputLabelOnlyTest extends TestCase {

    public void testDefaultRendererType() throws Exception {
        HtmlOutputLabel htmlOutputLabel = new HtmlOutputLabel();
        assertEquals("javax.faces.Label", htmlOutputLabel.getRendererType());
    }

    public void testGetFamily() {
        HtmlOutputLabel htmlOutputLabel = new HtmlOutputLabel();
        assertEquals(UIOutput.COMPONENT_FAMILY, htmlOutputLabel.getFamily());
    }

}
