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
package org.seasar.teeda.ajax;

/**
 * @author yone
 */
public interface AjaxConstants {

    String DEFAULT_AJAX_METHOD = "ajaxAction";

    String TEEDA_AJAX_META = "teeda-ajax";

    String CONTENT_TYPE_XML = "text/xml; charset=UTF-8";

    String CONTENT_TYPE_JSON = "text/javascript; charset=UTF-8";

    String CONTENT_TYPE_TEXT = "text/plain; charset=UTF-8";

    String CONTENT_TYPE_HTML = "text/html; charset=UTF-8";

    String REQ_PARAM_COMPONENT = "component";

    String REQ_PARAM_ACTION = "action";

    String COMMA = ",";

    String COLON = ":";

    String NULL_STRING = "null";
    
    String TRUE_STRING = "true";

    String FALSE_STRING = "false";

    String START_BRACKET = "[";

    String END_BRACKET = "]";

    String START_BRACE = "{";
    
    String END_BRACE = "}";
    
    String DEFAULT_ARRAY_PARAM_NAME = "AjaxParam";
    
    int DEFAULT_ARRAY_PARAM_LENGTH = DEFAULT_ARRAY_PARAM_NAME.length();
}