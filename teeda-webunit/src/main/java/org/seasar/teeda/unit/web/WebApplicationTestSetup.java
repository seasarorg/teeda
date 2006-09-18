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
import java.util.Arrays;
import java.util.Properties;

import junit.extensions.TestSetup;
import junit.framework.Test;

import org.apache.maven.BuildFailureException;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.cli.ConsoleDownloadMonitor;
import org.apache.maven.embedder.MavenEmbedder;
import org.apache.maven.embedder.MavenEmbedderConsoleLogger;
import org.apache.maven.embedder.MavenEmbedderException;
import org.apache.maven.embedder.MavenEmbedderLogger;
import org.apache.maven.embedder.PlexusLoggerAdapter;
import org.apache.maven.lifecycle.LifecycleExecutionException;
import org.apache.maven.monitor.event.DefaultEventMonitor;
import org.apache.maven.monitor.event.EventMonitor;
import org.apache.maven.project.DuplicateProjectException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuildingException;
import org.codehaus.plexus.util.dag.CycleDetectedException;

/**
 * @author manhole
 */
public class WebApplicationTestSetup extends TestSetup {

    private File pomFile_;

    public WebApplicationTestSetup(final Test test) {
        super(test);
    }

    protected void setUp() throws Exception {
        super.setUp();
        buildWebapp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    protected void buildWebapp() throws MavenEmbedderException,
        ProjectBuildingException, ArtifactResolutionException,
        ArtifactNotFoundException, CycleDetectedException,
        LifecycleExecutionException, BuildFailureException,
        DuplicateProjectException {

        final MavenEmbedder maven = new MavenEmbedder();
        maven.setClassLoader(Thread.currentThread().getContextClassLoader());
        final MavenEmbedderLogger mavenLogger = new MavenEmbedderConsoleLogger();
        mavenLogger.setThreshold(MavenEmbedderLogger.LEVEL_ERROR);
        maven.setLogger(mavenLogger);
        maven.start();

        final File pomFile = getProjectPomFile();
        System.out.println("pomFile:" + pomFile);
        final File projectDirectory = pomFile.getParentFile();

        final MavenProject mavenProject = maven
            .readProjectWithDependencies(pomFile);
        final EventMonitor eventMonitor = new DefaultEventMonitor(
            new PlexusLoggerAdapter(mavenLogger));

        final Properties prop = new Properties();
        prop.put("maven.test.skip", "true");

        maven.execute(mavenProject, Arrays
            .asList(new String[] { "resources:resources", "compiler:compile",
                "resources:testResources", "compiler:testCompile",
                "war:exploded" }), eventMonitor, new ConsoleDownloadMonitor(),
            prop, projectDirectory);
        maven.stop();
    }

    private File getProjectPomFile() {
        if (pomFile_ == null) {
            throw new NullPointerException("pomFile is null");
        }
        return pomFile_;
    }

    public void setPomFile(final File pomFile) {
        pomFile_ = pomFile;
    }

    private String name_;

    public String getName() {
        return name_;
    }

    public void setName(final String name) {
        name_ = name;
    }

}
