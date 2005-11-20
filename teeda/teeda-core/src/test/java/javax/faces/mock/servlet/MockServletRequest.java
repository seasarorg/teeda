package javax.faces.mock.servlet;

import java.util.Locale;

import javax.servlet.ServletRequest;

/**
 * @author Shinpei Ohtani
 */
public interface MockServletRequest extends ServletRequest{
    
    public String DEFAULT_CHARACTER_ENCODING = "ISO-8859-1";
    
    public void setContentLength(int contentLength);

    public void setContentType(String contentType);

    public void addParameter(String name, String value);

    public void addParameter(String name, String[] values);

    public void setParameter(String name, String value);

    public void setParameter(String name, String[] values);

    public void setProtocol(String protocol);

    public void setScheme(String scheme);

    public void setServerName(String serverName);

    public void setServerPort(int serverPort);

    public void setRemoteAddr(String remoteAddr);

    public void setRemoteHost(String remoteHost);

    public void setLocalAddr(String localAddr);

    public void setLocalName(String localName);

    public void setLocalPort(int localPort);

    public void setRemotePort(int remotePort);

    public void setLocale(Locale locale);
}