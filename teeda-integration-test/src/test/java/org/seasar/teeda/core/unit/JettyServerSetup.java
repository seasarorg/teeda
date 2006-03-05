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
package org.seasar.teeda.core.unit;

import java.io.File;
import java.io.IOException;

import junit.extensions.TestSetup;
import junit.framework.Test;

import org.mortbay.http.SocketListener;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.WebApplicationContext;
import org.mortbay.util.InetAddrPort;
import org.mortbay.util.MultiException;

/**
 * @author manhole
 */
public class JettyServerSetup extends TestSetup {

    private Server server_;

    private int port_ = 8080;

    private File webapp_;

    private String contextPath_ = "/";

    private String host_ = "localhost";

    public JettyServerSetup(Test test) {
        super(test);
    }

    protected void setUp() throws Exception {
        super.setUp();
        server_ = startJetty();
    }

    private Server startJetty() throws IOException, MultiException {
        Server server = new Server();
        SocketListener listener = new SocketListener(new InetAddrPort(
            getHost(), getPort()));
        server.addListener(listener);

        File webapp = getWebapp();
        System.out.println("webapp:" + webapp);
        WebApplicationContext context = new WebApplicationContext(webapp
            .getCanonicalPath());
        context.setContextPath(getContextPath());
        server.addContext(context);

        try {
            System.out.println("### Jetty start ###");
            server.start();
        } catch (MultiException e) {
            e.printStackTrace();
            throw e;
        }
        return server;
    }

    protected void tearDown() throws Exception {
        stopJetty();
        super.tearDown();
    }

    private void stopJetty() throws InterruptedException {
        System.out.println("### Jetty stop ###");
        server_.stop();
        System.out.println("stopped.");
    }

    public File getWebapp() {
        return webapp_;
    }

    public void setWebapp(File webapp) {
        webapp_ = webapp;
    }

    public int getPort() {
        return port_;
    }

    public void setPort(int port) {
        port_ = port;
    }

    public String getContextPath() {
        return contextPath_;
    }

    public void setContextPath(String contextPath) {
        contextPath_ = contextPath;
    }

    public String getHost() {
        return host_;
    }

    public void setHost(String host) {
        host_ = host;
    }

    public Server getServer() {
        return server_;
    }

    public void setServer(Server server) {
        server_ = server;
    }

}
