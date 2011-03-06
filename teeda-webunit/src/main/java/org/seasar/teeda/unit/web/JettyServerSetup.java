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
package org.seasar.teeda.unit.web;

import java.io.File;

import junit.extensions.TestSetup;
import junit.framework.Test;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * @author manhole
 */
public class JettyServerSetup extends TestSetup {

    private Server server_;

    private int port_ = 8080;

    private File webapp_;

    private String contextPath_ = "/";

    private String host_ = "localhost";

    public JettyServerSetup(final Test test) {
        super(test);
    }

    protected void setUp() throws Exception {
        super.setUp();
        server_ = startJetty();
    }

    private Server startJetty() throws Exception {
        final Server server = new Server();
        final SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(getPort());
        connector.setHost(getHost());
        server.addConnector(connector);
        final WebAppContext handler = new WebAppContext();
        handler.setContextPath(getContextPath());
        final File webapp = getWebapp();
        System.out.println("webapp:" + webapp);
        handler.setWar(webapp.getCanonicalPath());
        server.addHandler(handler);
        try {
            System.out.println("### Jetty start ###");
            server.start();
        } catch (final Exception e) {
            e.printStackTrace();
            throw e;
        }
        return server;
    }

    protected void tearDown() throws Exception {
        stopJetty();
        super.tearDown();
    }

    private void stopJetty() throws Exception {
        System.out.println("### Jetty stop ###");
        server_.stop();
        System.out.println("stopped.");
    }

    public File getWebapp() {
        return webapp_;
    }

    public void setWebapp(final File webapp) {
        webapp_ = webapp;
    }

    public int getPort() {
        return port_;
    }

    public void setPort(final int port) {
        port_ = port;
    }

    public String getContextPath() {
        return contextPath_;
    }

    public void setContextPath(final String contextPath) {
        contextPath_ = contextPath;
    }

    public String getHost() {
        return host_;
    }

    public void setHost(final String host) {
        host_ = host;
    }

    public Server getServer() {
        return server_;
    }

    public void setServer(final Server server) {
        server_ = server;
    }

    private String name_;

    public String getName() {
        return name_;
    }

    public void setName(final String name) {
        name_ = name;
    }

}
