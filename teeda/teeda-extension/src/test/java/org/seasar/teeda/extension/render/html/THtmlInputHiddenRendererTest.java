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
package org.seasar.teeda.extension.render.html;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;
import org.seasar.teeda.core.render.Base64EncodeConverter;
import org.seasar.teeda.extension.mock.MockTHtmlInputHidden;

/**
 * @author manhole
 */
public class THtmlInputHiddenRendererTest extends RendererTest {

    private THtmlInputHiddenRenderer renderer;

    private MockTHtmlInputHidden htmlInputHidden;

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlInputHidden.setRendered(false);

        // ## Act ##
        encodeByRenderer(renderer, htmlInputHidden);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_NoValue() throws Exception {
        // ## Arrange ##

        // ## Act ##
        encodeByRenderer(renderer, htmlInputHidden);

        // ## Assert ##
        assertEquals("<input type=\"hidden\" name=\"_id0\" value=\"\" />",
                getResponseText());
    }

    public void testEncode_IllegalValue() throws Exception {
        // ## Arrange ##
        htmlInputHidden.setValue("invalid");

        // ## Act ##
        // ## Assert ##
        try {
            encodeByRenderer(renderer, htmlInputHidden);
            fail();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void testEncode_WithId() throws Exception {
        htmlInputHidden.setId("a");

        UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("b");
        parent.getChildren().add(htmlInputHidden);

        encodeByRenderer(renderer, htmlInputHidden);

        assertEquals(
                "<input type=\"hidden\" id=\"a\" name=\"b:a\" value=\"\" />",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute() throws Exception {
        htmlInputHidden.setId("a");
        htmlInputHidden.getAttributes().put("unknown", "foo");

        encodeByRenderer(renderer, htmlInputHidden);

        assertEquals(
                "<input type=\"hidden\" id=\"a\" name=\"a\" value=\"\" unknown=\"foo\" />",
                getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlInputHidden.setId("A");
        htmlInputHidden.setValue(new HiddenDto[] {});

        encodeByRenderer(renderer, htmlInputHidden);

        final Diff diff = diff(
                "<input type=\"hidden\" id=\"A\" name=\"A\" value=\".*\""
                        + " />", getResponseText());
        assertEquals(diff.toString(), true, diff.similar());
    }

    public void testDecode_None() throws Exception {
        // ## Arrange ##
        htmlInputHidden.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlInputHidden);

        // ## Assert ##
        assertEquals(null, htmlInputHidden.getSubmittedValue());
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        htmlInputHidden.setClientId("key:aa");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put("key:aa",
                "12345");

        // ## Act ##
        renderer.decode(context, htmlInputHidden);

        // ## Assert ##
        assertEquals("12345", htmlInputHidden.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    public void testCopyToMap() throws Exception {
        // ## Arrange ##
        final HiddenDto dto = new HiddenDto();
        dto.setAaa("x");
        dto.setBbb(new Integer(321));
        dto.setCcc(new BigDecimal("12.3"));
        dto.xxx = "x";
        dto.yyy = "x";
        dto.zzz = "x";

        // ## Act ##
        final Map map = new HashMap();
        renderer.copyToMap(dto, map);

        // ## Assert ##
        assertEquals("x", map.get("aaa"));
        assertEquals(new Integer(321), map.get("bbb"));
        assertEquals(new BigDecimal("12.3"), map.get("ccc"));
        assertEquals(null, map.get("xxx"));
        assertEquals(null, map.get("yyy"));
        assertEquals(null, map.get("zzz"));
    }

    public void testCopyToBean() throws Exception {
        // ## Arrange ##
        final Map map = new HashMap();
        map.put("aaa", "AAA");
        map.put("bbb", new Integer(1234));
        map.put("ccc", new BigDecimal("10.002"));
        map.put("xxx", "x");
        map.put("yyy", "y");
        map.put("zzz", "z");

        // ## Act ##
        final HiddenDto dto = new HiddenDto();
        renderer.copyToBean(map, dto);

        // ## Assert ##
        assertEquals("AAA", dto.getAaa());
        assertEquals(new Integer(1234), dto.getBbb());
        assertEquals(new BigDecimal("10.002"), dto.getCcc());
        assertEquals(null, dto.xxx);
        assertEquals(null, dto.yyy);
        assertEquals(null, dto.zzz);
    }

    public void testEncodeAndDecode_ArrayValue() throws Exception {
        // ## Arrange ##
        final HiddenDto[] dtos = new HiddenDto[3];
        {
            final HiddenDto dto = new HiddenDto();
            dto.setAaa("a1");
            dto.setBbb(new Integer(1));
            dto.setCcc(new BigDecimal("101"));
            dtos[0] = dto;
        }
        {
            final HiddenDto dto = new HiddenDto();
            dto.setAaa("a2");
            dto.setBbb(new Integer(2));
            dto.setCcc(new BigDecimal("201"));
            dtos[1] = dto;
        }
        {
            final HiddenDto dto = new HiddenDto();
            dto.setAaa("a3");
            dto.setBbb(new Integer(3));
            dto.setCcc(new BigDecimal("301"));
            dtos[2] = dto;
        }
        htmlInputHidden.setValue(dtos);

        // ## Act ##
        encodeByRenderer(renderer, htmlInputHidden);

        // ## Assert ##
        final String responseText = getResponseText();
        final Pattern pattern = Pattern.compile("(.+?)value=\"(.+?)\"(.+?)");
        final Matcher matcher = pattern.matcher(responseText);
        assertEquals(true, matcher.matches());
        assertEquals(3, matcher.groupCount());
        assertEquals("<input type=\"hidden\" name=\"_id0\" ", matcher.group(1));
        assertEquals(" />", matcher.group(3));
        final String serializedValue = matcher.group(2);
        final Object deserialized = renderer.getConvertedValue(
                getFacesContext(), htmlInputHidden, serializedValue);
        final HiddenDto[] deserializedDto = (HiddenDto[]) deserialized;
        assertEquals(3, deserializedDto.length);
        {
            final HiddenDto dto = deserializedDto[0];
            assertEquals("a1", dto.getAaa());
            assertEquals(new Integer(1), dto.getBbb());
            assertEquals(new BigDecimal("101"), dto.getCcc());
        }
        {
            final HiddenDto dto = deserializedDto[1];
            assertEquals("a2", dto.getAaa());
            assertEquals(new Integer(2), dto.getBbb());
            assertEquals(new BigDecimal("201"), dto.getCcc());
        }
        {
            final HiddenDto dto = deserializedDto[2];
            assertEquals("a3", dto.getAaa());
            assertEquals(new Integer(3), dto.getBbb());
            assertEquals(new BigDecimal("301"), dto.getCcc());
        }
    }

    public void testEncodeAndDecode_ListValue() throws Exception {
        // ## Arrange ##
        final List dtos = new ArrayList();
        {
            final HiddenDto dto = new HiddenDto();
            dto.setAaa("a1");
            dto.setBbb(new Integer(1));
            dto.setCcc(new BigDecimal("101"));
            dtos.add(dto);
        }
        {
            final HiddenDto dto = new HiddenDto();
            dto.setAaa("a2");
            dto.setBbb(new Integer(2));
            dto.setCcc(new BigDecimal("201"));
            dtos.add(dto);
        }
        htmlInputHidden.setValue(dtos);

        // ## Act ##
        encodeByRenderer(renderer, htmlInputHidden);

        // ## Assert ##
        final String responseText = getResponseText();
        final Pattern pattern = Pattern.compile("(.+?)value=\"(.+?)\"(.+?)");
        final Matcher matcher = pattern.matcher(responseText);
        assertEquals(true, matcher.matches());
        assertEquals(3, matcher.groupCount());
        assertEquals("<input type=\"hidden\" name=\"_id0\" ", matcher.group(1));
        assertEquals(" />", matcher.group(3));
        final String serializedValue = matcher.group(2);
        final Object deserialized = renderer.getConvertedValue(
                getFacesContext(), htmlInputHidden, serializedValue);
        final List deserializedList = (List) deserialized;
        assertEquals(2, deserializedList.size());
        {
            final HiddenDto dto = (HiddenDto) deserializedList.get(0);
            assertEquals("a1", dto.getAaa());
            assertEquals(new Integer(1), dto.getBbb());
            assertEquals(new BigDecimal("101"), dto.getCcc());
        }
        {
            final HiddenDto dto = (HiddenDto) deserializedList.get(1);
            assertEquals("a2", dto.getAaa());
            assertEquals(new Integer(2), dto.getBbb());
            assertEquals(new BigDecimal("201"), dto.getCcc());
        }
    }

    public void testGetAndSetValue_Array() throws Exception {

    }

    public static class HiddenDto {

        private String aaa;

        private Integer bbb;

        private BigDecimal ccc;

        // no accessor
        private String xxx;

        // read only
        private String yyy;

        // write only 
        private String zzz;

        public String getAaa() {
            return aaa;
        }

        public void setAaa(String aaa) {
            this.aaa = aaa;
        }

        public Integer getBbb() {
            return bbb;
        }

        public void setBbb(Integer bbb) {
            this.bbb = bbb;
        }

        public BigDecimal getCcc() {
            return ccc;
        }

        public void setCcc(BigDecimal ccc) {
            this.ccc = ccc;
        }

        public String getYyy() {
            return yyy;
        }

        public void setZzz(String ccc) {
            this.zzz = ccc;
        }

    }

    protected void setUp() throws Exception {
        super.setUp();
        renderer = (THtmlInputHiddenRenderer) createRenderer();
        htmlInputHidden = new MockTHtmlInputHidden();
        htmlInputHidden.setRenderer(renderer);
    }

    protected Renderer createRenderer() {
        THtmlInputHiddenRenderer renderer = new THtmlInputHiddenRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        renderer.setRenderAttributes(getRenderAttributes());
        renderer.setEncodeConverter(new Base64EncodeConverter());
        return renderer;
    }

}
