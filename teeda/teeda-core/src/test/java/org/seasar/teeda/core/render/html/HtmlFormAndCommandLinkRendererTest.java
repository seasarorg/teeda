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

import java.io.IOException;

import javax.faces.component.UIParameter;
import javax.faces.render.AbstractRendererTest;
import javax.xml.parsers.ParserConfigurationException;

import org.custommonkey.xmlunit.Diff;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockHtmlCommandLink;
import org.seasar.teeda.core.mock.MockHtmlForm;
import org.seasar.teeda.core.unit.TestUtil;
import org.xml.sax.SAXException;

/**
 * @author manhole
 */
public class HtmlFormAndCommandLinkRendererTest extends AbstractRendererTest {

    private HtmlFormRenderer formRenderer;

    private HtmlCommandLinkRenderer commandLinkRenderer;

    private MockHtmlForm htmlForm;

    protected void setUp() throws Exception {
        super.setUp();
        formRenderer = new HtmlFormRenderer();
        formRenderer
                .setComponentIdLookupStrategy(getComponentIdLookupStrategy());
        htmlForm = new MockHtmlForm();
        htmlForm.setRenderer(formRenderer);
        htmlForm.setEnctype(null);

        commandLinkRenderer = new HtmlCommandLinkRenderer();
        commandLinkRenderer
                .setComponentIdLookupStrategy(getComponentIdLookupStrategy());

        // MockHtmlFormのプロパティ
        formRenderer.addIgnoreAttributeName("setSubmittedCalls");
    }

    public void testEncode_WithCommandLink() throws Exception {
        // ## Arrange ##
        MockHtmlCommandLink htmlCommandLink = new MockHtmlCommandLink();
        htmlCommandLink.setRenderer(commandLinkRenderer);
        htmlCommandLink.setId("fooLink");

        htmlForm.setId("fooForm");
        htmlForm.getChildren().add(htmlCommandLink);

        MockFacesContext context = getFacesContext();
        context.getViewRoot().setViewId("/aa");

        // ## Act ##
        formRenderer.encodeBegin(context, htmlForm);
        formRenderer.encodeChildren(context, htmlForm);
        formRenderer.encodeEnd(context, htmlForm);

        // ## Assert ##
        System.out.println(getResponseText());
        final String readText = TestUtil.readText(getClass(),
                "testEncode_WithCommandLink.html", "UTF-8");
        final Diff diff = diff(readText, getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testEncode_WithParameter() throws Exception {
        // ## Arrange ##
        MockFacesContext context = getFacesContext();
        context.getViewRoot().setViewId("/aaaViewId");
        htmlForm.setId("fooForm");

        MockHtmlCommandLink commandLink = new MockHtmlCommandLink();
        commandLink.setRenderer(commandLinkRenderer);
        {
            UIParameter param = new UIParameter();
            param.setName("a");
            param.setValue("1");
            commandLink.getChildren().add(param);

            htmlForm.getChildren().add(commandLink);
        }

        // ## Act ##
        htmlForm.encodeBegin(context);
        commandLink.encodeBegin(context);
        commandLink.encodeChildren(context);
        commandLink.encodeEnd(context);
        htmlForm.encodeEnd(context);

        System.out.println(getResponseText());
        // ## Assert ##
        final String readText = TestUtil.readText(getClass(),
                "testEncode_WithParameter.html", "UTF-8");
        final Diff diff = diff(readText, getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    public void testEncode_WithParameters() throws Exception {
        // ## Arrange ##
        MockHtmlCommandLink htmlCommandLink = new MockHtmlCommandLink();
        htmlCommandLink.setRenderer(commandLinkRenderer);
        htmlCommandLink.setId("fooLink");

        htmlForm.setId("barForm");
        htmlForm.getChildren().add(htmlCommandLink);
        {
            UIParameter param = new UIParameter();
            param.setName("x");
            param.setValue("1");
            htmlCommandLink.getChildren().add(param);
        }
        {
            UIParameter param = new UIParameter();
            param.setName("y");
            param.setValue("2");
            htmlCommandLink.getChildren().add(param);
        }

        MockFacesContext context = getFacesContext();
        context.getViewRoot().setViewId("/abc");

        // ## Act ##
        formRenderer.encodeBegin(context, htmlForm);
        formRenderer.encodeChildren(context, htmlForm);
        formRenderer.encodeEnd(context, htmlForm);

        // ## Assert ##
        System.out.println(getResponseText());
        final String readText = TestUtil.readText(getClass(),
                "testEncode_WithParameters.html", "UTF-8");
        final Diff diff = diff(readText, getResponseText());
        assertEquals(diff.toString(), true, diff.identical());
    }

    protected Diff diff(final String expected, final String actual)
            throws SAXException, IOException, ParserConfigurationException {
        return super.diff("<dummy>" + expected + "</dummy>", "<dummy>" + actual
                + "</dummy>");
    }

}
