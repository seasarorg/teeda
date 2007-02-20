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
package org.seasar.teeda.extension.render.html;

import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.extension.component.TViewRoot;
import org.seasar.teeda.extension.component.html.THtmlInputDateText;
import org.seasar.teeda.extension.mock.MockHtmlInputDateText;
import org.seasar.teeda.extension.util.JavaScriptContext;

/**
 * @author shot
 */
public class THtmlInputDateTextRendererTest extends RendererTest {

    private MockHtmlInputDateText htmlInputDateText;

    private THtmlInputDateTextRenderer renderer;

    private Locale defaultLocale;

    public void setUp() throws Exception {
        super.setUp();
        defaultLocale = Locale.getDefault();
        renderer = createHtmlInputDateTextRenderer();
        htmlInputDateText = new MockHtmlInputDateText();
        htmlInputDateText.setRenderer(renderer);
    }

    protected void tearDown() throws Exception {
        Locale.setDefault(defaultLocale);
        super.tearDown();
    }

    public void testEncodeEnd_withoutJsAndValue() throws Exception {
        Locale.setDefault(Locale.JAPANESE);
        TViewRoot root = new TViewRoot();
        root.addScript(THtmlInputDateText.class.getName(),
                new JavaScriptContext());
        root.setLocale(Locale.getDefault());
        FacesContext context = getFacesContext();
        context.setViewRoot(root);

        // ## Act ##
        encodeByRenderer(renderer, context, htmlInputDateText);

        System.out.print(getResponseText());
        // ## Assert ##
        assertEquals(
                "<input type=\"text\" name=\"_id0\" value=\"\" "
                        + "onfocus=\"Teeda.THtmlInputDateText.removeDelimeter(this, '/', 6);\" "
                        + "onblur=\"Teeda.THtmlInputDateText.convertByKey(this);Teeda.THtmlInputDateText.addDelimeter(this, 'yyyy/MM/dd', 6, 71, '/');\" "
                        + "onkeydown=\"return Teeda.THtmlInputDateText.keycheckForNumber(event, this, 'yyyy/MM/dd', 6, 71, '/');\" "
                        + "onkeypress=\"return Teeda.THtmlInputDateText.keycheckForNumber(event, this, 'yyyy/MM/dd', 6, 71, '/');\" "
                        + "onkeyup=\"Teeda.THtmlInputDateText.convertByKey(this);Teeda.THtmlInputDateText.addDelimeter(this, 'yyyy/MM/dd', 6, 71, '/');\" "
                        + "style=\"ime-mode:disabled;\" />", getResponseText());
    }

    public void testEncodeEnd_withPattern() throws Exception {
        TViewRoot root = new TViewRoot();
        root.addScript(THtmlInputDateText.class.getName(),
                new JavaScriptContext());
        htmlInputDateText.setPattern("yyyy/MM");
        root.setLocale(Locale.JAPAN);
        FacesContext context = getFacesContext();
        context.setViewRoot(root);

        // ## Act ##
        encodeByRenderer(renderer, context, htmlInputDateText);

        // ## Assert ##
        System.out.println(getResponseText());
        assertEquals(
                "<input type=\"text\" name=\"_id0\" value=\"\" "
                        + "onfocus=\"Teeda.THtmlInputDateText.removeDelimeter(this, '/', 4);\" "
                        + "onblur=\"Teeda.THtmlInputDateText.convertByKey(this);Teeda.THtmlInputDateText.addDelimeter(this, 'yyyy/MM', 4, 71, '/');\" "
                        + "onkeydown=\"return Teeda.THtmlInputDateText.keycheckForNumber(event, this, 'yyyy/MM', 4, 71, '/');\" "
                        + "onkeypress=\"return Teeda.THtmlInputDateText.keycheckForNumber(event, this, 'yyyy/MM', 4, 71, '/');\" "
                        + "onkeyup=\"Teeda.THtmlInputDateText.convertByKey(this);Teeda.THtmlInputDateText.addDelimeter(this, 'yyyy/MM', 4, 71, '/');\" "
                        + "style=\"ime-mode:disabled;\" />", getResponseText());
    }

    public void testEncodeEnd_withAnotherLocale() throws Exception {
        TViewRoot root = new TViewRoot();
        root.addScript(THtmlInputDateText.class.getName(),
                new JavaScriptContext());
        root.setLocale(Locale.GERMANY);
        FacesContext context = getFacesContext();
        context.setViewRoot(root);

        // ## Act ##
        encodeByRenderer(renderer, context, htmlInputDateText);

        System.out.println(getResponseText());
        // ## Assert ##
        assertEquals(
                "<input type=\"text\" name=\"_id0\" value=\"\" "
                        + "onfocus=\"Teeda.THtmlInputDateText.removeDelimeter(this, '.', 6);\" "
                        + "onblur=\"Teeda.THtmlInputDateText.convertByKey(this);Teeda.THtmlInputDateText.addDelimeter(this, 'dd.MM.yyyy', 6, 71, '.');\" "
                        + "onkeydown=\"return Teeda.THtmlInputDateText.keycheckForNumber(event, this, 'dd.MM.yyyy', 6, 71, '.');\" "
                        + "onkeypress=\"return Teeda.THtmlInputDateText.keycheckForNumber(event, this, 'dd.MM.yyyy', 6, 71, '.');\" "
                        + "onkeyup=\"Teeda.THtmlInputDateText.convertByKey(this);Teeda.THtmlInputDateText.addDelimeter(this, 'dd.MM.yyyy', 6, 71, '.');\" "
                        + "style=\"ime-mode:disabled;\" />", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        TViewRoot root = new TViewRoot();
        root.addScript(THtmlInputDateText.class.getName(),
                new JavaScriptContext());
        htmlInputDateText.getAttributes().put("accesskey", "a");
        htmlInputDateText.getAttributes().put("alt", "b");
        htmlInputDateText.getAttributes().put("dir", "c");
        htmlInputDateText.getAttributes().put("disabled", Boolean.TRUE);
        htmlInputDateText.getAttributes().put("lang", "e");
        htmlInputDateText.getAttributes().put("maxlength", new Integer(5));
        htmlInputDateText.getAttributes().put("onblur", "g");
        htmlInputDateText.getAttributes().put("onchange", "h");
        htmlInputDateText.getAttributes().put("onclick", "i");
        htmlInputDateText.getAttributes().put("ondblclick", "j");
        htmlInputDateText.getAttributes().put("onfocus", "k");
        htmlInputDateText.getAttributes().put("onkeydown", "l");
        htmlInputDateText.getAttributes().put("onkeypress", "m");
        htmlInputDateText.getAttributes().put("onkeyup", "n");
        htmlInputDateText.getAttributes().put("onmousedown", "o");
        htmlInputDateText.getAttributes().put("onmousemove", "p");
        htmlInputDateText.getAttributes().put("onmouseout", "q");
        htmlInputDateText.getAttributes().put("onmouseover", "r");
        htmlInputDateText.getAttributes().put("onmouseup", "s");
        htmlInputDateText.getAttributes().put("onselect", "t");
        htmlInputDateText.getAttributes().put("readonly", Boolean.TRUE);
        htmlInputDateText.getAttributes().put("size", new Integer(2));
        htmlInputDateText.getAttributes().put("style", "w");
        htmlInputDateText.getAttributes().put("styleClass", "u");
        htmlInputDateText.getAttributes().put("tabindex", "x");
        htmlInputDateText.getAttributes().put("title", "y");
        htmlInputDateText.getAttributes().put("id", "A");
        htmlInputDateText.getAttributes().put("value", "123");

        MockFacesContext context = getFacesContext();
        root.setLocale(Locale.JAPAN);
        context.setViewRoot(root);

        encodeByRenderer(renderer, context, htmlInputDateText);

        System.out.println(getResponseText());
    }

    public void testEncodeEnd_withAnotherOnblur() throws Exception {
        TViewRoot root = new TViewRoot();
        root.addScript(THtmlInputDateText.class.getName(),
                new JavaScriptContext());
        htmlInputDateText.setPattern("yyyy/MM");
        htmlInputDateText.setLength("6");
        htmlInputDateText.setThreshold("10");
        FacesContext context = getFacesContext();
        context.setViewRoot(root);

        // ## Act ##
        encodeByRenderer(renderer, context, htmlInputDateText);

        System.out.println(getResponseText());

        // ## Assert ##
        assertEquals(
                "<input type=\"text\" name=\"_id0\" value=\"\" "
                        + "onfocus=\"Teeda.THtmlInputDateText.removeDelimeter(this, '/', 6);\" "
                        + "onblur=\"Teeda.THtmlInputDateText.convertByKey(this);Teeda.THtmlInputDateText.addDelimeter(this, 'yyyy/MM', 6, 10, '/');\" "
                        + "onkeydown=\"return Teeda.THtmlInputDateText.keycheckForNumber(event, this, 'yyyy/MM', 6, 10, '/');\" "
                        + "onkeypress=\"return Teeda.THtmlInputDateText.keycheckForNumber(event, this, 'yyyy/MM', 6, 10, '/');\" "
                        + "onkeyup=\"Teeda.THtmlInputDateText.convertByKey(this);Teeda.THtmlInputDateText.addDelimeter(this, 'yyyy/MM', 6, 10, '/');\" "
                        + "style=\"ime-mode:disabled;\" />", getResponseText());
    }

    private THtmlInputDateTextRenderer createHtmlInputDateTextRenderer() {
        return (THtmlInputDateTextRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        THtmlInputDateTextRenderer renderer = new THtmlInputDateTextRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

}
