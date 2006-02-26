package teeda.test;

import java.io.File;
import java.io.IOException;

import junit.extensions.TestSetup;
import junit.framework.Test;

import org.mortbay.http.SocketListener;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.WebApplicationContext;
import org.mortbay.util.InetAddrPort;
import org.mortbay.util.MultiException;

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
            server.start();
        } catch (MultiException e) {
            e.printStackTrace();
            throw e;
        }
        return server;
    }

    protected void tearDown() throws Exception {
        server_.stop();
        super.tearDown();
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
