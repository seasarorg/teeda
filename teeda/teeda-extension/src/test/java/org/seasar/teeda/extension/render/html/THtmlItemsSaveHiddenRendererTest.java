/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;
import org.seasar.teeda.core.render.Base64EncodeConverter;
import org.seasar.teeda.extension.mock.MockTHtmlItemsSaveHidden;

/**
 * @author manhole
 * @author shot
 */
public class THtmlItemsSaveHiddenRendererTest extends RendererTest {

    private THtmlItemsSaveHiddenRenderer renderer;

    private MockTHtmlItemsSaveHidden htmlInputHidden;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = (THtmlItemsSaveHiddenRenderer) createRenderer();
        htmlInputHidden = new MockTHtmlItemsSaveHidden();
        htmlInputHidden.setRenderer(renderer);
    }

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

    //from this test, other tests that fail should be modified.
    public void testEncodeAndDecode_DtoArray() throws Exception {
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
        htmlInputHidden.setId("hogeItemsSave");

        MockFacesContext context = getFacesContext();
        final Hoge hoge = new Hoge();
        context.getApplication().setVariableResolver(new VariableResolver() {

            public Object resolveVariable(FacesContext context, String name)
                    throws EvaluationException {
                return hoge;
            }

        });
        // ## Act ##
        encodeByRenderer(renderer, htmlInputHidden);

        // ## Assert ##
        final String responseText = getResponseText();
        final Pattern pattern = Pattern.compile("(.+?)value=\"(.+?)\"(.+?)");
        final Matcher matcher = pattern.matcher(responseText);
        assertEquals(true, matcher.matches());
        assertEquals(3, matcher.groupCount());
        assertEquals(
                "<input type=\"hidden\" id=\"hogeItemsSave\" name=\"hogeItemsSave\" ",
                matcher.group(1));
        assertEquals(" />", matcher.group(3));
        final String serializedValue = matcher.group(2);
        final Object deserialized = renderer.getConvertedValue(context,
                htmlInputHidden, serializedValue);
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

    public void testEncodeAndDecode_DtoList() throws Exception {
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
        htmlInputHidden.setId("fooItemsSave");

        MockFacesContext context = getFacesContext();
        final Foo foo = new Foo();
        context.getApplication().setVariableResolver(new VariableResolver() {

            public Object resolveVariable(FacesContext context, String name)
                    throws EvaluationException {
                return foo;
            }

        });

        // ## Act ##
        encodeByRenderer(renderer, htmlInputHidden);

        // ## Assert ##
        final String responseText = getResponseText();
        final Pattern pattern = Pattern.compile("(.+?)value=\"(.+?)\"(.+?)");
        final Matcher matcher = pattern.matcher(responseText);
        assertEquals(true, matcher.matches());
        assertEquals(3, matcher.groupCount());
        assertEquals(
                "<input type=\"hidden\" id=\"fooItemsSave\" name=\"fooItemsSave\" ",
                matcher.group(1));
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

    public void testEncodeAndDecode_MapArray() throws Exception {
        // ## Arrange ##
        final Map[] dtos = new Map[2];
        final List list = new ArrayList();
        {
            final Map map = new HashMap();
            map.put("A", "aa1");
            map.put("B", Boolean.TRUE);
            map.put("C", new BigInteger("5525"));
            list.add(map);
        }
        {
            final Map map = new HashMap();
            map.put("A", "aa2");
            map.put("B", Boolean.FALSE);
            map.put("C", new BigInteger("6636"));
            list.add(map);
        }
        list.toArray(dtos);
        htmlInputHidden.setValue(dtos);
        htmlInputHidden.setId("hogeItemsSave");

        MockFacesContext context = getFacesContext();
        final Hoge2 hoge2 = new Hoge2();
        context.getApplication().setVariableResolver(new VariableResolver() {

            public Object resolveVariable(FacesContext context, String name)
                    throws EvaluationException {
                return hoge2;
            }

        });

        // ## Act ##
        encodeByRenderer(renderer, htmlInputHidden);

        // ## Assert ##
        final String responseText = getResponseText();
        final Pattern pattern = Pattern.compile("(.+?)value=\"(.+?)\"(.+?)");
        final Matcher matcher = pattern.matcher(responseText);
        assertEquals(true, matcher.matches());
        assertEquals(3, matcher.groupCount());
        assertEquals(
                "<input type=\"hidden\" id=\"hogeItemsSave\" name=\"hogeItemsSave\" ",
                matcher.group(1));
        assertEquals(" />", matcher.group(3));
        final String serializedValue = matcher.group(2);
        final Object deserialized = renderer.getConvertedValue(
                getFacesContext(), htmlInputHidden, serializedValue);
        final Map[] deserializedDtos = (Map[]) deserialized;
        assertEquals(2, deserializedDtos.length);
        {
            final Map map = deserializedDtos[0];
            assertEquals("aa1", map.get("A"));
            assertEquals(Boolean.TRUE, map.get("B"));
            assertEquals(new BigInteger("5525"), map.get("C"));
        }
        {
            final Map map = deserializedDtos[1];
            assertEquals("aa2", map.get("A"));
            assertEquals(Boolean.FALSE, map.get("B"));
            assertEquals(new BigInteger("6636"), map.get("C"));
        }
    }

    public void testEncodeAndDecode_MapList() throws Exception {
        // ## Arrange ##
        final List dtos = new ArrayList();
        {
            final Map map = new HashMap();
            map.put("A", "aa1");
            map.put("B", Boolean.TRUE);
            map.put("C", new BigInteger("5525"));
            dtos.add(map);
        }
        {
            final Map map = new HashMap();
            map.put("A", "aa2");
            map.put("B", Boolean.FALSE);
            map.put("C", new BigInteger("6636"));
            dtos.add(map);
        }
        htmlInputHidden.setValue(dtos);
        htmlInputHidden.setId("fooItemsSave");

        MockFacesContext context = getFacesContext();
        final Foo foo = new Foo();
        context.getApplication().setVariableResolver(new VariableResolver() {

            public Object resolveVariable(FacesContext context, String name)
                    throws EvaluationException {
                return foo;
            }

        });

        // ## Act ##
        encodeByRenderer(renderer, htmlInputHidden);

        // ## Assert ##
        final String responseText = getResponseText();
        final Pattern pattern = Pattern.compile("value=\"(.+?)\"");
        final Matcher matcher = pattern.matcher(responseText);
        assertEquals(true, matcher.find());
        final String serializedValue = matcher.group(1);
        final Object deserialized = renderer.getConvertedValue(
                getFacesContext(), htmlInputHidden, serializedValue);
        final List deserializedList = (List) deserialized;
        assertEquals(2, deserializedList.size());
        {
            final Map map = (Map) deserializedList.get(0);
            assertEquals("aa1", map.get("A"));
            assertEquals(Boolean.TRUE, map.get("B"));
            assertEquals(new BigInteger("5525"), map.get("C"));
        }
        {
            final Map map = (Map) deserializedList.get(1);
            assertEquals("aa2", map.get("A"));
            assertEquals(Boolean.FALSE, map.get("B"));
            assertEquals(new BigInteger("6636"), map.get("C"));
        }
    }

    public void testEncodeAndDecode_StringArray() throws Exception {
        // ## Arrange ##
        final String[] dtos = new String[] { "a", "bb", "ccc" };
        htmlInputHidden.setValue(dtos);
        htmlInputHidden.setId("hogeItemsSave");

        MockFacesContext context = getFacesContext();
        final Hoge3 hoge3 = new Hoge3();
        context.getApplication().setVariableResolver(new VariableResolver() {

            public Object resolveVariable(FacesContext context, String name)
                    throws EvaluationException {
                return hoge3;
            }

        });

        // ## Act ##
        encodeByRenderer(renderer, htmlInputHidden);

        // ## Assert ##
        final String responseText = getResponseText();
        final Pattern pattern = Pattern.compile("value=\"(.+?)\"");
        final Matcher matcher = pattern.matcher(responseText);
        assertEquals(true, matcher.find());
        final String serializedValue = matcher.group(1);
        final Object deserialized = renderer.getConvertedValue(
                getFacesContext(), htmlInputHidden, serializedValue);
        final String[] deserializedArray = (String[]) deserialized;
        assertEquals(3, deserializedArray.length);
        assertEquals("a", deserializedArray[0]);
        assertEquals("bb", deserializedArray[1]);
        assertEquals("ccc", deserializedArray[2]);
    }

    public void testEncodeAndDecode_StringList() throws Exception {
        // ## Arrange ##
        final List dtos = Arrays.asList(new String[] { "a", "bb", "ccc" });
        htmlInputHidden.setValue(dtos);
        htmlInputHidden.setId("fooItemsSave");
        MockFacesContext context = getFacesContext();
        final Foo foo = new Foo();
        context.getApplication().setVariableResolver(new VariableResolver() {

            public Object resolveVariable(FacesContext context, String name)
                    throws EvaluationException {
                return foo;
            }

        });

        // ## Act ##
        encodeByRenderer(renderer, htmlInputHidden);

        // ## Assert ##
        final String responseText = getResponseText();
        final Pattern pattern = Pattern.compile("(.+?)value=\"(.+?)\"(.+?)");
        final Matcher matcher = pattern.matcher(responseText);
        assertEquals(true, matcher.matches());
        assertEquals(3, matcher.groupCount());
        assertEquals(
                "<input type=\"hidden\" id=\"fooItemsSave\" name=\"fooItemsSave\" ",
                matcher.group(1));
        assertEquals(" />", matcher.group(3));
        final String serializedValue = matcher.group(2);
        final Object deserialized = renderer.getConvertedValue(
                getFacesContext(), htmlInputHidden, serializedValue);
        final List deserializedList = (List) deserialized;
        assertEquals(3, deserializedList.size());
        assertEquals("a", deserializedList.get(0));
        assertEquals("bb", deserializedList.get(1));
        assertEquals("ccc", deserializedList.get(2));
    }

    public void testGetConvertedValue() throws Exception {
        THtmlItemsSaveHiddenRenderer renderer = (THtmlItemsSaveHiddenRenderer) createRenderer();
        Object value = "aaa";
        Base64EncodeConverter converter = new Base64EncodeConverter();
        String s = converter.getAsEncodeString(value);
        renderer.getConvertedValue(getFacesContext(), htmlInputHidden, s);
    }

    public static class HiddenDto implements Serializable {

        private static final long serialVersionUID = 1L;

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

    protected Renderer createRenderer() {
        THtmlItemsSaveHiddenRenderer renderer = new THtmlItemsSaveHiddenRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        renderer.setEncodeConverter(new Base64EncodeConverter());
        return renderer;
    }

    public static class Hoge {
        private HiddenDto[] hogeItems;

        public HiddenDto[] getHogeItems() {
            return hogeItems;
        }

        public void setHogeItems(HiddenDto[] hogeItems) {
            this.hogeItems = hogeItems;
        }

    }

    public static class Hoge2 {
        private Map[] hogeItems;

        public Map[] getHogeItems() {
            return hogeItems;
        }

        public void setHogeItems(Map[] hogeItems) {
            this.hogeItems = hogeItems;
        }

    }

    public static class Hoge3 {
        private String[] hogeItems;

        public String[] getHogeItems() {
            return hogeItems;
        }

        public void setHogeItems(String[] hogeItems) {
            this.hogeItems = hogeItems;
        }

    }

    public static class Foo {
        private List fooItems;

        public List getFooItems() {
            return fooItems;
        }

        public void setFooItems(List hogeItems) {
            this.fooItems = hogeItems;
        }

    }

}
