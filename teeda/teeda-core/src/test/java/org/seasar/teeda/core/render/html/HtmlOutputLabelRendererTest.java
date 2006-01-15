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
package org.seasar.teeda.core.render.html;

import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;

/**
 * @author manhole
 */
public class HtmlOutputLabelRendererTest extends RendererTest {

    public void testEncodeBegin() throws Exception {
        HtmlOutputLabel label = new HtmlOutputLabel();
        HtmlOutputLabelRenderer renderer = createHtmlOutputLabelRenderer();
        renderer.encodeBegin(getFacesContext(), label);

        assertEquals("<label>", getResponseText());
    }

    public void testEncodeBeginAndEnd() throws Exception {
        HtmlOutputLabel htmlOutputLabel = new HtmlOutputLabel();
        HtmlOutputLabelRenderer renderer = createHtmlOutputLabelRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLabel);
        renderer.encodeEnd(getFacesContext(), htmlOutputLabel);

        assertEquals("<label></label>", getResponseText());
    }

    public void testEncodeBegin_WithValue() throws Exception {
        HtmlOutputLabel htmlOutputLabel = new HtmlOutputLabel();
        htmlOutputLabel.setValue("aaa");
        HtmlOutputLabelRenderer renderer = createHtmlOutputLabelRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLabel);

        assertEquals("<label>aaa", getResponseText());
    }

    public void testEncodeBegin_WithId() throws Exception {
        HtmlOutputLabel htmlOutputLabel = new HtmlOutputLabel();
        htmlOutputLabel.setId("someId");
        HtmlOutputLabelRenderer renderer = createHtmlOutputLabelRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLabel);

        assertEquals("<label id=\"someId\">", getResponseText());
    }

    public void testEncodeBegin_WithFor() throws Exception {
        HtmlOutputLabel htmlOutputLabel = new HtmlOutputLabel();
        htmlOutputLabel.setFor("bb");
        HtmlOutputLabelRenderer renderer = createHtmlOutputLabelRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLabel);

        assertEquals("<label for=\"bb\">", getResponseText());
    }

    public void testEncodeBegin_WithAllAttributes() throws Exception {
        HtmlOutputLabel htmlOutputLabel = new HtmlOutputLabel();
        htmlOutputLabel.setAccesskey("a");
        htmlOutputLabel.setDir("b");
        htmlOutputLabel.setFor("c");
        htmlOutputLabel.setLang("d");
        htmlOutputLabel.setOnblur("e");
        htmlOutputLabel.setOnclick("f");
        htmlOutputLabel.setOndblclick("g");
        htmlOutputLabel.setOnfocus("h");
        htmlOutputLabel.setOnkeydown("i");
        htmlOutputLabel.setOnkeypress("j");
        htmlOutputLabel.setOnkeyup("k");
        htmlOutputLabel.setOnmousedown("l");
        htmlOutputLabel.setOnmousemove("m");
        htmlOutputLabel.setOnmouseout("n");
        htmlOutputLabel.setOnmouseover("o");
        htmlOutputLabel.setOnmouseup("p");
        htmlOutputLabel.setStyle("q");
        htmlOutputLabel.setStyleClass("r");
        htmlOutputLabel.setTabindex("s");
        htmlOutputLabel.setTitle("t");

        htmlOutputLabel.setValue("u");

        HtmlOutputLabelRenderer renderer = createHtmlOutputLabelRenderer();

        renderer.encodeBegin(getFacesContext(), htmlOutputLabel);
        renderer.encodeEnd(getFacesContext(), htmlOutputLabel);

        Diff diff = new Diff("<label" + " accesskey=\"a\"" + " dir=\"b\""
                + " for=\"c\"" + " lang=\"d\"" + " onblur=\"e\""
                + " onclick=\"f\"" + " ondblclick=\"g\"" + " onfocus=\"h\""
                + " onkeydown=\"i\"" + " onkeypress=\"j\"" + " onkeyup=\"k\""
                + " onmousedown=\"l\"" + " onmousemove=\"m\""
                + " onmouseout=\"n\"" + " onmouseover=\"o\""
                + " onmouseup=\"p\"" + " style=\"q\"" + " class=\"r\""
                + " tabindex=\"s\"" + " title=\"t\">u</label>",
                getResponseText());
        System.out.println(getResponseText());
        assertEquals(diff.toString(), true, diff.identical());

    }

    private HtmlOutputLabelRenderer createHtmlOutputLabelRenderer() {
        return (HtmlOutputLabelRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlOutputLabelRenderer();
    }

}
