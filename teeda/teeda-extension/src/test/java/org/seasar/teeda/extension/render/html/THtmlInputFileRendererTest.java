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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockConverter;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.component.html.THtmlInputFile;
import org.seasar.teeda.extension.util.UploadedFile;
import org.seasar.teeda.extension.util.UploadedFileImpl;

/**
 * @author shot
 */
public class THtmlInputFileRendererTest extends RendererTest {

    private THtmlInputFileRenderer renderer;

    private MockTHtmlInputFile htmlInputFile;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createTHtmlInputFileRenderer();
        htmlInputFile = (MockTHtmlInputFile) createUIInput();
        htmlInputFile.setRenderer(renderer);
    }

    protected UIInput createUIInput() {
        return new MockTHtmlInputFile();
    }

    public void testEncode_NoValue() throws Exception {

        // ## Act ##
        encodeByRenderer(renderer, htmlInputFile);

        // ## Assert ##
        assertEquals("<input type=\"file\" name=\"_id0\" />", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlInputFile.setRendered(false);

        // ## Act ##
        encodeByRenderer(renderer, htmlInputFile);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_WithValue() throws Exception {
        // ## Arrange ##
        htmlInputFile.setValue("abc");

        // ## Act ##
        encodeByRenderer(renderer, htmlInputFile);

        // ## Assert ##
        assertEquals("<input type=\"file\" name=\"_id0\" />", getResponseText());
    }

    public void testEncode_WithId() throws Exception {
        htmlInputFile.setId("a");

        UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("b");
        parent.getChildren().add(htmlInputFile);

        encodeByRenderer(renderer, htmlInputFile);

        assertEquals("<input type=\"file\" id=\"a\" name=\"b:a\" />",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute1() throws Exception {
        htmlInputFile.setId("a");
        htmlInputFile.getAttributes().put("bbb", "ccc");

        encodeByRenderer(renderer, htmlInputFile);

        assertEquals("<input type=\"file\" id=\"a\" name=\"a\" bbb=\"ccc\" />",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute2() throws Exception {
        htmlInputFile.setId("a");
        htmlInputFile.getAttributes().put("b.b", "ccc");

        encodeByRenderer(renderer, htmlInputFile);

        assertEquals("<input type=\"file\" id=\"a\" name=\"a\" />",
                getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlInputFile.setAccept("text/html");
        htmlInputFile.setAccesskey("a");
        htmlInputFile.setAlt("b");
        htmlInputFile.setDir("c");
        htmlInputFile.setDisabled(true);
        htmlInputFile.setLang("e");
        htmlInputFile.setMaxlength(5);
        htmlInputFile.setOnblur("g");
        htmlInputFile.setOnchange("h");
        htmlInputFile.setOnclick("i");
        htmlInputFile.setOndblclick("j");
        htmlInputFile.setOnfocus("k");
        htmlInputFile.setOnkeydown("l");
        htmlInputFile.setOnkeypress("m");
        htmlInputFile.setOnkeyup("n");
        htmlInputFile.setOnmousedown("o");
        htmlInputFile.setOnmousemove("p");
        htmlInputFile.setOnmouseout("q");
        htmlInputFile.setOnmouseover("r");
        htmlInputFile.setOnmouseup("s");
        htmlInputFile.setOnselect("t");
        htmlInputFile.setReadonly(true);
        htmlInputFile.setSize(2);
        htmlInputFile.setStyle("w");
        htmlInputFile.setStyleClass("u");
        htmlInputFile.setTabindex("x");
        htmlInputFile.setTitle("y");

        htmlInputFile.setId("A");
        htmlInputFile.setValue(new UploadedFileImpl(null));

        encodeByRenderer(renderer, htmlInputFile);

        Diff diff = new Diff("<input type=\"file\" id=\"A\" name=\"A\""
                + " accept=\"text/html\"" + " accesskey=\"a\"" + " alt=\"b\""
                + " dir=\"c\"" + " disabled=\"disabled\"" + " lang=\"e\""
                + " maxlength=\"5\"" + " onblur=\"g\"" + " onchange=\"h\""
                + " onclick=\"i\"" + " ondblclick=\"j\"" + " onfocus=\"k\""
                + " onkeydown=\"l\"" + " onkeypress=\"m\"" + " onkeyup=\"n\""
                + " onmousedown=\"o\"" + " onmousemove=\"p\""
                + " onmouseout=\"q\"" + " onmouseover=\"r\""
                + " onmouseup=\"s\"" + " onselect=\"t\""
                + " readonly=\"readonly\"" + " size=\"2\"" + " style=\"w\""
                + " class=\"u\"" + " tabindex=\"x\"" + " title=\"y\"" + "/>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testDecode_None() throws Exception {
        // ## Arrange ##
        htmlInputFile.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlInputFile);

        // ## Assert ##
        assertEquals(null, htmlInputFile.getSubmittedValue());
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        htmlInputFile.setClientId("key");
        FileItem fileItem = new DiskFileItem("key", "text/html", false,
                "foo/bar/file", 10 * 1024, new File("C:\\temp"));
        Map fileItems = new HashMap();
        fileItems.put("key", fileItem);
        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestMap().put(
                ExtensionConstants.REQUEST_ATTRIBUTE_UPLOADED_FILES, fileItems);

        // ## Act ##
        renderer.decode(context, htmlInputFile);

        // ## Assert ##
        UploadedFile uploadedFile = (UploadedFile) htmlInputFile
                .getSubmittedValue();
        assertEquals("file", uploadedFile.getName());
        assertEquals("foo/bar/file", uploadedFile.getOriginalName());
        assertEquals("text/html", uploadedFile.getContentType());
    }

    public void testEncode_Converter() throws Exception {
        // ## Arrange ##
        Converter converter = new MockConverter() {
            public String getAsString(FacesContext context,
                    UIComponent component, Object value)
                    throws ConverterException {
                return value + "ddd";
            }
        };
        htmlInputFile.setValue("abc");
        htmlInputFile.setConverter(converter);

        // ## Act ##
        encodeByRenderer(renderer, htmlInputFile);

        // ## Assert ##
        assertEquals("<input type=\"file\" name=\"_id0\" />", getResponseText());
    }

    public void testGetConvertedValue() throws Exception {
        Converter converter = new MockConverter() {
            public Object getAsObject(FacesContext context,
                    UIComponent component, String value)
                    throws ConverterException {
                return value + ".testGetConvertedValue";
            }
        };
        htmlInputFile.setConverter(converter);
        Object convertedValue = renderer.getConvertedValue(getFacesContext(),
                htmlInputFile, "aaa");

        assertEquals("aaa.testGetConvertedValue", convertedValue);
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    public void testErrorStyleClass() throws Exception {
        htmlInputFile.setClientId("hoge");
        MockFacesContext facesContext = getFacesContext();
        facesContext.addMessage("hoge", new FacesMessage("sssss"));
        htmlInputFile.setErrorStyleClass("foo");

        // ## Act ##
        encodeByRenderer(renderer, htmlInputFile);

        // ## Assert ##
        assertEquals("<input type=\"file\" name=\"hoge\" class=\"foo\" />",
                getResponseText());

    }

    private THtmlInputFileRenderer createTHtmlInputFileRenderer() {
        return (THtmlInputFileRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        THtmlInputFileRenderer renderer = new THtmlInputFileRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    public static class MockTHtmlInputFile extends THtmlInputFile {

        private Renderer renderer;

        private String clientId;

        public void setRenderer(Renderer renderer) {
            this.renderer = renderer;
        }

        protected Renderer getRenderer(FacesContext context) {
            if (renderer != null) {
                return renderer;
            }
            return super.getRenderer(context);
        }

        public String getClientId(FacesContext context) {
            if (clientId != null) {
                return clientId;
            }
            return super.getClientId(context);
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

    }
}
