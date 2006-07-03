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

import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.internal.FacesConfigOptions;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockExternalContext;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.extension.component.ScriptEnhanceUIViewRoot;
import org.seasar.teeda.extension.component.html.THtmlInputCommaText;
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
        ScriptEnhanceUIViewRoot root = new ScriptEnhanceUIViewRoot();
        root.addScript(htmlInputCommaText.getClass().getName(),
                new JavaScriptContext());
        root.setLocale(Locale.JAPAN);
        FacesContext context = getFacesContext();
        context.setViewRoot(root);

        // ## Act ##
        encodeByRenderer(renderer, context, htmlInputCommaText);

        // ## Assert ##
        assertEquals(
                "<input type=\"text\" name=\"_id0\" value=\"\" onfocus=\"removeComma(this);\" " +
                "onblur=\"convertByKey(this);addComma(this, '0', ',', '.');\" onkeydown=\"return keycheckForNumber(event);\" " +
                "onkeypress=\"return keycheckForNumber(event);\" onkeyup=\"convertByKey(this);\" style=\"ime-mode:disabled;\" />",
                getResponseText());
    }

    public void testEncodeEnd_withFraction() throws Exception {
        ScriptEnhanceUIViewRoot root = new ScriptEnhanceUIViewRoot();
        root.addScript(htmlInputCommaText.getClass().getName(),
                new JavaScriptContext());
        htmlInputCommaText.setFraction("4");
        root.setLocale(Locale.JAPAN);
        FacesContext context = getFacesContext();
        context.setViewRoot(root);

        // ## Act ##
        encodeByRenderer(renderer, context, htmlInputCommaText);

        // ## Assert ##
        assertEquals(
                "<input type=\"text\" name=\"_id0\" value=\"\" onfocus=\"removeComma(this);\" " +
                "onblur=\"convertByKey(this);addComma(this, '4', ',', '.');\" onkeydown=\"return keycheckForNumber(event);\" " +
                "onkeypress=\"return keycheckForNumber(event);\" onkeyup=\"convertByKey(this);\" style=\"ime-mode:disabled;\" />",
                getResponseText());
    }

    public void testEncodeEnd_withAnotherLocale() throws Exception {
        ScriptEnhanceUIViewRoot root = new ScriptEnhanceUIViewRoot();
        root.addScript(htmlInputCommaText.getClass().getName(),
                new JavaScriptContext());
        htmlInputCommaText.setFraction("4");
        root.setLocale(Locale.GERMANY);
        FacesContext context = getFacesContext();
        context.setViewRoot(root);

        // ## Act ##
        encodeByRenderer(renderer, context, htmlInputCommaText);

        // ## Assert ##
        assertEquals(
                "<input type=\"text\" name=\"_id0\" value=\"\" onfocus=\"removeComma(this);\" " +
                "onblur=\"convertByKey(this);addComma(this, '4', '.', ',');\" onkeydown=\"return keycheckForNumber(event);\" " +
                "onkeypress=\"return keycheckForNumber(event);\" onkeyup=\"convertByKey(this);\" style=\"ime-mode:disabled;\" />",
                getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        ScriptEnhanceUIViewRoot root = new ScriptEnhanceUIViewRoot();
        root.addScript(htmlInputCommaText.getClass().getName(),
                new JavaScriptContext());
        htmlInputCommaText.getAttributes().put("accesskey", "a");
        htmlInputCommaText.getAttributes().put("alt", "b");
        htmlInputCommaText.getAttributes().put("dir","c");
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
        htmlInputCommaText.getAttributes().put("readonly" , Boolean.TRUE);
        htmlInputCommaText.getAttributes().put("size", new Integer(2));
        htmlInputCommaText.getAttributes().put("style", "w");
        htmlInputCommaText.getAttributes().put("styleClass", "u");
        htmlInputCommaText.getAttributes().put("tabindex", "x");
        htmlInputCommaText.getAttributes().put("title", "y");
        htmlInputCommaText.getAttributes().put("id", "A");
        htmlInputCommaText.getAttributes().put("value", "B");
        htmlInputCommaText.getAttributes().put("fraction", "4");

        MockFacesContext context = getFacesContext();
        root.setLocale(Locale.JAPAN);
        context.setViewRoot(root);

        encodeByRenderer(renderer, context, htmlInputCommaText);

        Diff diff = new Diff(
                "<input type=\"text\" id=\"A\" name=\"A\" value=\"B\" disabled=\"disabled\" " +
                "onfocus=\"k;removeComma(this);\" " +
                "onblur=\"g;convertByKey(this);addComma(this, '4', ',', '.');\" " +
                "onkeydown=\"l;return keycheckForNumber(event);\" " +
                "onkeypress=\"m;return keycheckForNumber(event);\" onkeyup=\"n;convertByKey(this);\" " +
                "style=\"w;ime-mode:disabled;\" title=\"y\" onchange=\"h\" dir=\"c\" readonly=\"true\" " +
                "class=\"u\" accesskey=\"a\" ondblclick=\"j\" size=\"2\" onmouseover=\"r\" " +
                "tabindex=\"x\" maxlength=\"5\" lang=\"e\" onclick=\"i\" alt=\"b\" " +
                "onmouseout=\"q\" onmousedown=\"o\" label=\"A\" onselect=\"t\" onmouseup=\"s\" " +
                "onmousemove=\"p\" />",
                getResponseText());

        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testEncodeEnd_withAnotherOnblur() throws Exception {
        ScriptEnhanceUIViewRoot root = new ScriptEnhanceUIViewRoot();
        root.addScript(htmlInputCommaText.getClass().getName(),
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
                "<input type=\"text\" name=\"_id0\" value=\"\" onfocus=\"removeComma(this);\" " +
                "onblur=\"hoge();convertByKey(this);addComma(this, '4', ',', '.');\" onkeydown=\"return keycheckForNumber(event);\" " +
                "onkeypress=\"return keycheckForNumber(event);\" onkeyup=\"convertByKey(this);\" style=\"ime-mode:disabled;\" />",
                getResponseText());
    }

    public void testEncodeEnd_withJavaScript() throws Exception {
        ScriptEnhanceUIViewRoot root = new ScriptEnhanceUIViewRoot();
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
        ScriptEnhanceUIViewRoot root = new ScriptEnhanceUIViewRoot();
        htmlInputCommaText.setFraction("4");
        htmlInputCommaText.setOnblur("hoge();");
        root.setLocale(Locale.JAPAN);
        FacesContext context = getFacesContext();
        MockExternalContext extContext = (MockExternalContext) context.getExternalContext();
        extContext.setRequestPathInfo("/path1/hoge");
        FacesConfigOptions.setJavascriptNotPermittedPath(new String[]{"/not_path1"});
        context.setViewRoot(root);

        // ## Act ##
        encodeByRenderer(renderer, context, htmlInputCommaText);

        // ## Assert ##
        assertFalse(getResponseText().matches("removeComma(this);"));
    }

    private THtmlInputCommaTextRenderer createHtmlInputCommaTextRenderer() {
        return (THtmlInputCommaTextRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        THtmlInputCommaTextRenderer renderer = new THtmlInputCommaTextRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

    private static class MockHtmlInputCommaText extends THtmlInputCommaText {

        private Renderer renderer;

        public Renderer getRenderer(FacesContext context) {
            if (this.renderer != null) {
                return this.renderer;
            }
            return super.getRenderer(context);
        }

        public void setRenderer(Renderer renderer) {
            this.renderer = renderer;
        }

    }

}
