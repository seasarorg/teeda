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
package org.seasar.teeda.it;

import java.io.File;
import java.io.FileFilter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.FileUtil;
import org.seasar.teeda.core.util.MavenUtil;

import com.gargoylesoftware.base.util.DirectoryWalker;

/**
 * @author manhole
 */
public class TeedaIntegrationTests extends TestCase {

    public static Test suite() throws Exception {
        final Class testClass = TeedaIntegrationTests.class;
        final File pomFile = MavenUtil.getProjectPomFile(testClass);
        final File project = pomFile.getParentFile();
        final String startingDirectory = new File(project,
                "target/test-classes").getCanonicalPath().replace('\\', '/');

        final TestSuite suite = new TestSuite();
        DirectoryWalker walker = new DirectoryWalker(startingDirectory);
        walker.getFiles(new FileFilter() {

            public boolean accept(final File pathname) {
                final String name = FileUtil.getCanonicalPath(pathname)
                        .replace('\\', '/');
                if (isTestClass(name)) {
                    final int pos = name.lastIndexOf(startingDirectory);
                    String className = name.substring(pos
                            + startingDirectory.length());
                    if (className.startsWith("/")) {
                        className = className.substring(1);
                    }
                    className = className.substring(0, className.length()
                            - ".class".length());
                    className = className.replace('/', '.');
                    System.out.println("found testClass: " + className);
                    Class clazz = ClassUtil.forName(className);
                    suite.addTestSuite(clazz);
                    return true;
                }
                return false;
            }

            private boolean isTestClass(final String name) {
                return name.endsWith("Test.class") && (-1 == name.indexOf("$"));
            }
        });
        return AbstractTestCase.setUpTestSuite(suite, pomFile);
    }

}
