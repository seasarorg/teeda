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
package org.seasar.teeda.unit.web;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.unit.web.FileSystemWebTestBuilder;

/**
 * @author manhole
 */
public class FileSystemWebTestBuilderTest extends TestCase {

    public void testCollectTestClass() throws Exception {
        // ## Arrange ##
        final File buildDir = ResourceUtil
            .getBuildDir(FileSystemWebTestBuilderTest.class);
        final FileSystemWebTestBuilder builder = new FileSystemWebTestBuilder();

        // ## Act ##
        final List testClasses = builder.collectTestClasses(buildDir);

        // ## Assert ##
        boolean found = false;
        for (Iterator it = testClasses.iterator(); it.hasNext();) {
            final Class clazz = (Class) it.next();
            if (clazz == FileSystemWebTestBuilderTest.class) {
                found = true;
            }
        }
        assertEquals(true, found);
    }

    public void testIsTestClass() throws Exception {
        final FileSystemWebTestBuilder.TestClassHandler handler = new FileSystemWebTestBuilder.TestClassHandler();
        assertEquals(true, handler
            .isTestClass(FileSystemWebTestBuilderTest.class));
        assertEquals(false, handler.isTestClass(ATest.class));
        assertEquals(false, handler.isTestClass(BTest.class));
        assertEquals(true, handler.isTestClass(CTest.class));
    }

    private static interface ATest {
    }

    private static abstract class BTest {
    }

    private static class CTest {
    }

}
