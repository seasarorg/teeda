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

    public String APPLICATION_SCOPE = "applicationScope";

    public String COOKIE = "cookie";

    public String DISABLED_ATTR = "disabled";

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

    public String TEXT_VALUE = "text";

    public String HIDDEN_VALUE = "hidden";

    // -- element --

    public String ANCHOR_ELEM = "a";

    public String INPUT_ELEM = "input";

    public String SPAN_ELEM = "span";

    public String TEXTAREA_ELEM = "textarea";

    // -- attribute --

    public String ACCESSKEY_ATTR = "accesskey";

    public String ALIGN_ATTR = "align";

    public String ALT_ATTR = "alt";

    public String CHARSET_ATTR = "charset";

    public String CHECKED_ATTR = "checked";

    public String CLASS_ATTR = "class";

    public String COLS_ATTR = "cols";

    public String COORDS_ATTR = "coords";

    public String DATAFLD_ATTR = "datafld";

    public String DATASRC_ATTR = "datasrc";

    public String DATAFORMATAS_ATTR = "dataformatas";

    public String DIR_ATTR = "dir";

    public String FOR_ATTR = "for";

    public String HREF_ATTR = "href";

    public String HREFLANG_ATTR = "hreflang";

    public String ID_ATTR = "id";

    public String LABEL_ATTR = "label";

    public String LANG_ATTR = "lang";

    public String MAXLENGTH_ATTR = "maxlength";

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

    public String ONSELECT_ATTR = "onselect";

    public String READONLY_ATTR = "readonly";

    public String REL_ATTR = "rel";

    public String REV_ATTR = "rev";

    public String ROWS_ATTR = "rows";

    public String SHAPE_ATTR = "shape";

    public String SIZE_ATTR = "size";

    public String STYLE_ATTR = "style";

    public String STYLE_CLASS_ATTR = "styleClass";

    public String TABINDEX_ATTR = "tabindex";

    public String TARGET_ATTR = "target";

    public String TITLE_ATTR = "title";

    public String TYPE_ATTR = "type";

    public String VALUE_ATTR = "value";

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
            READONLY_ATTR, SIZE_ATTR, };

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
            DATASRC_ATTR, DATAFORMATAS_ATTR, READONLY_ATTR, ROWS_ATTR, };

    public String[] TEXTAREA_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED = (String[]) ArrayUtil
            .add(TEXTAREA_ATTRIBUTES,
                    COMMON_FIELD_PASSTROUGH_ATTRIBUTES_WITHOUT_DISABLED);

    public static final String[] BUTTON_ATTRIBUTES = { ALIGN_ATTR, ALT_ATTR,
            DATAFLD_ATTR, DATASRC_ATTR, DATAFORMATAS_ATTR, };

    public static final String[] BUTTON_PASSTHROUGH_ATTRIBUTES_WITHOUT_DISABLED = (String[]) ArrayUtil
            .add(BUTTON_ATTRIBUTES,
                    COMMON_FIELD_PASSTROUGH_ATTRIBUTES_WITHOUT_DISABLED);

    public String DEFAULT_RENDERKIT_CLASS = HtmlRenderKitImpl.class.getName();

}
