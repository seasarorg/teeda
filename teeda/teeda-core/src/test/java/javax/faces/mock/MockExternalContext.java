package javax.faces.mock;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;


public class MockExternalContext extends ExternalContext {

    private ServletContext context_;
    public MockExternalContext(ServletContext context){
        context_ = context;
    }
    public void dispatch(String path) throws IOException {
    }

    public String encodeActionURL(String url) {
        return null;
    }

    public String encodeNamespace(String name) {
        return null;
    }

    public String encodeResourceURL(String url) {
        return null;
    }

    public Map getApplicationMap() {
        return null;
    }

    public String getAuthType() {
        return null;
    }

    public Object getContext() {
        return null;
    }

    public String getInitParameter(String name) {
        return context_.getInitParameter(name);
    }

    public Map getInitParameterMap() {
        return null;
    }

    public String getRemoteUser() {
        return null;
    }

    public Object getRequest() {
        return null;
    }

    public String getRequestContextPath() {
        return null;
    }

    public Map getRequestCookieMap() {
        return null;
    }

    /**
     *
     */

    public Map getRequestHeaderMap() {
        return null;
    }

    /**
     *
     */

    public Map getRequestHeaderValuesMap() {
        return null;
    }

    /**
     *
     */

    public Locale getRequestLocale() {
        return null;
    }

    /**
     *
     */

    public Iterator getRequestLocales() {
        return null;
    }

    /**
     *
     */

    public Map getRequestMap() {
        return null;
    }

    /**
     *
     */

    public Map getRequestParameterMap() {
        return null;
    }

    /**
     *
     */

    public Iterator getRequestParameterNames() {
        return null;
    }

    /**
     *
     */

    public Map getRequestParameterValuesMap() {
        return null;
    }

    /**
     *
     */

    public String getRequestPathInfo() {
        return null;
    }

    /**
     *
     */

    public String getRequestServletPath() {
        return null;
    }

    /**
     *
     */

    public URL getResource(String path) throws MalformedURLException {
        return null;
    }

    /**
     *
     */

    public InputStream getResourceAsStream(String path) {
        return null;
    }

    /**
     *
     */

    public Set getResourcePaths(String path) {
        return null;
    }

    /**
     *
     */

    public Object getResponse() {
        return null;
    }

    /**
     *
     */

    public Object getSession(boolean create) {
        return null;
    }

    /**
     *
     */

    public Map getSessionMap() {
        return null;
    }

    /**
     *
     */

    public Principal getUserPrincipal() {
        return null;
    }

    /**
     *
     */

    public boolean isUserInRole(String role) {
        return false;
    }

    /**
     *
     */

    public void log(String message) {
    }

    /**
     *
     */

    public void log(String message, Throwable exception) {
    }

    /**
     *
     */

    public void redirect(String url) throws IOException {
    }

}
