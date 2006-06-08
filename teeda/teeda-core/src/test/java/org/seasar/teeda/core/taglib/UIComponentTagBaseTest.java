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
 * @author shot
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
        assertEquals(null, tag.getStyleClass());
        assertEquals(null, tag.getTitle());
        assertEquals(null, tag.getEnabledClass());
        assertEquals(null, tag.getDisabledClass());
        assertEquals(null, tag.getOnclick());
        assertEquals(null, tag.getOndblclick());
        assertEquals(null, tag.getOnmousedown());
        assertEquals(null, tag.getOnmouseover());
        assertEquals(null, tag.getOnmousemove());
        assertEquals(null, tag.getOnmouseout());
        assertEquals(null, tag.getOnkeypress());
        assertEquals(null, tag.getOnkeydown());
        assertEquals(null, tag.getOnkeyup());
        assertEquals(null, tag.getLang());
        assertEquals(null, tag.getDir());
        assertEquals(null, tag.getHeight());
        assertEquals(null, tag.getWidth());
        assertEquals(null, tag.getCellspacing());
        assertEquals(null, tag.getCellpadding());
        assertEquals(null, tag.getDisabled());
        assertEquals(null, tag.getSize());
        assertEquals(null, tag.getTabindex());
        assertEquals(null, tag.getChecked());
        assertEquals(null, tag.getBorder());
        assertEquals(null, tag.getReadonly());
        assertEquals(null, tag.getIsmap());
        assertEquals(null, tag.getMaxlength());
        assertEquals(null, tag.getRows());
        assertEquals(null, tag.getCols());
        assertEquals(null, tag.getFormatStyle());
        assertEquals(null, tag.getDateStyle());
        assertEquals(null, tag.getTimeStyle());
        assertEquals(null, tag.getTimezone());
        assertEquals(null, tag.getFormatPattern());
        assertEquals(null, tag.getAccept());
        assertEquals(null, tag.getAcceptcharset());
        assertEquals(null, tag.getAccesskey());
        assertEquals(null, tag.getAction());
        assertEquals(null, tag.getAlt());
        assertEquals(null, tag.getCharset());
        assertEquals(null, tag.getCoords());
        assertEquals(null, tag.getEnctype());
        assertEquals(null, tag.getHtmlFor());
        assertEquals(null, tag.getHref());
        assertEquals(null, tag.getHreflang());
        assertEquals(null, tag.getHspace());
        assertEquals(null, tag.getLongdesc());
        assertEquals(null, tag.getMethod());
        assertEquals(null, tag.getMultiple());
        assertEquals(null, tag.getName());
        assertEquals(null, tag.getOnblur());
        assertEquals(null, tag.getOnchange());
        assertEquals(null, tag.getOnfocus());
        assertEquals(null, tag.getOnmouseup());
        assertEquals(null, tag.getOnreset());
        assertEquals(null, tag.getOnselect());
        assertEquals(null, tag.getOnsubmit());
        assertEquals(null, tag.getRel());
        assertEquals(null, tag.getRev());
        assertEquals(null, tag.getSelected());
        assertEquals(null, tag.getShape());
        assertEquals(null, tag.getSrc());
        assertEquals(null, tag.getStyle());
        assertEquals(null, tag.getTarget());
        assertEquals(null, tag.getType());
        assertEquals(null, tag.getUsemap());
        assertEquals(null, tag.getValue());
        assertEquals(null, tag.getSummary());
        assertEquals(null, tag.getBgcolor());
        assertEquals(null, tag.getFrame());
        assertEquals(null, tag.getRules());
        assertEquals(null, tag.getConverter());
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
