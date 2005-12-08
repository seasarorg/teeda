package org.seasar.teeda.core.mock;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.servlet.http.Cookie;


public abstract class MockExternalContext extends ExternalContext{

    public abstract void addRequestCookieMap(Cookie cookie);

    public abstract void setRequestCookieMap(Map map);

    public abstract void addRequestParameterMap(String key, String value);

    public abstract void setRequestParameterMap(Map map);

}
