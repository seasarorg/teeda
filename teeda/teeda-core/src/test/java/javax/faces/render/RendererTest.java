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
package javax.faces.render;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.seasar.teeda.core.mock.NullFacesContext;
import org.seasar.teeda.core.mock.NullRenderer;
import org.seasar.teeda.core.mock.NullUIComponent;
import org.seasar.teeda.core.unit.ExceptionAssert;
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
public class RendererTest extends AbstractRendererTest {

    public final void testConvertClientId_FacesContextIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.convertClientId(null, "fooClientId");
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testConvertClientId_ClientIdIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.convertClientId(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testDecode_FacesContextIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.decode(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testDecode_UIComponentIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.decode(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testEncodeBegin_FacesContextIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.encodeBegin(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testEncodeBegin_UIComponentIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.encodeBegin(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testEncodeChildren_FacesContextIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.encodeChildren(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testEncodeChildren_UIComponentIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.encodeChildren(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testEncodeEnd_FacesContextIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.encodeEnd(null, new NullUIComponent());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testEncodeEnd_UIComponentIsNull() throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.encodeEnd(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testGetConvertedValue_FacesContextIsNull()
            throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.getConvertedValue(null, new NullUIComponent(),
                    new Object());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    public final void testGetConvertedValue_UIComponentIsNull()
            throws Exception {
        Renderer renderer = createRenderer();
        try {
            renderer.getConvertedValue(new NullFacesContext(), null,
                    new Object());
            fail();
        } catch (NullPointerException npe) {
            ExceptionAssert.assertMessageExist(npe);
        }
    }

    protected Renderer createRenderer() {
        return new NullRenderer();
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
                new StringReader(expected));
        Document tDoc = XMLUnit.buildDocument(XMLUnit.getTestParser(),
                new StringReader(actual));
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

}
