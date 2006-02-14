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
import org.seasar.teeda.core.render.html.HtmlRenderKitImpl;

/**
 * @author shot
 * @author manhole
 */
public interface JsfConstants {

    public String MESSAGES = "Messages";

    public String DEFAULT_XML_NS = "http://java.sun.com/JSF/Configuration";

    public String CORE_PACKAGE_ROOT = "org.seasar.teeda.core";

    public String RESOURCE_PACKAGE_ROOT = CORE_PACKAGE_ROOT + "." + "resource";

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

    // -- value --

    public String CHECKBOX_VALUE = "checkbox";

    public String CHECKED_VALUE = "checked";

    public String COLGROUP_VALUE = "colgroup";

    public String COL_VALUE = "col";

    public String DISABLED_VALUE = "disabled";

    public String HIDDEN_VALUE = "hidden";

    public String IMAGE_VALUE = "image";

    public String MULTIPLE_VALUE = "multiple";

    public String PASSWORD_VALUE = "password";

    public String POST_VALUE = "post";

    public String RESET_VALUE = "reset";

    public String SELECTED_VALUE = "selected";

    public String TABLE_VALUE = "table";

    public String TEXT_VALUE = "text";

    // -- element --

    public String ANCHOR_ELEM = "a";

    public String BR_ELEM = "br";

    public String FORM_ELEM = "form";

    public String IMG_ELEM = "img";

    public String INPUT_ELEM = "input";

    public String LABEL_ELEM = "label";

    public String LI_ELEM = "li";

    public String OPTGROUP_ELEM = "optgroup";

    public String OPTION_ELEM = "option";

    public String SELECT_ELEM = "select";

    public String SPAN_ELEM = "span";

    public String TABLE_ELEM = "table";

    public String TEXTAREA_ELEM = "textarea";

    public String TBODY_ELEM = "tbody";

    public String TD_ELEM = "td";

    public String TFOOT_ELEM = "tfoot";

    public String TH_ELEM = "th";

    public String THEAD_ELEM = "thead";

    public String TR_ELEM = "tr";

    public String UL_ELEM = "ul";

    // -- attribute --

    public String ACCEPT_ATTR = "accept";

    public String ACCEPTCHARSET_ATTR = "acceptcharset";

    public String ACCEPT_CHARSET_ATTR = "accept-charset";

    public String ACCESSKEY_ATTR = "accesskey";

    public String ALIGN_ATTR = "align";

    public String ALT_ATTR = "alt";

    public String BGCOLOR_ATTR = "bgcolor";

    public String BORDER_ATTR = "border";

    public String CELLPADDING_ATTR = "cellpadding";

    public String CELLSPACING_ATTR = "cellspacing";

    public String CHARSET_ATTR = "charset";

    public String CHECKED_ATTR = "checked";

    public String CLASS_ATTR = "class";

    public String COLGROUP_ATTR = "colgroup";

    public String COLS_ATTR = "cols";

    public String COLSPAN_ATTR = "colspan";

    public String COLUMN_CLASSES_ATTR = "columnClasses";

    public String COORDS_ATTR = "coords";

    public String DATAFLD_ATTR = "datafld";

    public String DATASRC_ATTR = "datasrc";

    public String DATAFORMATAS_ATTR = "dataformatas";

    public String DIR_ATTR = "dir";

    public String DISABLED_ATTR = "disabled";

    public String DISABLED_CLASS_ATTR = "disabledClass";

    public String ENABLED_CLASS_ATTR = "enabledClass";

    public String ENCTYPE_ATTR = "enctype";

    public String FOOTER_CLASS_ATTR = "footerClass";

    public String FOR_ATTR = "for";

    public String FRAME_ATTR = "frame";

    public String HEADER_CLASS_ATTR = "headerClass";

    public String HREF_ATTR = "href";

    public String HREFLANG_ATTR = "hreflang";

    public String HEIGHT_ATTR = "height";

    public String HSPACE_ATTR = "hspace";

    public String ID_ATTR = "id";

    public String IMAGE_ATTR = "image";

    public String ISMAP_ATTR = "ismap";

    public String LABEL_ATTR = "label";

    public String LANG_ATTR = "lang";

    public String LONGDESC_ATTR = "longdesc";

    public String MAXLENGTH_ATTR = "maxlength";

    public String METHOD_ATTR = "method";

    public String MULTIPLE_ATTR = "multiple";

    public String NAME_ATTR = "name";

    public String ONBLUR_ATTR = "onblur";

    public String ONCHANGE_ATTR = "onchange";

    public String ONCLICK_ATTR = "onclick";

    public String ONDBLCLICK_ATTR = "ondblclick";

    public String ONFOCUS_ATTR = "onfocus";

    public String ONMOUSEDOWN_ATTR = "onmousedown";

    public String ONMOUSEUP_ATTR = "onmouseup";

    public String ONMOUSEOVER_ATTR = "onmouseover";

    public String ONMOUSEMOVE_ATTR = "onmousemove";

    public String ONMOUSEOUT_ATTR = "onmouseout";

    public String ONKEYPRESS_ATTR = "onkeypress";

    public String ONKEYDOWN_ATTR = "onkeydown";

    public String ONKEYUP_ATTR = "onkeyup";

    public String ONRESET_ATTR = "onreset";

    public String ONSELECT_ATTR = "onselect";

    public String ONSUMBIT_ATTR = "onsubmit";

    public String PAGE_DIRECTION_ATTR = "pageDirection";

    public String READONLY_ATTR = "readonly";

    public String REL_ATTR = "rel";

    public String REV_ATTR = "rev";

    public String ROW_CLASSES_ATTR = "rowClasses";

    public String ROWS_ATTR = "rows";

    public String RULES_ATTR = "rules";

    public String SCOPE_ATTR = "scope";

    public String SELECTED_ATTR = "selected";

    public String SHAPE_ATTR = "shape";

    public String SIZE_ATTR = "size";

    public String SRC_ATTR = "src";

    public String STYLE_ATTR = "style";

    public String STYLE_CLASS_ATTR = "styleClass";

    public String SUMMARY_ATTR = "summary";

    public String TABINDEX_ATTR = "tabindex";

    public String TARGET_ATTR = "target";

    public String TITLE_ATTR = "title";

    public String TYPE_ATTR = "type";

    public String USEMAP_ATTR = "usemap";

    public String VALUE_ATTR = "value";

    public String VSPACE_ATTR = "vspace";

    public String WIDTH_ATTR = "width";

    public String[] UNIVERSAL_ATTRIBUTES_WITHOUT_STYLE = { DIR_ATTR, LANG_ATTR,
            TITLE_ATTR };

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

    public String[] ANCHOR_ATTRIBUTES = { ACCESSKEY_ATTR, CHARSET_ATTR,
            COORDS_ATTR, HREFLANG_ATTR, REL_ATTR, REV_ATTR, SHAPE_ATTR,
            TABINDEX_ATTR, TARGET_ATTR, TYPE_ATTR };

    public String[] ANCHOR_PASSTHROUGH_ATTRIBUTES = (String[]) ArrayUtil.add(
            ANCHOR_ATTRIBUTES, COMMON_PASSTROUGH_ATTRIBUTES);

    public String[] COMMON_PASSTROUGH_ATTRIBUTES_WITHOUT_STYLE = (String[]) ArrayUtil
            .add(EVENT_HANDLER_ATTRIBUTES, UNIVERSAL_ATTRIBUTES_WITHOUT_STYLE);

    public String[] ANCHOR_PASSTHROUGH_ATTRIBUTES_WITHOUT_STYLE = (String[]) ArrayUtil
            .add(ANCHOR_ATTRIBUTES, COMMON_PASSTROUGH_ATTRIBUTES_WITHOUT_STYLE);

    public String[] ONBLUR_AND_ONFOCUS_ATTRIBUTES = { ONBLUR_ATTR, ONFOCUS_ATTR };

    public String[] ANCHOR_PASSTHROUGH_AND_ONBLUR_AND_ONFOCUS_ATTRIBUTES = (String[]) ArrayUtil
            .add(ANCHOR_PASSTHROUGH_ATTRIBUTES, ONBLUR_AND_ONFOCUS_ATTRIBUTES);

    public String[] COMMON_FIELD_EVENT_ATTRIBUTES = { ONFOCUS_ATTR,
            ONBLUR_ATTR, ONSELECT_ATTR, ONCHANGE_ATTR };

    public String[] LABEL_ATTRIBUTES = { ACCESSKEY_ATTR, ONBLUR_ATTR,
            ONFOCUS_ATTR, TABINDEX_ATTR };

    public String[] LABEL_PASSTHROUGH_ATTRIBUTES = (String[]) ArrayUtil.add(
            LABEL_ATTRIBUTES, COMMON_PASSTROUGH_ATTRIBUTES);

    public String[] ID_WITH_COMMON_PASSTROUGH_ATTRIBUTES = (String[]) ArrayUtil
            .add(new String[] { ID_ATTR }, COMMON_PASSTROUGH_ATTRIBUTES);

    public String[] INPUT_ATTRIBUTES = { ALIGN_ATTR, ALT_ATTR, CHECKED_ATTR,
            DATAFLD_ATTR, DATASRC_ATTR, DATAFORMATAS_ATTR, MAXLENGTH_ATTR,
            READONLY_ATTR, SIZE_ATTR };

    public String[] COMMON_FIELD_ATTRIBUTES_WITHOUT_DISABLED = {
            ACCESSKEY_ATTR, TABINDEX_ATTR };

    public String[] COMMON_FIELD_PASSTROUGH_ATTRIBUTES_WITHOUT_DISABLED = (String[]) ArrayUtil
            .add(ArrayUtil.add(COMMON_PASSTROUGH_ATTRIBUTES,
                    COMMON_FIELD_ATTRIBUTES_WITHOUT_DISABLED),
                    COMMON_FIELD_EVENT_ATTRIBUTES);

    public String[] INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED = (String[]) ArrayUtil
            .add(INPUT_ATTRIBUTES,
                    COMMON_FIELD_PASSTROUGH_ATTRIBUTES_WITHOUT_DISABLED);

    public String[] TEXTAREA_ATTRIBUTES = { COLS_ATTR, DATAFLD_ATTR,
            DATASRC_ATTR, DATAFORMATAS_ATTR, READONLY_ATTR, ROWS_ATTR };

    public String[] TEXTAREA_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED = (String[]) ArrayUtil
            .add(TEXTAREA_ATTRIBUTES,
                    COMMON_FIELD_PASSTROUGH_ATTRIBUTES_WITHOUT_DISABLED);

    public String[] BUTTON_ATTRIBUTES = { ALIGN_ATTR, ALT_ATTR, DATAFLD_ATTR,
            DATASRC_ATTR, DATAFORMATAS_ATTR };

    public String[] BUTTON_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED = (String[]) ArrayUtil
            .add(BUTTON_ATTRIBUTES,
                    COMMON_FIELD_PASSTROUGH_ATTRIBUTES_WITHOUT_DISABLED);

    public String[] TABLE_ATTRIBUTES = { ALIGN_ATTR, BGCOLOR_ATTR, BORDER_ATTR,
            CELLPADDING_ATTR, CELLSPACING_ATTR, DATAFLD_ATTR, DATASRC_ATTR,
            DATAFORMATAS_ATTR, FRAME_ATTR, RULES_ATTR, SUMMARY_ATTR, WIDTH_ATTR };

    public String[] TABLE_PASSTHROUGH_ATTRIBUTES = (String[]) ArrayUtil.add(
            TABLE_ATTRIBUTES, COMMON_PASSTROUGH_ATTRIBUTES);

    public String[] IMG_ATTRIBUTES = { ALIGN_ATTR, ALT_ATTR, BORDER_ATTR,
            HEIGHT_ATTR, HSPACE_ATTR, ISMAP_ATTR, LONGDESC_ATTR, USEMAP_ATTR,
            VSPACE_ATTR, WIDTH_ATTR };

    public String[] IMG_PASSTHROUGH_ATTRIBUTES = (String[]) ArrayUtil.add(
            IMG_ATTRIBUTES, COMMON_PASSTROUGH_ATTRIBUTES);

    public String[] FORM_ATTRIBUTES = { ACCEPT_ATTR, ACCEPTCHARSET_ATTR,
            ENCTYPE_ATTR, ONRESET_ATTR, ONSUMBIT_ATTR, TARGET_ATTR };

    public String[] FORM_PASSTHROUGH_ATTRIBUTES = (String[]) ArrayUtil.add(
            FORM_ATTRIBUTES, COMMON_PASSTROUGH_ATTRIBUTES);

    public String[] COMMON_FIELD_PASSTROUGH_ATTRIBUTES_WITHOUT_DISABLED_AND_STYLE = (String[]) ArrayUtil
            .add(COMMON_PASSTROUGH_ATTRIBUTES_WITHOUT_STYLE,
                    (String[]) ArrayUtil.add(
                            COMMON_FIELD_ATTRIBUTES_WITHOUT_DISABLED,
                            COMMON_FIELD_EVENT_ATTRIBUTES));

    public String[] INPUT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED_AND_STYLE = (String[]) ArrayUtil
            .add(INPUT_ATTRIBUTES,
                    COMMON_FIELD_PASSTROUGH_ATTRIBUTES_WITHOUT_DISABLED_AND_STYLE);

    public String[] SELECT_TABLE_PASSTHROUGH_ATTRIBUTES = new String[] {
            STYLE_ATTR, STYLE_CLASS_ATTR, BORDER_ATTR };

    public String[] SELECT_ATTRIBUTES = { DATAFLD_ATTR, DATASRC_ATTR,
            DATAFORMATAS_ATTR, READONLY_ATTR };

    public String[] SELECT_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED = (String[]) ArrayUtil
            .add(SELECT_ATTRIBUTES,
                    COMMON_FIELD_PASSTROUGH_ATTRIBUTES_WITHOUT_DISABLED);

    public String DEFAULT_RENDERKIT_CLASS = HtmlRenderKitImpl.class.getName();

    public String WEB_XML_PATH = "/WEB-INF/web.xml";

    public String WEB_INF_LIB = "/WEB-INF/lib/";

    public String JAR_POSTFIX = ".jar";

    public String FACES_CONFIG_RESOURCES = "META-INF/faces-config.xml";

    public String HTML_CONTENT_TYPE = "text/html";

    public String ANY_CONTENT_TYPE = "*/*";

    public String XHTML_CONTENT_TYPE = "application/xhtml+xml";

    public String APPLICATION_XML_CONTENT_TYPE = "application/xml";

    public String TEXT_XML_CONTENT_TYPE = "text/xml";

    public String COMPRESS_STATE_ATTR = "javax.faces.COMPRESS_STATE";

    public String STATE_MARKER = CORE_PACKAGE_ROOT + ".STATE_MARKER";

    public String JSP_EXCEPTION = "javax.servlet.jsp.jspException";

    public String SERVLET_ERROR_EXCEPTION = "javax.servlet.error.exception";

    public String SERVLET_ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";

    public String SERVLET_ERROR_EXCEPTION_MESSAGE = "javax.servlet.error.message";

    public String ERROR_EXCEPTION = "seasar.jsf.error.exception";

    public String ERROR_EXCEPTION_TYPE = "seasar.jsf.error.exception_type";

    public String ERROR_MESSAGE = "seasar.jsf.error.message";

}
