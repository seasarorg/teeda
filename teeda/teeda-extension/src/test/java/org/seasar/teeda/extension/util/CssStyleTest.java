package org.seasar.teeda.extension.util;

import junit.framework.TestCase;

import org.seasar.teeda.core.JsfConstants;

public class CssStyleTest extends TestCase {

    private CssStyle cssStyle = new CssStyle();

    public void testStartStyle() throws Exception {
        cssStyle.startStyle();
        assertEquals("<style>" + JsfConstants.LINE_SP, cssStyle.getString());
    }

    public void testEndStyle() throws Exception {
        cssStyle.endStyle();
        assertEquals("</style>" + JsfConstants.LINE_SP, cssStyle.getString());
    }

    public void testStartSelector() throws Exception {
        cssStyle.startSelector(".aaa");
        assertEquals(".aaa {" + JsfConstants.LINE_SP, cssStyle.getString());
    }

    public void testEndSelector() throws Exception {
        cssStyle.endSelector();
        assertEquals("}" + JsfConstants.LINE_SP, cssStyle.getString());
    }

    public void testAddProperty() throws Exception {
        cssStyle.addProperty("overflow", "hidden");
        assertEquals("  overflow : hidden;" + JsfConstants.LINE_SP, cssStyle
                .getString());
    }
}
