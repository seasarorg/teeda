/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.internal.FacesConfigOptions;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockExternalContext;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.extension.component.TViewRoot;
import org.seasar.teeda.extension.component.html.THtmlInputCommaText;
import org.seasar.teeda.extension.mock.MockHtmlInputCommaText;
import org.seasar.teeda.extension.util.JavaScriptContext;

/**
 * @author shot
 */
public class THtmlInputCommaTextRendererTest extends RendererTest {

    private MockHtmlInputCommaText htmlInputCommaText;

    private THtmlInputCommaTextRenderer renderer;

    public void setUp() throws Exception {
        super.setUp();
        renderer = createHtmlInputCommaTextRenderer();
        htmlInputCommaText = new MockHtmlInputCommaText();
        htmlInputCommaText.setRenderer(renderer);
    }

    public void testEncodeEnd_withoutJsAndValue() throws Exception {
        TViewRoot root = new TViewRoot();
        root.addScript(THtmlInputCommaText.class.getName(),
                new JavaScriptContext());
        root.setLocale(Locale.JAPAN);
        FacesContext context = getFacesContext();
        context.setViewRoot(root);

        // ## Act ##
        encodeByRenderer(renderer, context, htmlInputCommaText);

        System.out.print(getResponseText());
        // ## Assert ##
        assertEquals(
                "<input type=\"text\" name=\"_id0\" value=\"\" onfocus=\"Teeda.THtmlInputCommaText.removeComma(this, ',');this.select();\" "
                        + "onblur=\"Teeda.THtmlInputCommaText.convertByKey(this);Teeda.THtmlInputCommaText.addComma(this, 0, ',', '.');\" onkeydown=\"return Teeda.THtmlInputCommaText.keycheckForNumber(event, this, 0, '.');\" "
                        + "onkeypress=\"return Teeda.THtmlInputCommaText.keycheckForNumber(event, this, 0, '.');\" onkeyup=\"Teeda.THtmlInputCommaText.convertByKey(this);\" style=\"ime-mode:disabled;\" />",
                getResponseText());
    }

    public void testEncodeEnd_withFraction() throws Exception {
        TViewRoot root = new TViewRoot();
        root.addScript(THtmlInputCommaText.class.getName(),
                new JavaScriptContext());
        htmlInputCommaText.setFraction("4");
        root.setLocale(Locale.JAPAN);
        FacesContext context = getFacesContext();
        context.setViewRoot(root);

        // ## Act ##
        encodeByRenderer(renderer, context, htmlInputCommaText);

        // ## Assert ##
        System.out.println(getResponseText());
        assertEquals(
                "<input type=\"text\" name=\"_id0\" value=\"\" onfocus=\"Teeda.THtmlInputCommaText.removeComma(this, ',');this.select();\" "
                        + "onblur=\"Teeda.THtmlInputCommaText.convertByKey(this);Teeda.THtmlInputCommaText.addComma(this, 4, ',', '.');\" onkeydown=\"return Teeda.THtmlInputCommaText.keycheckForNumber(event, this, 4, '.');\" "
                        + "onkeypress=\"return Teeda.THtmlInputCommaText.keycheckForNumber(event, this, 4, '.');\" onkeyup=\"Teeda.THtmlInputCommaText.convertByKey(this);\" style=\"ime-mode:disabled;\" />",
                getResponseText());
    }

    public void testEncodeEnd_withAnotherLocale() throws Exception {
        TViewRoot root = new TViewRoot();
        root.addScript(THtmlInputCommaText.class.getName(),
                new JavaScriptContext());
        htmlInputCommaText.setFraction("4");
        root.setLocale(Locale.GERMANY);
        FacesContext context = getFacesContext();
        context.setViewRoot(root);

        // ## Act ##
        encodeByRenderer(renderer, context, htmlInputCommaText);

        // ## Assert ##
        assertEquals(
                "<input type=\"text\" name=\"_id0\" value=\"\" onfocus=\"Teeda.THtmlInputCommaText.removeComma(this, '.');this.select();\" "
                        + "onblur=\"Teeda.THtmlInputCommaText.convertByKey(this);Teeda.THtmlInputCommaText.addComma(this, 4, '.', ',');\" onkeydown=\"return Teeda.THtmlInputCommaText.keycheckForNumber(event, this, 4, ',');\" "
                        + "onkeypress=\"return Teeda.THtmlInputCommaText.keycheckForNumber(event, this, 4, ',');\" onkeyup=\"Teeda.THtmlInputCommaText.convertByKey(this);\" style=\"ime-mode:disabled;\" />",
                getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        TViewRoot root = new TViewRoot();
        root.addScript(THtmlInputCommaText.class.getName(),
                new JavaScriptContext());
        htmlInputCommaText.getAttributes().put("accesskey", "a");
        htmlInputCommaText.getAttributes().put("alt", "b");
        htmlInputCommaText.getAttributes().put("dir", "c");
        htmlInputCommaText.getAttributes().put("disabled", Boolean.TRUE);
        htmlInputCommaText.getAttributes().put("lang", "e");
        htmlInputCommaText.getAttributes().put("maxlength", new Integer(5));
        htmlInputCommaText.getAttributes().put("onblur", "g");
        htmlInputCommaText.getAttributes().put("onchange", "h");
        htmlInputCommaText.getAttributes().put("onclick", "i");
        htmlInputCommaText.getAttributes().put("ondblclick", "j");
        htmlInputCommaText.getAttributes().put("onfocus", "k");
        htmlInputCommaText.getAttributes().put("onkeydown", "l");
        htmlInputCommaText.getAttributes().put("onkeypress", "m");
        htmlInputCommaText.getAttributes().put("onkeyup", "n");
        htmlInputCommaText.getAttributes().put("onmousedown", "o");
        htmlInputCommaText.getAttributes().put("onmousemove", "p");
        htmlInputCommaText.getAttributes().put("onmouseout", "q");
        htmlInputCommaText.getAttributes().put("onmouseover", "r");
        htmlInputCommaText.getAttributes().put("onmouseup", "s");
        htmlInputCommaText.getAttributes().put("onselect", "t");
        htmlInputCommaText.getAttributes().put("readonly", Boolean.TRUE);
        htmlInputCommaText.getAttributes().put("size", new Integer(2));
        htmlInputCommaText.getAttributes().put("style", "w");
        htmlInputCommaText.getAttributes().put("styleClass", "u");
        htmlInputCommaText.getAttributes().put("tabindex", "x");
        htmlInputCommaText.getAttributes().put("title", "y");
        htmlInputCommaText.getAttributes().put("id", "A");
        htmlInputCommaText.getAttributes().put("value", "123");
        htmlInputCommaText.getAttributes().put("fraction", "4");

        MockFacesContext context = getFacesContext();
        root.setLocale(Locale.JAPAN);
        context.setViewRoot(root);

        encodeByRenderer(renderer, context, htmlInputCommaText);

        Diff diff = new Diff(
                "<input type=\"text\" id=\"A\" name=\"A\" value=\"123\" disabled=\"disabled\" "
                        + "onfocus=\"k;Teeda.THtmlInputCommaText.removeComma(this, ',');this.select();\" "
                        + "onblur=\"g;Teeda.THtmlInputCommaText.convertByKey(this);Teeda.THtmlInputCommaText.addComma(this, 4, ',', '.');\" "
                        + "onkeydown=\"l;return Teeda.THtmlInputCommaText.keycheckForNumber(event, this, 4, '.');\" "
                        + "onkeypress=\"m;return Teeda.THtmlInputCommaText.keycheckForNumber(event, this, 4, '.');\" onkeyup=\"n;Teeda.THtmlInputCommaText.convertByKey(this);\" "
                        + "style=\"w;ime-mode:disabled;\" title=\"y\" onchange=\"h\" dir=\"c\" readonly=\"readonly\" "
                        + "class=\"u\" accesskey=\"a\" ondblclick=\"j\" size=\"2\" onmouseover=\"r\" "
                        + "tabindex=\"x\" maxlength=\"5\" lang=\"e\" onclick=\"i\" alt=\"b\" "
                        + "onmouseout=\"q\" onmousedown=\"o\" onselect=\"t\" onmouseup=\"s\" "
                        + "onmousemove=\"p\" />", getResponseText());

        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testEncodeEnd_withAnotherOnblur() throws Exception {
        TViewRoot root = new TViewRoot();
        root.addScript(THtmlInputCommaText.class.getName(),
                new JavaScriptContext());
        htmlInputCommaText.setFraction("4");
        htmlInputCommaText.setOnblur("hoge();");
        root.setLocale(Locale.JAPAN);
        FacesContext context = getFacesContext();
        context.setViewRoot(root);

        // ## Act ##
        encodeByRenderer(renderer, context, htmlInputCommaText);

        // ## Assert ##
        assertEquals(
                "<input type=\"text\" name=\"_id0\" value=\"\" onfocus=\"Teeda.THtmlInputCommaText.removeComma(this, ',');this.select();\" "
                        + "onblur=\"hoge();Teeda.THtmlInputCommaText.convertByKey(this);Teeda.THtmlInputCommaText.addComma(this, 4, ',', '.');\" onkeydown=\"return Teeda.THtmlInputCommaText.keycheckForNumber(event, this, 4, '.');\" "
                        + "onkeypress=\"return Teeda.THtmlInputCommaText.keycheckForNumber(event, this, 4, '.');\" onkeyup=\"Teeda.THtmlInputCommaText.convertByKey(this);\" style=\"ime-mode:disabled;\" />",
                getResponseText());
    }

    public void testEncodeEnd_withJavaScript() throws Exception {
        TViewRoot root = new TViewRoot();
        htmlInputCommaText.setFraction("4");
        htmlInputCommaText.setOnblur("hoge();");
        root.setLocale(Locale.JAPAN);
        FacesContext context = getFacesContext();
        context.setViewRoot(root);

        // ## Act ##
        encodeByRenderer(renderer, context, htmlInputCommaText);

        // ## Assert ##
        System.out.println(getResponseText());
    }

    public void testEncodeEnd_withoutJavaScript() throws Exception {
        // # Arrange #
        TViewRoot root = new TViewRoot();
        htmlInputCommaText.setFraction("4");
        htmlInputCommaText.setOnblur("hoge();");
        root.setLocale(Locale.JAPAN);
        FacesContext context = getFacesContext();
        MockExternalContext extContext = (MockExternalContext) context
                .getExternalContext();
        extContext.setRequestPathInfo("/path1/hoge");
        FacesConfigOptions
                .setJavascriptNotPermittedPath(new String[] { "/not_path1" });
        context.setViewRoot(root);

        // ## Act ##
        encodeByRenderer(renderer, context, htmlInputCommaText);

        // ## Assert ##
        assertFalse(getResponseText().matches(
                "Teeda.THtmlInputCommaText.removeComma(this);"));
    }

    public void testEncodeEnd_withErrorStyle() throws Exception {
        TViewRoot root = new TViewRoot();
        root.addScript(THtmlInputCommaText.class.getName(),
                new JavaScriptContext());
        root.setLocale(Locale.JAPAN);
        FacesContext context = getFacesContext();
        context.setViewRoot(root);
        htmlInputCommaText.setClientId("aaa");
        htmlInputCommaText.setErrorStyleClass("hoge");
        context.addMessage("aaa", new FacesMessage("bbb"));

        // ## Act ##
        encodeByRenderer(renderer, context, htmlInputCommaText);

        System.out.print(getResponseText());
        // ## Assert ##
        assertEquals(
                "<input type=\"text\" name=\"aaa\" value=\"\" onfocus=\"Teeda.THtmlInputCommaText.removeComma(this, ',');this.select();\" "
                        + "onblur=\"Teeda.THtmlInputCommaText.convertByKey(this);Teeda.THtmlInputCommaText.addComma(this, 0, ',', '.');\" onkeydown=\"return Teeda.THtmlInputCommaText.keycheckForNumber(event, this, 0, '.');\" "
                        + "onkeypress=\"return Teeda.THtmlInputCommaText.keycheckForNumber(event, this, 0, '.');\" onkeyup=\"Teeda.THtmlInputCommaText.convertByKey(this);\" style=\"ime-mode:disabled;\" class=\"hoge\" />",
                getResponseText());
    }

    public void testEncodeValueIsMinus() throws Exception {
        TViewRoot root = new TViewRoot();
        root.addScript(THtmlInputCommaText.class.getName(),
                new JavaScriptContext());
        htmlInputCommaText.setFraction("4");
        htmlInputCommaText.setValue(new Double(-0.1));
        root.setLocale(Locale.JAPAN);
        FacesContext context = getFacesContext();
        context.setViewRoot(root);

        // ## Act ##
        encodeByRenderer(renderer, context, htmlInputCommaText);

        // ## Assert ##
        System.out.println(getResponseText());
        assertEquals(
                "<input type=\"text\" name=\"_id0\" value=\"-0.1\" onfocus=\"Teeda.THtmlInputCommaText.removeComma(this, ',');this.select();\" "
                        + "onblur=\"Teeda.THtmlInputCommaText.convertByKey(this);Teeda.THtmlInputCommaText.addComma(this, 4, ',', '.');\" onkeydown=\"return Teeda.THtmlInputCommaText.keycheckForNumber(event, this, 4, '.');\" "
                        + "onkeypress=\"return Teeda.THtmlInputCommaText.keycheckForNumber(event, this, 4, '.');\" onkeyup=\"Teeda.THtmlInputCommaText.convertByKey(this);\" style=\"ime-mode:disabled;\" />",
                getResponseText());
    }

    public void testEncodeIllegalValue_TEEDA465() throws Exception {
        TViewRoot root = new TViewRoot();
        root.addScript(THtmlInputCommaText.class.getName(),
                new JavaScriptContext());
        htmlInputCommaText.setFraction("4");
        htmlInputCommaText.setSubmittedValue("..");
        root.setLocale(Locale.JAPAN);
        FacesContext context = getFacesContext();
        context.setViewRoot(root);

        // ## Act ##
        encodeByRenderer(renderer, context, htmlInputCommaText);

        // ## Assert ##
        System.out.println(getResponseText());
        assertEquals(
                "<input type=\"text\" name=\"_id0\" value=\"..\" onfocus=\"Teeda.THtmlInputCommaText.removeComma(this, ',');this.select();\" "
                        + "onblur=\"Teeda.THtmlInputCommaText.convertByKey(this);Teeda.THtmlInputCommaText.addComma(this, 4, ',', '.');\" onkeydown=\"return Teeda.THtmlInputCommaText.keycheckForNumber(event, this, 4, '.');\" "
                        + "onkeypress=\"return Teeda.THtmlInputCommaText.keycheckForNumber(event, this, 4, '.');\" onkeyup=\"Teeda.THtmlInputCommaText.convertByKey(this);\" style=\"ime-mode:disabled;\" />",
                getResponseText());
    }

    private THtmlInputCommaTextRenderer createHtmlInputCommaTextRenderer() {
        return (THtmlInputCommaTextRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        THtmlInputCommaTextRenderer renderer = new THtmlInputCommaTextRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

}
