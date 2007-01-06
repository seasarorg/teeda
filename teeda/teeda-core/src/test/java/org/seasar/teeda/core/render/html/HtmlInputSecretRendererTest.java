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
package org.seasar.teeda.core.render.html;

import javax.faces.component.UIComponent;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockHtmlInputSecret;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;

/**
 * @author manhole
 */
public class HtmlInputSecretRendererTest extends RendererTest {

    private HtmlInputSecretRenderer renderer;

    private MockHtmlInputSecret htmlInputSecret;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlInputSecretRenderer();
        htmlInputSecret = new MockHtmlInputSecret();
        htmlInputSecret.setRenderer(renderer);
    }

    public void testEncode_NoValue() throws Exception {
        // ## Act ##
        encodeByRenderer(renderer, htmlInputSecret);

        // ## Assert ##
        assertEquals("<input type=\"password\" name=\"_id0\" value=\"\" />",
                getResponseText());
    }

    public void testEncodeEnd_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlInputSecret.setRendered(false);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.encodeBegin(context, htmlInputSecret);
        renderer.encodeEnd(context, htmlInputSecret);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_WithValue() throws Exception {
        // ## Arrange ##
        htmlInputSecret.setValue("abc");

        // ## Act ##
        encodeByRenderer(renderer, htmlInputSecret);

        // ## Assert ##
        assertEquals("<input type=\"password\" name=\"_id0\" value=\"\" />",
                getResponseText());
    }

    public void testEncode_WithValueRedisplayTrue() throws Exception {
        // ## Arrange ##
        htmlInputSecret.setValue("abc");
        htmlInputSecret.setRedisplay(true);

        // ## Act ##
        encodeByRenderer(renderer, htmlInputSecret);

        // ## Assert ##
        assertEquals("<input type=\"password\" name=\"_id0\" value=\"abc\" />",
                getResponseText());
    }
    
    public void testEncode_WithValueRedisplayTrue2() throws Exception {
        // ## Arrange ##
        htmlInputSecret.setValue("&hearts;");
        htmlInputSecret.setRedisplay(true);

        // ## Act ##
        encodeByRenderer(renderer, htmlInputSecret);

        // ## Assert ##
        assertEquals("<input type=\"password\" name=\"_id0\" value=\"&amp;hearts;\" />",
                getResponseText());
    }

    public void testEncode_WithId() throws Exception {
        htmlInputSecret.setId("a");

        UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("b");
        parent.getChildren().add(htmlInputSecret);

        encodeByRenderer(renderer, htmlInputSecret);

        assertEquals(
                "<input type=\"password\" id=\"a\" name=\"b:a\" value=\"\" />",
                getResponseText());
    }

    public void testEncode_WithUnknownAttribute() throws Exception {
        htmlInputSecret.setId("a");
        htmlInputSecret.getAttributes().put("foo", "bar");

        encodeByRenderer(renderer, htmlInputSecret);

        assertEquals(
                "<input type=\"password\" id=\"a\" name=\"a\" value=\"\" foo=\"bar\" />",
                getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        htmlInputSecret.setAccesskey("a");
        htmlInputSecret.setAlt("b");
        htmlInputSecret.setDir("c");
        htmlInputSecret.setDisabled(true);
        htmlInputSecret.setLang("e");
        htmlInputSecret.setMaxlength(5);
        htmlInputSecret.setOnblur("g");
        htmlInputSecret.setOnchange("h");
        htmlInputSecret.setOnclick("i");
        htmlInputSecret.setOndblclick("j");
        htmlInputSecret.setOnfocus("k");
        htmlInputSecret.setOnkeydown("l");
        htmlInputSecret.setOnkeypress("m");
        htmlInputSecret.setOnkeyup("n");
        htmlInputSecret.setOnmousedown("o");
        htmlInputSecret.setOnmousemove("p");
        htmlInputSecret.setOnmouseout("q");
        htmlInputSecret.setOnmouseover("r");
        htmlInputSecret.setOnmouseup("s");
        htmlInputSecret.setOnselect("t");
        htmlInputSecret.setReadonly(true);
        htmlInputSecret.setSize(2);
        htmlInputSecret.setStyle("w");
        htmlInputSecret.setStyleClass("u");
        htmlInputSecret.setTabindex("x");
        htmlInputSecret.setTitle("y");

        htmlInputSecret.setId("Aa");
        htmlInputSecret.setValue("Ba");
        htmlInputSecret.setRedisplay(false);

        encodeByRenderer(renderer, htmlInputSecret);

        Diff diff = new Diff(
                "<input type=\"password\" id=\"Aa\" name=\"Aa\" value=\"\""
                        + " accesskey=\"a\"" + " alt=\"b\"" + " dir=\"c\""
                        + " disabled=\"disabled\"" + " lang=\"e\""
                        + " maxlength=\"5\"" + " onblur=\"g\""
                        + " onchange=\"h\"" + " onclick=\"i\""
                        + " ondblclick=\"j\"" + " onfocus=\"k\""
                        + " onkeydown=\"l\"" + " onkeypress=\"m\""
                        + " onkeyup=\"n\"" + " onmousedown=\"o\""
                        + " onmousemove=\"p\"" + " onmouseout=\"q\""
                        + " onmouseover=\"r\"" + " onmouseup=\"s\""
                        + " onselect=\"t\"" + " readonly=\"true\""
                        + " size=\"2\"" + " style=\"w\"" + " class=\"u\""
                        + " tabindex=\"x\"" + " title=\"y\"" + "/>",
                getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testDecode_None() throws Exception {
        // ## Arrange ##
        htmlInputSecret.setClientId("key");

        MockFacesContext context = getFacesContext();

        // ## Act ##
        renderer.decode(context, htmlInputSecret);

        // ## Assert ##
        assertEquals(null, htmlInputSecret.getSubmittedValue());
    }

    public void testDecode_Success() throws Exception {
        // ## Arrange ##
        htmlInputSecret.setClientId("key");

        MockFacesContext context = getFacesContext();
        context.getExternalContext().getRequestParameterMap().put("key",
                "12345");

        // ## Act ##
        renderer.decode(context, htmlInputSecret);

        // ## Assert ##
        assertEquals("12345", htmlInputSecret.getSubmittedValue());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer.getRendersChildren());
    }

    private HtmlInputSecretRenderer createHtmlInputSecretRenderer() {
        return (HtmlInputSecretRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        HtmlInputSecretRenderer renderer = new HtmlInputSecretRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

}
