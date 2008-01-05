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

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.seasar.framework.util.ResourceUtil;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockHtmlForm;
import org.seasar.teeda.core.mock.MockHtmlOutputLink;
import org.seasar.teeda.core.render.html.HtmlFormRenderer;
import org.seasar.teeda.extension.html.impl.HtmlSuffixImpl;

/**
 * @author shot
 */
public class THtmlOutputLinkRendererTest extends RendererTest {

    private THtmlOutputLinkRenderer renderer;

    private MockHtmlOutputLink htmlOutputLink;

    protected void setUp() throws Exception {
        super.setUp();
        renderer = createTHtmlOutputLinkRenderer();
        htmlOutputLink = new MockHtmlOutputLink();
        htmlOutputLink.setRenderer(renderer);
        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("form");
        form.getChildren().add(htmlOutputLink);
    }

    public void testRender() throws Exception {
        htmlOutputLink.setId("aaa");
        htmlOutputLink.setValue("a");
        encodeByRenderer(renderer, getFacesContext(), htmlOutputLink);

        assertEquals("<a id=\"aaa\" href=\"a\"></a>", getResponseText());
    }

    public void testRender2() throws Exception {
        htmlOutputLink.setId("aaa");
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = format.parse("2007/02/21");

        UIParameter param = new UIParameter();
        param.setName("date");
        param.setValue(date);
        htmlOutputLink.getChildren().add(param);

        htmlOutputLink.setValue("a");

        encodeByRenderer(renderer, getFacesContext(), htmlOutputLink);
        System.out.println(getResponseText());

        assertEquals("<a id=\"aaa\" href=\"a?date=2007%2F02%2F21\"></a>",
                getResponseText());
    }

    public void testRenderWithSuffixButNoSuffixSet() throws Exception {
        renderer.setHtmlSuffix(new HtmlSuffixImpl());
        htmlOutputLink.setId("aaa");
        htmlOutputLink.setValue("a");
        encodeByRenderer(renderer, getFacesContext(), htmlOutputLink);

        assertEquals("<a id=\"aaa\" href=\"a\"></a>", getResponseText());
    }

    public void testRenderWithSuffix() throws Exception {
        try {
            MockFacesContext facesContext = getFacesContext();
            facesContext.getViewRoot().setViewId("/view/hoge/foo.html");
            renderer.setHtmlSuffix(new HtmlSuffixImpl() {

                public String getSuffix(FacesContext context) {
                    return "_i";
                }

            });
            htmlOutputLink.setId("aaa");
            htmlOutputLink.setValue(ResourceUtil.convertPath("a.html",
                    getClass()));
            encodeByRenderer(renderer, facesContext, htmlOutputLink);

            assertEquals(
                    "<a id=\"aaa\" href=\"/org/seasar/teeda/extension/render/html/a_i.html\"></a>",
                    getResponseText());
        } finally {
            renderer.setHtmlSuffix(null);
        }
    }

    public void testGetSuffixedBase1() throws Exception {
        try {
            MockFacesContext facesContext = getFacesContext();
            facesContext.getViewRoot().setViewId("/view/hoge/foo.html");
            renderer.setHtmlSuffix(new HtmlSuffixImpl() {

                public String getSuffix(FacesContext context) {
                    return "_i";
                }

            });
            String suffixedBase = renderer.getSuffixedBase(facesContext,
                    "hoge.html");
            assertEquals("hoge.html", suffixedBase);
        } finally {
            renderer.setHtmlSuffix(null);
        }
    }

    public void testGetSuffixedBase2() throws Exception {
        MockFacesContext facesContext = getFacesContext();
        facesContext.getViewRoot().setViewId("/view/hoge/foo.html");
        String suffixedBase = renderer.getSuffixedBase(facesContext, "hoge");
        assertEquals("hoge", suffixedBase);
    }

    public void testGetSuffixedBase3() throws Exception {
        try {
            MockFacesContext facesContext = getFacesContext();
            facesContext.getViewRoot().setViewId("/view/hoge/foo.html");
            renderer.setHtmlSuffix(new HtmlSuffixImpl() {

                public String getSuffix(FacesContext context) {
                    return "_i";
                }

            });
            String suffixedBase = renderer.getSuffixedBase(facesContext,
                    "/view/hoge/hoge.html");
            assertEquals("/view/hoge/hoge_i.html", suffixedBase);
        } finally {
            renderer.setHtmlSuffix(null);
        }
    }

    public void testGetSuffixedBase4() throws Exception {
        try {
            renderer.setHtmlSuffix(new HtmlSuffixImpl() {

                public String getSuffix(FacesContext context) {
                    return "_i";
                }

            });
            MockFacesContext facesContext = getFacesContext();
            facesContext.getViewRoot().setViewId("/view/hoge/fooo.html");
            String suffixedBase = renderer.getSuffixedBase(facesContext,
                    "./hoge.html");
            System.out.println(suffixedBase);
            assertEquals("/view/hoge/hoge_i.html", suffixedBase);
        } finally {
            renderer.setHtmlSuffix(null);
        }
    }

    public void testGetSuffixedBase5() throws Exception {
        try {
            renderer.setHtmlSuffix(new HtmlSuffixImpl() {

                public String getSuffix(FacesContext context) {
                    return "_i";
                }

            });
            MockFacesContext facesContext = getFacesContext();
            facesContext.getViewRoot().setViewId("/view/hoge/foo.html");
            String suffixedBase = renderer.getSuffixedBase(facesContext,
                    "../bar/bar.html");
            System.out.println(suffixedBase);
            assertEquals("/view/bar/bar_i.html", suffixedBase);
        } finally {
            renderer.setHtmlSuffix(null);
        }
    }

    public void testGetSuffixedBase6() throws Exception {
        try {
            renderer.setHtmlSuffix(new HtmlSuffixImpl() {

                public String getSuffix(FacesContext context) {
                    return "_i";
                }

            });
            MockFacesContext facesContext = getFacesContext();
            facesContext.getViewRoot().setViewId("/view/hoge/foo.html");
            String suffixedBase = renderer.getSuffixedBase(facesContext,
                    "../../bar.html");
            System.out.println(suffixedBase);
            assertEquals("/view/hoge/bar_i.html", suffixedBase);
        } finally {
            renderer.setHtmlSuffix(null);
        }
    }

    private THtmlOutputLinkRenderer createTHtmlOutputLinkRenderer() {
        return (THtmlOutputLinkRenderer) createRenderer();
    }

    protected Renderer createRenderer() {
        THtmlOutputLinkRenderer renderer = new THtmlOutputLinkRenderer();
        renderer.setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        return renderer;
    }

}
