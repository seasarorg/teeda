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
package org.seasar.teeda.util;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.util.MavenUtil;

/**
 * @author manhole
 */
public class MavenUtilTest extends TestCase {

    public void testGetSeleniumDriverWar() throws Exception {
        final File project = getProjectFile("teeda-integration-test");
        final File pom = new File(project, "pom.xml");
        final File artifactFile = MavenUtil.getArtifactFromPom(pom, "junit");
        System.out.println(artifactFile);
        assertNotNull(artifactFile);
        assertEquals(true, artifactFile.exists());
        final String name = artifactFile.getName();
        assertEquals(name, true, name.startsWith("junit"));
        assertEquals(name, true, name.endsWith(".jar"));
    }

    public void no_testResourceUtilSpike() throws Exception {
        final File buildDir = ResourceUtil.getBuildDir(getClass());
        final File resourceDir = ResourceUtil.getResourceAsFile(".");
        // with Maven2, each path end with "target/test-classes"
        // but with Eclipse, former ends with "target/test-classes"
        // and latter latter ends with "target/classes"
        System.out.println(buildDir);
        System.out.println(resourceDir);
        assertEquals(buildDir, resourceDir);
    }

    private File getProjectFile(final String projectName) throws IOException {
        File project = null;
        for (File current = ResourceUtil.getResourceAsFile("."); current != null; current = current
            .getParentFile()) {
            if (projectName.equals(current.getName())) {
                if (pomExists(current)) {
                    project = current;
                    break;
                }
            }
            final File brother = new File(current, projectName);
            if (pomExists(brother)) {
                project = brother;
                break;
            }
        }
        if (project == null) {
            return null;
        }
        return project.getCanonicalFile();
    }

    private boolean pomExists(final File f) {
        return new File(f, "pom.xml").exists();
    }

}
