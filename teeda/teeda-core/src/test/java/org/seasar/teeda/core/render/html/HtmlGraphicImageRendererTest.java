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

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockApplicationImpl;
import org.seasar.teeda.core.mock.MockExternalContext;
import org.seasar.teeda.core.mock.MockExternalContextWrapper;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockUIComponentBaseWithNamingContainer;
import org.seasar.teeda.core.mock.MockViewHandlerImpl;

/**
 * @author manhole
 */
public class HtmlGraphicImageRendererTest extends RendererTest {

    private HtmlGraphicImageRenderer renderer_;

    private MockHtmlGraphicImage htmlGraphicImage_;

    protected void setUp() throws Exception {
        super.setUp();
        renderer_ = createHtmlGraphicImageRenderer();
        htmlGraphicImage_ = new MockHtmlGraphicImage();
        htmlGraphicImage_.setRenderer(renderer_);
    }

    public void testEncode_WithNoValue() throws Exception {
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlGraphicImage_);

        // ## Assert ##
        assertEquals("<img src=\"\" />", getResponseText());
    }

    public void testEncode_RenderFalse() throws Exception {
        // ## Arrange ##
        htmlGraphicImage_.setRendered(false);
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlGraphicImage_);

        // ## Assert ##
        assertEquals("", getResponseText());
    }

    public void testEncode_WithValue() throws Exception {
        // ## Arrange ##
        htmlGraphicImage_.setValue("abc");
        MockFacesContext context = getFacesContext();

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlGraphicImage_);

        // ## Assert ##
        assertEquals("<img src=\"abc\" />", getResponseText());
    }

    public void testEncode_WithId() throws Exception {
        htmlGraphicImage_.setId("a");

        UIComponent parent = new MockUIComponentBaseWithNamingContainer();
        parent.setId("b");
        parent.getChildren().add(htmlGraphicImage_);

        encodeByRenderer(renderer_, getFacesContext(), htmlGraphicImage_);

        assertEquals("<img id=\"a\" src=\"\" />", getResponseText());
    }

    public void testEncode_UrlEncode() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        final ExternalContext externalContext = new MockExternalContextWrapper(
                (MockExternalContext) context.getExternalContext()) {
            public String encodeResourceURL(String url) {
                return url + "_2";
            }
        };

        final MockApplication application = new MockApplicationImpl() {
            public ViewHandler getViewHandler() {
                return new MockViewHandlerImpl() {
                    public String getResourceURL(FacesContext context,
                            String path) {
                        return path + "_1";
                    }
                };
            }
        };
        context.setApplication(application);
        context.setExternalContext(externalContext);

        htmlGraphicImage_.setValue("abc");

        // ## Act ##
        encodeByRenderer(renderer_, context, htmlGraphicImage_);

        // ## Assert ##
        assertEquals("<img src=\"abc_1_2\" />", getResponseText());
    }

    public void testEncode_WithAllAttributes() throws Exception {
        MockApplication application = new MockApplicationImpl();
        application.setViewHandler(new MockViewHandlerImpl());
        MockFacesContext context = getFacesContext();
        context.setApplication(application);

        htmlGraphicImage_.setAlt("a");
        htmlGraphicImage_.setDir("b");
        htmlGraphicImage_.setHeight("c");
        htmlGraphicImage_.setIsmap(true);
        htmlGraphicImage_.setLang("e");
        htmlGraphicImage_.setLongdesc("f");
        htmlGraphicImage_.setOnclick("g");
        htmlGraphicImage_.setOndblclick("h");
        htmlGraphicImage_.setOnkeydown("i");
        htmlGraphicImage_.setOnkeypress("j");
        htmlGraphicImage_.setOnkeyup("k");
        htmlGraphicImage_.setOnmousedown("l");
        htmlGraphicImage_.setOnmousemove("m");
        htmlGraphicImage_.setOnmouseout("n");
        htmlGraphicImage_.setOnmouseover("o");
        htmlGraphicImage_.setOnmouseup("p");
        htmlGraphicImage_.setStyle("q");
        htmlGraphicImage_.setStyleClass("r");
        htmlGraphicImage_.setTitle("s");
        htmlGraphicImage_.setUsemap("t");
        htmlGraphicImage_.setWidth("u");

        htmlGraphicImage_.setId("A");
        htmlGraphicImage_.setValue("B");

        encodeByRenderer(renderer_, context, htmlGraphicImage_);

        Diff diff = new Diff("<img id=\"A\" src=\"B\"" + " alt=\"a\""
                + " dir=\"b\"" + " height=\"c\"" + " ismap=\"true\""
                + " lang=\"e\"" + " longdesc=\"f\"" + " onclick=\"g\""
                + " ondblclick=\"h\"" + " onkeydown=\"i\""
                + " onkeypress=\"j\"" + " onkeyup=\"k\"" + " onmousedown=\"l\""
                + " onmousemove=\"m\"" + " onmouseout=\"n\""
                + " onmouseover=\"o\"" + " onmouseup=\"p\"" + " style=\"q\""
                + " class=\"r\"" + " title=\"s\"" + " usemap=\"t\""
                + " width=\"u\"" + "/>", getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testGetRendersChildren() throws Exception {
        assertEquals(false, renderer_.getRendersChildren());
    }

    private HtmlGraphicImageRenderer createHtmlGraphicImageRenderer() {
        return (HtmlGraphicImageRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        return new HtmlGraphicImageRenderer();
    }

    private static class MockHtmlGraphicImage extends HtmlGraphicImage {
        private Renderer renderer_;

        private String clientId_;

        public void setRenderer(Renderer renderer) {
            renderer_ = renderer;
        }

        protected Renderer getRenderer(FacesContext context) {
            if (renderer_ != null) {
                return renderer_;
            }
            return super.getRenderer(context);
        }

        public String getClientId(FacesContext context) {
            if (clientId_ != null) {
                return clientId_;
            }
            return super.getClientId(context);
        }

        public void setClientId(String clientId) {
            clientId_ = clientId;
        }
    }

}
