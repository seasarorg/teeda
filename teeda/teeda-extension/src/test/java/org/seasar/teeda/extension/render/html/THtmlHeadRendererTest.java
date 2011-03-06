/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.AbstractRendererTest;
import javax.faces.render.Renderer;

import org.seasar.teeda.extension.component.html.THtmlHead;
import org.seasar.teeda.extension.mock.MockUITitle;
import org.seasar.teeda.extension.render.TTitleRenderer;
import org.seasar.teeda.extension.util.VirtualResource;

/**
 * @author higa
 * @author shot
 */
public class THtmlHeadRendererTest extends AbstractRendererTest {

    private THtmlHeadRenderer renderer;

    private THtmlHead component;

    private MockUITitle title;

    public void setUp() throws Exception {
        super.setUp();
        renderer = new THtmlHeadRenderer();
        component = new THtmlHead();
        title = new MockUITitle();
        title.setTemplateValue("タイトル");
        title.setRenderer(new TTitleRenderer());
        title.setParent(component);
        component.getChildren().add(title);
    }

    protected void encodeByRenderer(Renderer renderer, FacesContext context,
            UIComponent component) throws IOException {
        renderer.encodeBegin(context, component);
        super.encodeByRenderer(title.getRenderer(context), context, title);
        renderer.encodeEnd(context, component);
    }

    public void testEncode_jsResource() throws Exception {
        VirtualResource.addJsResource(getFacesContext(), "hoge.js");
        encodeByRenderer(renderer, component);
        System.out.println(getResponseText());
        assertEquals(
                "<head>\n<title>タイトル</title><script language=\"JavaScript\" type=\"text/javascript\" src=\"/mock-context/teedaExtension/hoge.js\"></script>\n</head>\n",
                getResponseText());
    }

    public void testEncode_jsResource_startsWithReference() throws Exception {
        VirtualResource.addJsResource(getFacesContext(), "../hoge.js");
        encodeByRenderer(renderer, component);
        System.out.println(getResponseText());
        assertEquals(
                "<head>\n<title>タイトル</title><script language=\"JavaScript\" type=\"text/javascript\" src=\"../hoge.js\"></script>\n</head>\n",
                getResponseText());
    }

    public void testEncode_jsResource_startsWithReference2() throws Exception {
        VirtualResource.addJsResource(getFacesContext(), "./hoge.js");
        encodeByRenderer(renderer, component);
        System.out.println(getResponseText());
        assertEquals(
                "<head>\n<title>タイトル</title><script language=\"JavaScript\" type=\"text/javascript\" src=\"./hoge.js\"></script>\n</head>\n",
                getResponseText());
    }

    public void testEncode_inlineJsResource() throws Exception {
        VirtualResource.addInlineJsResource(getFacesContext(), "aaa", "hoge");
        encodeByRenderer(renderer, component);
        System.out.println(getResponseText());
        assertEquals(
                "<head>\n<title>タイトル</title>\n<script language=\"JavaScript\" type=\"text/javascript\">\n<!--\nhoge\n//-->\n</script>\n</head>\n",
                getResponseText());
    }

    public void testEncode_inlineCssResource() throws Exception {
        VirtualResource.addInlineCssResource(getFacesContext(), "aaa",
                "<style></style>\n");
        encodeByRenderer(renderer, component);
        System.out.println(getResponseText());
        assertEquals("<head>\n<title>タイトル</title><style></style>\n</head>\n",
                getResponseText());
    }

    public void testEncode_cssResource() throws Exception {
        VirtualResource.addCssResource(getFacesContext(), "aaa");
        encodeByRenderer(renderer, component);
        System.out.println(getResponseText());
        assertEquals(
                "<head>\n<title>タイトル</title><link type=\"text/css\" rel=\"stylesheet\" href=\"/mock-context/teedaExtension/aaa\" />\n</head>\n",
                getResponseText());
    }

    public void testEncode_cssResource_startWithReference() throws Exception {
        VirtualResource.addCssResource(getFacesContext(), "../aaa");
        encodeByRenderer(renderer, component);
        System.out.println(getResponseText());
        assertEquals(
                "<head>\n<title>タイトル</title><link type=\"text/css\" rel=\"stylesheet\" href=\"../aaa\" />\n</head>\n",
                getResponseText());
    }

    public void testEncode_cssResource_startWithReference2() throws Exception {
        VirtualResource.addCssResource(getFacesContext(), "./aaa");
        encodeByRenderer(renderer, component);
        System.out.println(getResponseText());
        assertEquals(
                "<head>\n<title>タイトル</title><link type=\"text/css\" rel=\"stylesheet\" href=\"./aaa\" />\n</head>\n",
                getResponseText());
    }

}