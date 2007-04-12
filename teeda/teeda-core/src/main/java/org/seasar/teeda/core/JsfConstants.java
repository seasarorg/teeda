/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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

import org.seasar.teeda.core.render.html.HtmlRenderKitImpl;

/**
 * @author shot
 * @author manhole
 * @author yone
 * @author higa
 */
public interface JsfConstants {

    String JSF_HTML_URI = "http://java.sun.com/jsf/html";

    String JSF_CORE_URI = "http://java.sun.com/jsf/core";

    String LINE_SP = "\n";//System.getProperty("line.separator");

    String MESSAGES = "Messages";

    String DEFAULT_XML_NS = "http://java.sun.com/JSF/Configuration";

    String CORE_PACKAGE_ROOT = "org.seasar.teeda.core";

    String RESOURCE_PACKAGE_ROOT = CORE_PACKAGE_ROOT + "." + "resource";

    String APPLICATION_SCOPE = "applicationScope";

    String COOKIE = "cookie";

    String FACES_CONTEXT = "facesContext";

    String HEADER = "header";

    String HEADER_VALUES = "headerValues";

    String INIT_PARAM = "initParam";

    String PARAM = "param";

    String PARAM_VALUES = "paramValues";

    String REQUEST_SCOPE = "requestScope";

    String SESSION_SCOPE = "sessionScope";

    String VIEW = "view";

    String[] JSF_IMPLICIT_OBJECTS = { APPLICATION_SCOPE, COOKIE, FACES_CONTEXT,
            HEADER, HEADER_VALUES, INIT_PARAM, PARAM, PARAM_VALUES,
            REQUEST_SCOPE, SESSION_SCOPE, VIEW };

    String SCOPE_NONE = "none";

    String SCOPE_APPLICATION = "application";

    String SCOPE_SESSION = "session";

    String SCOPE_REQUEST = "request";

    String SCOPE_DISPATCH = "teeda_dispatch_scope";

    // -- value --

    String BUTTON_VALUE = "button";

    String CHECKBOX_VALUE = "checkbox";

    String COLGROUP_VALUE = "colgroup";

    String COL_VALUE = "col";

    String HIDDEN_VALUE = "hidden";

    String IMAGE_VALUE = "image";

    String JAVASCRIPT_VALUE = "JavaScript";

    String MULTIPLE_VALUE = "multiple";

    String PASSWORD_VALUE = "password";

    String POST_VALUE = "post";

    String GET_VALUE = "get";

    String RADIO_VALUE = "radio";

    String RESET_VALUE = "reset";

    String SUBMIT_VALUE = "submit";

    String TABLE_VALUE = "table";

    String TEXT_JAVASCRIPT_VALUE = "text/javascript";

    String TEXT_VALUE = "text";

    String TEXT_CSS_VALUE = "text/css";

    String STYLESHEET_VALUE = "stylesheet";

    String PAGE_DIRECTION_VALUE = "pageDirection";

    String LINE_DIRECTION_VALUE = "lineDirection";

    String NONE_VALUE = "none";

    // -- element --

    String ANCHOR_ELEM = "a";

    String AREA_ELEM = "area";

    String BASE_ELEM = "base";

    String BASEFONT_ELEM = "basefont";

    String BR_ELEM = "br";

    String COL_ELEM = "col";

    String COLGROUP_ELEM = "colgroup";

    String DIV_ELEM = "div";

    String FORM_ELEM = "form";

    String FRAME_ELEM = "frame";

    String HR_ELEM = "hr";

    String IMG_ELEM = "img";

    String INPUT_ELEM = "input";

    String ISINDEX_ELEM = "isindex";

    String LABEL_ELEM = "label";

    String LI_ELEM = "li";

    String LINK_ELEM = "link";

    String META_ELEM = "meta";

    String NOBR_ELEM = "nobr";

    String OPTGROUP_ELEM = "optgroup";

    String OPTION_ELEM = "option";

    String PARAM_ELEM = "param";

    String SCRIPT_ELEM = "script";

    String SELECT_ELEM = "select";

    String SPAN_ELEM = "span";

    String TABLE_ELEM = "table";

    String TEXTAREA_ELEM = "textarea";

    String TBODY_ELEM = "tbody";

    String TD_ELEM = "td";

    String TFOOT_ELEM = "tfoot";

    String TH_ELEM = "th";

    String THEAD_ELEM = "thead";

    String TR_ELEM = "tr";

    String UL_ELEM = "ul";

    String HEAD_ELEM = "head";

    String TITLE_ELEM = "title";

    // -- attribute --

    String ACCEPT_ATTR = "accept";

    String ACCEPTCHARSET_ATTR = "acceptcharset";

    String ACCEPT_CHARSET_ATTR = "accept-charset";

    String ACCESSKEY_ATTR = "accesskey";

    String ALIGN_ATTR = "align";

    String ALT_ATTR = "alt";

    String BGCOLOR_ATTR = "bgcolor";

    String BORDER_ATTR = "border";

    String CELLPADDING_ATTR = "cellpadding";

    String CELLSPACING_ATTR = "cellspacing";

    String CHARSET_ATTR = "charset";

    String CHECKED_ATTR = "checked";

    String CLASS_ATTR = "class";

    String COLGROUP_ATTR = "colgroup";

    String COLS_ATTR = "cols";

    String COLSPAN_ATTR = "colspan";

    String COLUMN_CLASSES_ATTR = "columnClasses";

    String COLUMNS_ATTR = "columns";

    String CONVERTER_ATTR = "converter";

    String COORDS_ATTR = "coords";

    String DATAFLD_ATTR = "datafld";

    String DATASRC_ATTR = "datasrc";

    String DATAFORMATAS_ATTR = "dataformatas";

    String DATE_STYLE_ATTR = "dateStyle";

    String DIR_ATTR = "dir";

    String DISABLED_ATTR = "disabled";

    String DISABLED_CLASS_ATTR = "disabledClass";

    String ENABLED_CLASS_ATTR = "enabledClass";

    String ENCTYPE_ATTR = "enctype";

    String ERROR_CLASS_ATTR = "errorClass";

    String ERROR_STYLE_ATTR = "errorStyle";

    String ESCAPE_ATTR = "escape";

    String FATAL_CLASS_ATTR = "fatalClass";

    String FATAL_STYLE_ATTR = "fatalStyle";

    String FIRST_ATTR = "first";

    String FOOTER_CLASS_ATTR = "footerClass";

    String FORMAT_STYLE_ATTR = "formatStyle";

    String FOR_ATTR = "for";

    String FORMAT_PATTERN_ATTR = "formatPattern";

    String GLOBAL_ONLY_ATTR = "globalOnly";

    String FRAME_ATTR = "frame";

    String HEADER_CLASS_ATTR = "headerClass";

    String HREF_ATTR = "href";

    String HREFLANG_ATTR = "hreflang";

    String HEIGHT_ATTR = "height";

    String HSPACE_ATTR = "hspace";

    String HTML_FOR_ATTR = "htmlFor";

    String ID_ATTR = "id";

    String IMAGE_ATTR = "image";

    String IMMEDIATE_ATTR = "immediate";

    String INFO_CLASS_ATTR = "infoClass";

    String INFO_STYLE_ATTR = "infoStyle";

    String ISMAP_ATTR = "ismap";

    String ITEM_DISABLED_ATTR = "itemDisabled";

    String ITEM_DESCRIPTION_ATTR = "itemDescription";

    String ITEM_VALUE_ATTR = "itemValue";

    String ITEM_LABEL_ATTR = "itemLabel";

    String LABEL_ATTR = "label";

    String LAYOUT_ATTR = "layout";

    String LANG_ATTR = "lang";

    String LANGUAGE_ATTR = "language";

    String LONGDESC_ATTR = "longdesc";

    String MAXLENGTH_ATTR = "maxlength";

    String METHOD_ATTR = "method";

    String MULTIPLE_ATTR = "multiple";

    String NAME_ATTR = "name";

    String ONBLUR_ATTR = "onblur";

    String ONCHANGE_ATTR = "onchange";

    String ONCLICK_ATTR = "onclick";

    String ONDBLCLICK_ATTR = "ondblclick";

    String ONFOCUS_ATTR = "onfocus";

    String ONLOAD_ATTR = "onload";

    String ONMOUSEDOWN_ATTR = "onmousedown";

    String ONMOUSEUP_ATTR = "onmouseup";

    String ONMOUSEOVER_ATTR = "onmouseover";

    String ONMOUSEMOVE_ATTR = "onmousemove";

    String ONMOUSEOUT_ATTR = "onmouseout";

    String ONKEYPRESS_ATTR = "onkeypress";

    String ONKEYDOWN_ATTR = "onkeydown";

    String ONKEYUP_ATTR = "onkeyup";

    String ONRESET_ATTR = "onreset";

    String ONSCROLL_ATTR = "onscroll";

    String ONSELECT_ATTR = "onselect";

    String ONSUBMIT_ATTR = "onsubmit";

    String ONUNLOAD_ATTR = "onunload";

    String READONLY_ATTR = "readonly";

    String REDISPLAY_ATTR = "redisplay";

    String REL_ATTR = "rel";

    String RENDERER_TYPE_ATTR = "rendererType";

    String REQUIRED_ATTR = "required";

    String REV_ATTR = "rev";

    String ROW_CLASSES_ATTR = "rowClasses";

    String ROWS_ATTR = "rows";

    String ROWSPAN_ATTR = "rowspan";

    String RULES_ATTR = "rules";

    String SCOPE_ATTR = "scope";

    String SELECTED_ATTR = "selected";

    String SHAPE_ATTR = "shape";

    String SHOW_SUMMARY_ATTR = "showSummary";

    String SHOW_DETAIL_ATTR = "showDetail";

    String SIZE_ATTR = "size";

    String SRC_ATTR = "src";

    String STYLE_ATTR = "style";

    String STYLE_CLASS_ATTR = "styleClass";

    String SUMMARY_ATTR = "summary";

    String TABINDEX_ATTR = "tabindex";

    String TARGET_ATTR = "target";

    String TITLE_ATTR = "title";

    String TIME_STYLE_ATTR = "timeStyle";

    String TIMEZONE_ATTR = "timezone";

    String TOOLTIP_ATTR = "tooltip";

    String TYPE_ATTR = "type";

    String URL_ATTR = "url";

    String USEMAP_ATTR = "usemap";

    String VALUE_ATTR = "value";

    String VAR_ATTR = "var";

    String VSPACE_ATTR = "vspace";

    String WARN_CLASS_ATTR = "warnClass";

    String WARN_STYLE_ATTR = "warnStyle";

    String WIDTH_ATTR = "width";

    String WRAP_ATTR = "wrap";

    String ACTION_ATTR = "action";

    String FRACTION_ATTR = "fraction";

    String AUTOCOMPLETE_ATTR = "autocomplete";

    //  --  --

    String DEFAULT_RENDERKIT_CLASS = HtmlRenderKitImpl.class.getName();

    String WEB_XML_PATH = "/WEB-INF/web.xml";

    String WEB_INF_LIB = "/WEB-INF/lib/";

    String JAR_POSTFIX = ".jar";

    String FACES_CONFIG_RESOURCES = "META-INF/faces-config.xml";

    String HTML_CONTENT_TYPE = "text/html";

    String ANY_CONTENT_TYPE = "*/*";

    String XHTML_CONTENT_TYPE = "application/xhtml+xml";

    String APPLICATION_XML_CONTENT_TYPE = "application/xml";

    String TEXT_XML_CONTENT_TYPE = "text/xml";

    String COMPRESS_STATE_ATTR = "javax.faces.COMPRESS_STATE";

    String STATE_MARKER = CORE_PACKAGE_ROOT + ".STATE_MARKER";

    String JSP_EXCEPTION = "javax.servlet.jsp.jspException";

    //String SERVLET_ERROR_EXCEPTION = "javax.servlet.error.exception";

    //String SERVLET_ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";

    //String SERVLET_ERROR_EXCEPTION_MESSAGE = "javax.servlet.error.message";

    String ERROR_EXCEPTION = "teeda.jsf.error.exception";

    String ERROR_EXCEPTION_TYPE = "teeda.jsf.error.exception_type";

    String ERROR_MESSAGE = "teeda.jsf.error.message";

    String DEFAULT_CONVERTDATETIME_DATE_STYLE = "default";

    String DEFAULT_CONVERTDATETIME_TIME_STYLE = "default";

    String DEFAULT_CONVERTDATETIME_TYPE = "default";

    String DEFAULT_CONVERTNUMBER_GROUPING_USED = "true";

    String DEFAULT_CONVERTNUMBER_INTEGER_ONLY = "false";

    String DEFAULT_CONVERTNUMBER_TYPE = "number";

    String TEEDA_NAMESPACE = "teeda";

    String NS_SEP = ".";

    String JAVASCRIPT_NOT_PERMITTED_PATH = "teeda.JAVASCRIPT_NOT_PERMITTED_PATH";

    String REDIRECT_URL = "teeda.REDIRECT_URL";

    String SUBMITTED_COMMAND = "teeda.SUBMITTED_COMMAND";

    String COMPONENT_FAMILY = "COMPONENT_FAMILY";

    String RENDERER_TYPE = "RENDERER_TYPE";

    String DEFAULT_ENCODING = "UTF-8";

    String TRUE = "true";

    String FALSE = "false";

    String POSTBACK = "postback";

    String PREVIOUS_VIEW_ID = "previousViewId";

    String ERROR_MANAGER_EXCEPTION_KEY = "Teeda.ErrorManager.Exception";

    String BR_TAG = "<br />";
}
