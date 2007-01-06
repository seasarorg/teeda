/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

    public static File getArtifactFromPom(final File pom,
        final String artifactId) {
        final MavenEmbedder maven = new MavenEmbedder();
        final ClassLoader classLoader = Thread.currentThread()
            .getContextClassLoader();
        maven.setClassLoader(classLoader);
        final MavenEmbedderConsoleLogger logger = new MavenEmbedderConsoleLogger();
        logger.setThreshold(MavenEmbedderLogger.LEVEL_ERROR);
        maven.setLogger(logger);
        try {
            maven.start();
            final MavenProject mavenProject = maven
                .readProjectWithDependencies(pom);
            Artifact targetArtifact = null;
            for (final Iterator it = mavenProject.getArtifacts().iterator(); it
                .hasNext();) {
                final Artifact artifact = (Artifact) it.next();
                if (artifactId.equals(artifact.getArtifactId())) {
                    targetArtifact = artifact;
                }
            }
            if (targetArtifact == null) {
                throw new RuntimeException("[" + artifactId
                    + "] not found. from " + pom);
            }
            final File targetArtifactFile = targetArtifact.getFile()
                .getCanonicalFile();
            maven.stop();
            return targetArtifactFile;
        } catch (final MavenEmbedderException e) {
            throw new RuntimeException(e);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        } catch (final ArtifactResolutionException e) {
            throw new RuntimeException(e);
        } catch (final ArtifactNotFoundException e) {
            throw new RuntimeException(e);
        } catch (final ProjectBuildingException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getProjectPomFile(final Class clazz) {
        final File file = ResourceUtil.getBuildDir(clazz);
        for (File f = file; f != null; f = f.getParentFile()) {
            final File pomFile = new File(f, "pom.xml");
            if (pomFile.exists()) {
                return pomFile;
            }
        }
        return null;
    }
}
