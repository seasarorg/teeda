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
package org.seasar.teeda.core;

import org.seasar.framework.util.ArrayUtil;

/**
 * @author Shinpei Ohtani
 * @author manhole
 */
public interface JsfConstants {

    public String MESSAGES = "Messages";

    public String DEFAULT_XML_NS = "http://java.sun.com/JSF/Configuration";

    public String CORE_PACKAGE_ROOT = "org.seasar.teeda.core";

    public String APPLICATION_SCOPE = "applicationScope";

    public String COOKIE = "cookie";

    public String FACES_CONTEXT = "facesContext";

    public String HEADER = "header";

    public String HEADER_VALUES = "headerValues";

    public String INIT_PARAM = "initParam";

    public String PARAM = "param";

    public String PARAM_VALUES = "paramValues";

    public String REQUEST_SCOPE = "requestScope";

    public String SESSION_SCOPE = "sessionScope";

    public String VIEW = "view";

    public String[] JSF_IMPLICIT_OBJECTS = { APPLICATION_SCOPE, COOKIE,
            FACES_CONTEXT, HEADER, HEADER_VALUES, INIT_PARAM, PARAM,
            PARAM_VALUES, REQUEST_SCOPE, SESSION_SCOPE, VIEW };

    public String SCOPE_NONE = "none";

    public String SCOPE_APPLICATION = "application";

    public String SCOPE_SESSION = "session";

    public String SCOPE_REQUEST = "request";

    public String SPAN_ELEM = "span";

    public String CLASS_ATTR = "class";

    public String DIR_ATTR = "dir";

    public String ID_ATTR = "id";

    public String LANG_ATTR = "lang";

    public String ONCLICK_ATTR = "onclick";

    public String ONDBLCLICK_ATTR = "ondblclick";

    public String ONMOUSEDOWN_ATTR = "onmousedown";

    public String ONMOUSEUP_ATTR = "onmouseup";

    public String ONMOUSEOVER_ATTR = "onmouseover";

    public String ONMOUSEMOVE_ATTR = "onmousemove";

    public String ONMOUSEOUT_ATTR = "onmouseout";

    public String ONKEYPRESS_ATTR = "onkeypress";

    public String ONKEYDOWN_ATTR = "onkeydown";

    public String ONKEYUP_ATTR = "onkeyup";

    public String STYLE_ATTR = "style";

    public String STYLE_CLASS_ATTR = "styleClass";

    public String TITLE_ATTR = "title";

    public String[] UNIVERSAL_ATTRIBUTES_WITHOUT_STYLE = { DIR_ATTR, LANG_ATTR,
            TITLE_ATTR, };

    public String[] UNIVERSAL_ATTRIBUTES = (String[]) ArrayUtil.add(
            UNIVERSAL_ATTRIBUTES_WITHOUT_STYLE, new String[] { STYLE_ATTR,
                    STYLE_CLASS_ATTR });

    public String[] EVENT_HANDLER_ATTRIBUTES_WITHOUT_ONCLICK = {
            ONDBLCLICK_ATTR, ONMOUSEDOWN_ATTR, ONMOUSEUP_ATTR,
            ONMOUSEOVER_ATTR, ONMOUSEMOVE_ATTR, ONMOUSEOUT_ATTR,
            ONKEYPRESS_ATTR, ONKEYDOWN_ATTR, ONKEYUP_ATTR };

    public String[] EVENT_HANDLER_ATTRIBUTES = (String[]) ArrayUtil.add(
            EVENT_HANDLER_ATTRIBUTES_WITHOUT_ONCLICK,
            new String[] { ONCLICK_ATTR });

    public String[] COMMON_PASSTROUGH_ATTRIBUTES = (String[]) ArrayUtil.add(
            EVENT_HANDLER_ATTRIBUTES, UNIVERSAL_ATTRIBUTES);

}
