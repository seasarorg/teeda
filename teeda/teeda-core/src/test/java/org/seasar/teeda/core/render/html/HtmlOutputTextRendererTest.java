package org.seasar.teeda.core.render.html;

import javax.faces.component.html.HtmlOutputText;

import org.seasar.teeda.core.mock.MockFacesContext;
import org.seasar.teeda.core.mock.NullFacesContext;
import org.seasar.teeda.core.mock.NullUIComponent;
import org.seasar.teeda.core.unit.TeedaTestCase;

public class HtmlOutputTextRendererTest extends TeedaTestCase {

    public void testEncodeEnd() throws Exception {
        HtmlOutputTextRenderer renderer = new HtmlOutputTextRenderer();
        MockFacesContext context = getFacesContext();
        // TODO
        HtmlOutputText htmlOutputText = new HtmlOutputText();
        renderer.encodeEnd(context, htmlOutputText);
    }

    public void testEncodeEnd_null1() throws Exception {
        HtmlOutputTextRenderer renderer = new HtmlOutputTextRenderer();
        try {
            renderer.encodeEnd(null, new NullUIComponent());
            fail();
        } catch (NullPointerException expected) {
        }
    }

    public void testEncodeEnd_null2() throws Exception {
        HtmlOutputTextRenderer renderer = new HtmlOutputTextRenderer();
        try {
            renderer.encodeEnd(new NullFacesContext(), null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

}
