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

import java.util.Map;

import javax.faces.component.UIParameter;
import javax.faces.render.Renderer;
import javax.faces.render.RendererTest;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockHtmlForm;
import org.seasar.teeda.core.mock.MockHtmlOutputLink;
import org.seasar.teeda.core.render.html.HtmlFormRenderer;

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

    public void testDecode() throws Exception {
        THtmlOutputLinkRenderer renderer = new THtmlOutputLinkRenderer();
        MockFacesContext context = getFacesContext();
        MockHtmlOutputLink component = new MockHtmlOutputLink();
        {
            UIParameter child = new UIParameter();
            child.setName("aaa");
            component.getChildren().add(child);
        }
        {
            UIParameter child = new UIParameter();
            child.setName("bbb");
            component.getChildren().add(child);
        }
        MockHtmlForm form = new MockHtmlForm();
        form.setRenderer(new HtmlFormRenderer());
        form.setId("form");
        form.getChildren().add(component);
        Map map = context.getExternalContext().getRequestParameterMap();
        map.put("form:aaa", "AAA");
        map.put("form:bbb", "BBB");
        component.setValue("../ccc/ddd.html");
        renderer.decode(context, component);
        assertTrue(component.getChildCount() > 0);
        {
            UIParameter c = (UIParameter) component.getChildren().get(0);
            assertEquals("AAA", c.getValue());
        }
        {
            UIParameter c = (UIParameter) component.getChildren().get(1);
            assertEquals("BBB", c.getValue());
        }
    }

    public void testDecodeAndEncode() throws Exception {
        MockFacesContext context = getFacesContext();
        {
            UIParameter child = new UIParameter();
            child.setName("aaa");
            htmlOutputLink.getChildren().add(child);
        }
        {
            UIParameter child = new UIParameter();
            child.setName("bbb");
            htmlOutputLink.getChildren().add(child);
        }
        Map map = context.getExternalContext().getRequestParameterMap();
        map.put("form:aaa", "AAA");
        map.put("form:bbb", "BBB");
        htmlOutputLink.setValue("../ccc/ddd.html");
        renderer.decode(context, htmlOutputLink);
        assertTrue(htmlOutputLink.getChildCount() > 0);
        {
            UIParameter c = (UIParameter) htmlOutputLink.getChildren().get(0);
            assertEquals("AAA", c.getValue());
        }
        {
            UIParameter c = (UIParameter) htmlOutputLink.getChildren().get(1);
            assertEquals("BBB", c.getValue());
        }

        encodeByRenderer(renderer, htmlOutputLink);

        System.out.println(getResponseText());
        assertEquals("<a href=\"../ccc/ddd.html?aaa=AAA&amp;bbb=BBB\"></a>",
                getResponseText());

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
