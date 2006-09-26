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
package org.seasar.teeda.unit.web;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.embedder.MavenEmbedderException;
import org.apache.maven.project.ProjectBuildingException;
import org.seasar.framework.util.ClassTraversal;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.ClassTraversal.ClassHandler;
import org.seasar.teeda.util.MavenUtil;

/**
 * @author manhole
 */
public class FileSystemWebTestBuilder {

    public Test build(final Class referenceClass) throws IOException,
        MavenEmbedderException, ArtifactResolutionException,
        ArtifactNotFoundException, ProjectBuildingException {

        final TestSuite suite = new TestSuite();
        final List testClasses = collectTestClasses(ResourceUtil
            .getBuildDir(referenceClass));
        for (final Iterator it = testClasses.iterator(); it.hasNext();) {
            final Class testClass = (Class) it.next();
            suite.addTestSuite(testClass);
        }

        final File pomFile = MavenUtil.getProjectPomFile(referenceClass);
        return TeedaWebTestCase.setUpTest(suite, pomFile);
    }

    public List collectTestClasses(final File startingDirectory) {
        final TestClassHandler testClassHandler = new TestClassHandler();
        ClassTraversal.forEach(startingDirectory, testClassHandler);
        return testClassHandler.testClasses;
    }

    static class TestClassHandler implements ClassHandler {
        private List testClasses = new ArrayList();

        public void processClass(String packageName, String shortClassName) {
            if (!isTestClassNaming(shortClassName)) {
                return;
            }
            final String className = ClassUtil.concatName(packageName,
                shortClassName);
            final Class clazz = ClassUtil.forName(className);
            if (!isTestClass(clazz)) {
                return;
            }
            System.out.println("found testClass: " + className);
            testClasses.add(clazz);
        }

        protected boolean isTestClassNaming(final String name) {
            return name.endsWith("Test") && (-1 == name.indexOf("$"));
        }

        protected boolean isTestClass(Class clazz) {
            final int modifiers = clazz.getModifiers();
            if (Modifier.isInterface(modifiers)
                || Modifier.isAbstract(modifiers)) {
                return false;
            }
            return true;
        };
    }

}
