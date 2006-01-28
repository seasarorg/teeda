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
package org.seasar.teeda.core.spike.cl;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Iterator;

import junit.framework.TestCase;
import junitx.framework.StringAssert;

import org.seasar.teeda.core.util.EnumerationIterator;

public class ClassLoaderResourceTest extends TestCase {

    public void testGetResourceFromClassLoader() throws Exception {
        // ## Arrange ##
        File projectRoot = new File(".").getCanonicalFile();
        File jarDir = new File(projectRoot,
                "teeda-core/target/test-classes/org/seasar/teeda/core/spike/cl");
        File[] jars = jarDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if (name.endsWith(".jar")) {
                    return true;
                }
                return false;
            }
        });
        assertEquals(3, jars.length);
        URL[] jarUrls = new URL[jars.length];
        for (int i = 0; i < jars.length; i++) {
            System.out.println(jars[i].toURL());
            jarUrls[i] = jars[i].toURL();
        }
        URLClassLoader cl = new URLClassLoader(jarUrls);

        // ## Act ##
        Iterator facesConfigUrlIterator = getConfigurationUrlFromResources(cl);

        // ## Assert ##
        assertEquals(true, facesConfigUrlIterator.hasNext());
        URL url1 = (URL) facesConfigUrlIterator.next();
        StringAssert.assertEndsWith(
                "/faces-config-contains-1.jar!/META-INF/faces-config.xml", url1
                        .getPath());

        assertEquals(true, facesConfigUrlIterator.hasNext());
        URL url2 = (URL) facesConfigUrlIterator.next();
        StringAssert.assertEndsWith(
                "/faces-config-contains-2.jar!/META-INF/faces-config.xml", url2
                        .getPath());

        assertEquals(false, facesConfigUrlIterator.hasNext());
    }

    Iterator getConfigurationUrlFromResources(ClassLoader cl)
            throws IOException {
        Enumeration resources = cl.getResources("META-INF/faces-config.xml");
        return new EnumerationIterator(resources);
    }

}
