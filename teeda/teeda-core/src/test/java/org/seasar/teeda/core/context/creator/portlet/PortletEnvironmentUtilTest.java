package org.seasar.teeda.core.context.creator.portlet;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import junit.framework.TestCase;

import org.seasar.teeda.core.mock.MockPortletContextImpl;
import org.seasar.teeda.core.mock.MockPortletRequestImpl;
import org.seasar.teeda.core.mock.MockPortletResponseImpl;

public class PortletEnvironmentUtilTest extends TestCase {

    public void testIsServletEnvironment() throws Exception {
        String str = "a";
        PortletContext context = new MockPortletContextImpl();
        PortletRequest request = new MockPortletRequestImpl();
        PortletResponse response = new MockPortletResponseImpl(); 
        assertFalse(PortletEnvironmentUtil.isPortletEnvironment(str, str, str));
        assertFalse(PortletEnvironmentUtil.isPortletEnvironment(context, str, str));
        assertFalse(PortletEnvironmentUtil.isPortletEnvironment(context, request, str));
        assertTrue(PortletEnvironmentUtil.isPortletEnvironment(context, request, response));
    }

}
