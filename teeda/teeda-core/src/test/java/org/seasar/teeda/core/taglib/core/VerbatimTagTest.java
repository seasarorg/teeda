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
package org.seasar.teeda.core.taglib.core;

import javax.faces.component.UIOutput;
import javax.servlet.jsp.tagext.Tag;

import junit.framework.TestCase;

import org.seasar.teeda.core.JsfConstants;

/**
 * @author yone
 */
public class VerbatimTagTest extends TestCase {

    public void testGetComponentType() throws Exception {
        // # Arrange #
        VerbatimTag tag = new VerbatimTag();

        // # Act & Assert #
        assertEquals("javax.faces.Output", tag.getComponentType());
    }

    public void testGetRenderType() throws Exception {
        // # Arrange #
        VerbatimTag tag = new VerbatimTag();

        // # Act & Assert #
        assertEquals("javax.faces.Text", tag.getRendererType());
    }

    public void testSetProperties_default() throws Exception {
        // # Arrange #
        UIOutput output = createUIOutput();
        VerbatimTag tag = new VerbatimTag();

        // # Act #
        tag.setProperties(output);

        // # Assert #
        assertEquals(Boolean.FALSE, output.getAttributes().get(
                JsfConstants.ESCAPE_ATTR));
    }

    public void testSetProperties_true() throws Exception {
        // # Arrange #
        UIOutput output = createUIOutput();
        VerbatimTag tag = new VerbatimTag();

        // # Act #
        tag.setEscape("true");
        tag.setProperties(output);

        // # Assert #
        assertEquals(Boolean.TRUE, output.getAttributes().get(
                JsfConstants.ESCAPE_ATTR));
        assertEquals(true, output.isTransient());
    }

    public void testDoAfterBody() throws Exception {
        // # Arrange #
        VerbatimTag tag = new VerbatimTag();

        // # Act #
        int rc = tag.doAfterBody();

        // # Assert #
        assertEquals(rc, Tag.SKIP_BODY);
    }

    private UIOutput createUIOutput() {
        return (UIOutput) createUIComponent();
    }

    protected UIOutput createUIComponent() {
        return new UIOutput();
    }

}
