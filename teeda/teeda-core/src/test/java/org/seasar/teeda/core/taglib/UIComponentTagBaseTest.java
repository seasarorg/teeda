/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import org.seasar.teeda.core.mock.MockUIComponentBase;

/**
 * @author yone
 * @author shot
 */
public class UIComponentTagBaseTest extends TestCase {

    public void testRelease() throws Exception {
        // # Arrange #
        final UIComponentTagBase tag = new UIComponentTagBaseImpl();

        tag.setAccept("accept");
        tag.setAcceptcharset("acceptCharset");
        tag.setAccesskey("accesskey");
        tag.setAction("action");
        tag.setAlt("alt");
        tag.setBgcolor("green");
        tag.setBorder("4");
        tag.setCellpadding("2");
        tag.setCellspacing("5");
        tag.setCharset("charset");
        tag.setChecked("checked");
        tag.setCols("cols");
        tag.setConverter("converter");
        tag.setCoords("coords");
        tag.setDateStyle("dateStyle");
        tag.setDir("dir");
        tag.setDisabled("true");
        tag.setDisabledClass("disabledClass");
        tag.setEnabledClass("enabledClass");
        tag.setEnctype("enctype");
        tag.setFormatPattern("formatPattern");
        tag.setFormatStyle("formatStyle");
        tag.setFrame("frame");
        tag.setHeight("111");
        tag.setHref("href");
        tag.setHreflang("hreflang");
        tag.setHspace("hspace");
        tag.setHtmlFor("htmlFor");
        tag.setIsmap("ismap");
        tag.setLang("lang");
        tag.setLongdesc("longdesc");
        tag.setMaxlength("5");
        tag.setMethod("method");
        tag.setMultiple("multiple");
        tag.setName("name");
        tag.setOnblur("onblur");
        tag.setOnchange("onchange");
        tag.setOnclick("onclick");
        tag.setOndblclick("ondblclick");
        tag.setOnfocus("onfocus");
        tag.setOnkeydown("onkeydown");
        tag.setOnkeypress("onkeypress");
        tag.setOnkeyup("onkeyup");
        tag.setOnmousedown("onmousedown");
        tag.setOnmousemove("onmousemove");
        tag.setOnmouseout("onmouseout");
        tag.setOnmouseover("onmouseover");
        tag.setOnmouseup("onmouseup");
        tag.setOnreset("onreset");
        tag.setOnselect("onselect");
        tag.setOnsubmit("onsubmit");
        tag.setReadonly("true");
        tag.setRel("rel");
        tag.setRev("rev");
        tag.setRows("rows");
        tag.setRules("rules");
        tag.setSelected("selected");
        tag.setShape("shape");
        tag.setSize("44");
        tag.setSrc("src");
        tag.setStyle("style");
        tag.setStyleClass("styleClass");
        tag.setSummary("summary");
        tag.setTabindex("3");
        tag.setTarget("target");
        tag.setTimeStyle("timeStyle");
        tag.setTimezone("timezone");
        tag.setTitle("title");
        tag.setType("type");
        tag.setUsemap("usemap");
        tag.setValue("value");
        tag.setWidth("222");

        assertEquals("accept", tag.getAccept());
        assertEquals("acceptCharset", tag.getAcceptcharset());
        assertEquals("accesskey", tag.getAccesskey());
        assertEquals("action", tag.getAction());
        assertEquals("alt", tag.getAlt());
        assertEquals("green", tag.getBgcolor());
        assertEquals("4", tag.getBorder());
        assertEquals("2", tag.getCellpadding());
        assertEquals("5", tag.getCellspacing());
        assertEquals("charset", tag.getCharset());
        assertEquals("checked", tag.getChecked());
        assertEquals("cols", tag.getCols());
        assertEquals("converter", tag.getConverter());
        assertEquals("coords", tag.getCoords());
        assertEquals("dateStyle", tag.getDateStyle());
        assertEquals("dir", tag.getDir());
        assertEquals("true", tag.getDisabled());
        assertEquals("disabledClass", tag.getDisabledClass());
        assertEquals("enabledClass", tag.getEnabledClass());
        assertEquals("enctype", tag.getEnctype());
        assertEquals("formatPattern", tag.getFormatPattern());
        assertEquals("formatStyle", tag.getFormatStyle());
        assertEquals("frame", tag.getFrame());
        assertEquals("111", tag.getHeight());
        assertEquals("href", tag.getHref());
        assertEquals("hreflang", tag.getHreflang());
        assertEquals("hspace", tag.getHspace());
        assertEquals("htmlFor", tag.getHtmlFor());
        assertEquals("ismap", tag.getIsmap());
        assertEquals("lang", tag.getLang());
        assertEquals("longdesc", tag.getLongdesc());
        assertEquals("5", tag.getMaxlength());
        assertEquals("method", tag.getMethod());
        assertEquals("multiple", tag.getMultiple());
        assertEquals("name", tag.getName());
        assertEquals("onblur", tag.getOnblur());
        assertEquals("onchange", tag.getOnchange());
        assertEquals("onclick", tag.getOnclick());
        assertEquals("ondblclick", tag.getOndblclick());
        assertEquals("onfocus", tag.getOnfocus());
        assertEquals("onkeydown", tag.getOnkeydown());
        assertEquals("onkeypress", tag.getOnkeypress());
        assertEquals("onkeyup", tag.getOnkeyup());
        assertEquals("onmousedown", tag.getOnmousedown());
        assertEquals("onmousemove", tag.getOnmousemove());
        assertEquals("onmouseout", tag.getOnmouseout());
        assertEquals("onmouseover", tag.getOnmouseover());
        assertEquals("onmouseup", tag.getOnmouseup());
        assertEquals("onreset", tag.getOnreset());
        assertEquals("onselect", tag.getOnselect());
        assertEquals("onsubmit", tag.getOnsubmit());
        assertEquals("true", tag.getReadonly());
        assertEquals("rel", tag.getRel());
        assertEquals("rev", tag.getRev());
        assertEquals("rows", tag.getRows());
        assertEquals("rules", tag.getRules());
        assertEquals("selected", tag.getSelected());
        assertEquals("shape", tag.getShape());
        assertEquals("44", tag.getSize());
        assertEquals("src", tag.getSrc());
        assertEquals("style", tag.getStyle());
        assertEquals("styleClass", tag.getStyleClass());
        assertEquals("summary", tag.getSummary());
        assertEquals("3", tag.getTabindex());
        assertEquals("target", tag.getTarget());
        assertEquals("timeStyle", tag.getTimeStyle());
        assertEquals("timezone", tag.getTimezone());
        assertEquals("title", tag.getTitle());
        assertEquals("type", tag.getType());
        assertEquals("usemap", tag.getUsemap());
        assertEquals("value", tag.getValue());
        assertEquals("222", tag.getWidth());

        // # Act #
        tag.release();

        // # Assert #
        assertEquals(null, tag.getAccept());
        assertEquals(null, tag.getAcceptcharset());
        assertEquals(null, tag.getAccesskey());
        assertEquals(null, tag.getAction());
        assertEquals(null, tag.getAlt());
        assertEquals(null, tag.getBgcolor());
        assertEquals(null, tag.getBorder());
        assertEquals(null, tag.getCellpadding());
        assertEquals(null, tag.getCellspacing());
        assertEquals(null, tag.getCharset());
        assertEquals(null, tag.getChecked());
        assertEquals(null, tag.getCols());
        assertEquals(null, tag.getConverter());
        assertEquals(null, tag.getCoords());
        assertEquals(null, tag.getDateStyle());
        assertEquals(null, tag.getDir());
        assertEquals(null, tag.getDisabled());
        assertEquals(null, tag.getDisabledClass());
        assertEquals(null, tag.getEnabledClass());
        assertEquals(null, tag.getEnctype());
        assertEquals(null, tag.getFormatPattern());
        assertEquals(null, tag.getFormatStyle());
        assertEquals(null, tag.getFrame());
        assertEquals(null, tag.getHeight());
        assertEquals(null, tag.getHref());
        assertEquals(null, tag.getHreflang());
        assertEquals(null, tag.getHspace());
        assertEquals(null, tag.getHtmlFor());
        assertEquals(null, tag.getIsmap());
        assertEquals(null, tag.getLang());
        assertEquals(null, tag.getLongdesc());
        assertEquals(null, tag.getMaxlength());
        assertEquals(null, tag.getMethod());
        assertEquals(null, tag.getMultiple());
        assertEquals(null, tag.getName());
        assertEquals(null, tag.getOnblur());
        assertEquals(null, tag.getOnchange());
        assertEquals(null, tag.getOnclick());
        assertEquals(null, tag.getOndblclick());
        assertEquals(null, tag.getOnfocus());
        assertEquals(null, tag.getOnkeydown());
        assertEquals(null, tag.getOnkeypress());
        assertEquals(null, tag.getOnkeyup());
        assertEquals(null, tag.getOnmousedown());
        assertEquals(null, tag.getOnmousemove());
        assertEquals(null, tag.getOnmouseout());
        assertEquals(null, tag.getOnmouseover());
        assertEquals(null, tag.getOnmouseup());
        assertEquals(null, tag.getOnreset());
        assertEquals(null, tag.getOnselect());
        assertEquals(null, tag.getOnsubmit());
        assertEquals(null, tag.getReadonly());
        assertEquals(null, tag.getRel());
        assertEquals(null, tag.getRev());
        assertEquals(null, tag.getRows());
        assertEquals(null, tag.getRules());
        assertEquals(null, tag.getSelected());
        assertEquals(null, tag.getShape());
        assertEquals(null, tag.getSize());
        assertEquals(null, tag.getSrc());
        assertEquals(null, tag.getStyle());
        assertEquals(null, tag.getStyleClass());
        assertEquals(null, tag.getSummary());
        assertEquals(null, tag.getTabindex());
        assertEquals(null, tag.getTarget());
        assertEquals(null, tag.getTimeStyle());
        assertEquals(null, tag.getTimezone());
        assertEquals(null, tag.getTitle());
        assertEquals(null, tag.getType());
        assertEquals(null, tag.getUsemap());
        assertEquals(null, tag.getValue());
        assertEquals(null, tag.getWidth());
    }

    /**
     * actionとconverterを除く、
     * tagのプロパティが全てcomponentへセットされること
     */
    public void testSetProperties() throws Exception {
        // ## Arrange ##
        final UIComponentTagBase tag = new UIComponentTagBaseImpl();
        tag.setAccept("accept1");
        tag.setAcceptcharset("acceptcharset1");
        tag.setAccesskey("accesskey1");
        tag.setAction("action1");
        tag.setAlt("alt1");
        tag.setBgcolor("bgcolor1");
        tag.setBorder("border1");
        tag.setCellpadding("cellpadding1");
        tag.setCellspacing("cellspacing1");
        tag.setCharset("charset1");
        tag.setChecked("checked1");
        tag.setCols("cols1");
        tag.setConverter("converter1");
        tag.setCoords("coords1");
        tag.setDateStyle("dateStyle1");
        tag.setDir("dir1");
        tag.setDisabled("disabled1");
        tag.setDisabledClass("disabledClass1");
        tag.setEnabledClass("enabledClass1");
        tag.setEnctype("enctype1");
        tag.setFormatPattern("formatPattern1");
        tag.setFormatStyle("formatStyle1");
        tag.setFrame("frame1");
        tag.setHeight("height1");
        tag.setHref("href1");
        tag.setHreflang("hreflang1");
        tag.setHspace("hspace1");
        tag.setHtmlFor("htmlFor1");
        tag.setIsmap("ismap1");
        tag.setLang("lang1");
        tag.setLongdesc("longdesc1");
        tag.setMaxlength("maxlength1");
        tag.setMethod("method1");
        tag.setMultiple("multiple1");
        tag.setName("name1");
        tag.setOnblur("onblur1");
        tag.setOnchange("onchange1");
        tag.setOnclick("onclick1");
        tag.setOndblclick("ondblclick1");
        tag.setOnfocus("onfocus1");
        tag.setOnkeydown("onkeydown1");
        tag.setOnkeypress("onkeypress1");
        tag.setOnkeyup("onkeyup1");
        tag.setOnmousedown("onmousedown1");
        tag.setOnmousemove("onmousemove1");
        tag.setOnmouseout("onmouseout1");
        tag.setOnmouseover("onmouseover1");
        tag.setOnmouseup("onmouseup1");
        tag.setOnreset("onreset1");
        tag.setOnselect("onselect1");
        tag.setOnsubmit("onsubmit1");
        tag.setReadonly("readonly1");
        tag.setRel("rel1");
        tag.setRev("rev1");
        tag.setRows("rows1");
        tag.setRules("rules1");
        tag.setSelected("selected1");
        tag.setShape("shape1");
        tag.setSize("size1");
        tag.setSrc("src1");
        tag.setStyle("style1");
        tag.setStyleClass("styleClass1");
        tag.setSummary("summary1");
        tag.setTabindex("tabindex1");
        tag.setTarget("target1");
        tag.setTimeStyle("timeStyle1");
        tag.setTimezone("timezone1");
        tag.setTitle("title1");
        tag.setType("type1");
        tag.setUsemap("usemap1");
        tag.setValue("value1");
        tag.setWidth("width1");

        // ## Act ##
        final MockUIComponentBase component = new MockUIComponentBase();
        tag.setProperties(component);

        // ## Assert ##
        assertEquals("accept1", component.getAttributes().get("accept"));
        assertEquals("acceptcharset1", component.getAttributes().get(
                "acceptcharset"));
        assertEquals("accesskey1", component.getAttributes().get("accesskey"));
        assertEquals(null, component.getAttributes().get("action"));
        assertEquals("alt1", component.getAttributes().get("alt"));
        assertEquals("bgcolor1", component.getAttributes().get("bgcolor"));
        assertEquals("border1", component.getAttributes().get("border"));
        assertEquals("cellpadding1", component.getAttributes().get(
                "cellpadding"));
        assertEquals("cellspacing1", component.getAttributes().get(
                "cellspacing"));
        assertEquals("charset1", component.getAttributes().get("charset"));
        assertEquals("checked1", component.getAttributes().get("checked"));
        assertEquals("cols1", component.getAttributes().get("cols"));
        assertEquals(null, component.getAttributes().get("converter"));
        assertEquals("coords1", component.getAttributes().get("coords"));
        assertEquals("dateStyle1", component.getAttributes().get("dateStyle"));
        assertEquals("dir1", component.getAttributes().get("dir"));
        assertEquals("disabled1", component.getAttributes().get("disabled"));
        assertEquals("disabledClass1", component.getAttributes().get(
                "disabledClass"));
        assertEquals("enabledClass1", component.getAttributes().get(
                "enabledClass"));
        assertEquals("enctype1", component.getAttributes().get("enctype"));
        assertEquals("formatPattern1", component.getAttributes().get(
                "formatPattern"));
        assertEquals("formatStyle1", component.getAttributes().get(
                "formatStyle"));
        assertEquals("frame1", component.getAttributes().get("frame"));
        assertEquals("height1", component.getAttributes().get("height"));
        assertEquals("href1", component.getAttributes().get("href"));
        assertEquals("hreflang1", component.getAttributes().get("hreflang"));
        assertEquals("hspace1", component.getAttributes().get("hspace"));
        assertEquals("htmlFor1", component.getAttributes().get("htmlFor"));
        assertEquals("ismap1", component.getAttributes().get("ismap"));
        assertEquals("lang1", component.getAttributes().get("lang"));
        assertEquals("longdesc1", component.getAttributes().get("longdesc"));
        assertEquals("maxlength1", component.getAttributes().get("maxlength"));
        assertEquals("method1", component.getAttributes().get("method"));
        assertEquals("multiple1", component.getAttributes().get("multiple"));
        assertEquals("name1", component.getAttributes().get("name"));
        assertEquals("onblur1", component.getAttributes().get("onblur"));
        assertEquals("onchange1", component.getAttributes().get("onchange"));
        assertEquals("onclick1", component.getAttributes().get("onclick"));
        assertEquals("ondblclick1", component.getAttributes().get("ondblclick"));
        assertEquals("onfocus1", component.getAttributes().get("onfocus"));
        assertEquals("onkeydown1", component.getAttributes().get("onkeydown"));
        assertEquals("onkeypress1", component.getAttributes().get("onkeypress"));
        assertEquals("onkeyup1", component.getAttributes().get("onkeyup"));
        assertEquals("onmousedown1", component.getAttributes().get(
                "onmousedown"));
        assertEquals("onmousemove1", component.getAttributes().get(
                "onmousemove"));
        assertEquals("onmouseout1", component.getAttributes().get("onmouseout"));
        assertEquals("onmouseover1", component.getAttributes().get(
                "onmouseover"));
        assertEquals("onmouseup1", component.getAttributes().get("onmouseup"));
        assertEquals("onreset1", component.getAttributes().get("onreset"));
        assertEquals("onselect1", component.getAttributes().get("onselect"));
        assertEquals("onsubmit1", component.getAttributes().get("onsubmit"));
        assertEquals("readonly1", component.getAttributes().get("readonly"));
        assertEquals("rel1", component.getAttributes().get("rel"));
        assertEquals("rev1", component.getAttributes().get("rev"));
        assertEquals("rows1", component.getAttributes().get("rows"));
        assertEquals("rules1", component.getAttributes().get("rules"));
        assertEquals("selected1", component.getAttributes().get("selected"));
        assertEquals("shape1", component.getAttributes().get("shape"));
        assertEquals("size1", component.getAttributes().get("size"));
        assertEquals("src1", component.getAttributes().get("src"));
        assertEquals("style1", component.getAttributes().get("style"));
        assertEquals("styleClass1", component.getAttributes().get("styleClass"));
        assertEquals("summary1", component.getAttributes().get("summary"));
        assertEquals("tabindex1", component.getAttributes().get("tabindex"));
        assertEquals("target1", component.getAttributes().get("target"));
        assertEquals("timeStyle1", component.getAttributes().get("timeStyle"));
        assertEquals("timezone1", component.getAttributes().get("timezone"));
        assertEquals("title1", component.getAttributes().get("title"));
        assertEquals("type1", component.getAttributes().get("type"));
        assertEquals("usemap1", component.getAttributes().get("usemap"));
        assertEquals("value1", component.getAttributes().get("value"));
        assertEquals("width1", component.getAttributes().get("width"));

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
