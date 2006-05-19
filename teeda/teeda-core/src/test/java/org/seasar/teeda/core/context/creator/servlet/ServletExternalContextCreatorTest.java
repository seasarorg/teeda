package org.seasar.teeda.core.context.creator.servlet;

import javax.faces.context.ExternalContext;

import org.seasar.teeda.core.unit.TeedaTestCase;

public class ServletExternalContextCreatorTest extends TeedaTestCase {

    public void testCreate() throws Exception {
        ServletExternalContextCreator creator = new ServletExternalContextCreator();
        ExternalContext ctx = creator.create(getServletContext(), getRequest(),
                getResponse());
        assertNotNull(ctx);
    }
}
