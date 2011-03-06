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
package javax.faces.render;

import java.io.IOException;
import java.io.StringReader;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.seasar.framework.util.StringUtil;
import org.seasar.teeda.core.context.html.HtmlResponseWriter;
import org.seasar.teeda.core.mock.MockApplication;
import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.MockFacesContextImpl;
import org.seasar.teeda.core.mock.MockVariableResolver;
import org.seasar.teeda.core.render.ComponentIdLookupStrategy;
import org.seasar.teeda.core.render.DefaultComponentIdLookupStrategy;
import org.seasar.teeda.core.unit.xmlunit.DifferenceListenerChain;
import org.seasar.teeda.core.unit.xmlunit.HtmlDomUtil;
import org.seasar.teeda.core.unit.xmlunit.IgnoreJsessionidDifferenceListener;
import org.seasar.teeda.core.unit.xmlunit.RegexpDifferenceListener;
import org.seasar.teeda.core.unit.xmlunit.TextTrimmingDifferenceListener;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author manhole
 */
public abstract class AbstractRendererTest extends TestCase {

    protected MockFacesContext mockFacesContext_;

    protected ComponentIdLookupStrategy idLookupStrategy_;

    protected void setUp() throws Exception {
        super.setUp();
        mockFacesContext_ = new MockFacesContextImpl();
        idLookupStrategy_ = new DefaultComponentIdLookupStrategy();
    }

    protected void tearDown() throws Exception {
        mockFacesContext_.release();
        idLookupStrategy_ = null;
        super.tearDown();
    }

    protected MockFacesContext getFacesContext() {
        return mockFacesContext_;
    }

    protected MockApplication getApplication() {
        return (MockApplication) mockFacesContext_.getApplication();
    }

    protected MockVariableResolver getVariableResolver() {
        return (MockVariableResolver) getApplication().getVariableResolver();
    }

    protected ComponentIdLookupStrategy getComponentIdLookupStrategy() {
        return idLookupStrategy_;
    }

    protected String getResponseText() throws IOException {
        HtmlResponseWriter htmlResponseWriter = ((HtmlResponseWriter) mockFacesContext_
                .getResponseWriter());
        return htmlResponseWriter.getWriter().toString();
    }

    protected void encodeByRenderer(Renderer renderer, UIComponent component)
            throws IOException {
        encodeByRenderer(renderer, getFacesContext(), component);
    }

    protected void encodeByRenderer(Renderer renderer, FacesContext context,
            UIComponent component) throws IOException {
        renderer.encodeBegin(context, component);
        if (renderer.getRendersChildren()) {
            renderer.encodeChildren(context, component);
        }
        renderer.encodeEnd(context, component);
    }

    protected String extract(final String s) {
        final String BEGIN = "<!-- BEGIN -->";
        final String END = "<!-- END -->";
        final int begin = s.indexOf(BEGIN);
        if (-1 < begin) {
            final int end = s.indexOf(END, begin + BEGIN.length());
            if (-1 < end) {
                return s.substring(begin + BEGIN.length(), end);
            }
        }
        return s;
    }

    protected Diff diff(final String expected, final String actual)
            throws SAXException, IOException, ParserConfigurationException {
        Document cDoc = XMLUnit.buildDocument(XMLUnit.getControlParser(),
                new StringReader(revertEntity(expected)));
        Document tDoc = XMLUnit.buildDocument(XMLUnit.getTestParser(),
                new StringReader(revertEntity(actual)));
        HtmlDomUtil.removeBlankTextNode(cDoc.getChildNodes());
        HtmlDomUtil.removeBlankTextNode(tDoc.getChildNodes());
        Diff diff = new Diff(cDoc, tDoc);
        DifferenceListenerChain chain = new DifferenceListenerChain();
        chain.addDifferenceListener(new TextTrimmingDifferenceListener());
        chain.addDifferenceListener(new IgnoreJsessionidDifferenceListener());
        chain.addDifferenceListener(new RegexpDifferenceListener());
        diff.overrideDifferenceListener(chain);
        return diff;
    }

    private String revertEntity(String s) {
        s = StringUtil.replace(s, "&nbsp;", " ");
        return s;
    }

    protected String afterText(final String s1, final String s2) {
        final int pos = s1.indexOf(s2);
        if (pos == -1) {
            return s1;
        }
        return s1.substring(pos + s2.length()).trim();
    }

}
