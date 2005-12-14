package org.seasar.teeda.core;


public interface JsfConstants {

    public static final String MESSAGES = "Messages";

    public static final String DEFAULT_XML_NS = "http://java.sun.com/JSF/Configuration";
    
    public static final String CORE_PACKAGE_ROOT = "org.seasar.teeda.core";
    
    public static final String APPLICATION_SCOPE = "applicationScope";

    public static final String COOKIE = "cookie";

    public static final String FACES_CONTEXT = "facesContext";

    public static final String HEADER = "header";

    public static final String HEADER_VALUES = "headerValues";

    public static final String INIT_PARAM = "initParam";

    public static final String PARAM = "param";

    public static final String PARAM_VALUES = "paramValues";

    public static final String REQUEST_SCOPE = "requestScope";

    public static final String SESSION_SCOPE = "sessionScope";

    public static final String VIEW = "view";

    public static final String[] JSF_IMPLICIT_OBJECTS = {
        APPLICATION_SCOPE, COOKIE, FACES_CONTEXT, HEADER, HEADER_VALUES, 
        INIT_PARAM, PARAM, PARAM_VALUES, REQUEST_SCOPE, SESSION_SCOPE, VIEW
    };
    
    public static final String SCOPE_NONE = "none";
    
    public static final String SCOPE_APPLICATION = "application";
    
    public static final String SCOPE_SESSION = "session";
    
    public static final String SCOPE_REQUEST = "request";
}
