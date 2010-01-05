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
package javax.faces.component;

import junit.framework.TestCase;

/**
 * @author shot
 * @author manhole
 */
public class UIViewRootOnlyTest extends TestCase {

    public void testConstants() throws Exception {
        assertEquals("javax.faces.ViewRoot", UIViewRoot.COMPONENT_FAMILY);
        assertEquals("javax.faces.ViewRoot", UIViewRoot.COMPONENT_TYPE);
        assertEquals("_id", UIViewRoot.UNIQUE_ID_PREFIX);
    }

    public void testGetFamily() {
        assertEquals("javax.faces.ViewRoot", new UIViewRoot().getFamily());
    }

    public void testDefaultRendererType() throws Exception {
        assertEquals(null, new UIViewRoot().getRendererType());
    }

    public void testCreateUniqueId() {
        UIViewRoot root = new UIViewRoot();
        assertEquals(UIViewRoot.UNIQUE_ID_PREFIX + "0", root.createUniqueId());
        assertEquals(UIViewRoot.UNIQUE_ID_PREFIX + "1", root.createUniqueId());
    }

}
