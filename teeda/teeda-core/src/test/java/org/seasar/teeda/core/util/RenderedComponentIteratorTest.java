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

import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.faces.component.UIComponent;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockUIComponentBase;

/**
 * @author manhole
 */
public class RenderedComponentIteratorTest extends TestCase {

    public void testOne() throws Exception {
        // ## Arrange ##
        UIComponent component = new MockUIComponentBase();
        UIComponent child = new MockUIComponentBase();
        child.setRendered(true);
        component.getChildren().add(child);

        // ## Act ##
        RenderedComponentIterator it = new RenderedComponentIterator(component
                .getChildren());

        // ## Assert ##
        assertEquals(true, it.hasNext());
        it.next();
        assertEquals(false, it.hasNext());
    }

    public void testOne_RenderFalse() throws Exception {
        // ## Arrange ##
        UIComponent component = new MockUIComponentBase();
        UIComponent child = new MockUIComponentBase();
        child.setRendered(false);
        component.getChildren().add(child);

        // ## Act ##
        RenderedComponentIterator it = new RenderedComponentIterator(component
                .getChildren());

        // ## Assert ##
        assertEquals(false, it.hasNext());
    }

    public void testMany() throws Exception {
        // ## Arrange ##
        UIComponent component = new MockUIComponentBase();
        {
            UIComponent child = new MockUIComponentBase();
            child.setId("a");
            child.setRendered(false);
            component.getChildren().add(child);
        }
        {
            UIComponent child = new MockUIComponentBase();
            child.setId("b");
            child.setRendered(true);
            component.getChildren().add(child);
        }
        {
            UIComponent child = new MockUIComponentBase();
            child.setId("c");
            child.setRendered(true);
            component.getChildren().add(child);
        }
        {
            UIComponent child = new MockUIComponentBase();
            child.setId("d");
            child.setRendered(false);
            component.getChildren().add(child);
        }

        // ## Act ##
        RenderedComponentIterator it = new RenderedComponentIterator(component
                .getChildren());

        // ## Assert ##
        {
            UIComponent child = (UIComponent) it.next();
            assertEquals("b", child.getId());
        }
        {
            UIComponent child = (UIComponent) it.next();
            assertEquals("c", child.getId());
        }
        try {
            it.next();
        } catch (NoSuchElementException e) {
        }
    }

    public void testEmpty_HasNext() throws Exception {
        // ## Arrange ##

        // ## Act ##
        RenderedComponentIterator it = new RenderedComponentIterator(
                new ArrayList());

        // ## Assert ##
        assertEquals(false, it.hasNext());
    }

    public void testEmpty_Next() throws Exception {
        // ## Arrange ##

        // ## Act ##
        RenderedComponentIterator it = new RenderedComponentIterator(
                new ArrayList());

        // ## Assert ##
        try {
            it.next();
        } catch (NoSuchElementException e) {
        }
    }

}
