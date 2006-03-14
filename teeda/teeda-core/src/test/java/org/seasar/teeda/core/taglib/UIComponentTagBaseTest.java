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
package org.seasar.teeda.core.taglib;

import junit.framework.TestCase;

/**
 * @author yone
 */
public class UIComponentTagBaseTest extends TestCase {

    public void testRelease() throws Exception {
        // # Arrange #
        UIComponentTagBase tag = new UIComponentTagBaseImpl();
        tag.setStyleClass("styleClass");
        tag.setTitle("title");
        tag.setOnclick("onclick");
        tag.setOndblclick("ondblclick");
        tag.setOnmousedown("onmousedown");
        tag.setOnmouseover("mouseover");
        tag.setOnmousemove("onmousemove");
        tag.setOnmouseout("onmouseout");
        tag.setOnkeypress("onkeypress");
        tag.setOnkeydown("onkeydown");
        tag.setOnkeyup("onkeyup");
        tag.setLang("lang");
        tag.setDir("dir");
        tag.setCellspacing("5");
        tag.setCellpadding("2");
        tag.setDisabled("true");
        tag.setSize("44");
        tag.setTabindex("3");
        tag.setChecked("checked");
        tag.setBorder("4");
        tag.setReadonly("true");
        tag.setIsmap("ismap");
        tag.setMaxlength("5");
        tag.setRows("rows");
        tag.setCols("cols");
        tag.setFormatStyle("formatStyle");
        tag.setAccept("accept");
        tag.setAcceptcharset("acceptCharset");
        tag.setAccesskey("acceptKey");
        tag.setAction("action");
        tag.setAlt("alt");
        tag.setCharset("charset");
        tag.setCoords("coords");
        tag.setEnctype("enctype");
        tag.setHtmlFor("htmlFor");
        tag.setHref("href");
        tag.setHreflang("hrefLang");
        tag.setHspace("hspace");
        tag.setLabel("label");
        tag.setLongdesc("longdesc");
        tag.setMethod("method");
        tag.setMultiple("multiple");
        tag.setName("name");
        tag.setOnblur("onblur");
        tag.setOnchange("onchange");
        tag.setOnfocus("onforcus");
        tag.setOnmouseup("onmouseup");
        tag.setOnreset("onreset");
        tag.setOnselect("onselect");
        tag.setOnsubmit("onsubmit");
        tag.setRel("rel");
        tag.setRev("rev");
        tag.setSelected("selected");
        tag.setShape("shape");
        tag.setSrc("src");
        tag.setStyle("style");
        tag.setTarget("target");
        tag.setType("type");
        tag.setUsemap("usemap");
        tag.setValue("value");
        tag.setSummary("summary");
        tag.setBgcolor("green");
        tag.setFrame("frame");
        tag.setRules("rules");
        tag.setConverter("converter");
        
        // # Act #
        tag.release();
        
        // # Assert #
        assertEquals(null, tag.styleClass_);
        assertEquals(null, tag.title_);
        assertEquals(null, tag.enabledClass_);
        assertEquals(null, tag.disabledClass_);
        assertEquals(null, tag.onclick_);
        assertEquals(null, tag.ondblclick_);
        assertEquals(null, tag.onmousedown_);
        assertEquals(null, tag.onmouseover_);
        assertEquals(null, tag.onmousemove_);
        assertEquals(null, tag.onmouseout_);
        assertEquals(null, tag.onkeypress_);
        assertEquals(null, tag.onkeydown_);
        assertEquals(null, tag.onkeyup_);
        assertEquals(null, tag.lang_);
        assertEquals(null, tag.dir_);
        assertEquals(null, tag.height_);
        assertEquals(null, tag.width_);
        assertEquals(null, tag.cellspacing_);
        assertEquals(null, tag.cellpadding_);
        assertEquals(null, tag.disabled_);
        assertEquals(null, tag.size_);
        assertEquals(null, tag.tabindex_);
        assertEquals(null, tag.checked_);
        assertEquals(null, tag.border_);
        assertEquals(null, tag.readonly_);
        assertEquals(null, tag.ismap_);
        assertEquals(null, tag.maxlength_);
        assertEquals(null, tag.rows_);
        assertEquals(null, tag.cols_);
        assertEquals(null, tag.formatStyle_);
        assertEquals(null, tag.dateStyle_);
        assertEquals(null, tag.timeStyle_);
        assertEquals(null, tag.timezone_);
        assertEquals(null, tag.formatPattern_);
        assertEquals(null, tag.accept_);
        assertEquals(null, tag.acceptcharset_);
        assertEquals(null, tag.accesskey_);
        assertEquals(null, tag.action_);
        assertEquals(null, tag.alt_);
        assertEquals(null, tag.charset_);
        assertEquals(null, tag.coords_);
        assertEquals(null, tag.enctype_);
        assertEquals(null, tag.htmlFor_);
        assertEquals(null, tag.href_);
        assertEquals(null, tag.hreflang_);
        assertEquals(null, tag.hspace_);
        assertEquals(null, tag.label_);
        assertEquals(null, tag.longdesc_);
        assertEquals(null, tag.method_);
        assertEquals(null, tag.multiple_);
        assertEquals(null, tag.name_);
        assertEquals(null, tag.onblur_);
        assertEquals(null, tag.onchange_);
        assertEquals(null, tag.onfocus_);
        assertEquals(null, tag.onmouseup_);
        assertEquals(null, tag.onreset_);
        assertEquals(null, tag.onselect_);
        assertEquals(null, tag.onsubmit_);
        assertEquals(null, tag.rel_);
        assertEquals(null, tag.rev_);
        assertEquals(null, tag.selected_);
        assertEquals(null, tag.shape_);
        assertEquals(null, tag.src_);
        assertEquals(null, tag.style_);
        assertEquals(null, tag.target_);
        assertEquals(null, tag.type_);
        assertEquals(null, tag.usemap_);
        assertEquals(null, tag.value_);
        assertEquals(null, tag.summary_);
        assertEquals(null, tag.bgcolor_);
        assertEquals(null, tag.frame_);
        assertEquals(null, tag.rules_);
        assertEquals(null, tag.converter_);
    }

    class UIComponentTagBaseImpl extends UIComponentTagBase {

        public String getComponentType() {
            return null;
        }

        public String getRendererType() {
            return null;
        }
        
        public void release() {
            super.release();
        }
    }
}
