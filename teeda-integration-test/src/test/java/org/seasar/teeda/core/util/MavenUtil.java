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
package org.seasar.teeda.core.util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.embedder.MavenEmbedder;
import org.apache.maven.embedder.MavenEmbedderConsoleLogger;
import org.apache.maven.embedder.MavenEmbedderException;
import org.apache.maven.embedder.MavenEmbedderLogger;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuildingException;
import org.seasar.framework.util.ResourceUtil;

/**
 * @author manhole
 */
public class MavenUtil {

    public static File getArtifactFromPom(File pom, String artifactId) {
        MavenEmbedder maven = new MavenEmbedder();
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        maven.setClassLoader(classLoader);
        MavenEmbedderConsoleLogger logger = new MavenEmbedderConsoleLogger();
        logger.setThreshold(MavenEmbedderLogger.LEVEL_ERROR);
        maven.setLogger(logger);
        try {
            maven.start();
            MavenProject mavenProject = maven.readProjectWithDependencies(pom);
            Artifact targetArtifact = null;
            for (Iterator it = mavenProject.getArtifacts().iterator(); it
                    .hasNext();) {
                Artifact artifact = (Artifact) it.next();
                if (artifactId.equals(artifact.getArtifactId())) {
                    targetArtifact = artifact;
                }
            }
            if (targetArtifact == null) {
                throw new RuntimeException("[" + artifactId
                        + "] not found. from " + pom);
            }
            File targetArtifactFile = targetArtifact.getFile()
                    .getCanonicalFile();
            maven.stop();
            return targetArtifactFile;
        } catch (MavenEmbedderException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ArtifactResolutionException e) {
            throw new RuntimeException(e);
        } catch (ArtifactNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ProjectBuildingException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getProjectPomFile(final Class clazz) {
        File file = ResourceUtil.getBuildDir(clazz);
        for (File f = file; f != null; f = f.getParentFile()) {
            File pomFile = new File(f, "pom.xml");
            if (pomFile.exists()) {
                return pomFile;
            }
        }
        return null;
    }
}
