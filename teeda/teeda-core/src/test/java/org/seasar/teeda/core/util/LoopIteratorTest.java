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
package org.seasar.teeda.core.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

import org.seasar.teeda.core.unit.ExceptionAssert;

/**
 * @author manhole
 */
public class LoopIteratorTest extends TestCase {

    public void testNext() throws Exception {
        // ## Arrange ##
        Iterator it = new LoopIterator(new String[] { "a", "bb", "ccc" });

        // ## Act & Assert ##
        assertEquals("a", it.next());
        assertEquals("bb", it.next());
        assertEquals("ccc", it.next());
        assertEquals("a", it.next());
        assertEquals("bb", it.next());
        assertEquals("ccc", it.next());
        assertEquals("a", it.next());
    }

    public void testHasNext() throws Exception {
        // ## Arrange ##
        Iterator it = new LoopIterator(new String[] { "a", "bb", "ccc" });

        // ## Act & Assert ##
        assertEquals(true, it.hasNext());
        assertEquals(true, it.hasNext());
        assertEquals(true, it.hasNext());
        assertEquals(true, it.hasNext());
        assertEquals(true, it.hasNext());
        assertEquals(true, it.hasNext());
    }

    public void testNext_One() throws Exception {
        // ## Arrange ##
        Iterator it = new LoopIterator(new String[] { "a" });

        // ## Act & Assert ##
        assertEquals("a", it.next());
        assertEquals("a", it.next());
        assertEquals("a", it.next());
    }

    public void testHasNext_One() throws Exception {
        // ## Arrange ##
        Iterator it = new LoopIterator(new String[] { "a" });

        // ## Act & Assert ##
        assertEquals(true, it.hasNext());
        assertEquals(true, it.hasNext());
        assertEquals(true, it.hasNext());
        assertEquals(true, it.hasNext());
        assertEquals(true, it.hasNext());
        assertEquals(true, it.hasNext());
    }

    public void testHasNext_Empty() throws Exception {
        // ## Arrange ##
        Iterator it = new LoopIterator(new String[] {});

        // ## Act & Assert ##
        assertEquals(false, it.hasNext());
        assertEquals(false, it.hasNext());
        assertEquals(false, it.hasNext());
        assertEquals(false, it.hasNext());
    }

    public void testNext_Empty() throws Exception {
        // ## Arrange ##
        Iterator it = new LoopIterator(new String[] {});

        // ## Act & Assert ##
        try {
            it.next();
            fail();
        } catch (NoSuchElementException nsee) {
            ExceptionAssert.assertMessageExist(nsee);
        }
    }

    public void testConstructorWithNull() throws Exception {
        // ## Arrange ##
        try {
            // ## Act ##
            new LoopIterator(null);

            // ## Assert ##
            fail("");
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

}
